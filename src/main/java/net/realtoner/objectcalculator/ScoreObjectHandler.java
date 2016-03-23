package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.exception.ScoreCalculationException;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public interface ScoreObjectHandler {

    /**
     *
     * @param object
     * @return
     * @throws ScorePropertyHandler
     * */
    long getScore(Object object) throws ScoreCalculationException;
}
