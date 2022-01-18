package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.proxy.MqBroadcastProxy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.proxy.MqSingleProxy;

public interface MqProxy {
    static MqProxy getClientInstance(String... queueNames) {
        return getClientInstance(null, queueNames);
    }

    static MqProxy getClientInstance(Class<? extends MqClientStrategy> clazz, String... queueNames) {
        if (clazz == null || clazz.equals(MqClientStrategy.class)) {
            return MqBroadcastProxy.getClientInstance(queueNames);
        }
        return MqSingleProxy.getClientInstance(clazz, queueNames);
    }

    static MqProxy getServerInstance(String... queueNames) {
        return getServerInstance(null, queueNames);
    }

    static MqProxy getServerInstance(Class<? extends MqServerStrategy> clazz, String... queueNames) {
        if (clazz == null || clazz.equals(MqServerStrategy.class)) {
            return MqBroadcastProxy.getServerInstance(queueNames);
        }
        return MqSingleProxy.getServerInstance(clazz, queueNames);
    }

    String[] getQueueNames();

    Class<? extends MqServerStrategy>[] getServerTypes();

    Class<? extends MqClientStrategy>[] getClientTypes();
}
