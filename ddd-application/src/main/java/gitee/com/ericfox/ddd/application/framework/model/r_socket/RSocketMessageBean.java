package gitee.com.ericfox.ddd.application.framework.model.r_socket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RSocketMessageBean {
    private String title;
    private String content;
}
