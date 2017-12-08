package com.rimidev.array;

import com.rimidev.interfaces.MyQueue;
import com.rimidev.interfaces.MyStack;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a custom made Array that mimics the commonly used ArrayList in the
 * Collections.
 * Consists of constructors, add, remove, set, get, size, toString.
 * With private methods to aide these methods, copy and shift.
 * @author maximelacasse
 * @version 1.0
 */
public class DynamicArray <T> implements MyStack<T>, MyQueue<T>{
    
    //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());
    
    private Object[] container; //The actual array to manipulate (The whole size of the array)
    private int size = 0; //The size of the array (Where they are some objects inside)
    
    
    /**
     * Default constructor which creates the container array with 10 slots.
     */
    public DynamicArray(){
       this(10);
    }
    
    /**
     * Constructor that creates the container array with the 'capacity' slots.
     * @param capacity 
     */
    public DynamicArray(int capacity){
        this.container = new Object[capacity];
    }
    
    /**
     * Adds the obj to the end of the array.
     */
    public void add(T obj){
        if (size == this.container.length){
            //If we add this to the end, then the container will be maximized, need to double
            //the size before proceeding.
            copy(this.container.length*2); //Copies the array to a new array with doubled size.
        }
        this.container[size] = obj;
        size++;
                getArray(this.container);
    }
    
    /**
     * Adds the obj to the index position.
     * If the index position is not occupied, it will directly add into the array.
     * If the index position is occupied, it will shift the array to the right by 1, make room
     * for the obj, place it.
     * Increase the size in either cases.
     * @param index 
     * @param t 
     */
    public void add(int index, T t) {

        if (index > size){
            //Cannot add a value that is at an index greater than the size.
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        //If the array at position 0 is null, user has to use the add() method.
        if (this.container[0] == null){
           throw new NullPointerException("Please use the add() method for first inserts");
        }
        //If the array at position:index is empty, add the object to the index.
        if (this.container[index] == null){
           this.container[index] = t; //Adds the objects to the desired index position.
           size++; //Increase the size.
        } else {
        /**
         If array at position:index is occupied,
         1. Check if all the proceeding indexes after the index will maximize the array.
         2. shift all the proceeding objects +1 position.
         3. add the object to the desired index position.
         4. Increase the size.
        **/       
        if (this.container.length - size+1 == 0){
            //1. The array will be maximized if we add this object to the desired index.
            //Create a new array with double the size.
           copy(this.container.length*2); //Copies the array to a new array with doubled size.
        }
        //2. Shift all the proceeding objects from index +1 positions.
        shift(index,1,0);
        
        //3. Add the object to the desired index position.
        this.container[index] = t; 
        size++; //Increase the size.
        }       
    } // End of add method.
        
    /**
     * Returns the object from position:index
     * @param index
     * @return 
     */
    public Object get(int index) {
        if (index > size){
            //Cannot add a value that is at an index greater than the size.
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
     return this.container[index];
    }
    /**
     * Returns the index position of the specified object.
     * @param anObject
     * @return 
     */
    public int indexOf(T t) {
        //Iterate through the whole array to check if any of the objects are equal to the anObject.
        for (int i = 0; i <= this.container.length; i++){
            if (this.container[i] == t){
                //The object at 'i' is equavalent to anObject, returning index.
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Remove the object at the index position.
     * Decrease the size variable.
     * @param index 
     */
    public T remove (int index) {
        size--; //Decrease the size.
        return shift(index,-1,0); //Recreate the container array to not have the object in the index position.
    }

    /**
     * Returns how many positions are used up by objects in the array.
     * This does not return the total containing size of the array.
     * @return 
     */
    public int size() {
     return size;
    }
    /**
     * Change the value at the specific index position with the obj.
     * @param index The index that needs to change obj
     * @param t The obj that will replace the object in position index
     */
    public void set(int index, T t){
        if (index < size){
            //Switch the value
            
            //Create a temporary array.
            Object[] tempArray = this.container;
            //Grab the capacity of the array.
            int capacity = this.container.length;
            //Re-create the container.
            this.container = new Object[capacity];
            //Iterate through the whole array, when hit the index position
            //Add the obj at the position rather than the tempArray object.
            for (int i = 0; i < capacity; i++){
                if (index == i){
                    //Add the obj at the index.
                    this.container[i] = t;
                } else {
                    //Add the objects from the previous container to same indexes.
                    this.container[i] = tempArray[i];
                }                    
            }
            
        } else {
            //Cannot switch value that is higher than the size
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
    }
    /**
     * Copies the array passed to the method into the DynamicArray object array
     * @param pastingArray The array that we want to copy into the container.
     * @param capacity The size of the desired container.
     */
    private void copy(int capacity){
        Object[] tempArray = this.container;
        this.container = new Object[capacity];

        for (int i = 0; i < size; i++){
            this.container[i] = tempArray[i];
        }
        
    }
    /**
     * Re-creates the container object to have the position at index to be empty
     * by shifting all the objects either right or left the index position by shiftPosition integer.
     * @param index The position where the shifting to the right will occur
     * @param shiftPosition The integer to be shifting the array.
     */
    private T shift(int index, int add, int pop){
        
        //Capture the length of the container before re-creating it.
        //Depending if its a remove or an add, it will have different capacities.
        //Remove will have 1 less, and add will be the capacity that the container presently has.
        int capacity;
        if (add == 1) {
            capacity = this.container.length;
        } else {
            capacity = this.container.length-1;
        }
        //Create a temporary array.
        Object[] tempArray = this.container;
        //Recreate the container.
        this.container = new Object[this.container.length];
        
        if (pop == 1){
            
            for (int i = index-1; i >= 0; i--){

                log.debug("i " + i);
                    this.container[i] = tempArray[i];
                    log.debug("Container at i: " + this.container[i]);
                
            }

        } else {
        
        for (int i = 0; i < capacity-1; i++ ){
            
            log.debug("IN ARRAY IS: " + tempArray[i]);
            //log.debug("i is: " + i);
            
            if (i == index){
                //Leaves the position at index empty, and adds the rest on the right of it.
                //this.container[i+1] = tempArray[i]; 
                if (add == 1){
                    this.container[++i] = tempArray[i];                    
                } else {
                    this.container[i] = tempArray[i+1];
                }
            } else {
                if (add == 1){
                //Adds all the objects from the left of the index into the container.
                  this.container[i] = tempArray[i];
                } else {
                    if (index < i){
                  this.container[i] = tempArray[i+1];
                    } else {
                       this.container[i] = tempArray[i+1];
                    }
                } 
            
            }
        }
    }
        
        return (T) tempArray[index];
    } // End of shift

    /**
     * Adds the object at the end of the stack.
     * @param t The object that is being added to the stack.
     */
    @Override
    public void push(T t) {
        add(t);
    }
    /**
     * Removes the last member of the stack.
     * @return The removed object.
     */
    @Override
    public T pop() {
        String indexRemoved;
        log.debug("size is before: " + size);
          indexRemoved = this.container[--size].toString();
        log.debug("size is after: " + size);
        shift(size,-1,1);
        return (T) indexRemoved;
    }
    /**
     * Looks at the last member of the Stack.
     * @return 
     */
    @Override
    public T peek() {
        return (T) this.container[size - 1];
    }
    
    @Override
    public T element() {
        return (T) this.container[0];
    
    }
    
     /**
     * Remove the object at the end of the container.
     * Decrease the size variable.
     */
    @Override
    public T remove(){
        Object indexRemoved = this.container[0];
                log.debug("index: "+indexRemoved);

        shift(0,-1,0);
        log.debug("length " + this.container.length);
                log.debug("size " + size);

        
        return (T) indexRemoved;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Arrays.deepHashCode(this.container);
        hash = 71 * hash + this.size;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DynamicArray<?> other = (DynamicArray<?>) obj;
        if (this.size != other.size) {
            return false;
        }
        if (!Arrays.deepEquals(this.container, other.container)) {
            return false;
        }
        return true;
    }
  
    @Override
    public String toString() {
        return "DynamicArray{" + "container=" + container + ", size=" + size + '}';
    }
    
        
        public void getArray(Object[] s){
        for (int i = 0; i < s.length; i++){
            log.debug("ARRAY: " + s[i]);
        }
    }

    
    
    
} // End of DynamicArray
