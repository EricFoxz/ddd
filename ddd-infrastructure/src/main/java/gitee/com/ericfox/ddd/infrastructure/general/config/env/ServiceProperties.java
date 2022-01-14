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
    /**
     * 持久化策略
     */
    public RepoStrategyBean repoStrategy;

    /**
     * 缓存策略
     */
    public CacheStrategyBean cacheStrategy;

    @Getter
    @Setter
    public static class RepoStrategyBean {
        private DefaultStrategy defaultStrategy = DefaultStrategy.J_FINAL_REPO_STRATEGY;

        private JFinalBean jFinal;

        private LuceneBean lucene;

        @Setter
        @Getter
        public static class JFinalBean {
            private boolean enable = true;
        }

        @Setter
        @Getter
        public static class LuceneBean {
            private boolean enable = true;
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
        private boolean enable = false;

        private DefaultStrategy[] defaultStrategy;

        private boolean clearWhenStart = false;

        private int cacheTimeoutSeconds = 3600;

        public enum DefaultStrategy implements BasePropertiesEnum<CacheTypeStrategyEnum> {
            REDIS_STRATEGY,
            CAFFEINE_STRATEGY;

            @Override
            public CacheTypeStrategyEnum toBizEnum() {
                return CacheTypeStrategyEnum.REDIS_STRATEGY.getEnumByName(this.name());
            }
        }
    }


}
