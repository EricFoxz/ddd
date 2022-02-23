package gitee.com.ericfox.ddd.infrastructure.general.config.env;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.CacheTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.MqTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
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

    /**
     * MQ策略
     */
    public MqStrategyBean mqStrategy;

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
                return this.name();
            }

            @Override
            public RepoTypeStrategyEnum toBizEnum() {
                return RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY.getEnumByName(this.name());
            }
        }
    }

    @Getter
    @Setter
    public static class CacheStrategyBean {
        private boolean enable = false;
        private CachePropertiesEnum[] defaultStrategy;
        private Integer defaultExpireSeconds = 3600;
        private boolean clearWhenStart = false;

        public enum CachePropertiesEnum implements BasePropertiesEnum<CacheTypeStrategyEnum> {
            CAFFEINE_CACHE_STRATEGY,
            REDIS_CACHE_STRATEGY;

            @Override
            public String getName() {
                return this.name();
            }

            @Override
            public CacheTypeStrategyEnum toBizEnum() {
                return CacheTypeStrategyEnum.CAFFEINE_CACHE_STRATEGY.getEnumByName(this.name());
            }
        }
    }

    @Getter
    @Setter
    public static class MqStrategyBean {
        private boolean enable = false;
        private MqPropertiesEnum[] defaultStrategy;

        public enum MqPropertiesEnum implements BasePropertiesEnum<MqTypeStrategyEnum> {
            RABBIT_MQ_STRATEGY,
            KAFKA_MQ_STRATEGY;

            @Override
            public String getName() {
                return this.name();
            }

            @Override
            public MqTypeStrategyEnum toBizEnum() {
                return MqTypeStrategyEnum.RABBIT_MQ_STRATEGY.getEnumByName(this.name());
            }
        }
    }

}
