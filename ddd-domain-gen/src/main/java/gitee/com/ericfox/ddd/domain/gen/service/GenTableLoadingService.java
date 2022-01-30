package gitee.com.ericfox.ddd.domain.gen.service;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.service.RepoEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ClassUtil;
import javafx.event.ActionEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Consumer;

@Service
@Slf4j
public class GenTableLoadingService implements BaseGenService {
    public void init() {

    }

    /**
     * 从java读取表结构
     */
    public void readTableByJavaClassHandler(ActionEvent event) {
        logInfo(log, "开始从java代码读取表结构...");
        Set<Class<?>> classes = ClassUtil.scanPackage(BasePo.class.getPackage().getName());
        classes.forEach(new Consumer<Class<?>>() {
            @Override
            @SneakyThrows
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
        Set<Class<?>> classes = ClassUtil.scanPackage(BasePo.class.getPackage().getName());
        classes.forEach(new Consumer<Class<?>>() {
            @Override
            @SneakyThrows
            public void accept(Class<?> aClass) {
                if (aClass.isAnnotationPresent(RepoEnabledAnnotation.class)) {

                }
            }
        });
    }


}
