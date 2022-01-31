package gitee.com.ericfox.ddd.domain.gen.common.component;

import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ThreadUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Order(999)
@Slf4j
public class GenFX extends Application {
    @Autowired
    public void run() {
        ThreadUtil.execAsync(() -> Application.launch((String[]) null));
    }

    @Override
    @SneakyThrows
    public void init() {
        super.init();
    }

    @Override
    @SneakyThrows
    public void stop() {
        ((ConfigurableApplicationContext) InfrastructureComponents.getApplicationContext()).close();
        System.exit(0);
    }

    @Override
    public void start(Stage indexStage) {
        try {
            indexStage.setTitle("领域驱动-代码生成器");
            FXMLLoader rootLoader = new FXMLLoader(new ClassPathResource(GenConstants.INDEX_FXML_PATH).getURL());
            Parent root = rootLoader.load();
            indexStage.setScene(new Scene(root));
            indexStage.getIcons().add(new Image(GenConstants.DEFAULT_ICON_URL));
            indexStage.setResizable(false);
            indexStage.initStyle(StageStyle.DECORATED);
            indexStage.show();
            GenComponents.setIndexStage(indexStage);
            indexStage.setOnCloseRequest(event -> {
                Stage debugStage = GenComponents.getDebugStage();
                if (debugStage != null) {
                    debugStage.close();
                }
                Platform.exit();
            });
        } catch (Exception e) {
            log.error("genFX::start 初始化异常", e);
        }
    }

    public static void initDebugStage(Stage parentStage) throws Exception {
        if (GenComponents.getDebugStage() == null) {
            Stage debugStage = new Stage();
            debugStage.setTitle("debug");
            debugStage.initOwner(parentStage);
            debugStage.initModality(Modality.NONE);
            debugStage.getIcons().add(new Image(GenConstants.DEFAULT_ICON_URL));
            debugStage.setAlwaysOnTop(true);
            debugStage.setResizable(false);
            FXMLLoader debugLoader = new FXMLLoader(new ClassPathResource(GenConstants.DEBUG_FXML_PATH).getURL());
            debugStage.setScene(new Scene(debugLoader.load()));
            GenComponents.setDebugStage(debugStage);
            GenComponents.setDebugController(debugLoader.getController());
            debugStage.show();
            debugStage.setOnCloseRequest(event -> GenComponents.setDebugStage(null));
        } else {
            GenComponents.getDebugStage().show();
        }
    }
}
