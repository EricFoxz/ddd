package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenIndexController implements BaseJavaFxController {
    @FXML
    private Button readTableByJavaButton;
    @FXML
    private Button readTableByOrmButton;
    @FXML
    private Button initAllButton;

    @FXML
    private MenuItem debugModelMenuItem;

    @Override
    public void initialize() {
        //从XML获取数据结构
        initAllButton.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().initAll();
                finishLoading();
            });
        });
        //从java代码获取数据结构
        readTableByJavaButton.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().readTableByJavaClassHandler(event);
                finishLoading();
            });
        });
        //从数据库获取数据结构
        readTableByOrmButton.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().readTableByJavaClassHandler(event);
                finishLoading();
            });
        });
        //Debug模式
        debugModelMenuItem.setOnAction(event -> {
            try {
                GenFX.initDebugStage(GenComponents.getIndexStage());
            } catch (Exception e) {
                log.error("genIndexController::initialize 初始化debug窗口失败", e);
            }
        });
    }

    public void beginLoading() {
        readTableByJavaButton.setDisable(true);
        readTableByOrmButton.setDisable(true);
        initAllButton.setDisable(true);
    }

    public void finishLoading() {
        readTableByJavaButton.setDisable(false);
        readTableByOrmButton.setDisable(false);
        initAllButton.setDisable(false);
    }
}
