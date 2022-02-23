package gitee.com.ericfox.ddd.domain.gen.common.component;

import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.domain.gen.controller.GenDebugController;
import gitee.com.ericfox.ddd.domain.gen.controller.GenDomainViewController;
import gitee.com.ericfox.ddd.domain.gen.controller.GenIndexController;
import gitee.com.ericfox.ddd.domain.gen.controller.GenTableViewController;
import gitee.com.ericfox.ddd.domain.gen.service.GenCodeService;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableLoadingService;
import gitee.com.ericfox.ddd.domain.gen.service.GenTableWritingService;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    private static final Map<String, GenTableViewController> genTableViewControllerMap = MapUtil.newConcurrentHashMap();

    public static void putGenTableViewController(String domainName, GenTableViewController genTableViewController) {
        genTableViewController.setDomainName(domainName);
        genTableViewController.ready();
        genTableViewControllerMap.put(domainName, genTableViewController);
    }

    public static GenTableViewController setGenTableViewController(String domainName) {
        return genTableViewControllerMap.get(domainName);
    }

    @Getter
    private static GenTableLoadingService genTableLoadingService;

    @Autowired
    public void setGenTableLoadingService(GenTableLoadingService genTableLoadingService) {
        GenComponents.genTableLoadingService = genTableLoadingService;
    }

    @Getter
    private static GenTableWritingService genTableWritingService;

    @Autowired
    public void setGenTableWritingService(GenTableWritingService genTableWritingService) {
        GenComponents.genTableWritingService = genTableWritingService;
    }

    @Getter
    private static GenCodeService genCodeService;

    @Autowired
    public void setGenCodeService(GenCodeService genCodeService) {
        GenComponents.genCodeService = genCodeService;
    }
}
