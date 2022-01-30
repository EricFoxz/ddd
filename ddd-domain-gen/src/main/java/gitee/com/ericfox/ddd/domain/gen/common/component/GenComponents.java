package gitee.com.ericfox.ddd.domain.gen.common.component;

import gitee.com.ericfox.ddd.domain.gen.controller.GenDebugController;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenComponents {
    @Setter
    @Getter
    private static Stage debugStage;

    @Setter
    @Getter
    private static GenDebugController debugController;

    @Getter
    private static GenTableLoadingService genTableLoadingService;

    @Autowired
    public void setGenTableLoadingService(GenTableLoadingService genTableLoadingService) {
        GenComponents.genTableLoadingService = genTableLoadingService;
    }
}
