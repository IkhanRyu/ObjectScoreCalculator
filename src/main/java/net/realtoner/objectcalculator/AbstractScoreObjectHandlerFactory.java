package net.realtoner.objectcalculator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public abstract class AbstractScoreObjectHandlerFactory implements ScoreObjectHandlerFactory{

    private Map<Class<? extends Annotation> , CalculationContextBuilder> annotationMap = new HashMap<>();


    /**
     * {@inheritDoc}
     * */
    @Override
    public void addScorePropertyAnnotation(Class<? extends Annotation> annotationClass,
                                           CalculationContextBuilder builder) {

        annotationMap.put(annotationClass, builder);
    }

    /**
     * @param annotations
     * @return
     * */
    protected CalculationContext createCalculationContext(Annotation... annotations){

        CalculationContext calculationContext = new CalculationContext();

        for(Annotation annotation : annotations){
            CalculationContextBuilder builder = annotationMap.get(annotation.annotationType());

            if(builder != null)
                builder.build(annotation, calculationContext);
        }

        return calculationContext;
    }
}
