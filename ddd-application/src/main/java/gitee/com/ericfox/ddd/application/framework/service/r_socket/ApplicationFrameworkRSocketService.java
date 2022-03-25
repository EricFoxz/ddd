package gitee.com.ericfox.ddd.application.framework.service.r_socket;

import gitee.com.ericfox.ddd.application.framework.config.r_socket.RSocketConfig;
import gitee.com.ericfox.ddd.application.framework.model.r_socket.RSocketMessageBean;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@ConditionalOnBean(RSocketConfig.class)
public class ApplicationFrameworkRSocketService {
    @SneakyThrows
    public void echo(RSocketMessageBean messageBean) {
        log.info(messageBean.toString());
    }

    public RSocketMessageBean get(String title) {
        log.info("获取消息 {}", title);
        return new RSocketMessageBean();
    }

    public List<RSocketMessageBean> list() {
        log.info("获取列表");
        return CollUtil.newArrayList();
    }
}
