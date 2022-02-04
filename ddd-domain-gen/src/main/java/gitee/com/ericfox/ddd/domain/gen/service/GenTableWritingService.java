package gitee.com.ericfox.ddd.domain.gen.service;

import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class GenTableWritingService implements GenLogger {
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
}
