package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.proxy;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.MqTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqProxy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqServerStrategy;
import lombok.Getter;
import lombok.Setter;

/**
 * 广播消息
 * 找到所有可用的Mq服务，全部发送消息
 */
@Getter
@Setter
public class MqBroadcastProxy implements MqProxy {
    private static MqTypeStrategyEnum[] types = MqTypeStrategyEnum.values();
    private String[] queueNames;

    public MqBroadcastProxy(String... queueNames) {
        this.queueNames = queueNames;
    }

    @Override
    public Class<? extends MqServerStrategy>[] getTypes() {
        return null;
    }
}
