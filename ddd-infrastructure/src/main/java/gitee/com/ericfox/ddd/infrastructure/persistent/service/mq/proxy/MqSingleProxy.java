package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.proxy;

import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqProxy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqServerStrategy;
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
    private Class<? extends MqServerStrategy> type;

    public MqSingleProxy(Class<? extends MqServerStrategy> type, String... queueNames) {
        this.type = type;
        this.queueNames = queueNames;
    }

    public void setQueueName(String... queueNames) {
        this.queueNames = queueNames;
    }

    @Override
    public synchronized Class<? extends MqServerStrategy>[] getTypes() {
        return new Class[]{type};
    }
}
