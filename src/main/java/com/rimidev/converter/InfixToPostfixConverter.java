package com.rimidev.converter;

import com.rimidev.array.DynamicArray;
import com.rimidev.interfaces.MyQueue;
import com.rimidev.interfaces.MyStack;

/**
 * This class is used to convert an infix expression into a postfix expression.
 * The result of this converter will be able to be evaluated by the 'Evaluator' class.
 * @author maximelacasse
 * @version 1.0
 */
public class InfixToPostfixConverter {
    
    //Postfix Queue
    MyQueue<String> postfixQueue = new DynamicArray<>();
    
    //Operator Stack
    MyStack<String> operatorStack = new DynamicArray<>();
    
    //Expression Array
    DynamicArray<String> expressionArray = new DynamicArray<>();
    
    
    //Default constructor.
    public InfixToPostfixConverter(){
        this.postfixQueue = null;
        this.operatorStack = null;
        this.expressionArray = null;
    }
    
    /**
     * Constructor with 1 param, assigning each to the local variables.
     * @param expressionArray 
     */
    public InfixToPostfixConverter(DynamicArray expressionArray){       
        this.expressionArray = expressionArray;       
    }
    
    /**
     * This method is used to convert the infix expression to postfix expression.
     */
    public void convert(){
        
        boolean isInBracket = false;
        int bracketSize = 0;
        
        //Remove the strings one by one and validate if it's an operator
        //or it's a number to be processed.
        for (int i = 0; i < this.expressionArray.size(); i++){
            //The result of isOperator will define it's precedence w/ BEDMAS.
            int isOperatorResult = isOperator((String)this.expressionArray.get(i));
            //Place the expressArray at position i into a string to simplify.
            String stringAtIndex = (String) this.expressionArray.get(i);
            //Check if the isOperatorResult defines this String to be an operator or numeric.
            if (isOperatorResult == 0){
                //The String is numeric 
                //Add it to the postfixQueue
                postfixQueue.add(stringAtIndex);
            } else {
                //The String is non-numeric               
                //Check if the operatorStacks' size is greater than 1.
                if (this.operatorStack.size() > 0){
                    /**
                     * OperatorStacks' size is greater than 1.
                     * Pop to check if the top of the stack is greater than 
                     * the one you want to push (Using BEDMAS precedence)
                    **/                    
                    if ((isOperatorResult > isOperator(this.operatorStack.pop()))){
                        //Checks if opening bracket, to enter bracket mode.
                        if (isOperatorResult == 3){
                            isInBracket = true; //Become bracket mode.
                        }
                        //Checks if closing bracket, to get out of bracket mode.
                        if (isOperatorResult == 4){
                            //Exiting bracket mode, dump all the operators into the postfixQueue.
                            while (isOperator(operatorStack.pop()) != 3) {
                                //Adds the operators into the postfixQueue
                                //Until it hits the opening bracket, then stops.
                                postfixQueue.add(operatorStack.pop());
                            }
                            operatorStack.pop(); //Remove the opening bracket at the end.
                            bracketSize = 0; //Reset the bracket size.
                            isInBracket = false; //Bracket mode, off.
                        } // end of Bracket dumping.
                    //If the operatorResult is higher precedence than the popped String, add to stack.
                    operatorStack.push(stringAtIndex);
                    } else {
                        //Checks if we are in bracket mode.
                        if (isInBracket){
                            //First operator in the bracket mode automatically goes in.
                            if (bracketSize == 0){
                                operatorStack.push(stringAtIndex);
                                bracketSize++;
                                continue; // Breaks the iteration, since we already pushed to the operatorStack.
                                }
                        } // end of isInBracket
                    /**
                     * If the operatorResult is lower or equal precedence than the popped String,
                     * 1. pop the operatorStack
                     * 2. place popped string into postfixQueue
                     * 3. place operator into the stack.
                    **/
                        postfixQueue.add(operatorStack.pop()); //1 & 2, pop and add.
                        operatorStack.push(stringAtIndex); //3, place operator into stack.
                    }
                    
                } else {
                    //OperatorStacks' size is less than 1.
                    //Simply add the operator.
                    this.operatorStack.push(stringAtIndex);
                }
            
            }
            
        } // End of the iteration of the expressionArray.
        /**
         * Once all the numbers and the operators of the expressionArray has been iterated through,
         * 1. Place all the operators from top of bottom into the postfixQueue,
         * until operatorStack has size == 0 (no more operators).
         */
        while (operatorStack.size() != 0){
            postfixQueue.add(operatorStack.pop());
        }
      //postfixQueue is ready to be evaluated.
      
    } // End of convert.
    
    /**
     * This method will check if the string is an operator or numeric.
     * Depending on the result, you will send it to the operatorStack or the
     * postfixQueue.
     * @param s The string value in the expressionArray.
     * @return Returns an integer depending on the precedence of BEDMAS.
     * 0 -> Non-operator -> numeric string.
     * 1 -> Low
     * 2 -> Medium
     * 3 -> High
     */
    public int isOperator(String s){
        //Checks if the string 's' is an operator.
        switch (s) {
            case "+": return 1;
            case "-": return 1;
            case "*": return 2;
            case "/": return 2;
            case "(": return 3;
            case ")": return 4;
            default: return 0;
            
        }
        
     
    } // End of isOperator

} // End of InfixToPostfixConverter
