package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

public interface MqProxy {
    String[] getQueueNames();

    Class<? extends MqServerStrategy>[] getTypes();
}
