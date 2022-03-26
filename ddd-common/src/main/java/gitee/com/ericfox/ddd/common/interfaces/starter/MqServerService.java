package gitee.com.ericfox.ddd.common.interfaces.starter;

/**
 * MQ服务端接口
 */
public interface MqServerService {
    void send(String msg, MqProxy mqProxy);
}
