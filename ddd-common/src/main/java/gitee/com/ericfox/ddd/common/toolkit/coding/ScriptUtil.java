package gitee.com.ericfox.ddd.common.toolkit.coding;

import cn.hutool.script.ScriptRuntimeException;

import javax.script.SimpleBindings;
import java.util.Map;

/**
 * 脚本工具类
 */
public class ScriptUtil extends cn.hutool.script.ScriptUtil {
    public static Object eval(String script, Map<String, Object> bindings) throws ScriptRuntimeException {
        return eval(script, new SimpleBindings(bindings));
    }
}
