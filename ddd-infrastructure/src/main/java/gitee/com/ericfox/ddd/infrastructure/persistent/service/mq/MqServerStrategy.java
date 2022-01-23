package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

/**
 * MQ服务端接口
 */
public interface MqServerStrategy {
    void send(String msg, MqProxy mqProxy);
}
