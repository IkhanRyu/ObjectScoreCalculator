package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.exception.ScoreCalculationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class DefaultScoreObjectHandler implements ScoreObjectHandler{

    private List<MethodPropertyHandlerUnit> unitList = new ArrayList<>();

    public DefaultScoreObjectHandler(){

    }

    public DefaultScoreObjectHandler(List<MethodPropertyHandlerUnit> unitList){
        this.unitList = unitList;
    }

    public List<MethodPropertyHandlerUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<MethodPropertyHandlerUnit> unitList) {
        this.unitList = unitList;
    }

    @Override
    public long getScore(Object object) throws ScoreCalculationException {

        long score = 0;

        for(MethodPropertyHandlerUnit unit : unitList){
            score += unit.getScore(object);
        }

        return score;
    }
}
