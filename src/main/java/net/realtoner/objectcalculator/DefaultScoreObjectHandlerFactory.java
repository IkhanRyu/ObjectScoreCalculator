package net.realtoner.objectcalculator;

import net.realtoner.objectcalculator.annotation.ScoreProperty;
import net.realtoner.objectcalculator.exception.InvalidScoreObjectException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class DefaultScoreObjectHandlerFactory extends AbstractScoreObjectHandlerFactory {

    /**
     * handler name > annotation > type
     * */
    private List<MethodPropertyHandlerUnit> createMethodPropertyHandlerUnitList(Class<?> clazz,
                                                                                Map<String, ScorePropertyHandler> namePropertyHandlerMap,
                                                                                Map<String, ScorePropertyHandler> classPropertyHandlerMap)
    throws InvalidScoreObjectException{

        List<MethodPropertyHandlerUnit> methodPropertyHandlerUnitList = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();

        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                ScoreProperty scoreProperty = field.getAnnotation(ScoreProperty.class);

                if (scoreProperty != null) {

                    String handlerName = scoreProperty.value();

                    ScorePropertyHandler scorePropertyHandler;

                    if (handlerName == null || handlerName.trim().equals("")) {
                        scorePropertyHandler = classPropertyHandlerMap.get(field.getType().getName());
                    } else {
                        scorePropertyHandler = namePropertyHandlerMap.get(handlerName);
                    }

                    //there is no corresponding score property handler
                    if(scorePropertyHandler != null){
                        try {

                            String fieldName = field.getName();
                            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                            Method getterMethod = clazz.getMethod(methodName);

                            if(!getterMethod.getReturnType().equals(field.getType()))
                                throw new InvalidScoreObjectException("The type of given field is " +
                                        field.getType().getName() + " , " + "but getter method's return type is " +
                                        getterMethod.getReturnType().getName());

                            methodPropertyHandlerUnitList.add(new MethodPropertyHandlerUnit(
                                    getterMethod, scorePropertyHandler,
                                    createCalculationContext(field.getAnnotations())));

                        }catch(NoSuchMethodException e){
                            throw new InvalidScoreObjectException(e);
                        }
                    }
                }
            }
        }

        return methodPropertyHandlerUnitList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreObjectHandler make(Class<?> clazz, Map<String, ScorePropertyHandler> namePropertyHandler, Map<String,
            ScorePropertyHandler> classPropertyHandlerMap) throws InvalidScoreObjectException{

        List<MethodPropertyHandlerUnit> methodPropertyHandlerUnitList;

        methodPropertyHandlerUnitList = createMethodPropertyHandlerUnitList(clazz, namePropertyHandler,
                classPropertyHandlerMap);

        return new DefaultScoreObjectHandler(methodPropertyHandlerUnitList);
    }
}
