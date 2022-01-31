package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenFX;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class GenIndexController implements BaseJavaFxController {
    @FXML
    private Button readTableByJavaButton;
    @FXML
    private Button readTableByOrmButton;
    @FXML
    private Button initAllButton;
    @FXML
    private Button testButton;

    @FXML
    private MenuItem debugModelMenuItem;
    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private TabPane mainTabPane;

    @Override
    public void initialize() {
        beginLoading();
        //从XML获取数据结构
        initAllButton.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().initAll();
                renderAllTableList();
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
        //Debug菜单按钮
        debugModelMenuItem.setOnAction(event -> {
            try {
                GenFX.initDebugStage(GenComponents.getIndexStage());
            } catch (Exception e) {
                log.error("genIndexController::initialize 初始化debug窗口失败", e);
            }
        });
        //关闭菜单按钮
        closeMenuItem.setOnAction(event -> Platform.exit());
        finishLoading();
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

    /**
     * 渲染视图
     */
    private synchronized void renderAllTableList() {
        Set<String> domainSet = CollUtil.newHashSet();
        domainSet.addAll(GenTableLoadingService.getDomainMap().keySet());
        mainTabPane.getTabs().forEach(tab -> domainSet.add(tab.getId()));
        domainSet.forEach(this::renderTableList);
    }

    private synchronized void renderTableList(String domainName) {
        Map<String, Document> tableMap = GenTableLoadingService.getDomainMap().get(domainName);
        if (tableMap == null) {
            AtomicReference<Tab> removeTab = new AtomicReference<>();
            Tab tmp;
            mainTabPane.getTabs().forEach(ele -> {
                if (StrUtil.equals(ele.getId(), domainName)) {
                    removeTab.set(ele);
                }
            });
            if ((tmp = removeTab.get()) != null) {
                mainTabPane.getTabs().remove(tmp);
            }
        }
        GenTableLoadingService.getDomainMap().get(domainName).forEach((key, value) -> {

        });
    }
}
