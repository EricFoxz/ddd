package gitee.com.ericfox.ddd.domain.gen.service;

import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Properties;

@Slf4j
@Service
public class GenCodeService implements GenLogger {
    @SneakyThrows
    public String genPo(String domainName, String tableName) {
        TableXmlBean tableXmlBean = GenTableLoadingService.getDomainMap().get(domainName).get(tableName);
        Properties properties = new Properties();
        properties.load(ResourceUtil.getStream("vm.properties"));
        Velocity.init(properties);
        VelocityContext context = new VelocityContext();
        BeanUtil.beanToMap(tableXmlBean).forEach(context::put);
        Template template = null;
        try {
            template = Velocity.getTemplate("gen/velocity_home/po/Po.java.vm");
            if (template == null) {
                throw new ProjectFrameworkException("");
            }
        } catch (Exception e) {
            logError(log, "genCodeService::genPo 加载模板异常");
        }

        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        String result = sw.toString();
        logDebug(log, "生成代码：\n" + result);
        return result;
    }
}
