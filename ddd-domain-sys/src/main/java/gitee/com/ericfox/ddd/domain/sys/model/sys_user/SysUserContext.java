package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserContext implements BaseContext {
    private Rule rule;
    private Moment moment;

    public enum Rule implements BaseContext.BaseRule {
        MANAGER,
        WEB_USER,
        APP_USER,
    }

    public enum Moment implements BaseContext.BaseMoment {
        DEFAULT,
    }

    public abstract static class Description implements BaseContext.BaseDescription {
    }
}
