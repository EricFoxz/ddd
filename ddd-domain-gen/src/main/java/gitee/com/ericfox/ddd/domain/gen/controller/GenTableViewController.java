package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class GenTableViewController implements BaseJavaFxController, GenLogger {
    public static final String TABLE_CHECK_BOX_PREFIX = "tableCheckBox:";
    private static final String YES = "√";
    private static final String NO = "×";

    @Resource
    private CustomProperties customProperties;

    @Setter
    private String domainName;

    @FXML
    private ToolBar tableListToolBar;
    @FXML
    private Button sortAllButton;
    @FXML
    private CheckBox checkAllCheckBox;
    @FXML
    private ChoiceBox<String> repoTypeChoiceBox;
    @FXML
    private Button writeButton;
    @FXML
    private TabPane codeTabPane;

    /**
     * 代码视图
     */
    @FXML
    private Label poLabel;
    @FXML
    private TextArea poTextArea;
    @FXML
    private Label daoLabel;
    @FXML
    private TextArea daoTextArea;
    @FXML
    private Label entityLabel;
    @FXML
    private TextArea entityTextArea;
    @FXML
    private Label entityBaseLabel;
    @FXML
    private TextArea entityBaseTextArea;
    @FXML
    private Label dtoLabel;
    @FXML
    private TextArea dtoTextArea;
    @FXML
    private Label dtoBaseLabel;
    @FXML
    private TextArea dtoBaseTextArea;
    @FXML
    private Label serviceLabel;
    @FXML
    private TextArea serviceTextArea;
    @FXML
    private Label serviceBaseLabel;
    @FXML
    private TextArea serviceBaseTextArea;
    @FXML
    private Label pageParamLabel;
    @FXML
    private TextArea pageParamTextArea;

    @Override
    public void initialize() {
        sortAllButton.setOnAction(event -> {
            this.sortAll();
        });
        checkAllCheckBox.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                selectAll(checkAllCheckBox.isSelected());
            }
        });
        //存储类型
        for (RepoTypeStrategyEnum value : RepoTypeStrategyEnum.values()) {
            repoTypeChoiceBox.getItems().add(value.getCode());
        }
        repoTypeChoiceBox.setOnAction(event -> {
            String str = repoTypeChoiceBox.getSelectionModel().getSelectedItem();
            RepoTypeStrategyEnum repoEnum = RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY.getEnumByCode(str);
            if (repoEnum == null) {
                return;
            }
            List<TableXmlBean> list = CollUtil.newArrayList();
            tableListToolBar.getItems().forEach(node -> {
                if (node instanceof CheckBox && node.getUserData() != null && ((CheckBox) node).isSelected()) {
                    TableXmlBean tableXml = (TableXmlBean) node.getUserData();
                    list.add(tableXml);
                }
            });
            if (CollUtil.isEmpty(list)) {
                logInfo(log, "没有选中任何表");
                return;
            }
            list.forEach(item -> {
                this.changeRepo(item, repoEnum);
            });
            codeTabPane.setDisable(false);
            GenComponents.getGenTableWritingService().publishTablesToRuntime();
        });
        //生成按钮
        writeButton.setOnAction(event -> {
            this.multiWriteCode();
        });
        Font font = null;
        try {
            InputStream inputStream = new ClassPathResource("infrastructure/font/YaHei.Consolas.1.12.ttf").getInputStream();
            font = Font.loadFont(inputStream, 14);
            IoUtil.close(inputStream);
        } catch (Exception e) {
            logError(log, "genTableViewController::initialize 加载字体失败", e);
        }
        poTextArea.setFont(font);
        daoTextArea.setFont(font);
        entityTextArea.setFont(font);
        entityBaseTextArea.setFont(font);
        dtoTextArea.setFont(font);
        dtoBaseTextArea.setFont(font);
        serviceTextArea.setFont(font);
        serviceBaseTextArea.setFont(font);
        pageParamTextArea.setFont(font);
        //label点击事件
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Label label = (Label) event.getPickResult().getIntersectedNode().getParent();
                if (NO.equals(label.getText())) {
                    label.setTextFill(Paint.valueOf("LIME"));
                    label.setText(YES);
                } else if (YES.equals(label.getText())) {
                    label.setTextFill(Paint.valueOf("RED"));
                    label.setText(NO);
                }
            }
        };
        poLabel.setOnMouseClicked(eventHandler);
        daoLabel.setOnMouseClicked(eventHandler);
        entityLabel.setOnMouseClicked(eventHandler);
        entityBaseLabel.setOnMouseClicked(eventHandler);
        dtoLabel.setOnMouseClicked(eventHandler);
        dtoBaseLabel.setOnMouseClicked(eventHandler);
        serviceLabel.setOnMouseClicked(eventHandler);
        serviceBaseLabel.setOnMouseClicked(eventHandler);
        pageParamLabel.setOnMouseClicked(eventHandler);
    }

    @Override
    public void ready() {
        if (GenTableLoadingService.getDomainMap().containsKey(domainName)) {
            renderTableNames(GenTableLoadingService.getDomainMap().get(domainName).values(), true);
        }
    }

    /**
     * 渲染列表
     *
     * @param force 是否强制重新加载（会先清空再加载）
     */
    public void renderTableNames(Collection<TableXmlBean> tableXmlBeanList, boolean force) {
        ObservableList<Node> itemList = tableListToolBar.getItems();
        if (force && itemList.size() > 1) {
            tableListToolBar.getItems().remove(1, itemList.size());
        }
        if (CollUtil.isEmpty(tableXmlBeanList)) {
            return;
        }
        tableXmlBeanList.forEach(this::renderTableName);
    }

    public void renderTableName(TableXmlBean tableXml) {
        AtomicReference<CheckBox> exist = new AtomicReference<>();
        tableListToolBar.getItems().forEach(item -> {
            if (item instanceof CheckBox && StrUtil.equals(item.getId(), TABLE_CHECK_BOX_PREFIX + tableXml.getMeta().getTableName())) {
                exist.set((CheckBox) item);
            }
        });
        CheckBox checkBox = exist.get();
        int index = tableListToolBar.getItems().indexOf(checkBox);
        checkBox = new CheckBox();
        checkBox.setUserData(tableXml);
        SplitMenuButton splitMenuButton = new SplitMenuButton();
        splitMenuButton.setText(tableXml.getMeta().getTableName());
        splitMenuButton.setUserData(tableXml);
        splitMenuButton.setId(TABLE_CHECK_BOX_PREFIX + tableXml.getMeta().getTableName());
        splitMenuButton.setOnMouseClicked(event -> {
            renderCode(tableXml);
        });
        splitMenuButton.setMaxWidth(190);
        Label label = new Label(tableXml.getMeta().getRepoTypeStrategyEnum().getCode());
        label.setTextFill(Paint.valueOf("BLUE"));
        splitMenuButton.setGraphic(label);
        splitMenuButton.setTooltip(new Tooltip(tableXml.getMeta().getTableName()));
        if (StrUtil.isNotBlank(tableXml.getMeta().getTableComment())) {
            splitMenuButton.setTooltip(new Tooltip(tableXml.getMeta().getTableComment()));
        }
        checkBox.setId(TABLE_CHECK_BOX_PREFIX + tableXml.getMeta().getTableName());
        checkBox.setText(null);
        checkBox.setWrapText(true);
        checkBox.setGraphic(splitMenuButton);
        if (index >= 0) {
            tableListToolBar.getItems().set(index, checkBox);
        } else {
            tableListToolBar.getItems().add(checkBox);
        }
    }

    private void selectAll(boolean isSelect) {
        tableListToolBar.getItems().forEach(ele -> {
            if (ele instanceof CheckBox) {
                ((CheckBox) ele).setSelected(isSelect);
            }
        });
    }

    /**
     * 渲染代码
     */
    private void renderCode(TableXmlBean tableXml) {
        logInfo(log, "genTableViewController::renderCode 预览代码");
        codeTabPane.setDisable(false);
        String poCode = GenComponents.getGenCodeService().getPoCode(tableXml);
        String daoCode = GenComponents.getGenCodeService().getDaoCode(tableXml);
        String entityCode = GenComponents.getGenCodeService().getEntityCode(tableXml);
        String entityBaseCode = GenComponents.getGenCodeService().getEntityBaseCode(tableXml);
        String dtoCode = GenComponents.getGenCodeService().getDtoCode(tableXml);
        String dtoBaseCode = GenComponents.getGenCodeService().getDtoBaseCode(tableXml);
        String serviceCode = GenComponents.getGenCodeService().getServiceCode(tableXml);
        String serviceBaseCode = GenComponents.getGenCodeService().getServiceBaseCode(tableXml);
        String pageParamCode = GenComponents.getGenCodeService().getPageParamCode(tableXml);
        poTextArea.setText(poCode);
        daoTextArea.setText(daoCode);
        entityTextArea.setText(entityCode);
        entityBaseTextArea.setText(entityBaseCode);
        dtoTextArea.setText(dtoCode);
        dtoBaseTextArea.setText(dtoBaseCode);
        serviceTextArea.setText(serviceCode);
        serviceBaseTextArea.setText(serviceBaseCode);
        pageParamTextArea.setText(pageParamCode);
    }

    /**
     * 把生成的代码写进项目
     */
    private void multiWriteCode() {
        logInfo(log, "genTableViewController::multiWriteCode 正在生成代码");
        try {
            List<TableXmlBean> list = CollUtil.newArrayList();
            tableListToolBar.getItems().forEach(node -> {
                if (node instanceof CheckBox && node.getUserData() != null && ((CheckBox) node).isSelected()) {
                    TableXmlBean tableXml = (TableXmlBean) node.getUserData();
                    list.add(tableXml);
                }
            });
            if (CollUtil.isEmpty(list)) {
                logInfo(log, "没有选中任何表");
                return;
            }
            list.forEach(this::writeCode);
            logInfo(log, "genTableViewController::multiWriteCode 生成代码完成");
        } catch (Exception e) {
            logError(log, "生成代码异常", e);
            throw new ProjectFrameworkException("生成代码异常" + e.getMessage());
        }
    }

    /**
     * 改变持久化方式
     */
    private void changeRepo(TableXmlBean tableXml, RepoTypeStrategyEnum repoEnum) {
        String domainName = tableXml.getMeta().getDomainName();
        String tableName = tableXml.getMeta().getTableName();
        tableXml.getMeta().setRepoTypeStrategyEnum(repoEnum);
        GenTableLoadingService.getDomainMap().get(domainName).put(tableName, tableXml);
        renderTableName(tableXml);
        GenComponents.getGenTableWritingService().writeTableXml(tableXml);
    }

    /**
     * 生成/写入代码
     */
    private void writeCode(TableXmlBean tableXml) {
        if (YES.equals(poLabel.getText())) {
            String poCode = GenComponents.getGenCodeService().getPoCode(tableXml);
            String filePath = ClassUtil.getClassPaths(BasePo.class.getPackage().getName()).stream().findFirst().get().replaceAll("/target/classes", "/src/main/java") + "/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().toMap().get("ClassName") + ".java";
            File file = FileUtil.file(filePath);
            FileUtil.touch(file);
            IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, poCode);
        }
        if (YES.equals(daoLabel.getText())) {

        }
    }

    private void sortAll() {
        tableListToolBar.getItems().setAll(
                tableListToolBar.getItems().stream().sorted((node1, node2) -> {
                    if (!(node1 instanceof CheckBox) || node1.getId() == null) {
                        return -1;
                    }
                    if (!(node2 instanceof CheckBox) || node2.getId() == null) {
                        return 1;
                    }
                    if (((CheckBox) node1).getGraphic() == null) {
                        return -1;
                    } else if (((CheckBox) node2).getGraphic() == null) {
                        return 1;
                    }
                    Node graphic1 = ((CheckBox) node1).getGraphic();
                    Node graphic2 = ((CheckBox) node2).getGraphic();
                    if (graphic1 instanceof SplitMenuButton && graphic2 instanceof SplitMenuButton) {
                        return StrUtil.compare(graphic1.getId(), graphic2.getId(), true);
                    }
                    return 0;
                }).collect(Collectors.toList())
        );
        logInfo(log, "genTableViewController::sortAll 排序完成");
    }
}
