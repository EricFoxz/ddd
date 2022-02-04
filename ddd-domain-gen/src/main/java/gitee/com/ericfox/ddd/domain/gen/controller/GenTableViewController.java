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
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class GenTableViewController implements BaseJavaFxController, GenLogger {
    public static final String TABLE_CHECK_BOX_PREFIX = "tableCheckBox:";

    @Setter
    private String domainName;

    @FXML
    private ToolBar tableListToolBar;
    @FXML
    private CheckBox checkAllCheckBox;
    @FXML
    private TabPane codeTabPane;

    @Override
    public void initialize() {
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
            splitMenuButton.setOnMouseClicked(event -> {
                renderCode(tableXml);
            });
            splitMenuButton.setMaxWidth(190);
            tableListToolBar.getItems().add(checkBox);
            checkBox.setGraphic(splitMenuButton);
            tableListToolBar.getItems().sorted((node1, node2) -> StrUtil.compare(node1.getId(), node2.getId(), true));
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
}
