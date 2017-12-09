package com.rimidev.evaluatorTest;

import com.rimidev.array.DynamicArray;
import com.rimidev.converter.InfixToPostfixConverter;
import com.rimidev.evaluator.Evaluator;
import com.rimidev.interfaces.MyQueue;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maximelacasse
 * @version 1.0
 */
@RunWith(Parameterized.class)
public class EvaluateTester {
    
    
        //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());

// index is row, {0} {1} are the values/objects
    @Parameters(name = "{index} plan[{0}]={1}]")
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
            { convertToString(new String[] {"4","*","4"}), 16},
            { convertToString(new String[] {"4","*","4","*","2"}), 32},
            { convertToString(new String[] {"2","*","4","/","1","+","2"}),10},
            { convertToString(new String[] {"2","*","(","2","+","2",")"}),8},
            { convertToString(new String[] {"2","*","(","2","+","(","2","-","4",")",")"}),0},
            { convertToString(new String[] {"2","*","(","2","+","(","2","-","4",")","+","2",")"}),4},
            { convertToString(new String[] {"2","*","2","*","2","*","2","*","2","*","2"}),64},
            { convertToString(new String[] {"0","/","2"}),0},
            { convertToString(new String[] {"2","+","2","*","3","-","1","/","1"}),7},
            { convertToString(new String[] {"2","*","100","/","66","*","595"}),1803.0},
            { convertToString(new String[] {"20000","/","20000","+","9999","-","2500192"}),-2490192.0},
            { convertToString(new String[] {"123","+","123","-","560"}),-314},
            { convertToString(new String[] {"69","-","755","*","566","/","7"}),-60978.1},
            { convertToString(new String[] {"75","+","(","5","+","6",")","*","5",")"}),130},
            { convertToString(new String[] {"8","+","(","8","+","(","8","+","8","+","8","*","2",")",")"}),48},
            { convertToString(new String[] {"8","+","(","8","+","2","+","2","*","2","-","2","/","0.5",")"}),18},
            { convertToString(new String[] {"2","/","2","-","0","+","0","-","-2","+","-66"}),-63},
            { convertToString(new String[] {"55","+","(","55","-","51",")","+","66","-","22"}),103},
            { convertToString(new String[] {"999999","/","999999"}),1},
            { convertToString(new String[] {"22","+","(","66","-","66",")"}),22},
            { convertToString(new String[] {"23","+","23","-","46"}),0},
            { convertToString(new String[] {"66","+","66","+","67","*","32","/","200000"}),132.01},
            { convertToString(new String[] {"2","*","2","+","(","4","*","4",")"}),20},
            { convertToString(new String[] {"66"}),66},          
            { convertToString(new String[] {"99999999"}),99999999},

            });
    }
    
    /**
     * This method is used to convert a string array into a MyQueue object for testing.
     * @param equation
     * @return 
     */
    public static MyQueue<String> convertToString(String[] equation){
        
        MyQueue<String> mq = new DynamicArray<>();

        for (int i = 0; i < equation.length; i++){
            mq.add(equation[i]);      
        }      
        return mq;
        
        
    }
    
    //Variables
    MyQueue<String> infix;
    double expectedTotal;

    public EvaluateTester(MyQueue<String> infix, double expectedTotal) {
        this.infix = infix;
        this.expectedTotal = expectedTotal;
    }

    @Test
    public void testEvaluate() {      
        InfixToPostfixConverter postFix = new InfixToPostfixConverter(infix);
        postFix.convert();
        Evaluator instance = new Evaluator(postFix.getPostFixQueue());
        double result = instance.evaluate();
        log.debug("result is: " + result);
        assertEquals(expectedTotal, result, 1);
    } //Third place in assertEquals will precise the decimal poisitions.

}
