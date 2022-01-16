package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

public interface MqStrategy {
    void send(String msg);
}
