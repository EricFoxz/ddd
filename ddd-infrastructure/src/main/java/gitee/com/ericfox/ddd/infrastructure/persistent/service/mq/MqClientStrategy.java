package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

public interface MqClientStrategy {
    void addListener(MqProxy mqProxy);
}