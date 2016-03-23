package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.exception.ScoreCalculationException;

import java.util.HashMap;
import java.util.Map;

/**
 * ObjectScoreCalculator calculates the score of given object.
 *
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class ObjectScoreCalculator {

    private final Map<Class<?>, ScoreObjectHandler> scoreObjectHandlerMap = new HashMap<>();

    protected ObjectScoreCalculator() {

    }

    protected void putScoreObjectHandlerMap(Class<?> clazz, ScoreObjectHandler rankObjectHandler) {
        scoreObjectHandlerMap.put(clazz, rankObjectHandler);
    }

    /**
     *
     * @param clazz
     * @param object
     * @return
     * */
    public <T> long calculateScore(Class<T> clazz, T object) throws ClassNotFoundException, ScoreCalculationException {

        ScoreObjectHandler rankObjectHandler = scoreObjectHandlerMap.get(clazz);

        if (rankObjectHandler == null) {
            throw new ClassNotFoundException(
                    "Given class \'" + clazz.getName() + "\' is not added to this ObjectScoreCalculator.");
        }

        return rankObjectHandler.getScore(object);
    }
}
