package gitee.com.ericfox.ddd.domain.gen.factory;

import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;

/**
 * 表结构工厂类
 */
public class GenSchemaFactory {
    private final RepoTypeStrategyEnum repoTypeStrategyEnum;

    private GenSchemaFactory(RepoTypeStrategyEnum repoTypeStrategyEnum) {
        this.repoTypeStrategyEnum = repoTypeStrategyEnum;
    }

    public static GenSchemaFactory getMySqlInstance() {
        return new GenSchemaFactory(RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY);
    }

    public static GenSchemaFactory getLuceneInstance() {
        return new GenSchemaFactory(RepoTypeStrategyEnum.LUCENE_REPO_STRATEGY);
    }
}
