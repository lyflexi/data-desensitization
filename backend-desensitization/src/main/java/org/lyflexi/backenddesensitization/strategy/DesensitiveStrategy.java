package org.lyflexi.backenddesensitization.strategy;

import org.lyflexi.backenddesensitization.enums.DesensitizationType;

/**
 * @Description:
 * @Author: lyflexi
 * @project: data-desensitization
 * @Date: 2024/9/22 19:50
 */
public interface DesensitiveStrategy {
    boolean support(DesensitizationType type);
    String desensitize(String target);
}