package gitee.com.ericfox.ddd.common.pattern.starter;

import gitee.com.ericfox.ddd.common.interfaces.starter.MqClientService;
import gitee.com.ericfox.ddd.common.interfaces.starter.MqProxy;
import gitee.com.ericfox.ddd.common.interfaces.starter.MqServerService;
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
    private static Class<? extends MqServerService>[] serverTypes = new Class[]{MqServerService.class};
    private static Class<? extends MqClientService>[] clientTypes = new Class[]{MqClientService.class};
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
    public Class<? extends MqServerService>[] getServerTypes() {
        return serverTypes;
    }

    @Override
    public Class<? extends MqClientService>[] getClientTypes() {
        return clientTypes;
    }
}
