package gitee.com.ericfox.ddd.domain.sys.model.sys_token;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SysTokenContext implements BaseContext {
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

    @Getter
    public enum Moment implements BaseContext.BaseMoment {
        DEFAULT(null, "");
        private static Map<String, Moment> map = MapUtil.newHashMap();
        private final String code;
        private final String responseBodyScript;

        static {
            Moment[] values = values();
            for (Moment moment : values) {
                map.put(moment.code, moment);
            }
        }

        static Moment getMomentByCode(String code) {
            return map.get(code);
        }

        public static void setMap(Map<String, Moment> map) {
            Moment.map = map;
        }

        Moment(String code, String responseBodyScript) {
            this.code = code;
            this.responseBodyScript = responseBodyScript;
        }
    }
}
