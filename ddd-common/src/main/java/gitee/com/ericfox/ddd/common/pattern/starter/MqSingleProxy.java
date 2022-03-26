package gitee.com.ericfox.ddd.common.pattern.starter;

import gitee.com.ericfox.ddd.common.interfaces.starter.MqClientService;
import gitee.com.ericfox.ddd.common.interfaces.starter.MqProxy;
import gitee.com.ericfox.ddd.common.interfaces.starter.MqServerService;
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
    private Class<? extends MqServerService> serverType;
    private Class<? extends MqClientService> clientType;

    private MqSingleProxy() {
    }

    public static MqSingleProxy getClientInstance(Class<? extends MqClientService> clientType, String... queueNames) {
        MqSingleProxy instance = new MqSingleProxy();
        instance.clientType = clientType;
        instance.queueNames = queueNames;
        return instance;
    }

    public static MqSingleProxy getServerInstance(Class<? extends MqServerService> serverType, String... queueNames) {
        MqSingleProxy instance = new MqSingleProxy();
        instance.serverType = serverType;
        instance.queueNames = queueNames;
        return instance;
    }

    public void setQueueName(String... queueNames) {
        this.queueNames = queueNames;
    }

    @Override
    public synchronized Class<? extends MqServerService>[] getServerTypes() {
        return new Class[]{serverType};
    }

    @Override
    public Class<? extends MqClientService>[] getClientTypes() {
        return new Class[]{clientType};
    }
}
