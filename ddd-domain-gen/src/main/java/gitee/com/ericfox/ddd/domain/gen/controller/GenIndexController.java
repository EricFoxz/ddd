package gitee.com.ericfox.ddd.domain.gen.controller;

import com.sun.javafx.scene.control.skin.LabeledText;
import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenFX;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class GenIndexController implements BaseJavaFxController, GenLogger {
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
                GenComponents.getDomainViewController().refresh();
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
        closeMenuItem.setOnAction(event -> {
            GenComponents.getIndexStage().close();
            Platform.exit();
        });
        //主容器 实现鼠标中键关闭标签
        mainTabPane.setOnMouseClicked(event -> {
            if (MouseButton.MIDDLE.equals(event.getButton())) {
                Node node = event.getPickResult().getIntersectedNode();
                if (node instanceof LabeledText) {
                    String text = ((LabeledText) node).getText();
                    mainTabPane.getTabs().removeIf(ele -> StrUtil.equals(ele.getId(), "domainName:" + text));
                }
            }
        });
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new ClassPathResource(GenConstants.DOMAIN_VIEW_FXML_PATH).getURL());
            mainTabPane.getTabs().get(0).setContent(loader.load());
            GenComponents.setDomainViewController(loader.getController());
        } catch (Exception e) {
            logError(log, "genIndexController::initialize 初始化主容器异常", e);
        }
        testButton.setOnAction(event -> {
            String str = GenComponents.getGenCodeService().gen();
        });
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
    public synchronized void renderAllTableList() {
        Set<String> domainSet = CollUtil.newHashSet();
        domainSet.addAll(GenTableLoadingService.getDomainMap().keySet());
        mainTabPane.getTabs().forEach(tab -> {
            if (StrUtil.startWith(tab.getId(), "domainName:")) {
                domainSet.add(tab.getId());
            }
        });
        domainSet.forEach(this::renderTableList);
    }

    @SneakyThrows
    public synchronized void renderTableList(String domainName) {
        String finalDomainName = "domainName:" + domainName;
        Map<String, Document> tableMap = GenTableLoadingService.getDomainMap().get(domainName);
        if (tableMap == null) {
            AtomicReference<Tab> removeTab = new AtomicReference<>();
            Tab tab;
            mainTabPane.getTabs().forEach(ele -> {
                if (StrUtil.equals(ele.getId(), finalDomainName)) {
                    removeTab.set(ele);
                }
            });
            if ((tab = removeTab.get()) != null) {
                mainTabPane.getTabs().remove(tab);
            }
            return;
        }
        Tab tab;
        AtomicReference<Tab> existTab = new AtomicReference<>();
        tableMap.forEach((key, value) -> {
            mainTabPane.getTabs().forEach(ele -> {
                if (StrUtil.equals(ele.getId(), finalDomainName)) {
                    existTab.set(ele);
                }
            });
        });
        tab = existTab.get();
        if (tab != null) { //更新
        } else { //创建
            tab = new Tab();
            tab.setText(domainName);
            tab.setId(finalDomainName);
            tab.setClosable(true);
            mainTabPane.getTabs().add(tab);
            tab.getTabPane().setFocusTraversable(true);
            Tooltip tooltip = new Tooltip();
            tooltip.setText("点击鼠标中键可关闭标签");
            tab.setTooltip(tooltip);
            tab.setContent(FXMLLoader.load(new ClassPathResource(GenConstants.TABLE_VIEW_FXML_PATH).getURL()));
        }
    }
}
