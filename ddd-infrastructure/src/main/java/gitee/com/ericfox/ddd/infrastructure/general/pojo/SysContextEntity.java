package gitee.com.ericfox.ddd.infrastructure.general.pojo;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseCondition;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseService;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.Constants;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysContext;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysContextEntity implements BaseEntity<SysContext, SysContextEntity> {
    protected BaseCondition<?> _condition;
    protected SysContext _po;

    /**
     * 主键
     */
    private Long id;
    private String tableName;
    private String code;

    private String typeEnum;
    private String responseBodyScript;

    @Override
    public <SERVICE extends BaseService<SysContext, SysContextEntity>> SERVICE service() {
        return null;
    }

    @Override
    public SysContext toPo() {
        if (_po == null) {
            _po = new SysContext();
        }
        BeanUtil.copyProperties(this, _po, Constants.IGNORE_NULL_VALUE_COPY_OPTIONS);
        return _po;
    }

    @Override
    public SysContextEntity fromPo(SysContext _po) {
        this._po = _po;
        BeanUtil.copyProperties(_po, this, Constants.IGNORE_NULL_VALUE_COPY_OPTIONS);
        return this;
    }
}
