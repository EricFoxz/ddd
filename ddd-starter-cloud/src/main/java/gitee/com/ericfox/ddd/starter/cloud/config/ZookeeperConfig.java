package gitee.com.ericfox.ddd.starter.cloud.config;

import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.starter.cloud.listener.ZookeeperListener;
import gitee.com.ericfox.ddd.starter.cloud.properties.StarterCloudProperties;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnProperty(prefix = "custom.starter.cloud", value = "enable")
@ConditionalOnPropertyEnum(
        name = "custom.starter.cloud.default-register-strategy",
        enumClass = StarterCloudProperties.CloudRegisterPropertiesEnum.class,
        includeAnyValue = "zookeeper_register_strategy"
)
public class ZookeeperConfig {
    @Resource
    private StarterCloudProperties starterCloudProperties;

    @Bean
    @SneakyThrows
    public CuratorFramework curatorClient() {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ArrayUtil.join(starterCloudProperties.getZookeeperCurator().getClientUrl(), ","))
                .build();
        client.start();
        //注册监听器
        ZookeeperListener listener = new ZookeeperListener(client);
        listener.zNodeListener();
        listener.zNodeChildrenListener();
        return client;
    }
}
