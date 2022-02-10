package gitee.com.ericfox.ddd.domain.gen.common;

import javafx.scene.text.Font;

public interface GenBaseLogger {
    void info(Font font, String... msg);

    void warn(Font font, String... msg);

    void debug(Font font, String... msg);

    void error(Font font, String... msg);
}
