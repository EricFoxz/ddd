package gitee.com.ericfox.ddd.infrastructure.general.common.framework;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.reflect.Method;

/**
 * 根据配置文件中的枚举判断是否注入
 */
public class OnPropertyEnumCondition<T extends BaseEnum<T, ?>> implements Condition {
    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        MergedAnnotations annotations = metadata.getAnnotations();
        if (!annotations.isPresent(ConditionalOnPropertyEnum.class)) {
            return true;
        }
        MergedAnnotation<ConditionalOnPropertyEnum> annotation = annotations.get(ConditionalOnPropertyEnum.class);
        String value = annotation.getValue("value", String.class).orElse("");
        Class<BasePropertiesEnum<T>> enumClass = (Class<BasePropertiesEnum<T>>) annotation.getValue("enumClass", Class.class).orElse(null);
        String[] includeAllValue = annotation.getValue("includeAllValue", String[].class).orElse(null);
        String[] includeAnyValue = annotation.getValue("includeAnyValue", String[].class).orElse(null);
        String[] propertyValues = environment.getProperty(value, String[].class);
        if (ArrayUtil.isEmpty(propertyValues) || enumClass == null || (ArrayUtil.isEmpty(includeAnyValue) && ArrayUtil.isEmpty(includeAllValue))) {
            return false;
        }
        Method valuesMethod = enumClass.getMethod("values", (Class<?>[]) null);
        BasePropertiesEnum<T>[] enums = (BasePropertiesEnum<T>[]) valuesMethod.invoke(null, (Object[]) null);
        boolean flag = true;
        //传入的枚举值必须全匹配
        if (ArrayUtil.isNotEmpty(includeAllValue)) {
            checkValues(enums, includeAllValue);
            a:
            for (String enumStr : includeAllValue) {
                for (String propertyValue : propertyValues) {
                    if (enumStr.equalsIgnoreCase(propertyValue)) {
                        continue a;
                    }
                }
                flag = false;
                break;
            }
            if (!flag) {
                return false;
            }
        }
        //传入的枚举值匹配其中任意即可
        if (ArrayUtil.isNotEmpty(includeAnyValue)) {
            checkValues(enums, includeAnyValue);
            for (String enumStr : includeAnyValue) {
                for (String propertyValue : propertyValues) {
                    if (enumStr.equalsIgnoreCase(propertyValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查参数有效性
     */
    private void checkValues(BasePropertiesEnum<T>[] enums, String[] values) {
        a:
        for (String enumStr : values) {
            for (BasePropertiesEnum<T> anEnum : enums) {
                if (anEnum.equals(enumStr)) {
                    continue a;
                }
            }
            throw new ProjectFrameworkException("OnPropertyEnumCondition:checkValues "+enumStr + "不是枚举的有效值");
        }
    }
}
