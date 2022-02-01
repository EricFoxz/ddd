package gitee.com.ericfox.ddd.domain.gen.service;

import cn.hutool.core.io.resource.Resource;
import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.domain.gen.factory.GenSerializableFactory;
import gitee.com.ericfox.ddd.domain.gen.model.TableXmlBean;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.RepoEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import javafx.event.ActionEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Service
@Slf4j
public class GenTableLoadingService implements GenLogger {
    @Getter
    private static final Map<String, Map<String, TableXmlBean>> domainMap = MapUtil.newConcurrentHashMap();

    /**
     * 读取已有的
     */
    public synchronized void initAll() {
        logInfo(log, "genTableLoadingService::initAll 正在从运行时环境反序列化表结构...");
        domainMap.clear();
        try {
            Resource resourceObj = ResourceUtil.getResourceObj(GenConstants.META_HOME_PATH);
            File metaHome = FileUtil.file(resourceObj.getUrl());
            if (FileUtil.isDirectory(metaHome)) {
                GenSerializableFactory genSerializableFactory = GenSerializableFactory.getDefaultInstance();
                List<File> domainList = FileUtil.loopFiles(metaHome.toPath(), 1, null);

                if (CollUtil.isNotEmpty(domainList)) {
                    domainList.forEach(domainFile -> {
                        if (!FileUtil.isDirectory(domainFile)) {
                            return;
                        }
                        String domainName = FileUtil.getName(domainFile);
                        Map<String, TableXmlBean> tableMap = null;
                        if (!domainMap.containsKey(domainName)) {
                            tableMap = MapUtil.newConcurrentHashMap();
                            domainMap.put(domainName, tableMap);
                        } else {
                            tableMap = domainMap.get(domainName);
                        }
                        List<File> tableList = FileUtil.loopFiles(domainFile);
                        if (CollUtil.isNotEmpty(tableList)) {
                            Map<String, TableXmlBean> finalTableMap = tableMap;
                            tableList.forEach(tableFile -> {
                                TableXmlBean bean = TableXmlBean.load(tableFile);
                                finalTableMap.put(bean.getMeta().getTableName(), bean);
                            });
                        }
                    });
                }
            }
            logInfo(log, "genTableLoadingService::initAll 加载完成");
        } catch (Exception e) {
            logError(log, "genTableLoadingService::initAll 异常", e);
        }
    }

    /**
     * 从java读取表结构
     */
    public void readTableByJavaClassHandler(ActionEvent event) {
        logInfo(log, "genTableLoadingService::readTableByJavaClassHandler 开始从java代码读取表结构...");
        Set<Class<?>> classes = ClassUtil.scanPackage(BasePo.class.getPackage().getName());
        classes.forEach(new Consumer<Class<?>>() {
            @Override
            public void accept(Class<?> aClass) {
                if (aClass.isAnnotationPresent(RepoEnabledAnnotation.class)) {
                    //TODO-待实现
                }
            }
        });
        GenComponents.getGenTableWritingService().publishTablesToRuntime();
    }

    /**
     * 从数据库读取表结构
     */
    public void readTableByOrmHandler(ActionEvent event) {
        logInfo(log, "开始从数据库读取表结构...");
        //TODO-待实现
        GenComponents.getGenTableWritingService().publishTablesToRuntime();
    }


}
