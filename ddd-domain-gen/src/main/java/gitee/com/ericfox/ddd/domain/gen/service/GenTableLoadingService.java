package gitee.com.ericfox.ddd.domain.gen.service;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.resource.Resource;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.common.toolkit.coding.*;
import gitee.com.ericfox.ddd.domain.gen.bean.TableJavaBean;
import gitee.com.ericfox.ddd.domain.gen.bean.TableMySqlBean;
import gitee.com.ericfox.ddd.domain.gen.bean.TableXmlBean;
import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.component.GenComponents;
import gitee.com.ericfox.ddd.domain.gen.common.constants.GenConstants;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.service.RepoEnabledAnnotation;
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
     * 加载已有的表结构
     */
    public synchronized void initAll() {
        logInfo(log, "genTableLoadingService::initAll 正在从运行时环境反序列化表结构...");
        domainMap.clear();
        try {
            Resource resourceObj = ResourceUtil.getResourceObj(GenConstants.META_HOME_PATH);
            File metaHome = FileUtil.file(resourceObj.getUrl());
            if (FileUtil.isDirectory(metaHome)) {
                List<File> domainList = FileUtil.loopFiles(metaHome.toPath(), 1, null);

                if (CollUtil.isNotEmpty(domainList)) {
                    domainList.forEach(domainFile -> {
                        if (!FileUtil.isDirectory(domainFile)) {
                            return;
                        }
                        String domainName = FileUtil.getName(domainFile);
                        Map<String, TableXmlBean> tableMap;
                        if (!domainMap.containsKey(domainName)) {
                            tableMap = MapUtil.newConcurrentHashMap();
                            domainMap.put(domainName, tableMap);
                        } else {
                            tableMap = domainMap.get(domainName);
                        }
                        List<File> tableList = FileUtil.loopFiles(domainFile);
                        if (CollUtil.isNotEmpty(tableList)) {
                            tableList.forEach(tableFile -> {
                                TableXmlBean bean = TableXmlBean.load(tableFile);
                                tableMap.put(bean.getMeta().getTableName(), bean);
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
     * 从java导入表结构
     */
    public <PO extends BasePo<PO>> void importTableByJavaClass(String domainName, String tableName) {
        logInfo(log, "genTableLoadingService::importTableByJavaClass 开始从java代码读取表结构...");
        Set<Class<?>> classes = ClassUtil.scanPackage(BasePo.class.getPackage().getName());
        classes.forEach(new Consumer<Class<?>>() {
            @Override
            public void accept(Class<?> clazz) {
                if (!BasePo.class.isAssignableFrom(clazz)) {
                    return;
                }
                if (clazz.isAnnotationPresent(RepoEnabledAnnotation.class)) {
                    TableJavaBean<? extends BasePo<?>> tableJava = new TableJavaBean(clazz);
                    TableXmlBean tableXml = TableXmlBean.load(tableJava);
                    String domainName = tableXml.getMeta().getDomainName();
                    String tableName = tableXml.getMeta().getTableName();
                    if (!domainMap.containsKey(domainName)) {
                        domainMap.put(domainName, MapUtil.newConcurrentHashMap());
                    }
                    domainMap.get(domainName).put(tableName, tableXml);
                    GenComponents.getGenTableWritingService().writeTableXml(tableXml);
                }
            }
        });
        logInfo(log, "genTableLoadingService::importTableByJavaClass 读取完成");
        GenComponents.getGenTableWritingService().publishTablesToRuntime();
    }

    /**
     * 从MySql数据库导入表结构
     */
    public void importTableByMySql(String url, String username, String password) {
        logInfo(log, "genTableLoadingService::importTableByMySql 开始从数据库读取表结构...");
        try {
            /* 指定数据库链接*/
            /*ActiveRecordPlugin arp = SpringUtil.getBean(ActiveRecordPlugin.class);
            String driverClass = SpringUtil.getProperty("spring.datasource.hikari.driver-class-name");
            if (StrUtil.isBlank(url)) {
                url = SpringUtil.getProperty("spring.datasource.url");
                username = SpringUtil.getProperty("spring.datasource.hikari.username");
                password = SpringUtil.getProperty("spring.datasource.hikari.password");
            }
            HikariCpPlugin hikariCpPlugin = new HikariCpPlugin(url, username, password, driverClass);
            if (arp != null) {
                arp.stop();
            } else {
                arp = new ActiveRecordPlugin(hikariCpPlugin);
            }
            arp.setShowSql(true);
            arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
            arp.getEngine().getSourceFactory();
            arp.addSqlTemplate("infrastructure/jfinal-sql-templates/baseRepo.sql");
            hikariCpPlugin.start();
            arp.start();*/

            String databaseName = DbKit.getConfig().getConnection().getCatalog();
            List<Record> tableRecordList = Db.find("select * from information_schema.tables where table_schema = '" + databaseName + "'");
            tableRecordList.forEach(tableRecord -> {
                CopyOptions copyOptions = Constants.IGNORE_CASE_VALUE_COPY_OPTIONS;
                TableMySqlBean mySqlBean = BeanUtil.mapToBean(tableRecord.getColumns(), TableMySqlBean.class, false, copyOptions);
                List<Record> columnRecordList = Db.find("select * from information_schema.columns where table_schema = '" + databaseName + "' and table_name='" + mySqlBean.getTable_name() + "'");
                columnRecordList.forEach(columnRecord -> {
                    TableMySqlBean.ColumnSchemaBean columnSchema = BeanUtil.mapToBean(columnRecord.getColumns(), TableMySqlBean.ColumnSchemaBean.class, false, copyOptions);
                    mySqlBean.getColumnSchemaList().add(columnSchema);
                });
                TableXmlBean tableXml = TableXmlBean.load(mySqlBean);
                String domainName = tableXml.getMeta().getDomainName();
                String tableName = tableXml.getMeta().getTableName();
                if (!domainMap.containsKey(domainName)) {
                    domainMap.put(domainName, MapUtil.newConcurrentHashMap());
                }
                domainMap.get(domainName).put(tableName, tableXml);
                GenComponents.getGenTableWritingService().writeTableXml(tableXml);
            });
            logInfo(log, "genTableLoadingService::importTableByMySql 读取完成");
        } catch (Exception e) {
            logError(log, "genTableLoadingService::importTableByMySql", e.getMessage());
            throw new ProjectFrameworkException("genTableLoadingService::importTableByMySql " + e.getMessage());
        }
        GenComponents.getGenTableWritingService().publishTablesToRuntime();
    }

    /**
     * 从XML文件导入表结构
     */
    public void importTableByXml(File file) {
        //TODO
    }
}
