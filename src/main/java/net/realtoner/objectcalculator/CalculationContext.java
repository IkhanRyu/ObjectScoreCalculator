package net.realtoner.objectcalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class CalculationContext {

    private int constantMultiply = 1;

    private final Map<String, Object> params = new HashMap<>();

    public CalculationContext(){

    }

    public CalculationContext(int constantMultiply){
        this.constantMultiply = constantMultiply;
    }

    public int getConstantMultiply() {
        return constantMultiply;
    }

    public void setConstantMultiply(int constantMultiply) {
        this.constantMultiply = constantMultiply;
    }

    public void putParam(String key, Object value){
        params.put(key, value);
    }

    public Object getParam(String key){
        return params.get(key);
    }
}
