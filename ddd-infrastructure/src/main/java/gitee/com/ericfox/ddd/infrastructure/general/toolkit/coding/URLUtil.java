package gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding;

import java.net.URL;

public class URLUtil extends cn.hutool.core.util.URLUtil {
    public static String getName(URL url) {
        return getName(url.getPath());
    }

    public static String getName(String url) {
        if (StrUtil.isBlank(url)) {
            return null;
        }
        url = url.replaceAll("\\\\", "/");
        int index = url.lastIndexOf("/");
        if (index < 0) {
            return url;
        } else if (index == url.length() - 1) {
            return null;
        }
        return url.substring(index + 1);
    }
}
