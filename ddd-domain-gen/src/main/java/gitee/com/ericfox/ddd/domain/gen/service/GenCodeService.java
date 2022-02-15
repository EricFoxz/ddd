package gitee.com.ericfox.ddd.domain.gen.service;

import cn.hutool.core.date.DateUtil;
import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.FileUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ResourceUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.StringWriter;
import java.util.Map;
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
     * 获取模板原文
     */
    @SneakyThrows
    public String getTemplateCodeWithOutRendering(String path) {
        File file = new ClassPathResource(path).getFile();
        return FileUtil.readUtf8String(file);
    }

    /**
     * 创建代码
     */
    private String getCodeByTableXmlBean(TableXmlBean tableXml, VelocityContext context, String path) {
        context.put("strUtil", strUtil);
        context.put("dateUtil", dateUtil);
        context.put("rootPackage", customProperties.getRootPackage());
        Map<String, Object> metaMap = tableXml.getMeta().toMap();
        context.put("meta", metaMap);
        Map<String, Object> dataMap = BeanUtil.beanToMap(tableXml.getData());
        context.put("data", dataMap);
        Template template = null;
        try {
            template = Velocity.getTemplate(path);
            if (template == null) {
                logError(log, "genCodeService::getCodeByTableXmlBean velocity模板初始化失败: " + path);
                throw new ProjectFrameworkException("genCodeService::getCodeByTableXmlBean velocity模板初始化失败: " + path);
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
    public String getPoCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/po/Po.java.vm");
    }

    /**
     * dao代码
     */
    public String getDaoCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/dao/" + StrUtil.toUnderlineCase(tableXmlBean.getMeta().getRepoTypeStrategyEnum().getCode()) + "/Dao.java.vm");
    }

    public String getDtoCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/dto/Dto.java.vm");
    }

    public String getDtoBaseCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/dto/base/DtoBase.java.vm");
    }

    public String getEntityCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/entity/Entity.java.vm");
    }

    public String getEntityBaseCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/entity/base/EntityBase.java.vm");
    }

    public String getServiceCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/service/Service.java.vm");
    }

    public String getServiceBaseCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/service/base/ServiceBase.java.vm");
    }

    public String getPageParamCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/param/PageParam.java.vm");
    }

    public String getDetailParamCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/param/DetailParam.java.vm");
    }

    public String getControllerCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/controller/Controller.java.vm");
    }

    public String getControllerBaseCode(TableXmlBean tableXmlBean) {
        VelocityContext context = new VelocityContext();
        return getCodeByTableXmlBean(tableXmlBean, context, "gen/velocity_home/controller/base/ControllerBase.java.vm");
    }
}
