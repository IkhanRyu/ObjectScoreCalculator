import net.realtoner.objectcalculator.ObjectScoreCalculator;
import net.realtoner.objectcalculator.ObjectScoreCalculatorBuilder;
import net.realtoner.objectcalculator.annotation.ScoreProperty;
import net.realtoner.objectcalculator.date.DefaultDatePropertyHandler;
import net.realtoner.objectcalculator.date.annotation.DateProperty;
import net.realtoner.objectcalculator.exception.InvalidScoreObjectException;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /**
     *
     * */
    @Test(expected = InvalidScoreObjectException.class)
    public void testBuildInvalidObjectScoreCalculator() throws Exception {

        ObjectScoreCalculatorBuilder objectScoreCalculatorFactory = new ObjectScoreCalculatorBuilder();

        ObjectScoreCalculator objectScoreCalculator =
                objectScoreCalculatorFactory.addObjectScoreObjectClass(InvalidScoreObject.class)
                        .build();
    }

    /**
     *
     * */
    @Test
    public void testBuildValidObjectScoreCalculator() throws Exception {

        ObjectScoreCalculatorBuilder objectScoreCalculatorFactory = new ObjectScoreCalculatorBuilder();

        ObjectScoreCalculator objectScoreCalculator =
                objectScoreCalculatorFactory.addObjectScoreObjectClass(ValidScoreObject.class)
                        .build();
    }

    private static final int CALCULATION_TEST_COUNT = 20;

    /**
     *
     * */
    @Test
    public void testCalculation() throws Exception{

        ObjectScoreCalculatorBuilder objectScoreCalculatorFactory = new ObjectScoreCalculatorBuilder();

        ObjectScoreCalculator objectScoreCalculator =
                objectScoreCalculatorFactory.addObjectScoreObjectClass(ValidScoreObject.class)
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
            long result = objectScoreCalculator.calculateScore(ValidScoreObject.class, validScoreObject);

            assertEquals(result, expectedScore);
        }
    }

    public static class DateScoreObject{

        @ScoreProperty("DateProperty")
        @DateProperty
        private List<Date> dateList = null;

        public List<Date> getDateList() {
            return dateList;
        }

        public void setDateList(List<Date> dateList) {
            this.dateList = dateList;
        }
    }

    @Test
    public void testDateCalculation() throws Exception{

        ObjectScoreCalculatorBuilder objectScoreCalculatorFactory = new ObjectScoreCalculatorBuilder();

        ObjectScoreCalculator objectScoreCalculator =
                objectScoreCalculatorFactory.addObjectScoreObjectClass(DateScoreObject.class)
                        .addScorePropertyHandler("DateProperty", new DefaultDatePropertyHandler() {

                            @Override
                            protected Date getFromDate(Object object) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                                try {
                                    return sdf.parse("20160310");
                                } catch (ParseException ignored) {
                                    return null;
                                }
                            }
                        })
                        .setUseDateProperty(true)
                        .build();

        List<Date> dateList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        dateList.add(sdf.parse("20160322"));
        dateList.add(sdf.parse("20160321"));
        dateList.add(sdf.parse("20160320"));
        dateList.add(sdf.parse("20160319"));
        dateList.add(sdf.parse("20160318"));
        dateList.add(sdf.parse("20160317"));
        dateList.add(sdf.parse("20160316"));

        DateScoreObject dateScoreObject = new DateScoreObject();

        dateScoreObject.setDateList(dateList);

        long score = objectScoreCalculator.calculateScore(DateScoreObject.class, dateScoreObject);

        assertEquals(score, 63);
    }
}
