# AES-Java-iOS-Android
AES-Java-iOS-Android,兼容Java,iOS,Android三端的AES-128-ECB加密算法

代码逻辑中最后的data byte，转换成了16进制（HEX）返回最终加密串，当然使用base64也是可以的。

代码中的password必须为16位，低于16位其实不是不可以，得进行补全操作，demo中未处理。

password:1234512345123456

未加密字符串：12382929sadadasd

加密后的HEX字符串：071ec0de1f3bf09643aca32c743a9c07a5030bc46cfad652b45cc8dbeaea0eee

http://tool.chacuo.net/cryptaes 可以进行在线测试（ECB/ PKCS5/7 /数据块128 / 输出 hex）