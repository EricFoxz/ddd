package gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl;

import com.github.pagehelper.PageInfo;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.ModelBuilder;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sun.imageio.plugins.common.I18N;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.ModelFactory;

import java.io.Serializable;
import java.util.List;

@Service("repoMysqlStrategy")
public class RepoMysqlStrategy implements RepoStrategy {

    public <T extends BasePo<T>> T findById(T t) {
        Model model = getModel(t);
        Model result = model.findById(t.getId());
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return t;
    }

    @Override
    public <T extends BasePo<T>> boolean deleteById(T t) {
        Model model = getModel(t);
        return model.deleteById(t.getId());
    }

    @Override
    public <T extends BasePo<T>> T insert(T t) {
        Model model = getModel(t);
        Model result = model.put(model);
        BeanUtil.copyProperties(result.toRecord().getColumns(), t, false);
        return t;
    }

    @Override
    public <T extends BasePo<T>> boolean update(T t) {
        Model model = getModel(t);
        model = model.findById(t.getId());
        BeanUtil.copyProperties(t, model, false);
        return model.update();
    }

    @Override
    public <T extends BasePo<T>> List<T> queryPage(T t, int pageNum, int pageSize) {
        Model model = getModel(t);
        SqlPara sqlPara = new SqlPara();
        sqlPara.addPara(t);
        Page paginate = model.paginate(pageNum, pageSize, sqlPara);
        List<?> result = BeanUtil.copyToList(paginate.getList(), t.getClass());
        return (List<T>) result;
    }

    @Override
    public <T extends BasePo<T>> List<T> queryList(T t, int limit) {
        Model model = getModel(t);
        SqlPara sqlPara = new SqlPara();
        sqlPara.addPara(t);
        List list = model.find(sqlPara.getSql() + " limit " + limit);
        List<?> result = BeanUtil.copyToList(list, t.getClass());
        return (List<T>) result;
    }

    private <T extends BasePo<T>> Model getModel(T t) {
        return SpringUtil.getBean(StrUtil.toCamelCase(t.getClass().getSimpleName()) + "Dao");
    }
}
