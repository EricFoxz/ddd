package gitee.com.ericfox.ddd.common.interfaces.starter;

/**
 * MQ客户端接口
 */
public interface MqClientService {
    void addListener(MqProxy mqProxy);
}
