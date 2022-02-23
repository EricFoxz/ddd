package gitee.com.ericfox.ddd.starter.mq;

import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.starter.mq.config.RabbitMqConfig;
import gitee.com.ericfox.ddd.starter.mq.properties.StarterMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableConfigurationProperties(StarterMqProperties.class)
@ConditionalOnProperty(prefix = "custom.starter.mq", value = "enable")
public class StarterMqAutoConfig {
    @Resource
    private StarterMqProperties starterMqProperties;

    /**
     * 初始化
     */
    @Autowired
    public void initAll() {
        if (!starterMqProperties.isEnable()) {
            return;
        }
        if (ArrayUtil.isEmpty(starterMqProperties.getDefaultStrategy())) {
            throw new ProjectFrameworkException("");
        }
        SpringUtil.registerBean(StrUtil.toCamelCase(RabbitMqConfig.class.getSimpleName()), new RabbitMqConfig());
    }
}
