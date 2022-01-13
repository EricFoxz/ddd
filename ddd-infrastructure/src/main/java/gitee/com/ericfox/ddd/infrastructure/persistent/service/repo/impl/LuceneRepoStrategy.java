package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import cn.hutool.core.map.MapUtil;
import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.LuceneFieldKey;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.LuceneFieldTypeEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectRepoException;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseCondition;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service("luceneRepoStrategy")
@Slf4j
@SuppressWarnings("unchecked")
public class LuceneRepoStrategy implements RepoStrategy {
    @Resource
    ServiceProperties serviceProperties;

    private final Map<String, IndexWriter> indexWriterMap = MapUtil.newHashMap(4);

    private final Map<String, IndexSearcher> indexSearcherMap = MapUtil.newHashMap(4);

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V findById(V v) {
        T t = v.toPo();
        Document document = findDocumentById(t);
        if (document == null) {
            return null;
        }
        return v.fromPo(parseToPo(document, (Class<T>) t.getClass()));
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Document findDocumentById(T t) {
        return findDocumentById(null, t);
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Document findDocumentById(U dao, T t) {
        if (t == null) {
            return null;
        }
        Class<U> daoClass = null;
        if (dao == null) {
            daoClass = ClassUtil.getDaoClassByPo(t, this);
            dao = ReflectUtil.newInstance(daoClass);
        }
        IndexSearcher indexSearcher = getIndexSearcher((Class<T>) t.getClass());
        String idFieldName = dao.primaryKeyFieldName();
        Query query = EasyQuery.exactValueQuery(daoClass, idFieldName, BeanUtil.getProperty(t, idFieldName));
        TopDocs topDocs = indexSearcher.search(query, 1);
        if (topDocs.totalHits.value == 0) {
            return null;
        }
        ScoreDoc scoreDoc = topDocs.scoreDocs[0];
        return indexSearcher.doc(scoreDoc.doc);
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean deleteById(V v) {
        if (v == null) {
            return true;
        }
        try {
            T t = v.toPo();
            Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
            U dao = ReflectUtil.newInstance(daoClass);
            String idFieldName = dao.primaryKeyFieldName();
            Serializable id = BeanUtil.getProperty(t, idFieldName);
            if (id == null) {
                return true;
            }
            Query query = EasyQuery.exactValueQuery(daoClass, idFieldName, id);
            IndexWriter indexWriter = getIndexWriter(t.getClass());
            synchronized (indexWriter) {
                //TODO-拓展 indexWriter目前这么写只能支持单实例，横向拓展会有问题，到时候需要close
                indexWriter.deleteDocuments(query);
                indexWriter.commit();
            }
            return true;
        } catch (Exception e) {
            log.error("lucene删除文档失败");
            throw new ProjectRepoException("", e);
        }
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(List<V> v) {
        if (CollUtil.isEmpty(v)) {
            return true;
        }
        try {
            T t = v.get(0).toPo();
            Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
            U dao = ReflectUtil.newInstance(daoClass);
            String idFieldName = dao.primaryKeyFieldName();
            List<Serializable> idList = CollUtil.newArrayList();
            for (V tmpV : v) {
                T po = tmpV.toPo();
                idList.add(BeanUtil.getProperty(po, idFieldName));
            }
            Query query = EasyQuery.exactMultiValueQuery(daoClass, idFieldName, idList);
            IndexWriter indexWriter = getIndexWriter(t.getClass());
            synchronized (indexWriter) {
                //TODO-拓展 indexWriter目前这么写只能支持单实例，横向拓展会有问题，到时候需要close
                indexWriter.deleteDocuments(query);
                indexWriter.commit();
            }
            return true;
        } catch (Exception e) {
            log.error("lucene删除文档失败");
            throw new ProjectRepoException("", e);
        }
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiDeleteById(V... v) {
        if (ArrayUtil.isEmpty(v)) {
            return true;
        }
        return multiDeleteById(CollUtil.newArrayList(v));
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> V insert(V v) {
        boolean b = multiInsert(CollUtil.newArrayList(v));
        if (b) {
            return v;
        }
        return null;
    }

    @SafeVarargs
    @Override
    public final <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(V... list) {
        return multiInsert(CollUtil.newArrayList(list));
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean multiInsert(List<V> v) {
        if (CollUtil.isEmpty(v)) {
            return true;
        }
        try {
            List<U> daoList = CollUtil.newArrayList();
            Class<U> daoClass = ClassUtil.getDaoClassByPo(v.get(0).toPo(), this);
            for (V tmpV : v) {
                U dao = BeanUtil.toBean(tmpV.toPo(), daoClass);
                daoList.add(dao);
            }
            List<Document> docs = buildDocumentList(daoList);
            IndexWriter indexWriter = getIndexWriter(v.get(0).toPo().getClass());
            synchronized (indexWriter) {
                //TODO-拓展 indexWriter目前这么写只能支持单实例，横向拓展会有问题，到时候需要close
                indexWriter.addDocuments(docs);
                indexWriter.commit();
            }
            return true;
        } catch (Exception e) {
            log.error("lucene创建文档失败", e);
            throw new ProjectRepoException("lucene multiInsert异常", e);
        }
    }

    @Override
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> boolean updateById(V v) {
        T t = v.toPo();
        Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
        U dao = ReflectUtil.newInstance(daoClass);
        String primaryKeyName = dao.primaryKeyFieldName();
        Document document = findDocumentById(dao, t);
        if (null == document) {
            return true;
        }
        IndexWriter indexWriter = getIndexWriter(t.getClass());
        synchronized (indexWriter) {
            //TODO-拓展 indexWriter目前这么写只能支持单实例，横向拓展会有问题，到时候需要close
            indexWriter.updateDocument(new Term(primaryKeyName, (String) BeanUtil.getProperty(t, primaryKeyName)), document);
            indexWriter.commit();
        }
        return false;
    }

    @Override
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> PageInfo<V> queryPage(V v, int pageNum, int pageSize) {
        Class<U> daoClass = ClassUtil.getDaoClassByPo(v.toPo(), this);
        Query query = EasyQuery.parseCondition(daoClass, v, true);
        return queryPage(v, pageNum, pageSize, query, Sort.INDEXORDER);
    }

    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> PageInfo<V> queryPage(V v, Integer pageNum, Integer pageSize, Query query, Sort sort) {
        T t = v.toPo();
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
        List<V> resultList = new ArrayList<>();
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
            T po = parseToPo(document, (Class<T>) t.getClass());
            V entity = (V) ReflectUtil.newInstance(v.getClass());
            resultList.add(entity.fromPo(po));
        }

        // 构建 page 对象
        int pages = (int) Math.ceil((double) totalNum / (double) pageSize);//总页数
        // Long pagesByLong = totalNum % pageSize > 0 ? (totalNum / pageSize) + 1 : totalNum / pageSize;
        // int pages = pagesByLong.intValue();// 另外一种计算总页数的方法

        PageInfo<V> pageInfo = new PageInfo<>();
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
    @SneakyThrows
    public <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> List<V> queryList(V v, int limit) {
        T t = v.toPo();
        Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
        IndexSearcher indexSearcher = getIndexSearcher((Class<T>) t.getClass());
        Query query = EasyQuery.exactMultiFieldQuery(daoClass, t, true);
        TopDocs topDocs = indexSearcher.search(query, limit);
        if (topDocs.totalHits.value == 0) {
            return CollUtil.newArrayList();
        }
        ScoreDoc[] scoreDoc = topDocs.scoreDocs;
        List<V> result = CollUtil.newArrayList();
        for (ScoreDoc doc : scoreDoc) {
            Document tmpDoc = indexSearcher.doc(doc.doc);
            V entity = (V) ReflectUtil.newInstance(v.getClass());
            result.add(entity.fromPo(parseToPo(tmpDoc, (Class<T>) t.getClass())));
        }
        return result;
    }

    private String buildDirectoryPath(String rootPathName) {
        String luceneLocalIndexDirectoryPath = serviceProperties.getRepoStrategy().getLucene().getRootPath();
        if (StrUtil.endWith(luceneLocalIndexDirectoryPath, File.separator)) {
            return luceneLocalIndexDirectoryPath + rootPathName;
        }
        return luceneLocalIndexDirectoryPath + File.separator + rootPathName;
    }

    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> T parseToPo(Document document, U t) {
        return parseToPo(document, ClassUtil.getClass((T) t.toPo()));
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> T parseToPo(Document document, Class<T> clazz) {
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
                if (LuceneFieldTypeEnum.STRING_FIELD.equals(fieldTypeEnum)) {
                    ReflectUtil.setFieldValue(t, field.getName(), objectValue);
                } else if (LuceneFieldTypeEnum.TEXT_FIELD.equals(fieldTypeEnum)) {
                    ReflectUtil.setFieldValue(t, field.getName(), objectValue);
                } else if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                    ReflectUtil.setFieldValue(t, field.getName(), Convert.toInt(objectValue));
                } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                    ReflectUtil.setFieldValue(t, field.getName(), Convert.toLong(objectValue));
                } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                    ReflectUtil.setFieldValue(t, field.getName(), Convert.toDouble(objectValue));
                } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                    Class<?> fieldType = ReflectUtil.getField(daoClass, field.getName()).getType();
                    if (BytesRef.class.equals(fieldType)) {
                        ReflectUtil.setFieldValue(t, field.getName(), objectValue);
                    } else {
                        byte[] bytes = objectValue.getBytes(StandardCharsets.UTF_8);
                        ReflectUtil.setFieldValue(t, field.getName(), bytes);
                    }
                } else {
                    throw new ProjectRepoException("lucene不支持其他类型，请重新配置搜索类型");
                }
            }
        }
        return t;
    }

    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> List<Document> buildDocumentList(List<U> pojoList) {
        List<Document> docList = new ArrayList<>();
        if (CollUtil.isEmpty(pojoList)) {
            return docList;
        }

        for (U pojo : pojoList) {
            Document document = parseToDocument(pojo);
            docList.add(document);
        }

        return docList;
    }

    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Document parseToDocument(T t) {
        Class<U> daoClass = ClassUtil.getDaoClassByPo(t, this);
        U dao = ReflectUtil.newInstance(daoClass);
        return parseToDocument(dao);
    }

    @SneakyThrows
    private <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Document parseToDocument(U t) {
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
                } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                    if (objectValue instanceof BytesRef) {
                        document.add(new BinaryDocValuesField(field.getName(), (BytesRef) objectValue));
                    } else if (objectValue instanceof byte[]) {
                        BytesRef bytesRef = new BytesRef((byte[]) objectValue);
                        document.add(new BinaryDocValuesField(field.getName(), bytesRef));
                    }
                } else {
                    throw new ProjectRepoException("lucene不支持其他类型，请重新配置搜索类型");
                }
            }
        }
        return document;
    }

    @SneakyThrows
    private synchronized <T extends BasePo<T>, U extends LuceneBaseDao<T>> IndexWriter getIndexWriter(Class<T> clazz) {
        String className = clazz.getSimpleName();
        Class<BaseDao<T>> daoClass = ClassUtil.getDaoClassByPoClass(clazz, this);
        U dao = (U) ReflectUtil.newInstance(daoClass);
        if (indexWriterMap.containsKey(className) && indexWriterMap.get(className) != null) {
            return indexWriterMap.get(className);
        }
        String directoryPath = buildDirectoryPath(className);
        Directory directory = FSDirectory.open(Paths.get(directoryPath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(dao.analyzer());
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriterMap.put(className, indexWriter);
        return indexWriter;
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

    private static class EasyQuery {
        public static Builder builder() {
            return new Builder();
        }

        /**
         * 根据entity构建lucene的Query
         *
         * @param matchAllIfEmpty 当entity中的查询条件为空时，是否匹配全部数据
         */
        public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Query parseCondition(Class<U> daoClass, V v, boolean matchAllIfEmpty) {
            BaseCondition<?> condition = v.get_condition();
            if (condition == null) {
                return matchAllIfEmpty ? new MatchAllDocsQuery() : new MatchNoDocsQuery();
            }
            Map<String, Object> conditionMap = condition.getConditionMap();
            if (CollUtil.isEmpty(conditionMap) && matchAllIfEmpty) {
                return new MatchAllDocsQuery();
            }
            return parseCondition(daoClass, v.toPo(), conditionMap);
        }

        @SneakyThrows
        private static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Query parseCondition(Class<U> daoClass, T t, Map<String, Object> conditionMap) {
            BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
            for (String key : conditionMap.keySet()) {
                String fieldName = BaseCondition.getFieldByConditionKey(key);
                String type = BaseCondition.getTypeByConditionKey(key);
                Object value = conditionMap.get(key);
                if (StrUtil.equals(type, BaseCondition.MATCH_ALL)) {
                    queryBuilder.add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
                } else if (StrUtil.equals(type, BaseCondition.MATCH_NOTHING)) {
                    queryBuilder.add(new MatchNoDocsQuery(), BooleanClause.Occur.MUST);
                } else if (StrUtil.equals(type, BaseCondition.OR)) {
                    queryBuilder.add(parseCondition(daoClass, t, ((BaseCondition<?>) conditionMap.get(key)).getConditionMap()), BooleanClause.Occur.SHOULD);
                } else if (StrUtil.equals(key, BaseCondition.AND)) {
                    queryBuilder.add(parseCondition(daoClass, t, ((BaseCondition<?>) conditionMap.get(key)).getConditionMap()), BooleanClause.Occur.MUST);
                } else if (StrUtil.equals(key, BaseCondition.EQUALS)) {
                    queryBuilder.add(new TermQuery(new Term(fieldName, value.toString())), BooleanClause.Occur.MUST);
                } else if (StrUtil.equals(key, BaseCondition.NOT_EQUALS)) {
                    queryBuilder.add(new TermQuery(new Term(fieldName, value.toString())), BooleanClause.Occur.MUST_NOT);
                } else if (StrUtil.equals(key, BaseCondition.IS_NULL)) { //TODO-待验证 该Query是否是is null的意思
                    queryBuilder.add(new TermQuery(new Term(fieldName)), BooleanClause.Occur.MUST_NOT);
                } else if (StrUtil.equals(key, BaseCondition.IS_NOT_NULL)) { //TODO-待验证 该Query是否是is not null的意思
                    queryBuilder.add(new TermQuery(new Term(fieldName)), BooleanClause.Occur.MUST);
                } else if (StrUtil.equals(key, BaseCondition.REGEX)) {
                    queryBuilder.add(new RegexpQuery(new Term(fieldName, ((Pattern) value).pattern())), BooleanClause.Occur.MUST);
                } else {
                    Field field = daoClass.getField(fieldName);
                    LuceneFieldKey fieldKey = null;
                    if (!field.isAnnotationPresent(LuceneFieldKey.class) || (fieldKey = field.getAnnotation(LuceneFieldKey.class)) == null) {
                        continue;
                    }
                    LuceneFieldTypeEnum fieldTypeEnum = fieldKey.type();
                    boolean isString = LuceneFieldTypeEnum.STRING_FIELD.equals(fieldTypeEnum) || LuceneFieldTypeEnum.TEXT_FIELD.equals(fieldTypeEnum);
                    if (StrUtil.equals(key, BaseCondition.GREAT_THAN)) {
                        if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                            int i = Convert.toInt(value);
                            if (i < Integer.MAX_VALUE) {
                                i++;
                            }
                            queryBuilder.add(IntPoint.newRangeQuery(fieldName, i, Integer.MAX_VALUE), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                            long l = Convert.toLong(value);
                            if (l < Long.MAX_VALUE) {
                                l++;
                            }
                            queryBuilder.add(LongPoint.newRangeQuery(fieldName, l, Long.MAX_VALUE), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                            double d = Convert.toLong(value);
                            if (d < Long.MAX_VALUE) {
                                d += Double.MIN_VALUE;
                            }
                            queryBuilder.add(DoublePoint.newRangeQuery(fieldName, d, Double.MAX_VALUE), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                            byte[] bytes = null;
                            if (value instanceof byte[]) {
                                bytes = (byte[]) value;
                            } else if (value instanceof BytesRef) {
                                bytes = ((BytesRef) value).bytes;
                            }
                            assert bytes != null;
                            queryBuilder.add(BinaryPoint.newRangeQuery(fieldName, bytes, BytesRef.EMPTY_BYTES), BooleanClause.Occur.MUST);
                        } else if (isString) {
                            log.warn("luceneRepoStrategy暂不支持字符串比大小");
                        }
                    } else if (StrUtil.equals(key, BaseCondition.GREAT_THAN_OR_EQUALS)) {
                        if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(IntPoint.newRangeQuery(fieldName, Convert.toInt(value), Integer.MAX_VALUE), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(LongPoint.newRangeQuery(fieldName, Convert.toLong(value), Long.MAX_VALUE), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(DoublePoint.newRangeQuery(fieldName, Convert.toDouble(value), Double.MAX_VALUE), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                            byte[] bytes = null;
                            if (value instanceof byte[]) {
                                bytes = (byte[]) value;
                            } else if (value instanceof BytesRef) {
                                bytes = ((BytesRef) value).bytes;
                            }
                            assert bytes != null;
                            queryBuilder.add(BinaryPoint.newRangeQuery(fieldName, bytes, BytesRef.EMPTY_BYTES), BooleanClause.Occur.MUST);
                        } else if (isString) {
                            log.warn("luceneRepoStrategy暂不支持字符串比大小");
                        }
                    } else if (StrUtil.equals(key, BaseCondition.LESS_THAN)) {
                        if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                            Integer i = Convert.toInt(value);
                            if (i > Integer.MIN_VALUE) {
                                --i;
                            }
                            queryBuilder.add(IntPoint.newRangeQuery(fieldName, Integer.MIN_VALUE, i), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                            Long l = Convert.toLong(value);
                            if (l > Long.MIN_VALUE) {
                                --l;
                            }
                            queryBuilder.add(LongPoint.newRangeQuery(fieldName, Long.MIN_VALUE, l), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                            Double d = Convert.toDouble(value);
                            if (d > Double.MIN_VALUE) {
                                d -= Double.MIN_VALUE;
                            }
                            queryBuilder.add(DoublePoint.newRangeQuery(fieldName, Double.MIN_VALUE, d), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                            byte[] bytes = null;
                            if (value instanceof byte[]) {
                                bytes = (byte[]) value;
                            } else if (value instanceof BytesRef) {
                                bytes = ((BytesRef) value).bytes;
                            }
                            assert bytes != null;
                            queryBuilder.add(BinaryPoint.newRangeQuery(fieldName, BytesRef.EMPTY_BYTES, bytes), BooleanClause.Occur.MUST);
                        } else if (isString) {
                            log.warn("luceneRepoStrategy暂不支持字符串比大小");
                        }
                    } else if (StrUtil.equals(key, BaseCondition.LESS_THAN_OR_EQUALS)) {
                        if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(IntPoint.newRangeQuery(fieldName, Integer.MIN_VALUE, Convert.toInt(value)), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(LongPoint.newRangeQuery(fieldName, Long.MIN_VALUE, Convert.toLong(value)), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(DoublePoint.newRangeQuery(fieldName, Double.MIN_VALUE, Convert.toDouble(value)), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                            byte[] bytes = null;
                            if (value instanceof byte[]) {
                                bytes = (byte[]) value;
                            } else if (value instanceof BytesRef) {
                                bytes = ((BytesRef) value).bytes;
                            }
                            assert bytes != null;
                            queryBuilder.add(BinaryPoint.newRangeQuery(fieldName, BytesRef.EMPTY_BYTES, bytes), BooleanClause.Occur.MUST);
                        } else if (isString) {
                            log.warn("luceneRepoStrategy暂不支持字符串比大小");
                        }
                    } else if (StrUtil.equals(key, BaseCondition.BETWEEN)) {
                        List<?> list = (List<?>) value;
                        Object v1 = list.get(0);
                        Object v2 = list.get(1);
                        if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(IntPoint.newRangeQuery(fieldName, Convert.toInt(v1), Convert.toInt(v2)), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(LongPoint.newRangeQuery(fieldName, Convert.toLong(v1), Convert.toLong(v2)), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                            queryBuilder.add(DoublePoint.newRangeQuery(fieldName, Convert.toDouble(v1), Convert.toLong(v2)), BooleanClause.Occur.MUST);
                        } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                            byte[] bytes1 = null;
                            byte[] bytes2 = null;
                            if (v1 instanceof byte[]) {
                                bytes1 = (byte[]) v1;
                                bytes2 = (byte[]) v2;
                            } else if (v1 instanceof BytesRef) {
                                bytes1 = ((BytesRef) v1).bytes;
                                bytes2 = ((BytesRef) v2).bytes;
                            }
                            assert bytes1 != null;
                            assert bytes2 != null;
                            queryBuilder.add(BinaryPoint.newRangeQuery(fieldName, bytes1, bytes2), BooleanClause.Occur.MUST);
                        } else if (isString) {
                            log.warn("luceneRepoStrategy暂不支持字符串比大小");
                        }
                    } else if (StrUtil.equals(key, BaseCondition.LIKE)) {
                        queryBuilder.add(new PrefixQuery(new Term(value.toString())), BooleanClause.Occur.MUST);
                    } else if (StrUtil.equals(key, BaseCondition.NOT_LIKE)) {
                        queryBuilder.add(new PrefixQuery(new Term(value.toString())), BooleanClause.Occur.MUST_NOT);
                    } else if (StrUtil.equals(key, BaseCondition.IN)) {
                        List<?> list = (List<?>) value;
                        if (CollUtil.isNotEmpty(list)) {
                            Object v1 = list.get(0);
                            if (LuceneFieldTypeEnum.INT_POINT.equals(fieldTypeEnum)) {
                                queryBuilder.add(IntPoint.newSetQuery(fieldName, Convert.toList(Integer.class, list)), BooleanClause.Occur.MUST);
                            } else if (LuceneFieldTypeEnum.LONG_POINT.equals(fieldTypeEnum)) {
                                queryBuilder.add(LongPoint.newSetQuery(fieldName, Convert.toList(Long.class, list)), BooleanClause.Occur.MUST);
                            } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(fieldTypeEnum)) {
                                queryBuilder.add(DoublePoint.newSetQuery(fieldName, Convert.toList(Double.class, list)), BooleanClause.Occur.MUST);
                            } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(fieldTypeEnum)) {
                                byte[][] l = new byte[list.size()][];
                                if (v1 instanceof byte[]) {
                                    for (int i = 0; i < list.size(); i++) {
                                        byte[] tmp = (byte[]) list.get(i);
                                        l[i] = tmp;
                                    }
                                } else if (v1 instanceof BytesRef) {
                                    for (int i = 0; i < list.size(); i++) {
                                        BytesRef tmp = (BytesRef) list.get(i);
                                        l[i] = tmp.bytes;
                                    }
                                }
                                queryBuilder.add(BinaryPoint.newSetQuery(fieldName, l), BooleanClause.Occur.MUST);
                            } else if (isString) {
                                queryBuilder.add(new DocValuesTermsQuery(fieldName, ArrayUtil.toArray((List<String>) list, String.class)), BooleanClause.Occur.MUST);
                            }
                        } else {
                            queryBuilder.add(new MatchNoDocsQuery(), BooleanClause.Occur.MUST);
                        }
                    } else if (StrUtil.equals(key, BaseCondition.REGEX)) {
                        Pattern pattern = (Pattern) value;
                        queryBuilder.add(new RegexpQuery(new Term(fieldName, pattern.pattern())), BooleanClause.Occur.MUST);
                    }
                }
            }
            return queryBuilder.build();
        }

        /**
         * 多个字段精确匹配
         */
        public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Query exactMultiFieldQuery(Class<U> daoClass, T t, boolean matchAllIfEmpty) {
            if (t == null || BeanUtil.isEmpty(t)) {
                if (matchAllIfEmpty) {
                    return new MatchAllDocsQuery();
                }
                return new MatchNoDocsQuery();
            }
            Map<String, Object> map = BeanUtil.beanToMap(t);
            BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    queryBuilder.add(exactValueQuery(daoClass, key, value), BooleanClause.Occur.MUST);
                }
            }
            return queryBuilder.build();
        }

        /**
         * 单个字段精确匹配多个值
         */
        public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Query exactMultiValueQuery(Class<U> daoClass, String idFieldName, Serializable... values) {
            return exactMultiValueQuery(daoClass, idFieldName, CollUtil.newArrayList(values));
        }

        public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Query exactMultiValueQuery(Class<U> daoClass, String idFieldName, List<Serializable> valueList) {
            if (CollUtil.isEmpty(valueList)) {
                return new MatchNoDocsQuery();
            }
            BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
            for (Serializable id : valueList) {
                Query query = exactValueQuery(daoClass, idFieldName, id);
                queryBuilder.add(query, BooleanClause.Occur.SHOULD);
            }
            return queryBuilder.build();
        }

        /**
         * 单个字段精确查询
         */
        public static <T extends BasePo<T>, U extends BaseDao<T>, V extends BaseEntity<T, V>> Query exactValueQuery(Class<U> daoClass, String fieldName, Object value) {
            LuceneFieldTypeEnum luceneFieldTypeEnum = ReflectUtil.getField(daoClass, fieldName).getAnnotation(LuceneFieldKey.class).type();
            Query query = null;
            if (LuceneFieldTypeEnum.LONG_POINT.equals(luceneFieldTypeEnum)) {
                query = LongPoint.newExactQuery(fieldName, (Long) value);
            } else if (LuceneFieldTypeEnum.BINARY_POINT.equals(luceneFieldTypeEnum)) {
                if (value instanceof BytesRef) {
                    query = BinaryPoint.newExactQuery(fieldName, ((BytesRef) value).bytes);
                } else if (value instanceof byte[]) {
                    query = BinaryPoint.newExactQuery(fieldName, (byte[]) value);
                }
            } else if (LuceneFieldTypeEnum.TEXT_FIELD.equals(luceneFieldTypeEnum)) {
                query = new TermQuery(new Term(fieldName, (String) value));
            } else if (LuceneFieldTypeEnum.STRING_FIELD.equals(luceneFieldTypeEnum)) {
                query = new TermQuery(new Term(fieldName, (String) value));
            } else if (LuceneFieldTypeEnum.DOUBLE_POINT.equals(luceneFieldTypeEnum)) {
                query = DoublePoint.newExactQuery(fieldName, (Double) value);
            } else if (LuceneFieldTypeEnum.INT_POINT.equals(luceneFieldTypeEnum)) {
                query = IntPoint.newExactQuery(fieldName, (Integer) value);
            } else {
                throw new ProjectRepoException("lucene未定义的映射类型：" + value);
            }
            return query;
        }

        public static class Builder {
            public Query build() {
                //TODO 待实现
                return null;
            }
        }
    }
}
