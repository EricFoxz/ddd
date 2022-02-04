package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class GenTableViewController implements BaseJavaFxController, GenLogger {
    @Setter
    private String domainName;

    @FXML
    private ToolBar tableListToolBar;
    @FXML
    private CheckBox checkAllCheckBox;

    @Override
    public void initialize() {
        checkAllCheckBox.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                selectAll(checkAllCheckBox.isSelected());
            }
        });
    }

    /**
     *
     */
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
        tableListToolBar.getItems().forEach(ele -> {
            if (ele instanceof CheckBox) {
                CheckBox checkBox = new CheckBox();
                checkBox.getId();
            }
        });
    }

    private void selectAll(boolean isSelect) {
        tableListToolBar.getItems().forEach(ele -> {
            if (ele instanceof CheckBox) {
                ((CheckBox) ele).setSelected(isSelect);
            }
        });
    }
}
