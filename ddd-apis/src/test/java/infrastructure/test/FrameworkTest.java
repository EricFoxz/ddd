package infrastructure.test;

import com.jfinal.plugin.activerecord.Db;
import gitee.com.ericfox.ddd.apis.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FrameworkTest {
    @Test
    public void test001() {
        System.out.println(Db.getSql("select * from sys_user"));
    }
}
