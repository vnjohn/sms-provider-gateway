# sms-provider-vnjohn
支持多种短信服务提供商且可自行扩展

## 开源初衷
基于实际工作中短信服务场景，整理一套可适配、动态扩展的短信网关服务

更多详情，可以看作者 CSDN 个人博客：https://blog.csdn.net/vnjohn/article/details/129575809

## 如何使用
更改引入的额外配置文件：sms-provider-config.yaml
调整好对应的内容，如下：
```yaml
sms:
    provider:
        # TencentCloud、AliCloud、HuaWeiCloud
        service: TencentCloud
        # 阿里云
        ali:
            access-key:
            secret:
        # 腾讯云
        tencent:
            access-key: 
            secret: 
            sdkAppId: 
```
