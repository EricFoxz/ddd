package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

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
        ANONYMOUS,
    }

    @Getter
    public static class Moment implements BaseContext.BaseMoment {
        // ============如果要改为可配置，enum改为class，并且中间这段代码需要删除 start============
        /*DEFAULT(null, "");

        static {
            Moment[] values = values();
            Map<String, Moment> vMap = MapUtil.newHashMap();
            for (Moment moment : values) {
                vMap.put(moment.code, moment);
            }
            setMap(vMap);
        }

        Moment(String code, String responseBodyScript) {
            this.code = code;
            this.responseBodyScript = responseBodyScript;
        }*/
        // ============如果要改为可配置，enum改为class，并且中间这段代码需要删除 end============

        private static Map<String, Moment> map = MapUtil.newHashMap();
        private String code;
        private String responseBodyScript;

        static Moment getMomentByCode(String code) {
            return map.get(code);
        }

        public static void setMap(Map<String, Moment> map) {
            Moment.map = map;
        }
    }
}
