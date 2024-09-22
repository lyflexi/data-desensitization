package org.lyflexi.backenddesensitization.strategy.impl;

import org.lyflexi.backenddesensitization.enums.DesensitizationType;
import org.lyflexi.backenddesensitization.strategy.DesensitiveStrategy;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lyflexi
 * @project: data-desensitization
 * @Date: 2024/9/22 19:52
 */
@Component
public class BankCardDesensitiveStrategy implements DesensitiveStrategy {
    @Override
    public boolean support(DesensitizationType type) {
        return type.equals(DesensitizationType.BANK_CARD);
    }

    /**
     * target 是原始字符串。
     * replaceAll 方法用于根据正则表达式模式替换字符串中的内容。
     * 正则表达式 (\\d{4})\\d{8,12}(\\d{4}) 匹配的是由四组数字组成的字符串，其中：
     * (\\d{4}) 第一个捕获组，匹配前四个数字。
     * \\d{8,12} 匹配中间8到12个数字。这表示该部分可以是8位、9位、10位、11位或12位数字。
     * (\\d{4}) 第二个捕获组，匹配最后四个数字。
     *
     * 替换模板 $1************$2 将第一个捕获组（前4位数字）和第二个捕获组（后4位数字）保持不变，而中间的数字全部用星号代替。即使中间的数字长度不是12位，这个模板也会固定使用12个星号，
     * 例如，如果 target 是 "1234567890123456"，那么执行上述代码后的结果将是 "1234************56"。如果 target 是 "12345678901234"（14位数字），结果将是 "1234************34"，虽然实际上只有10位数字被隐藏，但模板仍然使用了12个星号。
     * @param target
     * @return
     */
    @Override
    public String desensitize(String target) {
        return target.replaceAll("(\\d{4})\\d{8,12}(\\d{4})", "$1************$2");
    }
}