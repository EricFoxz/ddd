package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenDomainViewController implements BaseJavaFxController, GenLogger {
    @FXML
    private BarChart<String, Number> domainBarChart;
    @FXML
    private CategoryAxis domainNameCategoryAxis;

    @Override
    public void initialize() {
    }

    public synchronized void refresh() {
        domainBarChart.getData().clear();
        GenTableLoadingService.getDomainMap().forEach((domainName, value) -> {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(domainName);
            XYChart.Data<String, Number> data = new XYChart.Data<>(domainName, value.size());
            series.getData().add(data);
            domainBarChart.getData().add(series);
            data.getNode().setCursor(Cursor.HAND);
            data.getNode().setScaleX(20);
            data.getNode().setOnMouseClicked(event -> {
                Tab tab = GenComponents.getIndexController().renderTableListView(domainName);
                GenComponents.getIndexController().getMainTabPane().getSelectionModel().select(tab);
            });
        });
    }
}
