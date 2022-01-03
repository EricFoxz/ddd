package gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;

public enum RepoTypeStrategyEnum implements BaseEnum<RepoTypeStrategyEnum, String> {
    J_FINAL_REPO_STRATEGY("jFinalRepoStrategy", "使用集成的jfinal进行持久化"),
    LUCENE_REPO_STRATEGY("luceneRepoStrategy", "使用集成的jfinal进行持久化");

    private final String code;
    private final String description;

    RepoTypeStrategyEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public RepoTypeStrategyEnum[] getEnums() {
        return values();
    }
}
