import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.ScriptUtil;
import lombok.SneakyThrows;

import javax.script.CompiledScript;
import javax.script.SimpleBindings;
import java.util.Map;

public class ScriptTest {
    @SneakyThrows
    public static void main(String[] args) {
        String script = "({\"code\":403,\"message\":\"禁止注册\",\"time\":$currentTime})";
        Map map = MapUtil.builder()
                .put("$currentTime", System.currentTimeMillis())
                .build();
        CompiledScript compile = ScriptUtil.compile(script);
        Object eval = compile.eval(new SimpleBindings(map));
        System.out.println(eval);
    }
}
