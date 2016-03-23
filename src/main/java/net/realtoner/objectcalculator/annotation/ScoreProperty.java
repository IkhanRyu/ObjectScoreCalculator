package net.realtoner.objectcalculator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScoreProperty {

    /**
     * @return the name of ScorePropertyHandler.
     * */
    String value() default "";

    /**
     *
     * */
    int constantMultiply() default 1;
}
