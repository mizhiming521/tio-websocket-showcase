# tio-websocket-showcase

#### 项目介绍
1. 展示tio-websocket的用法，官方提供的唯一tio-websocket示范教程
2. 包括wss和流量监控及处理等高级特性
3. 还包括t-io作者写的一个用于连接websocket服务器的js小框架----tiows.js（内置断链重连、定时主动发心跳等功能，使用API极其简单）
4. [演示地址](https://t-io.org/ws-showcase/index.html)
5. 效果图
![输入图片说明](https://t-io.org/ws-showcase/img/1.png "在这里输入图片标题")

#### 安装教程

1. 把工程以maven的形式导入到eclipse后
2. 运行org.tio.showcase.websocket.server.ShowcaseWebsocketStarter
3. 用chrome打开page/index.html，这是个用iframe嵌入了两个demo.html的页面，方便在一个窗口查看群聊效果
4. 可以愉快地聊天了

#### 使用说明

1. 服务器端，大家看代码就好，用起来比较简单
2. 客户端（js），page/tio/tiows.js是和业务无关的websocket js小框架，主要是实现了重连、定时发心跳等和业务无关的功能
3. 更多内容请前往：[让天下没有难开发的网络通信](https://t-io.org)

#### 参与贡献

1. 参与本项目最好的方式是多了解：[让天下没有难开发的网络通信](https://t-io.org)

