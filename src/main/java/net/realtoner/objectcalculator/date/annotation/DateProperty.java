package net.realtoner.objectcalculator.date.annotation;

import net.realtoner.objectcalculator.date.DateIntervalType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateProperty {

    DateIntervalType dateIntervalType() default DateIntervalType.DATE;
}
