package gitee.com.ericfox.ddd.infrastructure.general.common.framework.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OkHttpResponse {

    private int status;
    private String response;
}
