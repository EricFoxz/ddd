package gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 持久化策略枚举类
 */
@Getter
public enum RepoTypeStrategyEnum implements BaseEnum<RepoTypeStrategyEnum, String> {
    MY_SQL_REPO_STRATEGY("mySqlRepoStrategy", "使用mySql进行持久化"),
    LUCENE_REPO_STRATEGY("luceneRepoStrategy", "使用lucene进行持久化");

    private final String code;
    private final String comment;

    RepoTypeStrategyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public RepoTypeStrategyEnum[] getEnums() {
        return values();
    }
}
