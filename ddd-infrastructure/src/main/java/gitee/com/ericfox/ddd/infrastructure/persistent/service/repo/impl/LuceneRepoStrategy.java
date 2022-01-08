package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import cn.hutool.core.map.MapUtil;
import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.strategy.LuceneFieldKey;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.LuceneFieldTypeEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectRepoException;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.LuceneBaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.SystemException;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("luceneRepoStrategy")
@Slf4j
public class LuceneRepoStrategy implements RepoStrategy {
    @Resource
    CustomProperties customProperties;
    @Resource
    private IndexWriter indexWriter;

    private final Map<String, IndexSearcher> indexSearcherMap = MapUtil.newHashMap(4);

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> T findById(T t) {
        Document document = findDocumentById(t);
        if (document == null) {
            return null;
        }
        return parseClassToPo(document, (Class<T>) t.getClass());
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends BaseDao<T>> Document findDocumentById(T t) {
        IndexSearcher indexSearcher = getIndexSearcher((Class<T>) t.getClass());
        Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
        U dao = ReflectUtil.newInstance(daoClass);
        String idFieldName = dao.primaryKeyFieldName();
        Query query = QuickQuery.primaryKeyExactQuery(idFieldName, BeanUtil.getProperty(t, idFieldName));
        TopDocs topDocs = indexSearcher.search(query, 1);
        if (topDocs.totalHits.value == 0) {
            return null;
        }
        ScoreDoc scoreDoc = topDocs.scoreDocs[0];
        return indexSearcher.doc(scoreDoc.doc);
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean deleteById(T t) {
        if (t == null || t.getId() == null) {
            return true;
        }
        try {
            Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
            U dao = ReflectUtil.newInstance(daoClass);
            String idFieldName = dao.primaryKeyFieldName();
            Query query = QuickQuery.primaryKeyExactQuery(idFieldName, BeanUtil.getProperty(t, idFieldName));
            indexWriter.deleteDocuments(query);
            indexWriter.commit();
            return true;
        } catch (Exception e) {
            log.error("lucene删除文档失败");
            throw new ProjectRepoException("", e);
        }
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>> T insert(T t) {
        if (multiInsert(CollUtil.newArrayList(t))) {
            return t;
        }
        return null;
    }

    @SafeVarargs
    @Override
    public final <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(T... list) {
        return multiInsert(CollUtil.newArrayList(list));
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean multiInsert(List<T> list) {
        if (CollUtil.isEmpty(list)) {
            return true;
        }
        try {
            List<U> daoList = CollUtil.newArrayList();
            Class<U> daoClass = ClassUtil.getDaoClassByPo(list.get(0), this);
            for (T t : list) {
                U dao = BeanUtil.toBean(t, daoClass);
            }
            List<Document> docs = buildDocumentList(daoList);
            indexWriter.addDocuments(docs);
            indexWriter.commit();
            return true;
        } catch (Exception e) {
            log.error("lucene创建文档失败", e);
            throw new ProjectRepoException("lucene multiInsert异常", e);
        }
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> boolean update(T t) {

        return false;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize) {
        return queryPage(t, pageNum, pageSize, new MatchAllDocsQuery(), Sort.INDEXORDER);
    }

    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>> PageInfo<T> queryPage(T t, Integer pageNum, Integer pageSize, Query query, Sort sort) {
        Class<T> clazz = (Class<T>) t.getClass();
        Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
        int start = (pageNum - 1) * pageSize;// 下标从 0 开始
        int end = pageNum * pageSize;

        IndexSearcher indexSearcher = getIndexSearcher(t.getClass());
        //默认查询 list 只返回 100 条数据
        TopDocs topDocs;
        if (null == sort) {
            topDocs = indexSearcher.search(query, end);
        } else {
            topDocs = indexSearcher.search(query, end, sort);
        }

        long totalNum = topDocs.totalHits.value;

        if (totalNum == 0) {
            // 没有数据
            return null;
        }

        //获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        if (null == scoreDocs) {
            return null;
        }

        // 遍历结果集
        List<T> resultList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            ScoreDoc scoreDoc;
            try {
                scoreDoc = scoreDocs[i];
            } catch (Exception e) {
                // 因为 i++ 一直会加下去，查询出来的结果数组长度可能不会超过 end 值，这时候会有数组下标越界的问题：ArrayIndexOutOfBoundsException
                break;
            }
            //获取查询到的文档唯一标识, 文档id, 这个id是lucene在创建文档的时候自动分配的
            int luceneDocumentId = scoreDoc.doc;
            //通过文档id, 读取文档
            Document document = indexSearcher.doc(luceneDocumentId);
            // Document document = indexReader.document(scoreDocs[i].doc);//另一种写法
            U u = ReflectUtil.newInstance(daoClass);
            parseClassToPo(document, u);
            resultList.add((T) u.toPo());
        }

        // 构建 page 对象
        int pages = (int) Math.ceil((double) totalNum / (double) pageSize);//总页数
        // Long pagesByLong = totalNum % pageSize > 0 ? (totalNum / pageSize) + 1 : totalNum / pageSize;
        // int pages = pagesByLong.intValue();// 另外一种计算总页数的方法

        PageInfo<T> pageInfo = new PageInfo();
        pageInfo.setPages(pages);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setStartRow(start);
        pageInfo.setEndRow(end);
        pageInfo.setTotal(totalNum);
        pageInfo.setSize(end - start);
        pageInfo.setList(resultList);
        return pageInfo;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>> List<T> queryList(T t, int limit) {
        return null;
    }

    private String buildDirectoryPath(String rootPathName) {
        String luceneLocalIndexDirectoryPath = customProperties.getLucene().getRootPath();
        if (StrUtil.endWith(luceneLocalIndexDirectoryPath, File.separator)) {
            return luceneLocalIndexDirectoryPath + rootPathName;
        }

        return luceneLocalIndexDirectoryPath + File.separator + rootPathName;
    }

    private <T extends BasePo<T>, U extends BaseDao<T>> T parseClassToPo(Document document, U t) {
        return parseClassToPo(document, (Class<T>) t.toPo().getClass());
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends BaseDao<T>> T parseClassToPo(Document document, Class<T> clazz) {
        Field[] superFields = null;
        T t = ReflectUtil.newInstance(clazz);
        Class<LuceneBaseDao<T>> daoClass = ClassUtil.getDaoClassByPo(t, this);
        if (null != daoClass) {
            superFields = daoClass.getDeclaredFields();
        }
        Field[] declaredFields = clazz.getDeclaredFields();

        Field[] fields;
        if (null != superFields) {
            fields = ArrayUtil.addAll(superFields, declaredFields);
        } else {
            fields = declaredFields;
        }

        if (fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);// 私有属性必须设置访问权限
                if (!field.isAnnotationPresent(LuceneFieldKey.class)) {
                    continue;
                }
                LuceneFieldKey fieldKey = field.getAnnotation(LuceneFieldKey.class);
                if (null == fieldKey) {
                    continue;
                }
                String objectValue = document.get(field.getName());
                if (StrUtil.isBlank(objectValue)) {
                    continue;
                }
                LuceneFieldTypeEnum fieldTypeEnum = fieldKey.type();
                switch (fieldTypeEnum) {
                    case STRING_FIELD:
                        ReflectUtil.setFieldValue(t, field.getName(), objectValue);
                        break;
                    case TEXT_FIELD:
                        ReflectUtil.setFieldValue(t, field.getName(), objectValue);
                        break;
                    case INT_POINT:
                        ReflectUtil.setFieldValue(t, field.getName(), Integer.valueOf(objectValue));
                        break;
                    case LONG_POINT:
                        ReflectUtil.setFieldValue(t, field.getName(), Long.valueOf(objectValue));
                        break;
                    case DOUBLE_POINT:
                        ReflectUtil.setFieldValue(t, field.getName(), Double.valueOf(objectValue));
                        break;
                    default:
                        throw new SystemException("不支持其他类型，请重新配置搜索类型");
                }
            }
        }
        return t;
    }

    private <T extends BasePo<T>, U extends BaseDao<T>> List<Document> buildDocumentList(List<U> pojoList) {
        List<Document> docList = new ArrayList<>();
        if (CollUtil.isEmpty(pojoList)) {
            return docList;
        }

        for (U pojo : pojoList) {
            Document document = parseClassToDocument(pojo);
            docList.add(document);
        }

        return docList;
    }

    private <T extends BasePo<T>, U extends BaseDao<T>> Document parseClassToDocument(T t) {
        Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
        U dao = ReflectUtil.newInstance(daoClass);
        return parseClassToDocument(dao);
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends BaseDao<T>> Document parseClassToDocument(U t) {
        Document document = new Document();
        Class<U> clazz = (Class<U>) t.getClass();
        Class<? super U> superClazz = clazz.getSuperclass();
        Field[] superFields = null;
        if (null != superClazz) {
            superFields = superClazz.getDeclaredFields();
        }
        Field[] declaredFields = clazz.getDeclaredFields();

        Field[] fields;
        if (null != superFields) {
            fields = ArrayUtil.addAll(superFields, declaredFields);
        } else {
            fields = declaredFields;
        }

        if (fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);// 私有属性必须设置访问权限
                if (!field.isAnnotationPresent(LuceneFieldKey.class)) {
                    continue;
                }
                LuceneFieldKey fieldKey = field.getAnnotation(LuceneFieldKey.class);
                if (null == fieldKey) {
                    continue;
                }
                Object objectValue = field.get(t);
                if (null == objectValue) {
                    continue;
                }
                LuceneFieldTypeEnum fieldTypeEnum = fieldKey.type();
                boolean needSort = fieldKey.needSort();
                if (LuceneFieldTypeEnum.STRING_FIELD.equals(fieldTypeEnum)) {
                    document.add(new StringField(field.getName(), (String) objectValue, org.apache.lucene.document.Field.Store.YES));
                } else if (LuceneFieldTypeEnum.TEXT_FIELD.equals(fieldTypeEnum)) {
                    document.add(new TextField(field.getName(), (String) objectValue, org.apache.lucene.document.Field.Store.YES));
                } else if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                    Integer integerValue = (Integer) objectValue;
                    document.add(new IntPoint(field.getName(), integerValue));
                    document.add(new StoredField(field.getName(), integerValue));
                    if (needSort) {
                        document.add(new NumericDocValuesField(field.getName(), integerValue));
                    }
                } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                    Long longValue = (Long) objectValue;
                    document.add(new LongPoint(field.getName(), longValue));
                    document.add(new StoredField(field.getName(), longValue));
                    if (needSort) {
                        document.add(new NumericDocValuesField(field.getName(), longValue));
                    }
                } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                    Double doubleValue = (Double) objectValue;
                    document.add(new DoublePoint(field.getName(), doubleValue));
                    document.add(new StoredField(field.getName(), doubleValue));
                    if (needSort) {
                        document.add(new DoubleDocValuesField(field.getName(), doubleValue));
                    }
                } else {
                    throw new SystemException("不支持其他类型，请重新配置搜索类型");
                }
            }
        }
        return document;
    }

    @SneakyThrows
    private synchronized <T extends BasePo<T>> IndexSearcher getIndexSearcher(Class<T> clazz) {
        String className = clazz.getSimpleName();
        if (indexSearcherMap.containsKey(className)) {
            return indexSearcherMap.get(className);
        }
        String directoryPath = buildDirectoryPath(className);
        Directory directory = FSDirectory.open(Paths.get(directoryPath));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        indexSearcherMap.put(className, indexSearcher);
        return indexSearcher;
    }

    private static class QuickQuery {
        public static Builder builder() {
            return new Builder();
        }

        /**
         * 主键精确查询
         */
        public static Query primaryKeyExactQuery(String idFieldName, Object id) {
            Query query = null;
            if (id instanceof Long) {
                query = LongPoint.newExactQuery(idFieldName, (Long) id);
            } else if (id instanceof BytesRef) {
                new TermQuery(new Term(idFieldName, (BytesRef) id));
            } else if (id instanceof String) {
                query = new TermQuery(new Term(idFieldName, (String) id));
            } else if (id instanceof Double) {
                query = DoublePoint.newExactQuery(idFieldName, (Double) id);
            } else if (id instanceof Integer) {
                query = IntPoint.newExactQuery(idFieldName, (Integer) id);
            } else if (id instanceof byte[]) {
                query = BinaryPoint.newExactQuery(idFieldName, (byte[]) id);
            } else {
                throw new ProjectRepoException("lucene未定义的映射类型：" + id);
            }
            return query;
        }

        public static class Builder {
            //TODO 待实现
        }
    }
}
