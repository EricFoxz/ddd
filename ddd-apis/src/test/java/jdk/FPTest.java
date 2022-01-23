package jdk;

import cn.hutool.core.comparator.PinyinComparator;
import cn.hutool.core.util.RandomUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FPTest {
    public static void main(String[] args) {
        List<String> list = CollUtil.newArrayList();
        for (int i = 0; i < 100000; i++) {
            list.add(RandomUtil.randomChinese() + "");
        }
        long currentTime = System.currentTimeMillis();
        list = list.stream().parallel()
                .distinct()
                .sorted(new PinyinComparator())
                .collect(Collectors.toList());
        System.out.println("stream用时" + (System.currentTimeMillis() - currentTime) + "ms");


        list = CollUtil.newArrayList();
        for (int i = 0; i < 100000; i++) {
            list.add(RandomUtil.randomChinese() + "");
        }
        currentTime = System.currentTimeMillis();
        PinyinComparator pinyinComparator = new PinyinComparator();
        list.sort(pinyinComparator);
//        Set<String> set = CollUtil.newHashSet(list);
        System.out.println("foreach用时" + (System.currentTimeMillis() - currentTime) + "ms");
        System.out.println();
    }
}
