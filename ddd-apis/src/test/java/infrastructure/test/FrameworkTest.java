package infrastructure.test;

import gitee.com.ericfox.ddd.apis.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
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
}
