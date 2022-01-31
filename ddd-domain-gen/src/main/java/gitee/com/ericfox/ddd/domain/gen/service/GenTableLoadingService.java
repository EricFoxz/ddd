package gitee.com.ericfox.ddd.domain.gen.service;

import cn.hutool.core.io.resource.Resource;
import gitee.com.ericfox.ddd.domain.gen.factory.GenDocumentFactory;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.RepoEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import javafx.event.ActionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Service
@Slf4j
public class GenTableLoadingService implements BaseGenService {
    private final Map<String, Map<String, Object>> domainMap = MapUtil.newConcurrentHashMap();

    @Autowired
    public void initAll() {
        try {


            Resource resourceObj = ResourceUtil.getResourceObj("gen/meta_home");
            File metaHome = FileUtil.file(resourceObj.getUrl());
            if (FileUtil.isDirectory(metaHome)) {
                GenDocumentFactory genDocumentFactory = new GenDocumentFactory();
                List<File> domainList = FileUtil.loopFiles(metaHome.toPath(), 1, null);

                if (CollUtil.isNotEmpty(domainList)) {
                    domainList.forEach(domainFile -> {
                        if (!FileUtil.isDirectory(domainFile)) {
                            return;
                        }
                        String domainName = FileUtil.getName(domainFile);
                        Map<String, Object> tableMap = null;
                        if (!domainMap.containsKey(domainName)) {
                            tableMap = MapUtil.newConcurrentHashMap();
                            domainMap.put(domainName, tableMap);
                        }
                        List<File> tableList = FileUtil.loopFiles(domainFile);
                        if (CollUtil.isNotEmpty(tableList)) {
                            Map<String, Object> finalTableMap = tableMap;
                            tableList.forEach(tableFile -> {
                                Document tableDocument = genDocumentFactory.deserialization(tableFile);
                                Element class_name_element = XmlUtil.getElementByXPath("/root/meta/class_name", tableDocument);
                                String class_name = class_name_element.getTextContent();
                                finalTableMap.put(class_name, tableDocument);
                            });
                        }
                        System.out.println(tableList);
                    });
                }
                System.out.println(domainMap);
            }
        } catch (Exception e) {
            logError(log, "GenTableLoadingService:init异常", e);
        }
    }

    /**
     * 从java读取表结构
     */
    public void readTableByJavaClassHandler(ActionEvent event) {
        logInfo(log, "开始从java代码读取表结构...");
        Set<Class<?>> classes = ClassUtil.scanPackage(BasePo.class.getPackage().getName());
        classes.forEach(new Consumer<Class<?>>() {
            @Override
            public void accept(Class<?> aClass) {
                if (aClass.isAnnotationPresent(RepoEnabledAnnotation.class)) {

                }
            }
        });
    }

    /**
     * 从数据库读取表结构
     */
    public void readTableByOrmHandler(ActionEvent event) {
        logInfo(log, "开始从数据库读取表结构...");
    }


}
