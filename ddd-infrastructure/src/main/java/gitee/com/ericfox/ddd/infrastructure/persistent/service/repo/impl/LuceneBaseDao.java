package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Lucene抽象Dao
 *
 * @param <PO> Po实体类
 */
public interface LuceneBaseDao<PO extends BasePo<PO>> extends BaseDao<PO> {
    /**
     * 获取该文档的分词器
     */
    default Analyzer analyzer() {
        return new IKAnalyzer(false);
    }
}
