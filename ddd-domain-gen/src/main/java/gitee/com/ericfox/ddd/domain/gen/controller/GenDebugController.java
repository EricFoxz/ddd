package gitee.com.ericfox.ddd.domain.gen.controller;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseLogger;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenDebugController implements BaseJavaFxController {
    @Getter
    public final BaseLogger logger = new BaseLogger() {
        @Override
        public void info(Font font, String... msg) {
            Text text = getText(msg);
            text.setFill(Color.BLUE);
            text.setFont(font);
            print(text);
        }

        @Override
        public void warn(Font font, String... msg) {
            Text text = getText(msg);
            text.setFill(Color.ORANGE);
            text.setFont(font);
            print(text);
        }

        @Override
        public void debug(Font font, String... msg) {
            Text text = getText(msg);
            text.setFill(Color.BLACK);
            text.setFont(font);
            print(text);
        }

        @Override
        public void error(Font font, String... msg) {
            Text text = getText(msg);
            text.setFill(Color.RED);
            text.setFont(font);
            print(text);
        }

        private Text getText(String... msg) {
            Text text = null;
            if (ArrayUtil.isEmpty(msg)) {
                text = new Text("\n");
            } else if (msg.length == 1) {
                text = new Text(msg[0] + "\n");
            } else {
                text = new Text(ArrayUtil.join(msg, "\n") + "\n");
            }
            return text;
        }

        /**
         * 输出到log，当超过1000行后，每次输出移除前10行
         */
        private synchronized void print(Text text) {
            ObservableList<Node> children = logTextFlow.getChildren();
            if (CollUtil.size(children) - 1000 > 10) {
                children.remove(0, 10);
            }
            children.add(text);
            logScrollPane.setVvalue(1);
        }
    };

    @FXML
    private Button clearButton;

    @FXML
    private TextFlow logTextFlow;
    @FXML
    private ScrollPane logScrollPane;

    @Override
    public void initialize() {
        clearButton.setOnAction(event -> {
            logTextFlow.getChildren().clear();
        });
        logScrollPane.setVvalue(1);
    }

}
