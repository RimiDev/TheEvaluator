package com.rimidev.converter;

import com.rimidev.array.DynamicArray;
import com.rimidev.interfaces.MyQueue;
import com.rimidev.interfaces.MyStack;

/**
 *
 * @author maximelacasse
 * @version 1.0
 */
public class InfixToPostfixConverter <T> {
    
    //Postfix Queue
    MyQueue<String> postfixQueue = new DynamicArray<>();
    
    //Operator Stack
    MyStack<String> operatorStack = new DynamicArray<>();
    
    //Expression Array
    DynamicArray expressionArray = new DynamicArray<>();
    
    
    
    public InfixToPostfixConverter(){
        
    }
    
    public InfixToPostfixConverter(DynamicArray postfixQueue,
            DynamicArray operatorStack, DynamicArray expressionArray){
        
        this.postfixQueue = postfixQueue;
        this.operatorStack = operatorStack;
        this.expressionArray = expressionArray;
        
    }
    
    /**
     * This method is used to convert the infix expression to postfix expression.
     */
    public void convert(){
        
        //Remove the strings one by one and validate if it's an operator
        //or it's a number to be processed.
        
        
        
    }
    
    /**
     * This method is used to check if the object is a number.
     * @return True = Numeric || False = Non-numeric.
     * @Param t is the object to look if it's numeric or not.
     */
    public boolean isNumeric(T t){
        if (t instanceof Integer){
            //t is an Integer.
            return true;
        }
        return false;
        
     
    }
    
//    /**
//     * This method is used to check if the object is an operator.
//     * @return True = 
//     */
//    public boolean isOperator(){
//        
//    }
    
}
