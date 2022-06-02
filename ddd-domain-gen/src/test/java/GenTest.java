import gitee.com.ericfox.ddd.common.interfaces.infrastructure.Constants;
import gitee.com.ericfox.ddd.common.toolkit.coding.IoUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.ReUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.domain.gen.GenApplication;
import gitee.com.ericfox.ddd.domain.gen.bean.TableJavaBean;
import gitee.com.ericfox.ddd.domain.gen.service.GenCodeService;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GenApplication.class)
public class GenTest {
    @Resource
    private GenCodeService genCodeService;
    @Resource
    private CustomProperties customProperties;

    @Test
    public void test0001() {
        TableJavaBean tableJavaBean = new TableJavaBean(SysUser.class);
        System.out.println(tableJavaBean);
    }

    @Test
    public void updateTempCode() {
        String genResourcePath = Constants.PROJECT_ROOT_PATH + "/src/main/resources";

        String tableName = "sysUser";
        String className = StrUtil.toCamelCase(tableName);
        String ClassName = StrUtil.upperFirst(className);
        String class_name = StrUtil.toUnderlineCase(className);
        String domainName = StrUtil.splitToArray(class_name, '_', -1)[0];
        Map<String, String> map = MapUtil.newLinkedHashMap();
        map = MapUtil.builder(map)
                .put("gen/velocity_home/po/Po.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/po/Po.java.vm"))
                .put("gen/velocity_home/dao/my_sql_repo_strategy/Dao.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/dao/my_sql_repo_strategy/Dao.java.vm"))
                .put("gen/velocity_home/dao/lucene_repo_strategy/Dao.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/dao/lucene_repo_strategy/Dao.java.vm"))
                .put("gen/velocity_home/dto/Dto.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/dto/Dto.java.vm"))
                .put("gen/velocity_home/dto/base/DtoBase.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/dto/base/DtoBase.java.vm"))
                .put("gen/velocity_home/entity/Entity.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/entity/Entity.java.vm"))
                .put("gen/velocity_home/entity/base/EntityBase.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/entity/base/EntityBase.java.vm"))
                .put("gen/velocity_home/service/Service.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/service/Service.java.vm"))
                .put("gen/velocity_home/service/base/ServiceBase.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/service/base/ServiceBase.java.vm"))
                .put("gen/velocity_home/param/PageParam.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/param/PageParam.java.vm"))
                .put("gen/velocity_home/param/DetailParam.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/param/DetailParam.java.vm"))
                .put("gen/velocity_home/controller/Controller.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/controller/Controller.java.vm"))
                .put("gen/velocity_home/controller/base/ControllerBase.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/controller/base/ControllerBase.java.vm"))
                .put("gen/velocity_home/context/Context.java.vm", genCodeService.getTemplateCodeWithOutRendering("gen/velocity_home/context/Context.java.vm"))
                .build();
        map.forEach(new BiConsumer<String, String>() {
            @Override
            @SneakyThrows
            public void accept(String key, String value) {
                value = StrUtil.replace(value, ClassName, "${meta.ClassName}");
                value = StrUtil.replace(value, class_name, "${meta.class_name}");
                value = StrUtil.replace(value, className, "${meta.className}");
                value = StrUtil.replace(value, customProperties.getRootPackage(), "${rootPackage}");
                value = ReUtil.replaceAll(value, Pattern.compile("\\b(" + domainName + ")\\b"), "${meta.domainName}");
                IoUtil.writeUtf8(new FileOutputStream(genResourcePath + "/" + key), true, value);
            }
        });
    }
}
