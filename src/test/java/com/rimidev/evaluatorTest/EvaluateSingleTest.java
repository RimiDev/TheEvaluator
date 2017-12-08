package com.rimidev.evaluatorTest;

import com.rimidev.array.DynamicArray;
import com.rimidev.converter.InfixToPostfixConverter;
import com.rimidev.interfaces.MyQueue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maximelacasse
 */

public class EvaluateSingleTest {
    
    
    //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());
    
    
    @Test
    public void testEvaluate(){
        
        MyQueue<String> mq = new DynamicArray<>();
        mq.add("4");
        mq.add("*");
        mq.add("4");
        
        MyQueue<String> m = new DynamicArray<>();
        
        InfixToPostfixConverter postFix = new InfixToPostfixConverter(mq);
        m = postFix.convert();
        
        m.add("0");

        
        
//        for (int i = 0; i < m.size(); i++){
//            log.debug("ARRAY: ");
//        }

//        for (int i = 0; i < mq.size(); i++){
//        log.debug("mq" + mq.remove());
//        }
//        
        
        
        
    }
    

    
}
