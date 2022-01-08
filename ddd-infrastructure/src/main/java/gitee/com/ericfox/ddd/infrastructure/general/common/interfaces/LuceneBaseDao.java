package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public interface LuceneBaseDao<T extends BasePo<T>> extends BaseDao {
    /**
     * 获取该文档的分词器
     */
    default Analyzer analyzer() {
        return new IKAnalyzer(false);
    }
}
