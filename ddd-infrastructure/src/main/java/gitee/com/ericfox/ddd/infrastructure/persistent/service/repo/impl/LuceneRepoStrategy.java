package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.strategy.LuceneFieldKey;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.LuceneFieldTypeEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.LuceneBaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import lombok.SneakyThrows;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.SystemException;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service("luceneRepoStrategy")
public class LuceneRepoStrategy<W extends LuceneBaseEntity> implements RepoStrategy {
    @Resource
    CustomProperties customProperties;
    @Resource
    private IndexWriter indexWriter;
    @Resource
    private SearcherManager searcherManager;

    @Override
    @SneakyThrows
    public <T extends BasePo<T>> T findById(T t) {
        return null;
    }

    @Override
    public <T extends BasePo<T>> boolean deleteById(T t) {
        return false;
    }

    @Override
    public <T extends BasePo<T>> T insert(T t) {
        return null;
    }

    @Override
    public <T extends BasePo<T>> boolean update(T t) {
        return false;
    }

    @Override
    public <T extends BasePo<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize) {
        return queryPage(t, pageNum, pageSize, new MatchAllDocsQuery(), Sort.INDEXORDER);
    }

    @SneakyThrows
    public <T extends BasePo<T>> PageInfo<T> queryPage(T t1, Integer pageNum, Integer pageSize, Query query, Sort sort) {
        Class<T> clazz = (Class<T>) t1.getClass();
        String name = clazz.getName();
        String className = clazz.getSimpleName();
        Class<W> w = ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", name) + ".repository.sys.jfinal." + className + "Dao");
        int start = (pageNum - 1) * pageSize;// 下标从 0 开始
        int end = pageNum * pageSize;

        String directoryPath = buildDirectoryPath(clazz.getSimpleName());
        Directory directory = FSDirectory.open(Paths.get(directoryPath));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

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
            W t = ReflectUtil.newInstance(w);
            parseClassToPojo(document, t);
            resultList.add((T) t.toParent());
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
    public <T extends BasePo<T>> List<T> queryList(T t, int limit) {
        return null;
    }

    private String buildDirectoryPath(String rootPathName) {
        String luceneLocalIndexDirectoryPath = customProperties.getLucene().getRootPath();
        if (StrUtil.endWith(luceneLocalIndexDirectoryPath, File.separator)) {
            return luceneLocalIndexDirectoryPath + rootPathName;
        }

        return luceneLocalIndexDirectoryPath + File.separator + rootPathName;
    }

    @SneakyThrows
    private void parseClassToPojo(Document document, W t) {
        Class<?> clazz = t.getClass();
        Class<?> superClazz = clazz.getSuperclass();
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
    }
}
