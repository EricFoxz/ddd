package gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding;

import gitee.com.ericfox.ddd.infrastructure.persistent.service.util.GoogleTranslateService;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class I18NUtil {
    private final Properties properties = PropsUtil.get("framework_en_US.properties");
    private static GoogleTranslateService googleTranslateService;

    public static String translateToEnglishFromChinese(String text) {
        return getGoogleTranslateService().translateFromChinese(GoogleTranslateService.TL.TL_EN, text);
    }

    private static GoogleTranslateService getGoogleTranslateService() {
        if (googleTranslateService == null) {
            googleTranslateService = SpringUtil.getBean(GoogleTranslateService.class);
        }
        return googleTranslateService;
    }
}
