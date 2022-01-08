package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public interface LuceneBaseDao<T extends BasePo<T>> extends BaseDao<T> {
    /**
     * 获取该文档的分词器
     */
    default Analyzer analyzer() {
        return new IKAnalyzer(false);
    }
}
