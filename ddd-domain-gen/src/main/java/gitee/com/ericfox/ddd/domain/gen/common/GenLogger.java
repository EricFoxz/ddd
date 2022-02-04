package gitee.com.ericfox.ddd.domain.gen.common;

import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import org.slf4j.Logger;

public interface GenLogger {
    default void logInfo(Logger log, String msg, Object... objects) {
        GenComponents.getIndexController().getIndexInfoText().setText(msg);
        if (GenComponents.getDebugController() == null) {
            log.info(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().info(msg);
        }
    }

    default void logWarn(Logger log, String msg, Object... objects) {
        GenComponents.getIndexController().getIndexInfoText().setText(msg);
        if (GenComponents.getDebugController() == null) {
            log.warn(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().warn(msg);
        }
    }

    default void logDebug(Logger log, String msg, Object... objects) {
        GenComponents.getIndexController().getIndexInfoText().setText(msg);
        if (GenComponents.getDebugController() == null) {
            log.debug(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().debug(msg);
        }
    }

    default void logError(Logger log, String msg, Object... objects) {
        GenComponents.getIndexController().getIndexInfoText().setText(msg);
        if (GenComponents.getDebugController() == null) {
            log.error(msg, objects);
        } else {
            GenComponents.getDebugController().getLogger().error(msg);
        }
    }
}
