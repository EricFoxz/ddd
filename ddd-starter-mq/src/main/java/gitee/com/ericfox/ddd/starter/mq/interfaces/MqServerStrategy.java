package gitee.com.ericfox.ddd.starter.mq.interfaces;

/**
 * MQ服务端接口
 */
public interface MqServerStrategy {
    void send(String msg, MqProxy mqProxy);
}
