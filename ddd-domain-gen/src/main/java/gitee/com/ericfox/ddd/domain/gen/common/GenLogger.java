package gitee.com.ericfox.ddd.domain.gen.common;

import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.slf4j.Logger;

public interface GenLogger {
    Font normalFont = Font.font(GenConstants.DEFAULT_FONT_FAMILY, FontWeight.NORMAL, 12);
    Font boldFont = Font.font(GenConstants.DEFAULT_FONT_FAMILY, FontWeight.BOLD, 12);

    default void logInfo(Logger log, String msg, Object... objects) {
        Text indexInfoText = GenComponents.getIndexController().getIndexInfoText();
        indexInfoText.setFont(normalFont);
        indexInfoText.setText(msg);
        if (GenComponents.getDebugController() == null) {
            log.info(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().info(normalFont, msg);
        }
    }

    default void logWarn(Logger log, String msg, Object... objects) {
        Text indexInfoText = GenComponents.getIndexController().getIndexInfoText();
        indexInfoText.setFont(boldFont);
        indexInfoText.setText(msg);
        if (GenComponents.getDebugController() == null) {
            log.warn(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().warn(boldFont, msg);
        }
    }

    default void logDebug(Logger log, String msg, Object... objects) {
        if (GenComponents.getDebugController() == null) {
            log.debug(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().debug(normalFont, msg);
        }
    }

    default void logError(Logger log, String msg, Object... objects) {
        Text indexInfoText = GenComponents.getIndexController().getIndexInfoText();
        indexInfoText.setFont(boldFont);
        indexInfoText.setText(msg);
        if (GenComponents.getDebugController() == null) {
            log.error(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().error(boldFont, msg);
        }
    }
}
