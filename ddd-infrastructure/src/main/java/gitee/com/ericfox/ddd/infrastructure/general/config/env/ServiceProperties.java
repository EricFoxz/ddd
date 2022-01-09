package gitee.com.ericfox.ddd.infrastructure.general.config.env;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.CacheTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.service", ignoreInvalidFields = true)
public class ServiceProperties {
    public RepoStrategyBean repoStrategy;

    public CacheStrategyBean cacheStrategy;

    @Getter
    @Setter
    public static class RepoStrategyBean {
        private DefaultStrategy defaultStrategy = DefaultStrategy.J_FINAL_REPO_STRATEGY;

        private LuceneBean lucene;

        @Setter
        @Getter
        public static class LuceneBean {
            private String rootPath;
            private Boolean clearWhenStart = false;
        }

        public enum DefaultStrategy implements BasePropertiesEnum<RepoTypeStrategyEnum> {
            J_FINAL_REPO_STRATEGY,
            LUCENE_REPO_STRATEGY;

            @Override
            public RepoTypeStrategyEnum toBizEnum() {
                return RepoTypeStrategyEnum.J_FINAL_REPO_STRATEGY.getEnumByName(this.name());
            }
        }
    }

    @Getter
    @Setter
    public static class CacheStrategyBean {
        private boolean enable = true;

        private DefaultStrategy defaultStrategy = DefaultStrategy.REDIS_STRATEGY;

        public enum DefaultStrategy implements BasePropertiesEnum<CacheTypeStrategyEnum> {
            REDIS_STRATEGY;

            @Override
            public CacheTypeStrategyEnum toBizEnum() {
                return CacheTypeStrategyEnum.REDIS_STRATEGY.getEnumByName(this.name());
            }
        }
    }


}
