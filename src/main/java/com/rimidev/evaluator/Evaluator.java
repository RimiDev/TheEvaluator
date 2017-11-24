package com.rimidev.evaluator;

import com.rimidev.array.DynamicArray;
import com.rimidev.converter.InfixToPostfixConverter;
import com.rimidev.interfaces.MyQueue;
import com.rimidev.interfaces.MyStack;

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
    DynamicArray<String> expressionArray = new DynamicArray<>();
    
    //Default constructor
    public Evaluator(){
        this.postfixQueue = null;
        this.operandStack = null;
        this.expressionArray = null;
    }
    
    /* Constructor with 3 paramas, assigning each to the local variables.
     * @param postfixQueue
     * @param operatorStack
     * @param expressionArray 
     */
    public Evaluator(DynamicArray postfixQueue,
                     DynamicArray operatorStack, DynamicArray expressionArray) {
        this.postfixQueue = postfixQueue;
        this.operandStack = operatorStack;
        this.expressionArray = expressionArray;

    }
    
    public void evaluate(){
        
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
        for (int i = 0; i < postfixQueue.size(); i++){
            String stringAtIndex = postfixQueue.remove();
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
                    throw new IndexOutOfBoundsException("There's not enough operands in the"
                            + "operand stack to use the operator with!");
                }
                //Perform the evaluation.
                Double secondValue = Double.valueOf(operandStack.pop());
                Double firstValue = Double.valueOf(operandStack.pop());
                Double result = calculate(firstValue,secondValue,isOperator(stringAtIndex));
                //Now that we got the result, we add it to the operandStack for
                //further results OR it's the final result.
                operandStack.push(result.toString());
            }
        }
        
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
    public double calculate(double firstValue, double secondValue, int operator) {
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
        //Checks if the string 's' is an operator.
        switch (s) {
            case "+": return 1;
            case "-": return 2;
            case "*": return 3;
            case "/": return 4;
            default: return 0;
            
        }
        
     
    } // End of isOperator
    
    
    
    
    
}
