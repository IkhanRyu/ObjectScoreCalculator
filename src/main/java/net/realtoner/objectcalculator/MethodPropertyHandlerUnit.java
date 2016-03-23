package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.exception.ScoreCalculationException;

import java.lang.reflect.Method;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public final class MethodPropertyHandlerUnit {

    private Method method = null;
    private ScorePropertyHandler scorePropertyHandler = null;
    private CalculationContext calculationContext = null;

    MethodPropertyHandlerUnit(Method method, ScorePropertyHandler propertyHandler, CalculationContext calculationContext){
        this.method = method;
        this.scorePropertyHandler = propertyHandler;
        this.calculationContext = calculationContext;
    }

    /**
     *
     * @param object
     * @return
     * @throws ScoreCalculationException
     * */
    public long getScore(Object object) throws ScoreCalculationException {

        try {
            Object returnValue = method.invoke(object);

            return scorePropertyHandler.getScore(returnValue,calculationContext);

        } catch (Exception e) {
            throw new ScoreCalculationException(e);
        }
    }
}
