/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.evaluatorTest;

import com.rimidev.array.DynamicArray;
import com.rimidev.converter.InfixToPostfixConverter;
import com.rimidev.evaluator.Evaluator;
import com.rimidev.interfaces.MyQueue;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maximelacasse
 */
public class EvaluateTesterFAIL {
    
            //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());

// index is row, {0} {1} are the values/objects
    @Parameterized.Parameters(name = "{index} plan[{0}]={1}]")
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
            //FAILING
            { convertToString(new String[] {"8","+","(","8","+","(","8","(","8","(","8","*","2",")",")",")",")"}),584},
            { convertToString(new String[] {"8","+","(","8","+","(","8","+","8","(","8","*","2",")",")",")"}),32},
            { convertToString(new String[] {"2","/","0"}),0},
            { convertToString(new String[] {"*","3","+"}),3},
            { convertToString(new String[] {"(","(","(","(",")","3",")",")","+","2",")"}),6}
    
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

    public EvaluateTesterFAIL(MyQueue<String> infix, double expectedTotal) {
        this.infix = infix;
        this.expectedTotal = expectedTotal;
    }

    @Test (timeout = 1000, expected = Exception.class)
    public void testEvaluate() {      
        InfixToPostfixConverter postFix = new InfixToPostfixConverter(infix);
        postFix.convert();
        Evaluator instance = new Evaluator(postFix.getPostFixQueue());
        double result = instance.evaluate();
        fail("Exception has been thrown!");
    } //Third place in assertEquals will precise the decimal poisitions.
    
    
    
}
