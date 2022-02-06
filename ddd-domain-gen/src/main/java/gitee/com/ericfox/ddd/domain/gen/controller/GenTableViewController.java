package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.FileUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class GenTableViewController implements BaseJavaFxController, GenLogger {
    public static final String TABLE_CHECK_BOX_PREFIX = "tableCheckBox:";
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
    private TabPane codeTabPane;

    @FXML
    private TextArea poTextArea;
    @FXML
    private TextArea daoTextArea;
    @FXML
    private TextArea entityTextArea;
    @FXML
    private TextArea dtoTextArea;

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
        if (checkBox == null) {
            checkBox = new CheckBox();
            SplitMenuButton splitMenuButton = new SplitMenuButton();
            splitMenuButton.setText(tableXml.getMeta().getTableName());
            splitMenuButton.setUserData(tableXml);
            splitMenuButton.setId(TABLE_CHECK_BOX_PREFIX + tableXml.getMeta().getTableName());
            splitMenuButton.setOnMouseClicked(event -> {
                renderCode(tableXml);
            });
            splitMenuButton.setMaxWidth(190);
            if (StrUtil.isNotBlank(tableXml.getMeta().getTableComment())) {
                splitMenuButton.setTooltip(new Tooltip(tableXml.getMeta().getTableComment()));
            }
            tableListToolBar.getItems().add(checkBox);
            checkBox.setId(TABLE_CHECK_BOX_PREFIX + tableXml.getMeta().getTableName());
            checkBox.setText(null);
            checkBox.setWrapText(true);
            checkBox.setGraphic(splitMenuButton);
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
        dtoTextArea.setText(dtoCode);
    }

    private void genCode(TableXmlBean tableXml) {
        logInfo(log, "genTableViewController::genCode 正在生成代码");
        try {
            //TODO
            String poCode = GenComponents.getGenCodeService().getPoCode(tableXml);
            File file = FileUtil.file(new ClassPathResource(customProperties.getRootPackage() + "").getURL());
        } catch (Exception e) {
            logError(log, "生成代码异常", e);
            throw new ProjectFrameworkException("生成代码异常" + e.getMessage());
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
    }
}
