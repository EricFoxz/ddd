package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class GenTableViewController implements BaseJavaFxController, GenLogger {
    public static final String TABLE_CHECK_BOX_PREFIX = "tableCheckBox:";

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
        codeTabPane.setDisable(false);
        String poCode = GenComponents.getGenCodeService().genPo(tableXml);
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
