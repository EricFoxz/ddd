import cn.hutool.http.HttpUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.FileUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.ThreadUtil;
import kotlin.Unit;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.scheduling.Task;
import kotlinx.coroutines.test.TestCoroutineContext;
import org.objenesis.instantiator.util.UnsafeUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class CoroutinesTest {
    private static final AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) {
        Job job = new JobSupport(true);
        job.invokeOnCompletion((Throwable throwable) -> {
            int i = integer.incrementAndGet();
            List<String> list = CollUtil.newArrayList("https://www.github.com/", "https://www.baidu.com/", "https://blog.csdn.net/");
            String s = HttpUtil.get(list.get(i % 3));
            FileUtil.appendUtf8String(i + "", "F:/Desktop/test.txt");
            return Unit.INSTANCE;
        });
        job.start();

        LockSupport.parkUntil(3000);


        FileUtil.appendUtf8String("开始\n", "F:/Desktop/test.txt");
        Runnable task = new Task() {
            public void run() {
                int i = integer.incrementAndGet();
                List<String> list = CollUtil.newArrayList("https://www.github.com/", "https://www.baidu.com/", "https://blog.csdn.net/");
                String s = HttpUtil.get(list.get(i % 3));
                FileUtil.appendUtf8String(i + "", "F:/Desktop/test.txt");
            }
        };

        Dispatchers.getDefault().dispatchYield(new TestCoroutineContext("test"), task);
        Dispatchers.getDefault().dispatchYield(new TestCoroutineContext("test"), task);
        Dispatchers.getDefault().dispatchYield(new TestCoroutineContext("test"), task);
        Dispatchers.getDefault().dispatchYield(new TestCoroutineContext("test"), task);
        Dispatchers.getDefault().dispatchYield(new TestCoroutineContext("test"), task);
        Dispatchers.getDefault().dispatchYield(new TestCoroutineContext("test"), task);
    }
}
