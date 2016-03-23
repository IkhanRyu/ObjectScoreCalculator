package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.annotation.ScoreProperty;
import net.realtoner.objectcalculator.date.DateCalculationContextBuilder;
import net.realtoner.objectcalculator.date.annotation.DateProperty;
import net.realtoner.objectcalculator.exception.InvalidScoreObjectException;
import net.realtoner.objectcalculator.handler.FloatingPointPropertyHandler;
import net.realtoner.objectcalculator.handler.IntegerPropertyHandler;
import net.realtoner.objectcalculator.handler.StringPropertyHandler;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class ObjectScoreCalculatorBuilder {

    private final Set<Class<?>> classSet = new HashSet<>();
    private final Map<String, ScorePropertyHandler> nameScorePropertyHandlerMap = new HashMap<>();
    private final Map<String, ScorePropertyHandler> classScorePropertyHandlerMap = new HashMap<>();

    private final Map<Class<? extends Annotation>, CalculationContextBuilder> annotationCalculationContextBuilderMap = new HashMap<>();

    public ObjectScoreCalculatorBuilder addObjectScoreObjectClass(Class<?> clazz) {
        classSet.add(clazz);

        return this;
    }

    public ObjectScoreCalculatorBuilder addScorePropertyHandler(String name, ScorePropertyHandler propertyHandler) {
        nameScorePropertyHandlerMap.put(name, propertyHandler);

        return this;
    }

    public ObjectScoreCalculatorBuilder addScorePropertyHandler(Class<?> clazz, ScorePropertyHandler propertyHandler) {
        classScorePropertyHandlerMap.put(clazz.getName(), propertyHandler);

        return this;
    }

    private boolean integerScorePropertyHandlerFlag = false;

    public ObjectScoreCalculatorBuilder addIntegerScorePropertyHandler(ScorePropertyHandler handler) {
        classScorePropertyHandlerMap.put(Short.class.getName(), handler);
        classScorePropertyHandlerMap.put(Integer.class.getName(), handler);
        classScorePropertyHandlerMap.put(Long.class.getName(), handler);
        classScorePropertyHandlerMap.put("short", handler);
        classScorePropertyHandlerMap.put("int", handler);
        classScorePropertyHandlerMap.put("long", handler);

        integerScorePropertyHandlerFlag = true;

        return this;
    }

    private boolean floatingPointScorePropertyHandlerFlag= false;

    public ObjectScoreCalculatorBuilder addFloatingPointScorePropertyHandler(ScorePropertyHandler handler) {
        classScorePropertyHandlerMap.put(Float.class.getName(), handler);
        classScorePropertyHandlerMap.put(Double.class.getName(), handler);
        classScorePropertyHandlerMap.put("float", handler);
        classScorePropertyHandlerMap.put("double", handler);

        floatingPointScorePropertyHandlerFlag = true;

        return this;
    }

    private boolean stringScorePropertyHandlerFlag = false;

    public ObjectScoreCalculatorBuilder addStringScorePropertyHandler(ScorePropertyHandler handler) {
        classScorePropertyHandlerMap.put(String.class.getName(), handler);
        classScorePropertyHandlerMap.put("string", handler);

        stringScorePropertyHandlerFlag = true;

        return this;
    }

    private ScoreObjectHandlerFactory scoreObjectHandlerFactory = null;

    public ObjectScoreCalculatorBuilder setScoreObjectHandlerFactory(ScoreObjectHandlerFactory scoreObjectHandlerFactory) {
        this.scoreObjectHandlerFactory = scoreObjectHandlerFactory;

        return this;
    }

    /*
    * methods for CalculationContextBuilder
    * */

    public ObjectScoreCalculatorBuilder addCalculationContextBuilder(
            Class<? extends Annotation> annotationClass, CalculationContextBuilder calculationContextBuilder){
        this.annotationCalculationContextBuilderMap.put(annotationClass, calculationContextBuilder);

        return this;
    }

    /**
     *
     * @param flag
     * */
    public ObjectScoreCalculatorBuilder setUseDateProperty(boolean flag){

        if(!annotationCalculationContextBuilderMap.containsKey(DateProperty.class) && flag){
            annotationCalculationContextBuilderMap.put(DateProperty.class, new DateCalculationContextBuilder());
        }else if(annotationCalculationContextBuilderMap.containsKey(DateProperty.class) && !flag){

            annotationCalculationContextBuilderMap.remove(DateProperty.class);
        }

        return this;
    }

    private ScoreObjectHandlerFactory getDefaultScoreObjectHandlerFactory() {
        return new DefaultScoreObjectHandlerFactory();
    }

    private CalculationContextBuilder getScorePropertyCalculationContextBuilder(){
        return new ScorePropertyCalculationContextBuilder();
    }

    static class ScorePropertyCalculationContextBuilder implements CalculationContextBuilder{

        @Override
        public void build(Annotation annotation, CalculationContext calculationContext) {

            ScoreProperty scoreProperty = (ScoreProperty)annotation;

            calculationContext.setConstantMultiply(scoreProperty.constantMultiply());
        }
    }

    private void addDefaultScorePropertyHandler() {

        if(!integerScorePropertyHandlerFlag){
            addIntegerScorePropertyHandler(new IntegerPropertyHandler());
        }

        if(!floatingPointScorePropertyHandlerFlag){
            addFloatingPointScorePropertyHandler(new FloatingPointPropertyHandler());
        }

        if(!stringScorePropertyHandlerFlag){
            addStringScorePropertyHandler(new StringPropertyHandler());
        }
    }

    private void addDefaultCalculationContextBuilder() {

        if (!annotationCalculationContextBuilderMap.containsKey(ScoreProperty.class)) {
            annotationCalculationContextBuilderMap.put(ScoreProperty.class,
                    getScorePropertyCalculationContextBuilder());
        }
    }

    public ObjectScoreCalculator build() throws InvalidScoreObjectException {

        ObjectScoreCalculator objectScoreCalculator = new ObjectScoreCalculator();

        if(scoreObjectHandlerFactory == null){
            scoreObjectHandlerFactory = getDefaultScoreObjectHandlerFactory();
        }

        addDefaultScorePropertyHandler();

        addDefaultCalculationContextBuilder();

        for(Map.Entry<Class<? extends Annotation>, CalculationContextBuilder> entry :
                annotationCalculationContextBuilderMap.entrySet()){
            scoreObjectHandlerFactory.addScorePropertyAnnotation(entry.getKey(), entry.getValue());
        }

        for (Class<?> clazz : classSet) {
            objectScoreCalculator.putScoreObjectHandlerMap(clazz,
                    scoreObjectHandlerFactory.make(clazz, nameScorePropertyHandlerMap, classScorePropertyHandlerMap));
        }

        return objectScoreCalculator;
    }
}
