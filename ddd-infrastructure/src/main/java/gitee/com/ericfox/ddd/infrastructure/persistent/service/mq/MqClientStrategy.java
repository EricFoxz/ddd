package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

/**
 * MQ客户端接口
 */
public interface MqClientStrategy {
    void addListener(MqProxy mqProxy);
}
