package gitee.com.ericfox.ddd.starter.mq;

import gitee.com.ericfox.ddd.common.enums.strategy.MqTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.starter.mq.properties.StarterMqProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(StarterMqProperties.class)
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
        for (StarterMqProperties.MqPropertiesEnum mqPropertiesEnum : starterMqProperties.getDefaultStrategy()) {
            if (MqTypeStrategyEnum.RABBIT_MQ_STRATEGY.equals(mqPropertiesEnum.toBizEnum())) {
                initRabbitMq();
            } else if (MqTypeStrategyEnum.KAFKA_MQ_STRATEGY.equals(mqPropertiesEnum.toBizEnum())) {
                initKafkaMq();
            }
        }
    }

    /**
     * 初始化kafka配置
     */
    private void initKafkaMq() {

    }

    /**
     * 初始化RabbitMq配置
     */
    private void initRabbitMq() {

    }
}
