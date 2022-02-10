package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserContext implements BaseContext<SysUserEntity, SysUserContext.Rule, SysUserContext.Moment, SysUserContext.Description> {
    private SysUserEntity partPlaceThing;
    private Rule rule;
    private Moment moment;
    private Description description;

    @Override
    public void run() {
    }

    public enum Rule implements BaseContext.BaseRule {
        MANAGER,
    }

    public enum Moment implements BaseContext.BaseMoment {
        DEFAULT,
    }

    @Getter
    public enum Description implements BaseContext.BaseDescription {
        REGISTER("注册"),
        LOGIN("登录"),
        ;
        private final String comment;

        Description(String comment) {
            this.comment = comment;
        }
    }
}
