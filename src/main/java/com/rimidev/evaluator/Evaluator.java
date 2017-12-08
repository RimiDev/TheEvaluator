package com.rimidev.evaluator;

import com.rimidev.array.DynamicArray;
import com.rimidev.interfaces.MyQueue;
import com.rimidev.interfaces.MyStack;
import static java.lang.Double.parseDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is used to evaluate the postfix expression.
 * @author maximelacasse
 * @version 1.0
 */
public class Evaluator {
    

    //Postfix Queue
    MyQueue<String> postfixQueue = new DynamicArray<>();
    
    //Operand Stack
    MyStack<String> operandStack = new DynamicArray<>();
    
    //Expression Array
    MyQueue<String> expressionArray = new DynamicArray<>();
    
    //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());
    
    //Default constructor
    public Evaluator(){
        this.postfixQueue = null;
        this.operandStack = null;
        this.expressionArray = null;
    }
    
    /* Constructor that takes in the postfixQueue that is ready to be evaluated.
     * @param postfixQueue
     */
    public Evaluator(MyQueue postfixQueue) {
        this.postfixQueue = postfixQueue;
        log.debug("postfixInEvaluator: " + this.postfixQueue.size());
    }
    
    public double evaluate(){
        
        /**
         * Iterate through the postfixQueue
         * 1. Placing all the operands into the operand stack.
         * 2. Once an operator has been reached, Pop the last two operands from
         * the stack into the expressionArray with the operator in the middle of them.
         * NOTE: Making sure that the first number popped is in the last position
         * of the expressionArray (for division problems)
         * 3. Convert all the strings into doubles.
         * 4. Do the calculation.
         * 5. Place the result back into the operandStack.
         * Rinse, repeat until the postfixQueue is empty.
        **/
        while (postfixQueue.element() != null){    
            log.debug("removing in evaluate");
            String stringAtIndex = postfixQueue.remove();
            log.debug("in loop: " + stringAtIndex);
            log.debug("size: " + operandStack.size());

            //Checks if the popped String is an operand or operator.
            if (isOperator(stringAtIndex) == 0) {
                //popped String is operand.
                //Add it to the operand stack.
                operandStack.push(stringAtIndex);
            } else {
                //popped string is operator.
                //Check if there's at least two Strings in the operand stack
                //to perform the evaluation.
                if (operandStack.size() < 2){
                    log.debug("what is up size operand");
                    throw new IndexOutOfBoundsException("There's not enough operands in the"
                            + "operand stack to use the operator with!");
                }
                //Perform the evaluation.
                log.debug("what is it second?: " + operandStack.peek());
                Double secondValue = Double.valueOf(operandStack.pop());
                log.debug("secondValue: " + secondValue);
                log.debug("what is it first?" + operandStack.peek());
                Double firstValue = Double.valueOf(operandStack.pop());
                log.debug("firstValue: " + firstValue);
                Double result = calculate(firstValue,secondValue,isOperator(stringAtIndex));
                log.debug("result: " + result);
                //Now that we got the result, we add it to the operandStack for
                //further results OR it's the final result.
                operandStack.push(result.toString());
            }
            
        } // end of loop
        return parseDouble(operandStack.pop());
        
    }
    
    /**
     * Used to calculate the result of the two operands popped of the stack
     * using a switch statement, which calculates it depending on the operator
     * that was popped from the queue.
     * @param firstValue
     * @param secondValue
     * @param operator
     * @return
     */
    private double calculate(double firstValue, double secondValue, int operator) {
        switch (operator){
            case 1: return (firstValue + secondValue); //Addition
            case 2: return (firstValue - secondValue); //Subtration
            case 3: return (firstValue * secondValue); //Multiplication
            case 4: return (firstValue / secondValue); //Division
            default: return -1; //Something went wrong if -1.
        } //end of switch
    } //end of calculate
    
    
    /**
     * This method will check if the string is an operator or numeric.
     * If it is an operator, it will check which one it is.
     * @param s The string value in the expressionArray.
     * @return Returns an integer depending on the precedence of BEDMAS.
     * 0 -> Non-operator -> numeric string.
     * 1 -> + (addition)
     * 2 -> - (subtration)
     * 3 -> * (multiplication)
     * 4 -> / (division)
     */
        public int isOperator(String s){
            
        //Operator level
        int operatorLevel;
        log.debug("what is s: " + s);
        //Checks if the string 's' is an operator.
        switch (s) {
            case "+": operatorLevel = 1;
                      break;
            case "-": operatorLevel = 2;
                      break;
            case "*": operatorLevel = 3;
                      break;
            case "/": operatorLevel = 4;
                      break;
            default: operatorLevel = 0;
                     break;
            
        }
        
        log.debug("what is operator: " + operatorLevel);
        return operatorLevel;
     
    } // End of isOperator
          
    
}
