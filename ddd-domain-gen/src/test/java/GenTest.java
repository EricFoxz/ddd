import gitee.com.ericfox.ddd.domain.gen.GenApplication;
import gitee.com.ericfox.ddd.domain.gen.model.TableJavaBean;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GenApplication.class)
public class GenTest {
    @Test
    public void test0001() {
        TableJavaBean tableJavaBean = new TableJavaBean(SysUser.class);
        System.out.println(tableJavaBean);
    }
}
