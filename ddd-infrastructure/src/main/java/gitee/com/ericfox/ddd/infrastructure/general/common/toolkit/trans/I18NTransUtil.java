package gitee.com.ericfox.ddd.infrastructure.general.common.toolkit.trans;

import gitee.com.ericfox.ddd.common.toolkit.coding.PropsUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.util.GoogleTranslateService;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 多国语言
 */
@Slf4j
public class I18NTransUtil {
    private static Properties properties;
    private static GoogleTranslateService googleTranslateService;

    public static String getText(String text) {
        //TODO 多国语言支持
        String property = getProperties().getProperty(text);
        if (property == null) {
            return getGoogleTranslateService().translateFromChinese(GoogleTranslateService.TL.TL_EN, text);
        }
        return property;
    }

    private static GoogleTranslateService getGoogleTranslateService() {
        if (googleTranslateService == null) {
            googleTranslateService = SpringUtil.getBean(GoogleTranslateService.class);
        }
        return googleTranslateService;
    }

    private static Properties getProperties() {
        if (properties == null) {
            properties = PropsUtil.get("infrastructure/i18n/framework_en_US.properties");
        }
        return properties;
    }
}
