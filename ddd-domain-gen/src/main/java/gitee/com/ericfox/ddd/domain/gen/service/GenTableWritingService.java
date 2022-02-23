package gitee.com.ericfox.ddd.domain.gen.service;

import gitee.com.ericfox.ddd.common.toolkit.coding.*;
import gitee.com.ericfox.ddd.domain.gen.bean.TableXmlBean;
import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
@Slf4j
public class GenTableWritingService implements GenLogger {
    @Resource
    private CustomProperties customProperties;

    /**
     * 写入Po代码
     */
    public void writePoCode(TableXmlBean tableXml) {
        String poCode = GenComponents.getGenCodeService().getPoCode(tableXml);
        String filePath = getInfrastructurePath() + "/persistent/po/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().toMap().get("ClassName") + ".java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, poCode);
    }

    /**
     * 写入Dao代码
     */
    public void writeDaoCode(TableXmlBean tableXml) {
        String daoCode = GenComponents.getGenCodeService().getDaoCode(tableXml);
        String repoType = StrUtil.toUnderlineCase(tableXml.getMeta().getRepoTypeStrategyEnum().getCode());
        String filePath = getInfrastructurePath() + "/persistent/dao/" + tableXml.getMeta().getDomainName() + "/" + repoType + "/" + tableXml.getMeta().toMap().get("ClassName") + "Dao.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, daoCode);
    }

    /**
     * 写入Entity代码
     */
    public void writeEntityCode(TableXmlBean tableXml) {
        String entityCode = GenComponents.getGenCodeService().getEntityCode(tableXml);
        String filePath = getDomainPath(tableXml) + "/model/" + tableXml.getMeta().getTableName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "Entity.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, entityCode);
    }

    /**
     * 写入EntityBase代码
     */
    public void writeEntityBaseCode(TableXmlBean tableXml) {
        String entityBaseCode = GenComponents.getGenCodeService().getEntityBaseCode(tableXml);
        String filePath = getDomainPath(tableXml) + "/model/" + tableXml.getMeta().getTableName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "EntityBase.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, entityBaseCode);
    }

    /**
     * 写入Context代码
     */
    public void writeContextCode(TableXmlBean tableXml) {
        String entityBaseCode = GenComponents.getGenCodeService().getContextCode(tableXml);
        String filePath = getDomainPath(tableXml) + "/model/" + tableXml.getMeta().getTableName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "Context.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, entityBaseCode);
    }

    /**
     * 写入Service代码
     */
    public void writeServiceCode(TableXmlBean tableXml) {
        String serviceCode = GenComponents.getGenCodeService().getServiceCode(tableXml);
        String filePath = getDomainPath(tableXml) + "/model/" + tableXml.getMeta().getTableName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "Service.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, serviceCode);
    }

    /**
     * 写入ServiceBase代码
     */
    public void writeServiceBaseCode(TableXmlBean tableXml) {
        String serviceBaseCode = GenComponents.getGenCodeService().getServiceBaseCode(tableXml);
        String filePath = getDomainPath(tableXml) + "/model/" + tableXml.getMeta().getTableName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "ServiceBase.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, serviceBaseCode);
    }

    /**
     * 写入Dto代码
     */
    public void writeDtoCode(TableXmlBean tableXml) {
        String dtoCode = GenComponents.getGenCodeService().getDtoCode(tableXml);
        String filePath = getApisPath() + "/model/dto/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "Dto.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, dtoCode);
    }

    /**
     * 写入DtoBase代码
     */
    public void writeDtoBaseCode(TableXmlBean tableXml) {
        String dtoBaseCode = GenComponents.getGenCodeService().getDtoBaseCode(tableXml);
        String filePath = getApisPath() + "/model/dto/" + tableXml.getMeta().getDomainName() + "/base/" + tableXml.getMeta().toMap().get("ClassName") + "DtoBase.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, dtoBaseCode);
    }

    public void writePageParamCode(TableXmlBean tableXml) {
        String pageParamCode = GenComponents.getGenCodeService().getPageParamCode(tableXml);
        String filePath = getApisPath() + "/model/param/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().getTableName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "PageParam.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, pageParamCode);
    }

    public void writeDetailParamCode(TableXmlBean tableXml) {
        String detailParamCode = GenComponents.getGenCodeService().getDetailParamCode(tableXml);
        String filePath = getApisPath() + "/model/param/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().getTableName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "DetailParam.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, detailParamCode);
    }

    public void writeControllerCode(TableXmlBean tableXml) {
        String controllerCode = GenComponents.getGenCodeService().getControllerCode(tableXml);
        String filePath = getApisPath() + "/controller/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().toMap().get("ClassName") + "Controller.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, controllerCode);
    }

    public void writeControllerBaseCode(TableXmlBean tableXml) {
        String controllerBaseCode = GenComponents.getGenCodeService().getControllerBaseCode(tableXml);
        String filePath = getApisPath() + "/controller/" + tableXml.getMeta().getDomainName() + "/base/" + tableXml.getMeta().toMap().get("ClassName") + "ControllerBase.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, controllerBaseCode);
    }

    /**
     * 序列化
     */
    @SneakyThrows
    public void writeTableXml(TableXmlBean tableXml) {
        //运行时环境
        String targetPath = URLUtil.getURL(GenConstants.META_HOME_PATH).getFile();
        //coding环境
        String sourcePath = ReUtil.replaceAll(targetPath, "/target/classes", "/src/main/resources");
        File file = FileUtil.touch(sourcePath + "/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().getTableName() + ".xml");
        FileUtil.touch(file);
        XmlUtil.writeObjectAsXml(file, tableXml);
    }

    /**
     * 把xml发布到target运行时环境
     */
    public void publishTablesToRuntime() {
        logInfo(log, "genTableWritingService::publishTablesToRuntime 开始发布xml表结构...");
        String targetPath = URLUtil.getURL(GenConstants.META_HOME_PATH).getFile();
        //   /E:/idea_projects/ddd/ddd-domain-gen/target/classes/gen/meta_home
        String sourcePath = ReUtil.replaceAll(targetPath, "/target/classes", "/src/main/resources");
        //   /E:/idea_projects/ddd/ddd-domain-gen/src/main/resources/gen/meta_home
        targetPath = StrUtil.replace(targetPath, GenConstants.META_HOME_PATH, "gen");
        FileUtil.copy(sourcePath, targetPath, true);
        logInfo(log, "genTableWritingService::publishTablesToRuntime 发布xml表结构完成");
    }

    /**
     * 基础设施层真实路径
     */
    public String getInfrastructurePath() {
        String infrastructurePath = Constants.PROJECT_ROOT_PATH;
        infrastructurePath += "/" + customProperties.getProjectName() + "-infrastructure/src/main/java/" + customProperties.getRootPackage().replaceAll("[.]", "/") + "/infrastructure";
        return infrastructurePath;
    }

    /**
     * 领域层真实路径
     */
    public String getDomainPath(TableXmlBean tableXml) {
        String domainPath = Constants.PROJECT_ROOT_PATH;
        domainPath += "/" + customProperties.getProjectName() + "-domain-" + tableXml.getMeta().getDomainName() + "/src/main/java/" + customProperties.getRootPackage().replaceAll("[.]", "/") + "/domain/" + tableXml.getMeta().getDomainName();
        return domainPath;
    }

    /**
     * 接口层真实路径
     */
    public String getApisPath() {
        String apisPath = Constants.PROJECT_ROOT_PATH;
        apisPath += "/" + customProperties.getProjectName() + "-apis/src/main/java/" + customProperties.getRootPackage().replaceAll("[.]", "/") + "/apis";
        return apisPath;
    }
}
