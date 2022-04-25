package gitee.com.ericfox.ddd.common.toolkit.coding;

import cn.hutool.core.bean.DynaBean;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 反射工具类
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {
    @SneakyThrows
    public static <T extends Annotation, U> U annotationToBean(T annotation, Class<U> beanClass) {
        DynaBean dynaBean = DynaBean.create(beanClass);
        Field[] fields = ReflectUtil.getFields(beanClass);
        for (Field field : fields) {
            String name = field.getName();
            Object value = ReflectUtil.getPublicMethod(annotation.getClass(), name, (Class<?>[]) null).invoke(annotation, (Object[]) null);
            dynaBean.set(name, value);
        }
        return dynaBean.getBean();
    }
}
