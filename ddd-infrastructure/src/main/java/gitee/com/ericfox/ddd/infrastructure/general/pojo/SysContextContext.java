package gitee.com.ericfox.ddd.infrastructure.general.pojo;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import lombok.Getter;

public class SysContextContext {
    private Rule rule;
    private Moment moment;

    public class Description implements BaseContext.BaseDescription {
    }

    public class Rule implements BaseContext.BaseRule {
    }

    public class Moment implements BaseContext.BaseMoment {
    }
}
