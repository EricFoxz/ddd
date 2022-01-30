package gitee.com.ericfox.ddd.domain.gen.common.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GenConstants {
    @Getter
    private static boolean debug;

    @Value("${debug}")
    private void setDebug(boolean debug) {
        GenConstants.debug = debug;
    }
}
