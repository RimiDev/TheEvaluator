package com.rimidev.interfaces;

/**
 *
 * @author maximelacasse
 * @version 1.0
 */
public interface MyQueue<T> {
    
    void add(T t); //Add to the end of the Queue.
    
    T remove();//Removes from the front.
  
    T element();//Peek at the front.
    
    int size();//Returns the size of the Queue.
 
}
