package gitee.com.ericfox.ddd.starter.cloud.properties;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.CloudRegisterTypeStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.starter.cloud")
public class StarterCloudProperties {
    /*@Resource
    private EurekaClientConfigBean eurekaClientConfigBean;*/

    private boolean enable = false;

    private CloudRegisterPropertiesEnum defaultRegisterStrategy;

    private ZookeeperCuratorBean zookeeperCurator;

    public enum CloudRegisterPropertiesEnum implements BasePropertiesEnum<CloudRegisterTypeStrategyEnum> {
        EUREKA_REGISTER_STRATEGY,
        ZOOKEEPER_REGISTER_STRATEGY;

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public CloudRegisterTypeStrategyEnum toBizEnum() {
            return CloudRegisterTypeStrategyEnum.EUREKA_REGISTER_STRATEGY.getEnumByName(this.name());
        }
    }

    @Getter
    @Setter
    public static class ZookeeperCuratorBean {
        private String[] clientUrl;

        /**
         * 会话超时时间（毫秒）
         */
        private Long sessionTimeoutMs;

        /**
         * 重试间隔（毫秒）
         */
        private Long sleepMsBetweenRetry;

        /**
         * 最大重试次数
         */
        private Integer maxRetries;

        /**
         * 命名空间
         */
        private String namespace;

        /**
         * 连接超时时间（毫秒）
         */
        private Long connectionTimeoutMs;
    }

    /*private void setEurekaClientConfigBean(EurekaClientConfigBean eurekaClientConfigBean) {
    }*/
}
