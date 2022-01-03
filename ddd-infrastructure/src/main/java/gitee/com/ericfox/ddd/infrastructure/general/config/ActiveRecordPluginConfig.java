package gitee.com.ericfox.ddd.infrastructure.general.config;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import com.jfinal.template.source.ClassPathSourceFactory;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.OrmMysqlEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
public class ActiveRecordPluginConfig {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.hikari.username}")
    private String username;
    @Value("${spring.datasource.hikari.password}")
    private String password;
    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;

    @Bean
    public ActiveRecordPlugin activeRecordPlugin() {
        HikariCpPlugin hikariCpPlugin = new HikariCpPlugin(url, username, password, driverClassName);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(hikariCpPlugin);
        arp.setShowSql(true);
        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.getEngine().getSourceFactory();
        //arp.addSqlTemplate("/template/sql.sql");
        hikariCpPlugin.start();

        Set<Class<?>> classes = ClassUtil.scanPackage(BasePo.class.getPackage().getName());
        for (Class<?> aClass : classes) {
            String name = aClass.getName();
            String className = aClass.getSimpleName();
            if (aClass.isAnnotationPresent(OrmMysqlEnabledAnnotation.class)) {
                OrmMysqlEnabledAnnotation annotation = aClass.getAnnotation(OrmMysqlEnabledAnnotation.class);
                Class<? extends Model<?>> daoClass = ClassUtil.loadClass(ReUtil.delLast("\\.po\\..*", name) + ".repository.sys.mysql." + className + "Dao");
                arp.addMapping(StrUtil.toUnderlineCase(className), annotation.value(), daoClass);
            }
        }
        arp.start();
        return arp;
    }
}
