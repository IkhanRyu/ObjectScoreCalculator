package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.exception.InvalidScoreObjectException;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public interface ScoreObjectHandlerFactory {

    /**
     *
     * @param annotationClass
     * @param builder
     * */
    void addScorePropertyAnnotation(Class<? extends Annotation> annotationClass, CalculationContextBuilder builder);

    /**
     *
     * */
    ScoreObjectHandler make(Class<?> clazz, Map<String, ScorePropertyHandler> namePropertyHandler,
                           Map<String, ScorePropertyHandler> classPropertyHandlerMap) throws InvalidScoreObjectException;
}
