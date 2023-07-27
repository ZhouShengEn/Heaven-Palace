# Heaven-Palace —— 天宫
### <font color=DarkKhaki>在中国古代神话中，天宫是由众多神仙所组成的仙界，被认为是位于天空中的仙境，也是神仙们的居所和聚会地点。</font>


**Bright-Palace 光明宫**

**Heaven-South-Gate 南天门**

**Jasper-Palace 玉清宫**

**Moon-Palace 广寒宫**

**Peak-Cloud-Palace 凌霄殿**

**Purple-Cloud-Palace 紫霄宫**


    企业化Java后端脚手架！基于SpringBoot 2.3.12.RELEASE 


目前已添加功能：

①标准业务缓存脚手架 —— com.heaven.palace.purplecloudpalace.component.cache

②自定义数据权限隔离注解 —— com.heaven.palace.purplecloudpalace.isolation.annotation.DataIsolation

③基于SocketIO实现集群下通用业务websocket连接 —— com.heaven.palace.peakcloudpalace.business.socket.handler.BusinessMessageEventHandler

④实现代码生成可视化后台接口 —— com.heaven.palace.moonpalace.modular.code.controller.GenCodeController.genCode

⑤实现业务api权限可视化增删改查后台接口（支持在不同数据源中切换环境，自动记录历史操作和操作sql，支持历史记录操作一键同步到不同环境）—— com.heaven.palace.moonpalace.modular.system.controller.BusinessLogController

⑥IP工具，可识别多网段是否存在重叠 —— com.heaven.palace.purplecloudpalace.util.IpAddressUtils

待完成：
1、开发者后台集中管控低代码 前端
2、用户中心 基于jwt redis OAuth2.0 统一认证
3、整合阿里Sentinel组件
4、结合阿里QLExpress、Velocity、Redis完成可视化接口机，专用于第三方平台业务接口对接，实现依靠redis的集群任务调度
5、替换tk.mybatis 为 mybatis-flex
6、微服务框架结合 springcloud+nacos
7、原子级业务操作流程替代@Transactional rollback
8、分布式rollback暂定阿里的seata
9、部署方案暂定k8s+docker

更新中................................



