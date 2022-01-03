package gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding;

public class SecureUtil extends cn.hutool.crypto.SecureUtil {
    /**
     * 把字符串编码为 MD5 字符串(通过Google guava)
     * 新版本 guava 不推荐使用 md5 哈希，而是使用 sha256 或更高水平的哈希
     * 性能比 apache 的高，优先推荐
     *
     * @return
     */
    /*@SuppressWarnings("deprecation")
    public static String md5ByGuava(final String sourceString) {
        if (StrUtil.isNotBlank(sourceString)) {
            return Hashing.md5().hashString(sourceString, StandardCharsets.UTF_8).toString();
        }
        return null;
    }*/
    public static String md5(final String sourceString) {
        return SecureUtil.md5(sourceString);
    }

    public static String sha256(final String source) {
        return SecureUtil.sha256(source);
    }
}
