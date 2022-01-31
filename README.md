# ddd
[![EricFox/ddd](https://gitee.com/EricFox/ddd/widgets/widget_card.svg?colors=ff6857,000000,ffffff,e3e9ed,666666,739bff)](https://gitee.com/EricFox/ddd)
## 介绍
一个ddd思维，充血模型实现的项目 [https://gitee.com/EricFox/ddd](https://gitee.com/EricFox/ddd)

### 基本理念
- 避免"基于数据库编程"，领域层的entity并不对应数据库表，虽然会在入库的时候做转换，但业务变化并不直接导致数据库变化
- 积极采用策略设计模式，结合注解，延长项目寿命
- 多采用防腐层隔离服务与业务
- 领域的划分是基于具体业务的，而非服务
- 本项目采用的DCI设计模式，适用于业务较为复杂的项目，若项目不大则还应该用MVC模式
- 复杂的结构和频繁的反射，导致了对开发人员的素质要求较高，使用前的培训是必要的
- 只使用最佳方案，拥抱新的协议和技术，优先轻量级实现，因为AOP的思想，所以若是比较重的技术如果层次抽象的好。也可采用

## 软件结构
![](https://images0.cnblogs.com/blog/492619/201309/05132928-5d68253031d7418ea62b6ae96e4b90e3.jpg)

```text
- ddd-apis API接口层
	- model 视图模型，数据模型定义 vo/dto
	- assembler 装配器，实现模型转换eg. apiModel <--> domainModel
	- controller 控制器,对外提供（RestFUL）接口
- ddd-application 应用层
	- service 应用服务，非核心服务
	- task 任务定义
	- * others 其他
- ddd-domain-* 领域层
	- common 公共代码抽取，限于领域层有效
	- events 领域事件
	- model 领域模型
		- dict 领域划分的模块，可理解为子域划分
			- DictVo.java 领域值对象
			- DictEntity.java 领域值实体，充血的领域模型，如本身的CRUD操作在此处
			- DictAgg.java 领域聚合，通常表现为实体的聚合，需要有聚合根
			- DictService.java 领域服务，不能归与上述模型，如分页条件查询等可写在此处
	- service 领域服务类，一些不能归属某个具体领域模型的行为
	- factory 工厂类，负责复杂领域对象创建，封装细节
- ddd-infrastructure 基础设施层
	- persistent 持久化机制
		- po 持久化对象
		- repository 仓储类，持久化接口&实现，可与ORM映射框架结合
	- general 通用技术支持，向其他层输出通用服务
		- config 配置类
		- toolkit 工具类
		- common 基础公共模块等
```

## 安装教程

#### 使用说明

#### 参与贡献

#### 特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5. Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

## ddd #TODO list
- [ ] 缓存问题
    - [x] Caffeine一级缓存
    - [x] Redis二级缓存
    - [x] 策略设计模式
    - [ ] 并发下的同步，CP和AP的取舍
    - [Redis注解集成之解决@CacheEvict只能单一清除的扩展 -模糊清除多条缓存](https://blog.csdn.net/qq_33454884/article/details/89330649)
    - [Caffeine 详解 —— Caffeine 使用](https://zhuanlan.zhihu.com/p/329684099)
- [x] 整合MQ
    - [x] RabbitMQ
    - [x] Kafka
- [ ] 持久化问题
    - [ ] ~~分库分表~~(可选)
    - [ ] 整合MongoDB方式的持久化
    - [ ] 整合HDFS或HBase
    - [x] 整合MySQL
    - [x] 整合lucene
    - [x] 策略设计模式
- [ ] 常用功能
    - [ ] 整合OAuth2
    - [ ] 流式传输
    - [ ] 工作流
    - [ ] 代码生成
    - [ ] 日志方案
      - 
- [ ] 分布式/微服务
    - [ ] 尝试使用RSocket实现多实例/微服务之间的通信
  