package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserContext implements BaseContext {
    private Rule rule;
    private Moment moment;

    @Getter
    @AllArgsConstructor
    public enum Description implements BaseContext.BaseDescription {
        DEFAULT("default");
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public enum Rule implements BaseContext.BaseRule {
        MANAGER("manager"),
        WEB_USER("web_user"),
        APP_USER("app_user"),
        ANONYMOUS("anonymous");
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public enum Moment implements BaseContext.BaseMoment {
        DEFAULT("default");
        private final String name;
    }
}
