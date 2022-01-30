package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenDebugController implements BaseJavaFxController {
    @Getter
    private final BaseLogger logger = new BaseLogger() {
        private final Font normalFont = Font.font("System", FontWeight.NORMAL, 16);
        private final Font boldFont = Font.font("System", FontWeight.BOLD, 16);

        @Override
        public void info(String msg) {
            Text text = new Text(msg + "\n");
            text.setFill(Color.BLUE);
            text.setFont(normalFont);
            logTextFlow.getChildren().add(text);
        }

        @Override
        public void warn(String msg) {
            Text text = new Text(msg + "\n");
            text.setFill(Color.ORANGE);
            text.setFont(boldFont);
            logTextFlow.getChildren().add(text);
        }

        @Override
        public void debug(String msg) {
            Text text = new Text(msg + "\n");
            text.setFill(Color.BLACK);
            text.setFont(normalFont);
            logTextFlow.getChildren().add(text);
        }

        @Override
        public void error(String msg) {
            Text text = new Text(msg + "\n");
            text.setFill(Color.RED);
            text.setFont(boldFont);
            logTextFlow.getChildren().add(text);
        }
    };

    @FXML
    private Button clearButton;

    @FXML
    private TextFlow logTextFlow;

    @Override
    public void initialize() {
        clearButton.setOnAction(event -> {
            logTextFlow.getChildren().clear();
        });
    }

}
