package gitee.com.ericfox.ddd.domain.gen.common.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureComponents {
    private InfrastructureComponents() {
    }

    @Getter
    private static ApplicationContext applicationContext;

    @Autowired
    private void setApplicationContext(ApplicationContext applicationContext) {
        InfrastructureComponents.applicationContext = applicationContext;
    }
}
