package gitee.com.ericfox.ddd.starter.bpm.config;

import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.starter.bpm.properties.StarterBpmProperties;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConditionalOnPropertyEnum(
        name = "custom.starter.bpm.default-strategy",
        enumClass = StarterBpmProperties.BpmPropertiesEnum.class,
        includeAnyValue = "flowable_strategy"
)
@ConditionalOnProperty(prefix = "custom.starter.bpm", value = "enable")
@Slf4j
public class FlowableBpmConfig {

    @Bean
    @ConditionalOnMissingBean(SpringProcessEngineConfiguration.class)
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSourceTransactionManager dataSourceTransactionManager, DataSource dataSource) {
        SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
        springProcessEngineConfiguration.setDataSource(dataSource);
        springProcessEngineConfiguration.setTransactionManager(dataSourceTransactionManager);

        //不添加此项配置，在没创建表时，会抛出FlowableWrongDbException异常
        springProcessEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        return springProcessEngineConfiguration;
    }
}
