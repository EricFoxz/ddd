package gitee.com.ericfox.ddd.domain.gen.controller;

import javafx.application.Platform;

public interface BaseJavaFxController {
    /**
     * 由JavaFX触发的钩子，当各个组件实例化完成后调用
     */
    default void initialize() {
    }

    /**
     * 由本框架触发，规定是当把controller set进GenComponents.java后触发
     */
    default void ready() {
    }

    default void asyncExecute(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
