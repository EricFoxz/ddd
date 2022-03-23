package gitee.com.ericfox.ddd.starter.bpm.properties;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.BpmTypeStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.starter.bpm")
public class StarterBpmProperties {
    public boolean enable = false;
    public BpmPropertiesEnum defaultStrategy;

    public enum BpmPropertiesEnum implements BasePropertiesEnum<BpmTypeStrategyEnum> {
        FLOWABLE_STRATEGY;
        //ACTIVITI_STRATEGY;

        @Override
        public String getName() {
            return name();
        }

        @Override
        public BpmTypeStrategyEnum toBizEnum() {
            return BpmTypeStrategyEnum.FLOWABLE_STRATEGY.getEnumByName(this.name());
        }
    }
}
