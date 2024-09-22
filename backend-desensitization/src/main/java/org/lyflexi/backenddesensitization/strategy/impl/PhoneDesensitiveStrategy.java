package org.lyflexi.backenddesensitization.strategy.impl;

import org.lyflexi.backenddesensitization.enums.DesensitizationType;
import org.lyflexi.backenddesensitization.strategy.DesensitiveStrategy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: lyflexi
 * @project: data-desensitization
 * @Date: 2024/9/22 19:51
 */
@Component
public class PhoneDesensitiveStrategy implements DesensitiveStrategy {
    @Override
    public boolean support(DesensitizationType type) {
        return type.equals(DesensitizationType.PHONE);
    }

    /**
     * (\\d{3})\\d{4}(\\d{4}) 是正则表达式的模式：
     * \\d 表示任意一个数字（等价于 [0-9]）。
     * {3} 指定前面的元素必须连续出现三次，这里指的是三个数字。
     * (\\d{3}) 和 (\\d{4}) 中的圆括号表示捕获组，用来标记出需要保留的部分。第一个捕获组会匹配三个连续的数字，第二个捕获组会匹配四个连续的数字。
     * \\d{4} 在两个捕获组之间，表示中间有四个数字，但这部分不会被捕获组保存，而是会被替换掉。
     *
     * $1****$2 是替换模板：
     * $1 引用了第一个捕获组（即最开始的三个数字）。
     * **** 是用来替换中间四个数字的星号。
     * $2 引用了第二个捕获组（即最后的四个数字）。
     *
     * 例如 target 是一个电话号码，比如 "1234567890"，那么执行上述代码后，结果将会是 "123****890"。这种技术常用于保护个人隐私，例如在显示手机号码时隐藏中间几位数字。
     * @param target
     * @return
     */
    @Override
    public String desensitize(String target) {
        return target.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}