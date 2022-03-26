package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserContext implements BaseContext {
    private Rule rule;
    private Moment moment;

    public enum Description implements BaseContext.BaseDescription {
        DEFAULT,
    }

    public enum Rule implements BaseContext.BaseRule {
        MANAGER,
        WEB_USER,
        APP_USER,
    }

    public enum Moment implements BaseContext.BaseMoment {
        DEFAULT,
    }
}
