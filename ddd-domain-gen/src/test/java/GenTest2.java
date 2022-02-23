import gitee.com.ericfox.ddd.common.toolkit.coding.ReUtil;
import org.junit.Test;

import java.util.regex.Pattern;

public class GenTest2 {
    @Test
    public void test001() {
        String s = ReUtil.replaceAll(".sys.", Pattern.compile("\\b(sys)\\b"), "${}");
        System.out.println(s);
    }
}
