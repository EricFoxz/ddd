package gitee.com.ericfox.ddd.starter.mq.proxy;

import gitee.com.ericfox.ddd.starter.mq.interfaces.MqClientStrategy;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqProxy;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqServerStrategy;
import lombok.Getter;
import lombok.Setter;

/**
 * 为单独的一个服务发送消息
 */
@Getter
@Setter
@SuppressWarnings("unchecked")
public class MqSingleProxy implements MqProxy {
    private String[] queueNames;
    private Class<? extends MqServerStrategy> serverType;
    private Class<? extends MqClientStrategy> clientType;

    private MqSingleProxy() {
    }

    public static MqSingleProxy getClientInstance(Class<? extends MqClientStrategy> clientType, String... queueNames) {
        MqSingleProxy instance = new MqSingleProxy();
        instance.clientType = clientType;
        instance.queueNames = queueNames;
        return instance;
    }

    public static MqSingleProxy getServerInstance(Class<? extends MqServerStrategy> serverType, String... queueNames) {
        MqSingleProxy instance = new MqSingleProxy();
        instance.serverType = serverType;
        instance.queueNames = queueNames;
        return instance;
    }

    public void setQueueName(String... queueNames) {
        this.queueNames = queueNames;
    }

    @Override
    public synchronized Class<? extends MqServerStrategy>[] getServerTypes() {
        return new Class[]{serverType};
    }

    @Override
    public Class<? extends MqClientStrategy>[] getClientTypes() {
        return new Class[]{clientType};
    }
}
