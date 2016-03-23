package net.realtoner.objectcalculator.date;

import net.realtoner.objectcalculator.CalculationContext;
import net.realtoner.objectcalculator.CalculationContextBuilder;
import net.realtoner.objectcalculator.date.annotation.DateProperty;

import java.lang.annotation.Annotation;

/**
 * @author RyuIkHan
 * @since 2016. 3. 23.
 */
public class DateCalculationContextBuilder implements CalculationContextBuilder{

    public static final String CONTEXT_PARAM_DATE_INTERVAL = "date_param_date_interval_type";
    public static final String CONTEXT_PARAM_DATE_USE_ABSOLUTE = "date_param_date_use_absolute";

    @Override
    public void build(Annotation annotation, CalculationContext calculationContext) {

        DateProperty dateProperty = (DateProperty)annotation;

        calculationContext.putParam(CONTEXT_PARAM_DATE_INTERVAL, dateProperty.dateIntervalType());
        calculationContext.putParam(CONTEXT_PARAM_DATE_USE_ABSOLUTE, dateProperty.useAbsolute());
    }
}
