package gitee.com.ericfox.ddd.starter.filesystem.config;

import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.starter.filesystem.properties.StarterFilesystemProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnPropertyEnum(
        name = "custom.starter.filesystem.default-strategy",
        enumClass = StarterFilesystemProperties.FilesystemPropertiesEnum.class,
        includeAnyValue = "min_io_strategy"
)
@ConditionalOnProperty(prefix = "custom.starter.filesystem", value = "enable")
public class MinIoConfig {
}
