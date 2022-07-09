import cn.hutool.http.HttpUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.FileUtil;
import kotlinx.coroutines.scheduling.CoroutineScheduler;
import kotlinx.coroutines.scheduling.Task;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class CoroutinesTest {
    private static final AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) {
        CoroutineScheduler coroutineScheduler = new CoroutineScheduler(50, 10000, 1000L * 600, "");
        Runnable task = new Task() {
            public void run() {
                List<String> list = CollUtil.newArrayList("https://www.github.com/", "https://www.baidu.com/", "https://blog.csdn.net/");
                int i = integer.incrementAndGet();
                String s = HttpUtil.get(list.get(i%3));
                FileUtil.appendUtf8String(i+"", FileUtil.file("F:/Desktop/test.txt"));

            }
        };

        AtomicReferenceArray<CoroutineScheduler.Worker> workers = coroutineScheduler.workers;

        coroutineScheduler.execute(task);
        coroutineScheduler.execute(task);
        coroutineScheduler.execute(task);
        coroutineScheduler.execute(task);
        coroutineScheduler.execute(task);
        coroutineScheduler.execute(task);
    }
}
