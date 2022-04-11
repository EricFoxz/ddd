package gitee.com.ericfox.ddd.infrastructure.general.config.env;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.infrastructure", ignoreInvalidFields = true)
public class InfrastructureProperties {
    /**
     * 持久化策略
     */
    public RepoStrategyBean repoStrategy;

    @Getter
    @Setter
    public static class RepoStrategyBean {
        private RepoPropertiesEnum defaultStrategy = RepoPropertiesEnum.MY_SQL_REPO_STRATEGY;
        private MySqlBean mySql;
        private LuceneBean lucene;

        @Setter
        @Getter
        public static class MySqlBean {
            private boolean enable = true;
        }

        @Setter
        @Getter
        public static class LuceneBean {
            private boolean enable = true;
            private String rootPath;
            private Boolean clearWhenStart = false;
        }

        public enum RepoPropertiesEnum implements BasePropertiesEnum<RepoTypeStrategyEnum> {
            MY_SQL_REPO_STRATEGY,
            LUCENE_REPO_STRATEGY;

            @Override
            public String getName() {
                return name();
            }

            @Override
            public RepoTypeStrategyEnum toBizEnum() {
                return RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY.getEnumByName(getName());
            }
        }
    }
}
