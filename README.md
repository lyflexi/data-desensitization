# data-desensitization
一种低成本的数据脱敏方案

>数据脱敏，又称数据去隐私化或数据匿名化处理，是一种数据处理技术，旨在保护个人隐私和数据安全。它通过对敏感数据进行转换或修改，使得这些数据在保留其基本特征的同时，不再包含可以直接识别或推断出个人身份或其他敏感信息的具体内容。
数据脱敏通常应用于需要将包含敏感信息的数据集分享给第三方机构（如研究机构、合作伙伴等）时，或者在开发、测试环境中使用生产数据时，以避免敏感信息泄露的风险。


# 需求分析

某银行项目中保存的用户信息【手机号、身份证号、银行卡】需要脱敏展示，点击获取详细信息时，可以展示具体内容

# 设计方案

## 一、前端脱敏（不科学）
后端返回原始数据，前端显示的时候进行遮盖、替换、截断、屏蔽、随机化、泛化等操作
## 二、后端脱敏（推荐）
后端返回的数据已经是脱敏过的，这样更加安全
## 三、数据库脱敏（成本太高，难以维护）
数据库层面脱敏与密码存储一样，要求数据库表字段存储脱敏后的值

具体而言，数据库层面脱敏有以下思路：
1. MD5，MD5是不可逆的摘要算法，又称hash算法。一般仅用于密码
用户注册的时候执行以下sql：
```sql
insert into user (id,pwd) values (1,md5(123456))
```
用户登录的时候执行以下sql：多次md5加密值是相同的
```sql
select * from user where id = 1 and pwd = md5(123456)
```
2. 加密算法，要求支持可逆加解密，如AES，RSA
考虑一种场景，原始手机号查询场景：如果使用上面的MD5则无法反向解密出原始手机号
```sql
select * from user where id = 1
==> phone = fdfafasfsdgg5411ffas
```
使用AES，RSA可以加密后进行存储，使用的时候再解密返回给前端，缺点有二：
- 当数据量大的时候，解密很耗时
- 加密字段无法模糊查询，每次条件查询的时候只能精确匹配加密后的字段值

所以对于加密算法，最终的解决方案是，数据库依然要存储原文，新建一个数据库或者表，专门存储手机号原文和用户信息如：
```sql
id phone
1  18333333333
```
此数据库/表不对外开放

综上所属，使用数据库脱敏的方案，维护成本太高，企业很少使用

# 后端脱敏技术方案
基于注解+反射，实现脱敏组件，实现思路如下：
- 遍历vo的所有属性
- 找到标记脱敏注解的字段
- 取出注解的属性值
- 策略模式，匹配预定义的脱敏策略进行脱敏
![数据脱敏.png](backend-desensitization/pic/%E6%95%B0%E6%8D%AE%E8%84%B1%E6%95%8F.png)

mock请求：
```shell
http://{{ip}}:{{port}}/mock/1
```
测试结果如下：
```java
{
    "code": "000000",
    "message": "handle.success",
    "data": {
        "id": 1,
        "usename": "lyflexi",
        "password": "jgfdhadsga45t2gdf",
        "realname": "刘岩",
        "gender": 0,
        "phone": "137****0000",
        "idCard": "411381********0000",
        "bankCard": "6379************00000"
    },
    "success": true
}
```


> reference : https://www.yuque.com/leifengyang/live/zhlx2pdqvc78oabg



