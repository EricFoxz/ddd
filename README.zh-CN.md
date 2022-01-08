# ddd

## 介绍

一个ddd思维，充血模型实现的项目 [https://gitee.com/EricFox/ddd](https://gitee.com/EricFox/ddd)

## 软件结构

```text
- ddd-apis API接口层
  - model 视图模型，数据模型定义 vo/dto
  - assembler 装配器，实现模型转换eg. apiModel <--> domainModel
  - controller 控制器,对外提供（RestFUL）接口
- ddd-application 应用层
  - service 应用服务，非核心服务
  - task 任务定义
  - * others 其他
- ddd-*-domain 领域层
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
