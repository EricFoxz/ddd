package gitee.com.ericfox.ddd.domain.gen.service;

import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Properties;

@Slf4j
@Service
public class GenCodeService implements GenLogger {
    @SneakyThrows
    public String gen() {
        Properties properties = new Properties();
        properties.load(ResourceUtil.getStream("vm.properties"));
        Velocity.init(properties);
        VelocityContext context = new VelocityContext();
        context.put("ClassName", "SysUser");
        Template template = null;
        try {
            template = Velocity.getTemplate("gen/velocity_home/po/Po.java.vm");
        } catch (ResourceNotFoundException e) {
            // couldn't find the template
        } catch (ParseErrorException pee) {
            // syntax error: problem parsing the template
        } catch (MethodInvocationException mie) {
            // something invoked in the template
            // threw an exception
        } catch (Exception e) {

        }

        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        String result = sw.toString();
        logDebug(log, "生成代码：\n" + result);
        return result;
    }
}
