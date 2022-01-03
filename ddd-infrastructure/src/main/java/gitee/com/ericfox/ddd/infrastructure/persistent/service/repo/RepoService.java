package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.constants.ActiveProperties;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RepoService implements RepoStrategy {
    private final String beanName = ActiveProperties.customProperties.getRepoStrategy();
    private final Map<String, RepoStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public RepoService(Map<String, RepoStrategy> strategyMap) {
        strategyMap.forEach(this.strategyMap::put);
    }

    @Override
    public <T extends BasePo<T>> T findById(T t) {
        return strategyMap.get(beanName).findById(t);
    }

    @Override
    public <T extends BasePo<T>> boolean deleteById(T t) {
        return strategyMap.get(beanName).deleteById(t);
    }

    @Override
    public <T extends BasePo<T>> T insert(T t) {
        return strategyMap.get(beanName).insert(t);
    }

    @Override
    public <T extends BasePo<T>> boolean update(T t) {
        return strategyMap.get(beanName).update(t);
    }

    @Override
    public <T extends BasePo<T>> PageInfo<T> queryPage(T t, int pageNum, int pageSize) {
        return strategyMap.get(beanName).queryPage(t, pageNum, pageSize);
    }

    @Override
    public <T extends BasePo<T>> List<T> queryList(T t, int limit) {
        return strategyMap.get(beanName).queryList(t, limit);
    }
}
