# UCloudTeach-Monolithic

### 项目介绍
#### 1）项目说明
本项目选题自《2021华为云DevCloud开发者大赛-河南》赛题二，基本需求为在线教育平台应用的开发，后端功能需求如下：
<img src="https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/150d8857e6f444999b420fd8b9ca0ad2~tplv-k3u1fbpfcp-watermark.image">

最终项目定名为“优云教在线教育平台”，本仓库为项目的单体架构版本，基本功能都已实现，可用于毕设、练手Demo等。

此版架构优点是构建难度较小，缺点是服务边界不明确、维护成本较高。

#### 2）项目前端
一个完整的项目应用自然不能少了前端页面展示，项目前端页面分为管理端和学生端：

① 管理端使用`Vue3+Ant-Design-Vue`搭建，仓库地址为：[UCloudTeach-Admin](https://gitee.com/h0ss/ucloud-teach-admin)；

② 学生端使用`Vue3+Vant3`搭建，仓库地址为：xxx【整理完毕补充】；


### 基本架构
<img src="https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5c7d4eb4d9fd4ad0a791715b1d63e418~tplv-k3u1fbpfcp-watermark.image" alt="">

### 项目技术点
#### 1）数据存取
① 项目数据库使用MySQL，集成Mybatis进行数据存取操作，使用代码生成插件进行实体类以及mapper代码的构建；

② 集成pagehelper插件进行分页查询，可有效避免接口全量查询数据造成数据库过压情况；

③ 集成redis做数据的缓存，提升读多写少数据、热点数据的访问效率，同时实现了分布式session；

④ 集成华为云OBS进行图片等文件的存储；

*PS: 项目中的redis以及MySQL需要手动配置*

#### 2）数据处理与日志收集
① 使用雪花算法生成分布式自增id，在多机器环境下可以生成唯一有序ID，方便数据库索引构建；

② 集成fastjson实现数据的序列化与反序列化；

③ 集成validation进行数据的后端校验；

④ 集成slf4j进行日志的收集，并且为日志生成唯一的流水号，以方便运维排错；

#### 3）权限校验
① 使用轻量化开源框架sa-token做权限校验，相比于shiro与SpringSecurity集成配置更加方便；

② token信息使用本地64位随机字符串；

③ 基于Redis实现token信息的分布式存储，

④ 利用框架进行角色权限管理，针对不同用户接口进行权限校验；

⑤ 集成短信接口实现短信下发功能，集成腾讯防水墙服务进行滑动验证码的前后端校验【参考官网配置】；

#### 4）支付功能
① 集成支付宝沙箱支付功能实现课程的购买功能，用户提交购买请求后返回支付宝支付页面；

② 通过后端接受支付宝异步通知来获取用户支付状态，支付成功则写入数据表中；

*PS: 参考支付宝沙箱环境搭建配置*

### 项目使用

1.  克隆本项目: `git clone https://gitee.com/h0ss/ucloud-teach-monolithic.git`
2.  安装mvn依赖: `mvn clean install` 
3.  检查配置文件，完成数据库、Redis等相关信息的配置
4.  运行启动类: `mvn spring-boot:run`
