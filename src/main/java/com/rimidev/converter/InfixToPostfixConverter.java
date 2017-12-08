package com.rimidev.converter;

import com.rimidev.array.DynamicArray;
import com.rimidev.interfaces.MyQueue;
import com.rimidev.interfaces.MyStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    MyQueue<String> expressionArray = new DynamicArray<>();
    
    //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());
    
    
    //Default constructor.
    public InfixToPostfixConverter(){
        this.postfixQueue = null;
        this.operatorStack = null;
        this.expressionArray = null;
    }
    
    /**
     * Constructor that takes in the infix array.
     * @param expressionArray 
     */
    public InfixToPostfixConverter(MyQueue<String> expressionArray){
        this.expressionArray = expressionArray;
    }
    
    /**
     * This method is used to convert the infix expression to postfix expression.
     * @return The postfix expression.
     */
    public MyQueue<String> convert(){
        
        log.debug("Convert");
        
        boolean isInBracket = false;
        int bracketSize = 0;
        
        //Remove the strings one by one and validate if it's an operator
        //or it's a number to be processed.
        while (this.expressionArray.element() != null){
            log.debug("hit");
            //Place the expressArray at position i into a string to simplify.
            String stringAtIndex = this.expressionArray.remove();
            log.debug("StringAtIndex: " + stringAtIndex);
            //The result of isOperator will define it's precedence w/ BEDMAS.
            int isOperatorResult = isOperator(stringAtIndex);           
            //Check if the isOperatorResult defines this String to be an operator or numeric.
            if (isOperatorResult == 0){
                //The String is numeric 
                //Add it to the postfixQueue
                log.debug("hit number hit");
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
                    if ((isOperatorResult > isOperator(this.operatorStack.peek()))){
                        //Checks if opening bracket, to enter bracket mode.
                        if (isOperatorResult == 3){
                            isInBracket = true; //Become bracket mode.
                            bracketSize = 0;
                            log.debug("added the (");
                            operatorStack.push(stringAtIndex);
                        } else 
                        //Checks if closing bracket, to get out of bracket mode.
                        if (isOperatorResult == 4){
                            log.debug("closing bracket");
                            //Exiting bracket mode, dump all the operators into the postfixQueue.
                            while (isOperator(operatorStack.peek()) != 3) {
                                log.debug("operatorStack.peek()" + operatorStack.peek());
                                //Adds the operators into the postfixQueue
                                //Until it hits the opening bracket, then stops.
                                postfixQueue.add(operatorStack.pop());
                                log.debug("postfixqueue: " + postfixQueue.element());
                            } 
                            log.debug("opening bracket pop: " + operatorStack.peek());
                            operatorStack.pop(); //Remove the opening bracket at the end.
                            bracketSize = 0; //Reset the bracket size.
                            isInBracket = false; //Bracket mode, off.
                        } else {
                            operatorStack.push(stringAtIndex);
                        }// end of Bracket dumping.
                    //If the operatorResult is higher precedence than the popped String, add to stack.
                    //log.debug("ADDING THE DAM THING IN");
                    } else {
                        //Checks if we are in bracket mode.
                        if (isInBracket){
                            //First operator in the bracket mode automatically goes in.
                            if (bracketSize == 0){
                                log.debug("bracket push first element");
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
                        String poppedOperator = operatorStack.pop();
                        postfixQueue.add(poppedOperator); //1 & 2, pop and add.
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
        log.debug("hit end of interation");
        while (operatorStack.size() != 0){
            log.debug("adding operator" + operatorStack.size());
            postfixQueue.add(operatorStack.pop());
        }
        log.debug(""+this.postfixQueue.size());
        return this.postfixQueue;
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
        //Operator level
        int operatorLevel;
        //Checks if the string 's' is an operator.
        log.debug("what is s: " + s);
        switch (s) {
            case "+": operatorLevel = 1;
                      break;
            case "-": operatorLevel = 1;
                      break;
            case "*": operatorLevel = 2;
                      break;
            case "/": operatorLevel = 2;
                      break;
            case "(": operatorLevel = 3;
                      break;
            case ")": operatorLevel = 4;
                      break;
            default: operatorLevel = 0;
                     break;
        }
            
            log.debug("Operator is : " + operatorLevel);
            return operatorLevel;
        
    } // End of isOperator
    /**
     * Getter for the converted postfixQueue.
     * @return 
     */
    public MyQueue<String> getPostFixQueue(){
        log.debug("Grabbing queue: " + this.postfixQueue.size());
        log.debug("queue: " + this.postfixQueue.toString());
        return this.postfixQueue;
    }

//    public void getArray(MyQueue<String> s){
//        for (int i = 0; i < s.size(); i++){
//            log.debug("ARRAY: " + s.remove());
//        }
//    }


} // End of InfixToPostfixConverter
