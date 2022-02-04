package gitee.com.ericfox.ddd.domain.gen.service;

import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Properties;

@Slf4j
@Service
public class GenCodeService implements GenLogger {
    @Value("${spring.application.name}")
    private String projectName;

    @Autowired
    private void init() {
        try {
            Properties properties = new Properties();
            properties.load(ResourceUtil.getStream("vm.properties"));
            Velocity.init(properties);
        } catch (Exception e) {
            logError(log, "genCodeService::init velocity初始化失败");
        }
    }

    /**
     * 创建代码
     */
    private String getCodeByTableXmlBean(TableXmlBean tableXml, String path) {
        VelocityContext context = new VelocityContext();
        BeanUtil.beanToMap(tableXml).forEach(context::put);
        Template template = null;
        try {
            template = Velocity.getTemplate(path);
            if (template == null) {
                throw new ProjectFrameworkException("");
            }
        } catch (Exception e) {
            logError(log, "genCodeService::genPo 加载模板异常");
        }

        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        String result = sw.toString();
        logDebug(log, "生成PO代码：\n" + result);
        return result;
    }

    /**
     * po代码
     */
    public String genPo(TableXmlBean tableXmlBean) {
        return getCodeByTableXmlBean(tableXmlBean, "gen/velocity_home/po/Po.java.vm");
    }

    public String getDao(TableXmlBean tableXmlBean) {
        return getCodeByTableXmlBean(tableXmlBean, "gen/velocity_home/dao/" + tableXmlBean.getMeta().getRepoTypeStrategyEnum().getCode() + "/Dao.java.vm");
    }
}
