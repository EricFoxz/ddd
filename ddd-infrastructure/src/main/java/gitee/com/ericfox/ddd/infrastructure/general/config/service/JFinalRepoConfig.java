package gitee.com.ericfox.ddd.infrastructure.general.config.service;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import com.jfinal.template.source.ClassPathSourceFactory;
import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;
import gitee.com.ericfox.ddd.common.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.ReflectUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.service.RepoEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans.ClassTransUtil;
import gitee.com.ericfox.ddd.infrastructure.service.repo.impl.JFinalBaseDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Set;
import java.util.function.Consumer;

/**
 * JFinal持久化配置类
 */
@Configuration
@ConditionalOnProperty(prefix = "custom.infrastructure.repo-strategy.my-sql", value = {"enable"})
@SuppressWarnings("unchecked")
public class JFinalRepoConfig {
    public static volatile boolean isReady = false;
    @Resource
    private CustomProperties customProperties;

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.hikari.username}")
    private String username;
    @Value("${spring.datasource.hikari.password}")
    private String password;
    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;

    @Bean
    @SneakyThrows
    public <PO extends BasePo<PO>, DAO extends JFinalBaseDao, MODEL extends Model<MODEL>> ActiveRecordPlugin activeRecordPlugin() {
        HikariCpPlugin hikariCpPlugin = new HikariCpPlugin(url, username, password, driverClassName);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(hikariCpPlugin);
        arp.setShowSql(true);
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.getEngine().getSourceFactory();
        arp.addSqlTemplate("infrastructure/jfinal-sql-templates/baseRepo.sql");
        hikariCpPlugin.start();

        //扫描PO，加载采用mySql策略的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(customProperties.getRootPackage() + ".infrastructure.persistent.po", RepoEnabledAnnotation.class);
        classes.forEach(new Consumer<Class<?>>() {
            @Override
            @SneakyThrows
            public void accept(Class<?> aClass) {
                String className = aClass.getSimpleName();
                if (aClass.isAnnotationPresent(RepoEnabledAnnotation.class)) {
                    RepoEnabledAnnotation annotation = aClass.getAnnotation(RepoEnabledAnnotation.class);
                    if (RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY.equals(annotation.type())) {
                        Class<DAO> daoClass = ClassTransUtil.getDaoClassByPoClass((Class<PO>) aClass, RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY);
                        String daoName = JFinalBaseDao.DAO_FIELD_NAME;
                        Class<MODEL> daoClassM = (Class<MODEL>) ReflectUtil.getStaticFieldValue(ReflectUtil.getField(daoClass, daoName)).getClass();
                        arp.addMapping(StrUtil.toUnderlineCase(className), annotation.value(), daoClassM);
                    }
                }
            }
        });
        arp.start();
        isReady = true;
        return arp;
    }
}
