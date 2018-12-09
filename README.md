## 项目介绍
    新巴巴运动网,基于Spring+SpringMVC+Mybatis分布式敏捷开发系统架构，提供整套公共微服务服务模块:内容管理,用户管理（包括第三方）,商品管理,搜索管理,单点登录,订单管理,购物车管理
    
### 组织结构
    xbb-parent
    ├── xbb-common -- SSM框架公共模块
    ├── xbb-home-web -- 前台首页[端口:8087]
    ├── xbb-login-web -- 登录客户端[端口:8088]
    ├── xbb-manager-web -- 后台客户端[端口:8081]
    ├── xbb-search-web -- 搜索客户端[端口:8123]
    ├── xbb-page -- 商品详情页面模块[端口:8888]
    ├── xbb-cart-web -- 购物车客户端[端口:1234]
    ├── xbb-order-web -- 订单客户端[端口:8881]
    |    ├── xbb-page-interface -- rpc接口包
    |    ├── xbb-page-service -- rpc服务提供者[端口:8888]
    ├── xbb-product -- 商品模块
    |    ├── xbb-product-interface -- rpc接口包
    |    ├── xbb-product-domain -- 商品领域对象
    |    ├── xbb-product-dao -- 持久层
    |    └── xbb-product-service -- rpc服务提供者[端口:8080]
    ├── xbb-search -- 搜索模块
    |    ├── xbb-search-interface -- rpc接口包
    |    ├── xbb-search-service -- rpc服务提供者[端口:8089]
    ├── xbb-user -- 用户模块
    |    ├── xbb-user-domain -- 用户领域对象
    |    ├── xbb-user-dao -- 持久化层
    |    ├── xbb-user-interface-- rpc接口包
    |    └── xbb-user-service -- rpc服务提供者[端口:8084]
    ├── xbb-sso -- 单点登录模块
    |    ├── xbb-sso-interface -- rpc接口包
    |    ├── xbb-sso-service -- rpc服务提供者[端口:8083]
    ├── xbb-content -- 首页内容模块
    |    ├── xbb-content-interface -- rpc接口包
    |    ├── xbb-content-service -- rpc服务提供者[端口:8801]
    ├── xbb-order --订单模块
    |    ├── xbb-order-domain -- 订单领域对象
    |    ├── xbb-order-dao -- 持久化层
    |    ├── xbb-order-interface-- rpc接口包
    |    └── xbb-order-service -- rpc服务提供者[端口:8882]
    ├── xbb-cart -- 购物车模块
    |    ├── xbb-cart-interface -- rpc接口包
    |    ├── xbb-cart-service -- rpc服务提供者[端口:8086]
    |─────
    
### 技术选型

#### 后端技术:
技术 | 名称 | 官网
----|------|----
Spring Framework| 容器  |[http://projects.spring.io/spring-framework/](http://projects.spring.io/spring-framework/)
SpringMVC | MVC框架  | [http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc]
Spring session | 分布式Session管理  | [http://projects.spring.io/spring-session/](http://projects.spring.io/spring-session/)
MyBatis | ORM框架  | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
MyBatis-plus | mybatis增强工具  | [http://mp.baomidou.com/](http://mp.baomidou.com/)
MyBatis Generator | 代码生成  | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
PageHelper | MyBatis物理分页插件  | [http://git.oschina.net/free/Mybatis_PageHelper](http://git.oschina.net/free/Mybatis_PageHelper)
Druid | 数据库连接池  | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
ZooKeeper | 分布式协调服务  | [http://zookeeper.apache.org/](http://zookeeper.apache.org/)
Dubbo | 分布式服务框架  | [http://dubbo.io/](http://dubbo.io/)
Redis | 分布式缓存数据库  | [https://redis.io/](https://redis.io/)
Solr & Elasticsearch | 分布式全文搜索引擎  | [http://lucene.apache.org/solr/](http://lucene.apache.org/solr/) [https://www.elastic.co/](https://www.elastic.co/)
ActiveMQ | 消息队列  | [http://activemq.apache.org/](http://activemq.apache.org/)
FastDFS | 分布式文件系统  | [https://github.com/happyfish100/fastdfs](https://github.com/happyfish100/fastdfs)
Log4J | 日志组件  | [http://logging.apache.org/log4j/1.2/](http://logging.apache.org/log4j/1.2/)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)
freemarker | 模板引擎  | [https://freemarker.apache.org/](https://freemarker.apache.org/)
git | 分布式版本控制 |[https://git-scm.com/](https://git-scm.com/)
hutool | 工具集 |[https://www.hutool.cn/](https://www.hutool.cn/)

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        