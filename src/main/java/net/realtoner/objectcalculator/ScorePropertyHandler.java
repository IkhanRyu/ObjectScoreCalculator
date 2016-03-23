package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.exception.ScoreCalculationException;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public interface ScorePropertyHandler {

    /**
     *
     * @param value
     * @param calculationContext
     * @return
     * */
    long getScore(Object value, CalculationContext calculationContext) throws ScoreCalculationException;
}
