package com.rimidev.interfaces;

/**
 * LIFO structure
 * @author maximelacasse
 * @version 1.0
 */
public interface MyStack<T> {
    
    void push(T t);//Add at the end of the Stack.
    
    T pop(); //Removes the last member of the Stack. (LIFO)
    
    T peek(); //Peeks at the last member of the Stack.
    
    int size(); //Returns the size of the Stack.
    
    
}
