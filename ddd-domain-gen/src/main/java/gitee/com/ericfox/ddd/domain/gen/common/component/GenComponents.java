package gitee.com.ericfox.ddd.domain.gen.common.component;

import gitee.com.ericfox.ddd.domain.gen.controller.GenDebugController;
import gitee.com.ericfox.ddd.domain.gen.controller.GenDomainViewController;
import gitee.com.ericfox.ddd.domain.gen.controller.GenIndexController;
import gitee.com.ericfox.ddd.domain.gen.service.GenCodeService;
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
    private static Stage indexStage;
    @Setter
    @Getter
    private static Stage debugStage;

    @Setter
    @Getter
    private static GenIndexController indexController;
    @Setter
    @Getter
    private static GenDebugController debugController;
    @Setter
    @Getter
    private static GenDomainViewController domainViewController;

    @Getter
    private static GenTableLoadingService genTableLoadingService;

    @Autowired
    public void setGenTableLoadingService(GenTableLoadingService genTableLoadingService) {
        GenComponents.genTableLoadingService = genTableLoadingService;
    }

    @Getter
    private static GenCodeService genCodeService;

    @Autowired
    public void setGenCodeService(GenCodeService genCodeService) {
        GenComponents.genCodeService = genCodeService;
    }
}
