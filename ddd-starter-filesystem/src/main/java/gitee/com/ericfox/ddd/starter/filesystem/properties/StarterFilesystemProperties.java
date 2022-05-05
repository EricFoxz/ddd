package gitee.com.ericfox.ddd.starter.filesystem.properties;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.FilesystemStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.starter.filesystem")
public class StarterFilesystemProperties {
    public enum FilesystemPropertiesEnum implements BasePropertiesEnum<FilesystemStrategyEnum> {
        MIN_IO_STRATEGY;

        @Override
        public String getName() {
            return name();
        }

        @Override
        public FilesystemStrategyEnum toBizEnum() {
            return FilesystemStrategyEnum.MIN_IO_STRATEGY.getEnumByName(getName());
        }
    }
}
