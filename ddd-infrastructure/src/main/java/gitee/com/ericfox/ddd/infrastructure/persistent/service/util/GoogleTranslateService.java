package gitee.com.ericfox.ddd.infrastructure.persistent.service.util;

import cn.hutool.json.JSONArray;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.framework.pojo.OkHttpResponse;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.JSONUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;

@Service
public class GoogleTranslateService {
    @Getter
    public enum TL implements BaseEnum<GoogleTranslateService.TL, String> {
        TL_EN("en", "英语"),
        TL_JA("ja", "日语"),
        TL_ZH_CN("zh-CN", "中文");

        private final String code;
        private final String comment;

        TL(String code, String comment) {
            this.code = code;
            this.comment = comment;
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public TL[] getEnums() {
            return values();
        }
    }

    @Resource
    private OkHttpService okHttpService;

    // 实现js的charAt方法
    private char charAt(Object obj, int index) {
        char[] chars = obj.toString().toCharArray();
        return chars[index];
    }

    // 实现js的charCodeAt方法
    private int charCodeAt(Object obj, int index) {
        char[] chars = obj.toString().toCharArray();
        return chars[index];
    }

    // 实现js的Number方法
    private int number(Object cc) {
        try {
            long a = Long.parseLong(cc.toString());
            int b = a > 2147483647 ? (int) (a - 4294967296L) : a < -2147483647 ? (int) (a + 4294967296L) : (int) a;
            return b;
        } catch (Exception ex) {
            return 0;
        }
    }

    private String b(long a, String b) {
        for (int d = 0; d < b.length() - 2; d += 3) {
            char c = b.charAt(d + 2);
            int c0 = 'a' <= c ? charAt(c, 0) - 87 : number(c);
            long c1 = '+' == b.charAt(d + 1) ? a >> c0 : a << c0;
            a = '+' == b.charAt(d) ? a + c1 & 4294967295L : a ^ c1;
        }
        a = number(a);
        return a + "";
    }

    private String tk(String a, String TKK) {
        String[] e = TKK.split("\\.");
        int d = 0;
        int h = 0;
        int[] g = new int[a.length() * 3];
        h = number(e[0]);
        for (int f = 0; f < a.length(); f++) {
            int c = charCodeAt(a, f);
            if (128 > c) {
                g[d++] = c;
            } else {
                if (2048 > c) {
                    g[d++] = c >> 6 | 192;
                } else {
                    if (55296 == (c & 64512) && f + 1 < a.length() && 56320 == (charCodeAt(a, f + 1) & 64512)) {
                        c = 65536 + ((c & 1023) << 10) + charCodeAt(a, ++f) & 1023;
                        g[d++] = c >> 18 | 240;
                        g[d++] = c >> 12 & 63 | 128;
                    } else {
                        g[d++] = c >> 12 | 224;
                        g[d++] = c >> 6 & 63 | 128;
                        g[d++] = c & 63 | 128;
                    }
                }
            }
        }
        int gl = 0;
        for (int b : g) {
            if (b != 0) {
                gl++;
            }
        }
        int[] g0 = new int[gl];
        gl = 0;
        for (int c : g) {
            if (c != 0) {
                g0[gl] = c;
                gl++;
            }
        }
        long aa = h;
        for (d = 0; d < g0.length; d++) {
            aa += g0[d];
            aa = number(b(aa, "+-a^+6"));
        }
        aa = number(b(aa, "+-3^+b+-f"));
        long bb = aa ^ number(e[1]);
        aa = bb;
        aa = aa + bb;
        bb = aa - bb;
        aa = aa - bb;
        if (0 > aa) {
            aa = (aa & 2147483647) + 2147483648L;
        }
        aa %= (long) 1e6;
        return aa + "" + "." + (aa ^ h);
    }

    /**
     * 从中文翻译为指定语言
     */
    public String translateFromChinese(TL langTo, String text) {
        return translate(TL.TL_ZH_CN, langTo, text);
    }

    /**
     * 翻译为指定语言
     */
    @SneakyThrows
    public String translate(TL langFrom, TL langTo, String text) {
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + langFrom.getCode() +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
        OkHttpResponse okHttpResponse = okHttpService.get(url);
        return parseResult(okHttpResponse.getResponse());
    }

    @SneakyThrows
    private String parseResult(String inputJson) {
        JSONArray jsonArray = JSONUtil.parseArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
//        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
        String result = "";

        for (int i = 0; i < jsonArray2.size(); i++) {
            result += ((JSONArray) jsonArray2.get(i)).get(0).toString();
        }
        return result;
    }
}
