package net.realtoner.objectcalculator;

import java.lang.annotation.Annotation;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public interface CalculationContextBuilder {

    /**
     *
     * @param calculationContext
     * */
    void build(Annotation annotation, CalculationContext calculationContext);
}
