package org.lyflexi.backenddesensitization.annotation;

/**
 * @Description:
 * @Author: lyflexi
 * @project: data-desensitization
 * @Date: 2024/9/22 19:49
 */
import org.lyflexi.backenddesensitization.enums.DesensitizationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitive {
    DesensitizationType value() default DesensitizationType.RAW;
}
