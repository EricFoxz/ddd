package gitee.com.ericfox.ddd.domain.gen.controller;

import com.sun.javafx.scene.control.skin.LabeledText;
import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenFX;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.FileUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class GenIndexController implements BaseJavaFxController, GenLogger {
    public static final String DOMAIN_TAB_PREFIX = "domainName:" ;
    /**
     * 导入功能组件
     */
    @FXML
    private MenuButton importMenuButton;
    @FXML
    private MenuItem importTableByMySqlMenuItem;
    @FXML
    private MenuItem importTableByJavaMenuItem;
    @FXML
    private MenuItem importTableByXmlMenuItem;

    /**
     * 初始化组件
     */
    @FXML
    private Button initAllButton;
    @FXML
    private Button testButton;

    /**
     * 进度条组件
     */
    @FXML
    private ProgressBar indexProgressBar;
    @FXML
    @Getter
    private Text indexInfoText;

    /**
     * debug模式组件
     */
    @FXML
    private MenuItem debugModelMenuItem;
    /**
     * 退出按钮组件
     */
    @FXML
    private MenuItem closeMenuItem;

    /**
     * 主视图组件
     */
    @FXML
    @Getter
    private TabPane mainTabPane;

    @Override
    public void initialize() {
        beginLoading();
        //从项目中的xml获取数据结构
        initAllButton.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().initAll();
                GenComponents.getDomainViewController().refresh();
                renderAllTableList(true);
                finishLoading();
            });
        });
        //从java代码获取数据结构
        importTableByJavaMenuItem.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().importTableByJavaClass(null, null);
                finishLoading();
            });
        });
        //从MySql数据库获取数据结构
        importTableByMySqlMenuItem.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                GenComponents.getGenTableLoadingService().importTableByMySql("", "", "");
                finishLoading();
            });
        });
        //从XML导入数据结构
        importTableByXmlMenuItem.setOnAction(event -> {
            beginLoading();
            asyncExecute(() -> {
                //TODO-支持 导入导出功能
                File file = FileUtil.file();
                GenComponents.getGenTableLoadingService().importTableByXml(file);
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
                    mainTabPane.getTabs().removeIf(ele -> StrUtil.equals(ele.getId(), DOMAIN_TAB_PREFIX + text));
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
            String str = GenComponents.getGenCodeService().getPoCode(GenTableLoadingService.getDomainMap().get("sys").get("sys_user"));
//            TableXmlBean bean = new TableXmlBean();
//            TableXmlBean.MetaBean meta = bean.getMeta();
//            meta.setTableName("sys_user");
//            meta.setClassName("sysUser");
//            meta.setDomainName("sys");
//            meta.setIdField("id");
//            meta.setRepoTypeStrategyEnum(RepoTypeStrategyEnum.LUCENE_REPO_STRATEGY);
//            meta.getFieldClassMap().put("id", ReflectClassNameConstants.LONG);
//            meta.getFieldClassMap().put("username", ReflectClassNameConstants.STRING);
//            meta.getFieldClassMap().put("create_time", ReflectClassNameConstants.LONG);
//            meta.getFieldLengthMap().put("id", 8);
//            meta.getFieldLengthMap().put("username", 50);
//            meta.getFieldLengthMap().put("create_time", 8);
//            GenComponents.getGenTableWritingService().writeTableXml(bean);
//            GenComponents.getGenTableWritingService().publishTablesToRuntime();
        });
        finishLoading();
    }

    /**
     * 开始执行，禁用交互
     */
    public void beginLoading() {
        initAllButton.setDisable(true);
        importMenuButton.setDisable(true);
        indexProgressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
    }

    /**
     * 结束执行，启用交互
     */
    public void finishLoading() {
        initAllButton.setDisable(false);
        importMenuButton.setDisable(false);
        indexProgressBar.setProgress(0);
    }

    /**
     * 渲染视图
     *
     * @param force true先移除所有的领域标签页，达到全部更新的效果。false不移除已有标签页
     */
    public synchronized void renderAllTableList(boolean force) {
        logDebug(log, "开始渲染所有领域视图");
        ObservableList<Tab> tabs = mainTabPane.getTabs();
        if (force && tabs.size() > 1) {
            tabs.remove(1, tabs.size());
        }
        Set<String> domainSet = CollUtil.newHashSet();
        domainSet.addAll(GenTableLoadingService.getDomainMap().keySet());
        mainTabPane.getTabs().forEach(tab -> {
            if (StrUtil.startWith(tab.getId(), DOMAIN_TAB_PREFIX)) {
                domainSet.add(tab.getId());
            }
        });
        domainSet.forEach(this::renderTableListView);
        logDebug(log, "渲染完成");
    }

    /**
     * 加载表详情页签
     */
    @SneakyThrows
    public synchronized Tab renderTableListView(String domainName) {
        String finalDomainName = DOMAIN_TAB_PREFIX + domainName;
        Map<String, TableXmlBean> tableMap = GenTableLoadingService.getDomainMap().get(domainName);
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
            return null;
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
        int index = -1;
        if (tab != null) {
            index = mainTabPane.getTabs().indexOf(tab);
        }
        tab = new Tab();
        tab.setText(domainName);
        tab.setId(finalDomainName);
        tab.setClosable(true);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new ClassPathResource(GenConstants.TABLE_VIEW_FXML_PATH).getURL());
        tab.setContent(fxmlLoader.load());
        GenTableViewController tableViewController = fxmlLoader.getController();
        tableViewController.setDomainName(domainName);
        GenComponents.putGenTableViewController(domainName, tableViewController);
        Tooltip tooltip = new Tooltip();
        tooltip.setText("点击鼠标中键可关闭标签");
        tooltip.setFont(Font.font(GenConstants.DEFAULT_FONT_FAMILY, FontWeight.NORMAL, 14));
        tab.setTooltip(tooltip);
        if (index >= 0) {
            mainTabPane.getTabs().set(index, tab);
        } else {
            mainTabPane.getTabs().add(tab);
        }
        return tab;
    }
}
