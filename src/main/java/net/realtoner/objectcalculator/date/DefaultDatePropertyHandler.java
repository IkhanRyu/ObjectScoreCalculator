package net.realtoner.objectcalculator.date;

import net.realtoner.objectcalculator.CalculationContext;
import net.realtoner.objectcalculator.exception.ScoreCalculationException;

import java.util.Date;
import java.util.List;

/**
 * @author RyuIkHan
 * @since 2016. 3. 23.
 */
public abstract class DefaultDatePropertyHandler extends AbstractDatePropertyHandler{

    protected abstract Date getFromDate(Object object);


    @Override
    public long getScore(Object object, Object value, CalculationContext calculationContext)
            throws ScoreCalculationException {

        if(value instanceof List){
            List<Date> dateList = (List<Date>)value;

            return calculateInterval(getFromDate(object), dateList, getDateIntervalType(calculationContext),
                    isUseAbsolute(calculationContext));
        }else{
            Date date = (Date)value;

            return calculateInterval(getFromDate(object), date, getDateIntervalType(calculationContext),
                    isUseAbsolute(calculationContext));
        }
    }
}
