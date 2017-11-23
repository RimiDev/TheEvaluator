package com.rimidev.array;

/**
 *
 * @author maximelacasse
 * @version 1.0
 */
public class DynamicArray {
    
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
    public void add(Object obj){
        this.container[size+1] = obj;
        size++;
    }
    
    /**
     * Adds the obj to the index position.
     * If the index position is not occupied, it will directly add into the array.
     * If the index position is occupied, it will shift the array to the right by 1, make room
     * for the obj, place it.
     * Increase the size in either cases.
     * @param index 
     * @param obj 
     */
    public void add(int index, Object obj) {
        //If the array at position:index is empty, add the object to the index.
        if (this.container[index] == null){
           this.container[index] = obj; //Adds the objects to the desired index position.
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
           copy(container,container.length*2); //Copies the array to a new array with doubled size.
        }
        //2. Shift all the proceeding objects from index +1 positions.
        shift(index,1);
        
        //3. Add the object to the desired index position.
        this.container[index] = obj; 
        size++; //Increase the size.
        }       
    } // End of add method.
        
    /**
     * Returns the object from position:index
     * @param index
     * @return 
     */
    public Object get(int index) {
     return this.container[index];
    }
    /**
     * Returns the index position of the specified object.
     * @param anObject
     * @return 
     */
    public int indexOf(Object anObject) {
        //Iterate through the whole array to check if any of the objects are equal to the anObject.
        for (int i = 0; i < this.container.length; i++){
            if (this.container[i].equals(anObject)){
                //The object at 'i' is equavalent to anObject, returning index.
                return i;
            }
        }
        return -1;
    }

    /**
     * 
     * @param index 
     */
    public void remove (int index) {
        shift(index,1); //Recreate the container array to not have the object in the index position.
        size--; //Decrease the size.
    }
    /**
     * Returns how many positions are used up by objects in the array.
     * This does not return the total containing size of the array.
     * @return 
     */
    public int size() {
     return size;
    }

    public Object set(int index, Object obj){
    //TO DO
    return null;
  }
    /**
     * Copies the array passed to the method into the DynamicArray object array
     * @param pastingArray The array that we want to copy into the container.
     * @param capacity The size of the desired container.
     */
    public void copy(Object[] pastingArray, int capacity){
        Object[] tempArray = pastingArray;
        this.container = new Object[capacity];
        
        for (int i = 0; i < capacity; i++){
            this.container[i] = tempArray[i];
        }
        
    }
    /**
     * Re-creates the container object to have the position at index to be empty
     * by shifting all the objects either right or left the index position by shiftPosition integer.
     * @param index The position where the shifting to the right will occur
     * @param shiftPosition The integer to be shifting the array.
     */
    public void shift(int index, int shiftPosition){
        //Capture the length of the container before re-creating it.
        int capacity = this.container.length;
        //Create a temporary array.
        Object[] tempArray = this.container;
        //Recreate the container.
        this.container = new Object[capacity];
        
        for (int i = 1; i < this.container.length; i++ ){
            //Adds all the objects from the left of the index into the container.
            this.container[i] = tempArray[i];
            
            if (i >= index){
                //Leaves the position at index empty, and adds the rest on the right of it.
                this.container[i+shiftPosition] = tempArray[i];                
            }         
            
        }                
    } // End of shift
    
} // End of DynamicArray
