package org.lyflexi.backenddesensitization.component;

import org.lyflexi.backenddesensitization.annotation.Desensitive;
import org.lyflexi.backenddesensitization.strategy.DesensitiveStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description:
 * @Author: lyflexi
 * @project: data-desensitization
 * @Date: 2024/9/22 19:55
 */
@Component
public class DesensitiveComponent {

    @Autowired
    private List<DesensitiveStrategy> strategies;

    /**
     *
     * @param source
     * @param target
     */
    public void desensitize(Object source, Object target) {
        BeanUtils.copyProperties(source, target);

        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Desensitive.class)) {
                field.setAccessible(true);
                Desensitive annotation = field.getAnnotation(Desensitive.class);
                for (DesensitiveStrategy strategy : strategies) {
                    if (strategy.support(annotation.value())) {
                        try {
                            String originalValue = (String) field.get(target);
                            String desensitizedValue = strategy.desensitize(originalValue);
                            field.set(target, desensitizedValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
