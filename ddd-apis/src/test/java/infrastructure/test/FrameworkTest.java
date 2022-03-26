package infrastructure.test;

import gitee.com.ericfox.ddd.apis.ApisApplication;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.common.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.URLUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApisApplication.class)
public class FrameworkTest extends Parent {
    public static final class STRUCTURE {
        public static String id = "id2";
        public static String name = "name2";
    }

    @Test
    public void test0001() {
        System.out.println(FrameworkTest.STRUCTURE.id);
        System.out.println(Parent.STRUCTURE.id);
    }

    @Test
    public void test0002() {
        // /E:/idea_projects/ddd/ddd-infrastructure/target/classes/gitee/com/ericfox/ddd/infrastructure/persistent/po
        // /E:/idea_projects/ddd/ddd-infrastructure/src/main/java/gitee/com/ericfox/ddd/infrastructure/persistent/po/sys
        ClassUtil.getClassPaths(BasePo.class.getPackage().getName());
        System.out.println(URLUtil.getURL(BasePo.class.getName().replaceAll("[.]", "/")).getFile());
    }

}
