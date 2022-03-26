package gitee.com.ericfox.ddd.common.interfaces.starter;

import gitee.com.ericfox.ddd.common.pattern.starter.MqBroadcastProxy;
import gitee.com.ericfox.ddd.common.pattern.starter.MqSingleProxy;

/**
 * MQ代理模式接口
 */
public interface MqProxy {
    static MqProxy getClientInstance(String... queueNames) {
        return getClientInstance(null, queueNames);
    }

    static MqProxy getClientInstance(Class<? extends MqClientService> clazz, String... queueNames) {
        if (clazz == null || clazz.equals(MqClientService.class)) {
            return MqBroadcastProxy.getClientInstance(queueNames);
        }
        return MqSingleProxy.getClientInstance(clazz, queueNames);
    }

    static MqProxy getServerInstance(String... queueNames) {
        return getServerInstance(null, queueNames);
    }

    static MqProxy getServerInstance(Class<? extends MqServerService> clazz, String... queueNames) {
        if (clazz == null || clazz.equals(MqServerService.class)) {
            return MqBroadcastProxy.getServerInstance(queueNames);
        }
        return MqSingleProxy.getServerInstance(clazz, queueNames);
    }

    String[] getQueueNames();

    Class<? extends MqServerService>[] getServerTypes();

    Class<? extends MqClientService>[] getClientTypes();
}
