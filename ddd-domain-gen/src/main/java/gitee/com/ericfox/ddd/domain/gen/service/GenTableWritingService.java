package gitee.com.ericfox.ddd.domain.gen.service;

import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
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
        String filePath = getInfrastructurePath() + "/" + tableXml.getMeta().getDomainName() + "/" + tableXml.getMeta().toMap().get("ClassName") + ".java";
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
        String filePath = ClassUtil.getClassPaths(BaseDao.class.getPackage().getName()).stream().findFirst().get().replaceAll("/target/classes", "/src/main/java") + "/" + tableXml.getMeta().getDomainName() + "/" + repoType + "/" + tableXml.getMeta().toMap().get("ClassName") + "Dao.java";
        File file = FileUtil.file(filePath);
        FileUtil.touch(file);
        IoUtil.writeUtf8(FileUtil.getOutputStream(file), true, daoCode);
    }

    /**
     * 序列化
     */
    @SneakyThrows
    public void writeTableXml(TableXmlBean bean) {
        //运行时环境
        String targetPath = URLUtil.getURL(GenConstants.META_HOME_PATH).getFile();
        //coding环境
        String sourcePath = ReUtil.replaceAll(targetPath, "/target/classes", "/src/main/resources");
        File file = FileUtil.touch(sourcePath + "/" + bean.getMeta().getDomainName() + "/" + bean.getMeta().getTableName() + ".xml");
        FileUtil.touch(file);
        XmlUtil.writeObjectAsXml(file, bean);
    }

    /**
     * 把xml发布到target运行时环境
     */
    public void publishTablesToRuntime() {
        logInfo(log, "genTableWritingService::publishTablesToRuntime 1开始发布xml表结构...");
        String targetPath = URLUtil.getURL(GenConstants.META_HOME_PATH).getFile();
        //   /E:/idea_projects/ddd/ddd-domain-gen/target/classes/gen/meta_home
        String sourcePath = ReUtil.replaceAll(targetPath, "/target/classes", "/src/main/resources");
        //   /E:/idea_projects/ddd/ddd-domain-gen/src/main/resources/gen/meta_home
        targetPath = StrUtil.replace(targetPath, GenConstants.META_HOME_PATH, "gen");
        FileUtil.copy(sourcePath, targetPath, true);
        logInfo(log, "genTableWritingService::publishTablesToRuntime 2发布完成");
    }

    private String getInfrastructurePath() {
        return ClassUtil.getClassPaths(BasePo.class.getPackage().getName()).stream().findFirst().get().replaceAll("/target/classes", "/src/main/java");
    }
}
