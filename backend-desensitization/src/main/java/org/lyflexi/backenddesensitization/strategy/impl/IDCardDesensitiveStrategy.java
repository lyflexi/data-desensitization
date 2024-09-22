package org.lyflexi.backenddesensitization.strategy.impl;

import org.lyflexi.backenddesensitization.enums.DesensitizationType;
import org.lyflexi.backenddesensitization.strategy.DesensitiveStrategy;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lyflexi
 * @project: data-desensitization
 * @Date: 2024/9/22 19:51
 */
@Component
public class IDCardDesensitiveStrategy implements DesensitiveStrategy {
    @Override
    public boolean support(DesensitizationType type) {
        return type.equals(DesensitizationType.ID_CARD);
    }


    /**
     * target 是原始字符串。
     * replaceAll 方法用于根据正则表达式模式替换字符串中的内容。
     * 正则表达式 (\\d{6})\\d{8}(\\d{4}) 匹配的是由三部分组成的字符串，其中：
     * (\\d{6}) 第一个捕获组，匹配前六个数字。
     * \\d{8} 匹配中间八个数字。
     * (\\d{4}) 第二个捕获组，匹配最后四个数字。
     * 替换模板 $1********$2 将第一个捕获组（前6位数字）和第二个捕获组（后4位数字）保持不变，而中间的8位数字全部用星号代替。
     * 例如，如果 target 是 "1234567890123456"，那么执行上述代码后的结果将是 "123456********3456"。
     *
     * 示例
     * 假设 target 是以下几种情况：
     *
     * 输入: "1234567890123456"
     * 输出: "123456********3456"
     * 输入: "6789012345678901"
     * 输出: "678901********8901"
     * 输入: "1111112222223333"
     * 输出: "111111********3333"
     * @param target
     * @return
     */
    @Override
    public String desensitize(String target) {
        return target.replaceAll("(\\d{6})\\d{8}(\\d{4})", "$1********$2");
    }
}