DUBBO-POSTMAN

经过改造简化之后的Dubbo-Postman版本
- 去除了动态api jar 加载模块
- 修复bug：当请求参数中使用父类接收子类信息的时候，json 反序列化会丢失子类信息，从而导致请求参数转化异常，请求失

==============

<img src="https://github.com/everythingbest/dubbo-postman/blob/master/gif/logo.png?raw=true" alt="dubbo postman logo" height="90px" align="right" />

**DUBBO-POSTMAN**: 一个用于通过web-ui页面访问dubbo接口的工具,灵感源于
[postman](https://www.getpostman.com/products)

# 原介绍
**DUBBO-POSTMAN** 是一个通过web页面访问dubbo接口的开源工具,包括零代码创建一个dubbo consumer,保存访问用例,构建场景测试.
核心功能如下.

- 通过添加一个api的maven dependency即可创建一个dubbo consumer
- webui页面自动生成dto的各个参数
- 保存常用的请求作为用例
- 根据保存的用例即可构建复杂的场景测试,极大减少回归测试的工作量
- 开箱即用

DUBBO-POSTMAN UI
===============

## 开发

 基于[vue](https://github.com/vuejs/vue)+
 [element-ui](https://element.eleme.cn/#/zh-CN)+
 [vue-element-admin](https://panjiachen.github.io/vue-element-admin-site/zh/).

### 准备

下载源码到本地

```
git clone https://github.com/everythingbest/dubbo-postman.git
npm install --registry=https://registry.npm.taobao.org
```

### 本地调试
```
npm run dev
```

### 发布
```
npm run build
```


DUBBO-POSTMAN BACKEND
===============

首先需要确保代码已经下载到本地,后台的所有配置在项目的src/resource/application.properties文件里面

## 启动
在项目根目录下执行 maven clean package ,然后在命令行执行 java -jar target/dubbo-postman.jar

使用演示
===============

<p align="center">
dubbo demo创建
<img align="center" alt="face" src="https://github.com/everythingbest/dubbo-postman/blob/master/gif/dubbo-postman-1.gif?raw=true" />
</p>
<p align="center">
创建服务
 <img align="center" alt="face" src="https://github.com/everythingbest/dubbo-postman/blob/master/gif/dubbo-postman-2.gif?raw=true" />
</p>
<p align="center">
场景测试
 <img align="center" alt="face" src="https://github.com/everythingbest/dubbo-postman/blob/master/gif/dubbo-postman-3.gif?raw=true" />
</p>
<p align="center">
注册中心添加
 <img align="center" alt="face" src="https://github.com/everythingbest/dubbo-postman/blob/master/gif/dubbo-postman-4.gif?raw=true" />
</p>

