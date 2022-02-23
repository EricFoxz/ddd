package gitee.com.ericfox.ddd.starter.mq.interfaces;

/**
 * MQ客户端接口
 */
public interface MqClientStrategy {
    void addListener(MqProxy mqProxy);
}
