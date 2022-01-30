package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenHomeController implements BaseJavaFxController {
    @FXML
    private Button readTableByJavaButton;
    @FXML
    private Button readTableByOrmButton;

    @Override
    public void initialize() {
        //从java代码获取数据结构
        readTableByJavaButton.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().readTableByJavaClassHandler(event);
                finishLoading();
            });
        });

        readTableByOrmButton.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().readTableByJavaClassHandler(event);
                finishLoading();
            });
        });
    }

    public void beginLoading() {
        readTableByJavaButton.setDisable(true);
        readTableByOrmButton.setDisable(true);
    }

    public void finishLoading() {
        readTableByJavaButton.setDisable(false);
        readTableByOrmButton.setDisable(false);
    }
}
