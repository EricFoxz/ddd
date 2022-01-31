package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenDomainViewController implements BaseJavaFxController, GenLogger {
    @FXML
    private BarChart<String, Number> domainBarChart;

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
            /*Button label = new Button(domainName);
            label.setOnMouseClicked(event -> {
                if (MouseButton.PRIMARY.equals(event.getButton())) {
                    GenComponents.getIndexController().renderTableList(domainName);
                }
            });*/
            domainBarChart.getData().add(series);
        });
    }
}
