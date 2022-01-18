package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

public interface MqServerStrategy {
    void send(String msg, MqProxy mqProxy);
}
