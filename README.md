# data-desensitization
一种低成本的数据脱敏方案

# 背景调研

>数据脱敏，又称数据去隐私化或数据匿名化处理，是一种数据处理技术，旨在保护个人隐私和数据安全。它通过对敏感数据进行转换或修改，使得这些数据在保留其基本特征的同时，不再包含可以直接识别或推断出个人身份或其他敏感信息的具体内容。
数据脱敏通常应用于需要将包含敏感信息的数据集分享给第三方机构（如研究机构、合作伙伴等）时，或者在开发、测试环境中使用生产数据时，以避免敏感信息泄露的风险。

以MD5加密为例，用户注册的时候执行以下sql：
```sql
insert into user (id,pwd) values (1,md5(123456))
```
用户登录的时候执行以下sql，多次md5加密值是相同的，因此下述sql是有效的
```sql
select * from user where id = 1 and pwd = md5(123456)
```
但是MD5是不可逆的摘要算法，又称hash算法。所以MD5无法反向解密出明文，如果需要根据原始手机号来模糊查询的场景，则无法实现

因此MD5一般仅用于密码的存储场景，比较受限

# 需求分析

比如某银行项目： 
- 用户信息【手机号、身份证号、银行卡】需要脱敏展示
- 同时还希望脱敏后的数据支持模糊查询

# 设计方案

## 方案一、前端脱敏（不科学）
后端返回原始数据，前端显示的时候进行遮盖、替换、截断、屏蔽、随机化、泛化等操作
## 方案二、后端脱敏（推荐）
数据库依旧存储明文，但是经过后端的脱敏处理，再返回给前端，这样更加合理一些
## 方案三、数据库脱敏(维护成本较高)
数据库层面脱敏与密码存储一样，要求数据库表字段存储脱敏后的值

具体而言，数据库层面脱敏有以下思路：


1. 双向加解密算法如AES，RSA，查询时先解密再匹配
考虑一种场景，

此时就需要使用到AES/RSA加密后进行存储，查询之后先放入内存
```sql
select * from user where add_time > '2024-08-01'
==> 放入内存
```
最后将内存中的所有数据进行解密出明文，进一步可以模糊匹配返回给前端


但缺点是当数据量大的时候，解密很耗时，且解密很耗内存

2. 数据库建立起明文与密文的映射表
该方案除了要存储密文信息，还要新创建建映射表

如：明文与用户的映射表
```sql
id userId phone
1   1  18333333333
```
或者如：明文直接与密文的映射表
```sql
id     encode(phone)        phone
1       fasdgqtyq634tgw  18333333333
```
要求此数据库/映射表不对外开放，查找过程如下

- 先在映射表中根据明文字段进行匹配，或者模糊匹配
- 再根据匹配到的密文进行精确查找

但是这样做，无异于脱裤子放屁，因为谁也无法保证明文密文映射表不被泄露

3. 借助于sql中的解密函数
> MySQL AES_DECRYPT函数在解密加密的字符串后返回原始字符串。它使用AES(高级加密标准)算法执行解密。 

函数定义: `AES_DECRYPT(encrypted_string, key_string)`

函数参数：
- encrypted_string -它用于指定加密的字符串。
- key_string -它用于指定用于解密encrypted_string的字符串。
返回值：
- AES_DECRYPT函数返回解密的字符串，如果检测到无效数据，则返回NULL。

首先，向 users 表中插入一些加密数据：
```sql
INSERT INTO users (username, encrypted_credit_card)
VALUES ('alice', AES_ENCRYPT('4111111111111111', 'my_secret_key')),
       ('bob', AES_ENCRYPT('4222222222222222', 'my_secret_key'));
```
然后使用解密后的值进行条件筛选，例如查找所有信用卡号以 4111 开头的用户，可以这样做：
```sql
SELECT id, username, AES_DECRYPT(encrypted_credit_card, 'my_secret_key') AS credit_card
FROM users
WHERE AES_DECRYPT(encrypted_credit_card, 'my_secret_key') LIKE '4111%';
```

不过在where语句中使用函数计算，将会导致索引失效，因此当数据量大的时候，可能会出现慢sql的情况

4. 明文分词，大厂在用，如淘宝
明文分词严格来说，其实不算新的技术，其本质依然是可逆的加解密，只是额外适配了密文的模糊查询
> 根据4位英文字符（半角），2个中文字符（全角）为一个检索条件。将一个字段拆分为多个，分别加密后存储

加密方式：将给定的字符串（例如："taobao123"）使用4个字符为一组的加密方式。

首先将taobao123分割成每组4个字符的块。
- 第一组："taob"
- 第二组："aoba"（这里从第一个字符开始，每次向后移动一个字符）
- 第三组："obao"
- 第四组："bao1"
- 不足4个字符的部分可以补足，具体补足方式取决于你的加密算法
最后将上述四组分词分别加密之后，作为新的列如extra_encrypted_column，单独进行存储

理论上，你可以把所有可能的查询分词都冗余进去存储，这是一种非常典型的空间换时间的做法

支持模糊查询：
- 假设我们要检索包含"taob"的所有数据，执行如下sql
```sql
SELECT * FROM table_name WHERE extra_encrypted_column LIKE '%encreypt(taob)%';
```
- 假设我们要检索包含"aoba"的所有数据，执行如下sql
```sql
SELECT * FROM table_name WHERE extra_encrypted_column LIKE '%encreypt(aoba)%';
```
- 假设我们要检索包含"obao"的所有数据，执行如下sql
```sql
SELECT * FROM table_name WHERE extra_encrypted_column LIKE '%encreypt(obao)%';
```
- 假设我们要检索包含"bao1"的所有数据，执行如下sql
```sql
SELECT * FROM table_name WHERE extra_encrypted_column LIKE '%encreypt(bao1)%';
```


## 方案总结
最后总结一下：
- 前端脱敏不科学
- 使用数据库脱敏的方案，维护成本太高，中小微型企业很少使用

因此，本文以后端程序代码为例，设计与实现一种低成本的数据脱敏方案，见下文

# 正文->低成本的后端脱敏技术方案
基于自定义注解+反射解析，实现脱敏组件，实现思路如下：
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
tips: 测试代码基于pgsql实现
- 容器脚本见：[pgsql.sh](backend-desensitization%2Fsql%2Fpgsql.sh)
- 数据库脚本见：[init.sql](backend-desensitization%2Fsql%2Finit.sql)

如果有需要，大家可以自己替换为mysql

觉得好用，点个star⭐

> reference : 
> 
> 雷丰阳：https://www.yuque.com/leifengyang/live/zhlx2pdqvc78oabg
> 
> 淘宝开放平台：https://developer.alibaba.com/docs/doc.htm?articleId=106213&docType=1
> 
> Hollis：https://www.douyin.com/user/self?from_tab_name=main&modal_id=7417417650504092937&showTab=favorite_collection



