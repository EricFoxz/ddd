package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import javafx.scene.text.Font;

public interface BaseLogger {
    void info(Font font, String... msg);

    void warn(Font font, String... msg);

    void debug(Font font, String... msg);

    void error(Font font, String... msg);
}
