package gitee.com.ericfox.ddd.common.interfaces.application;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;

/**
 * 聚合类
 *
 * @param <ENTITY> entity类
 * @param <AGG>    聚合实现类
 */
public interface BaseAgg<ENTITY extends BaseEntity, AGG extends BaseAgg<ENTITY, AGG>> {
}
