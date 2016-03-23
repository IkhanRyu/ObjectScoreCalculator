import net.realtoner.objectcalculator.CalculationContext;
import net.realtoner.objectcalculator.ObjectScoreCalculator;
import net.realtoner.objectcalculator.ObjectScoreCalculatorBuilder;
import net.realtoner.objectcalculator.ScorePropertyHandler;
import net.realtoner.objectcalculator.annotation.ScoreProperty;
import net.realtoner.objectcalculator.exception.InvalidScoreObjectException;
import net.realtoner.objectcalculator.exception.ScoreCalculationException;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class ObjectScoreCalculatorTest {


    public static class InvalidScoreObject {

        @ScoreProperty(constantMultiply = 3)
        private int id;
    }

    private static final int INTEGER_TEST_CONSTANT_MULTIPLY = 3;
    private static final int DOUBLE_TEST_CONSTANT_MULTIPLY = 5;

    public static class ValidScoreObject {

        @ScoreProperty(constantMultiply = INTEGER_TEST_CONSTANT_MULTIPLY)
        private int id;

        @ScoreProperty
        private String name = null;

        @ScoreProperty(constantMultiply = DOUBLE_TEST_CONSTANT_MULTIPLY)
        private double score;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }

    @Test(expected = InvalidScoreObjectException.class)
    public void testBuildInvalidObjectScoreCalculator() throws Exception {

        ObjectScoreCalculatorBuilder objectScoreCalculatorFactory = new ObjectScoreCalculatorBuilder();

        ObjectScoreCalculator objectScoreCalculator =
                objectScoreCalculatorFactory.addObjectScoreObjectClass(InvalidScoreObject.class)
                        .build();
    }

    @Test
    public void testBuildValidObjectScoreCalculator() throws Exception {

        ObjectScoreCalculatorBuilder objectScoreCalculatorFactory = new ObjectScoreCalculatorBuilder();

        ObjectScoreCalculator objectScoreCalculator =
                objectScoreCalculatorFactory.addObjectScoreObjectClass(ValidScoreObject.class)
                        .build();
    }

    private static final int CALCULATION_TEST_COUNT = 20;

    @Test
    public void testCalculation() throws Exception{

        ObjectScoreCalculatorBuilder objectScoreCalculatorFactory = new ObjectScoreCalculatorBuilder();

        ObjectScoreCalculator objectScoreCalculator =
                objectScoreCalculatorFactory.addObjectScoreObjectClass(ValidScoreObject.class)
                        .addStringScorePropertyHandler(new ScorePropertyHandler() {
                            @Override
                            public long getScore(Object value, CalculationContext calculationContext)
                                    throws ScoreCalculationException {
                                return ((String)value).length();
                            }
                        })
                        .build();

        Random random = new Random();

        int id;
        double score;
        String name;

        ValidScoreObject validScoreObject = new ValidScoreObject();

        for(int i = 0; i < CALCULATION_TEST_COUNT; i++){

            id = random.nextInt(100);
            score = random.nextDouble() * 100;
            name = "ObjectScoreTest";

            validScoreObject.setId(id);
            validScoreObject.setScore(score);
            validScoreObject.setName(name);

            long expectedScore = id * INTEGER_TEST_CONSTANT_MULTIPLY + (long)(score * DOUBLE_TEST_CONSTANT_MULTIPLY) +
                    name.length();
            long result = objectScoreCalculator.score(ValidScoreObject.class, validScoreObject);

            assertEquals(result, expectedScore);
        }
    }
}
