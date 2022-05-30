package gitee.com.ericfox.ddd.infrastructure.general.config.events;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.ThreadUtil;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.CustomProperties;
import gitee.com.ericfox.ddd.infrastructure.general.config.service.JFinalRepoConfig;
import gitee.com.ericfox.ddd.infrastructure.general.config.service.LuceneRepoConfig;
import gitee.com.ericfox.ddd.infrastructure.general.pojo.SysContextEntity;
import gitee.com.ericfox.ddd.infrastructure.service.repo.RepoService;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FrameworkLoadingContextEvent {
    static volatile boolean isReady = false;
    @Getter
    private static final Map<String, SysContextEntity> contextEntityMap = MapUtil.newConcurrentHashMap();
    @Resource
    private RepoService repoService;
    @Resource
    private CustomProperties customProperties;

    /**
     * 从持久化途径加载表的上下文
     */
    @Autowired
    public synchronized void loadSysContext() {
        //springboot初始化完成后，才能获取到数据库连接
        if (isReady) {
            return;
        }
        ThreadUtil.execAsync(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                while (!JFinalRepoConfig.isReady || !LuceneRepoConfig.isReady) {
                }
                SysContextEntity sysContextEntity = new SysContextEntity();
                List<SysContextEntity> sysContextEntities = repoService.queryList(sysContextEntity);
                contextEntityMap.clear();
                for (SysContextEntity contextEntity : sysContextEntities) {
                    String className = StrUtil.toCamelCase(contextEntity.getTableName());
                    contextEntityMap.put(className, contextEntity);
                }
                isReady = true;
            }
        });

    }
}
