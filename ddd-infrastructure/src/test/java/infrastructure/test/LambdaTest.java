package infrastructure.test;

import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;

import java.util.List;

/**
 * LambdaTest
 */
public class LambdaTest {
    public int index = 0;

    public static void main(String[] args) {
        LambdaTest test = new LambdaTest();

    }

    public void run() {
        run2();
        List<String> list = CollUtil.newArrayList();
        list.forEach(str -> {
            index++;
        });
    }

    public void run2() {
        index++;
    }
}
