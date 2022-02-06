package gitee.com.ericfox.ddd.domain.gen.service;

import cn.hutool.core.date.DateUtil;
import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ResourceUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.Properties;

@Slf4j
@Service
public class GenCodeService implements GenLogger {
    @Resource
    private CustomProperties customProperties;
    private static final StrUtil strUtil = new StrUtil();
    private static final DateUtil dateUtil = new DateUtil();

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
    private String getCodeByTableXmlBean(TableXmlBean tableXml, VelocityContext context, String path) {
        context.put("strUtil", strUtil);
        context.put("dateUtil", dateUtil);
        context.put("rootPackage", customProperties.getRootPackage());
        BeanUtil.beanToMap(tableXml).forEach(context::put);
        Template template = null;
        try {
            template = Velocity.getTemplate(path);
            if (template == null) {
                throw new ProjectFrameworkException("");
            }
        } catch (Exception e) {
            logError(log, "genCodeService::getCodeByTableXmlBean 加载模板异常");
        }
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        String result = sw.toString();
        logDebug(log, "生成代码：\n" + result);
        return result;
    }

    /**
     * po代码
     */
    public String genPo(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/po/Po.java.vm");
    }

    /**
     * dao代码
     */
    public String getDao(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        tableXmlBean.getMeta().getIdField();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/dao/" + StrUtil.toUnderlineCase(tableXmlBean.getMeta().getRepoTypeStrategyEnum().getCode()) + "/Dao.java.vm");
    }
}
