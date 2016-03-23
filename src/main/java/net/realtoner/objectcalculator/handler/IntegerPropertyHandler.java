package net.realtoner.objectcalculator.handler;

import net.realtoner.objectcalculator.CalculationContext;
import net.realtoner.objectcalculator.ScorePropertyHandler;
import net.realtoner.objectcalculator.exception.ScoreCalculationException;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class IntegerPropertyHandler implements ScorePropertyHandler{
    @Override
    public long getScore(Object value, CalculationContext calculationContext) throws ScoreCalculationException{

        if(value instanceof Integer){
            return (long)((int)value * calculationContext.getConstantMultiply());
        }else if(value instanceof Long){
            return (long)value * calculationContext.getConstantMultiply();
        }else if(value instanceof Short){
            return (long)((short)value * calculationContext.getConstantMultiply());
        }else{
            throw new ScoreCalculationException("Given value '"+value.getClass().getName()+"' is not integer.");
        }
    }
}
