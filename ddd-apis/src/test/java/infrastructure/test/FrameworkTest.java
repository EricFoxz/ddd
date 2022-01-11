package infrastructure.test;

import gitee.com.ericfox.ddd.apis.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FrameworkTest {
    @Test
    public void test001() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1.22D);
        System.out.println(1 + Double.MIN_VALUE);
        System.out.println(1 + Double.MIN_NORMAL);
    }
}
