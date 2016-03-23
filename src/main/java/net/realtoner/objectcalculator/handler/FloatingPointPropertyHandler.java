package net.realtoner.objectcalculator.handler;

import net.realtoner.objectcalculator.CalculationContext;
import net.realtoner.objectcalculator.ScorePropertyHandler;
import net.realtoner.objectcalculator.exception.ScoreCalculationException;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class FloatingPointPropertyHandler implements ScorePropertyHandler{

    @Override
    public long getScore(Object value, CalculationContext calculationContext) throws ScoreCalculationException{

        if(value instanceof Double){
            return (long)((double)value * calculationContext.getConstantMultiply());
        }else if(value instanceof Float){
            return (long)((float)value * calculationContext.getConstantMultiply());
        }else{
            throw new ScoreCalculationException("Given value '"+value.getClass().getName()+"' is not floating point.");
        }
    }
}
