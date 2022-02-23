package gitee.com.ericfox.ddd.starter.mq.pattern;

import gitee.com.ericfox.ddd.starter.mq.interfaces.MqClientStrategy;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqProxy;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqServerStrategy;
import lombok.Getter;
import lombok.Setter;

/**
 * 广播消息
 * 找到所有可用的Mq服务，全部发送消息
 */
@Getter
@Setter
@SuppressWarnings("unchecked")
public class MqBroadcastProxy implements MqProxy {
    private static Class<? extends MqServerStrategy>[] serverTypes = new Class[]{MqServerStrategy.class};
    private static Class<? extends MqClientStrategy>[] clientTypes = new Class[]{MqClientStrategy.class};
    private String[] queueNames;

    private MqBroadcastProxy() {
    }

    public static MqBroadcastProxy getClientInstance(String... queueNames) {
        MqBroadcastProxy instance = new MqBroadcastProxy();
        instance.queueNames = queueNames;
        return instance;
    }

    public static MqBroadcastProxy getServerInstance(String... queueNames) {
        MqBroadcastProxy instance = new MqBroadcastProxy();
        instance.queueNames = queueNames;
        return instance;
    }

    @Override
    public Class<? extends MqServerStrategy>[] getServerTypes() {
        return serverTypes;
    }

    @Override
    public Class<? extends MqClientStrategy>[] getClientTypes() {
        return clientTypes;
    }
}
