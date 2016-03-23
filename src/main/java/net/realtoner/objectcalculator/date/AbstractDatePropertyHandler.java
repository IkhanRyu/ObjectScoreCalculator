package net.realtoner.objectcalculator.date;

import net.realtoner.objectcalculator.ScorePropertyHandler;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RyuIkHan
 * @since 2016. 3. 23.
 */
public abstract class AbstractDatePropertyHandler implements ScorePropertyHandler{

    /**
     *
     * @param dateIntervalType
     * @return
     * */
    private TimeUnit getTimeUnitFromIntervalType(DateIntervalType dateIntervalType){

        switch (dateIntervalType) {

            case SECOND:
                return TimeUnit.SECONDS;

            case MINUTE:
                return TimeUnit.MINUTES;

            case HOUR:
                return TimeUnit.HOURS;

            default:
            case DATE:
                return TimeUnit.DAYS;
        }
    }

    /**
     *
     * @param from
     * @param to
     * @param dateIntervalType
     * @return
     * */
    protected long calculateInterval(Date from, Date to, DateIntervalType dateIntervalType, boolean useAbsolute){

        TimeUnit timeUnit = getTimeUnitFromIntervalType(dateIntervalType);

        long diff = to.getTime() - from.getTime();

        return useAbsolute ? Math.abs(timeUnit.convert(diff, TimeUnit.MILLISECONDS)) :
                timeUnit.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @param from
     * @param to
     * @param dateIntervalType
     * @return
     * */
    protected long calculateInterval(Date from, List<Date> to, DateIntervalType dateIntervalType, boolean useAbsolute){

        TimeUnit timeUnit = getTimeUnitFromIntervalType(dateIntervalType);

        long interval = 0;

        for(Date d : to){

            long diff = d.getTime() - from.getTime();

            interval += useAbsolute ? Math.abs(timeUnit.convert(diff, TimeUnit.MILLISECONDS)) :
                    timeUnit.convert(diff, TimeUnit.MILLISECONDS);
        }

        return interval;
    }
}
