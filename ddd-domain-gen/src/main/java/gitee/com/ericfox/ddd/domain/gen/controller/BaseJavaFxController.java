package gitee.com.ericfox.ddd.domain.gen.controller;

import javafx.application.Platform;

public interface BaseJavaFxController {
    default void initialize() {
    }

    default void asyncExecute(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
