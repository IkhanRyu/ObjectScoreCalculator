package net.realtoner.objectcalculator.handler;

import net.realtoner.objectcalculator.CalculationContext;
import net.realtoner.objectcalculator.ScorePropertyHandler;
import net.realtoner.objectcalculator.exception.ScoreCalculationException;

/**
 * @author RyuIkHan
 * @since 2016. 3. 23.
 */
public class StringPropertyHandler implements ScorePropertyHandler{

    @Override
    public long getScore(Object object, Object value, CalculationContext calculationContext)
            throws ScoreCalculationException {
        return ((String)value).length();
    }
}
