
import com.rimidev.array.DynamicArray;
import org.junit.*;
import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the class that will test the workings of the DyanmicArray class.
 * @author maximelacasse
 * @version 1.0
 */
public class DynamicArrayTester {
    
    //Create the dynamic array
    DynamicArray array = new DynamicArray();
    
    //Logger for debugging.
    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());

    
    
    //WORKS
    @Test
    public void testAddAtEndOfTheArray(){      
        String test = "Testing";
        
        array.add(test);
     
        Assert.assertEquals("The object is in the array", array.get(0), test);
      
    }
    
    @Test
    public void testAddAtTheEndOfTheArrayWhenFullyOccupied(){
        for (int i = 0; i < 10; i++){
            String test = "Testing" + i;
            array.add(i,test);
        }
        
        log.debug("arraySize1: " + array.size());
        
        array.add("testing11");
        
        log.debug("arraySize2: " + array.size());
        
        Assert.assertEquals("The size of the object is 11", 11, array.size());
        
    }
    //WORKS
    @Test
    public void testAddAtIndex(){
        String test = "Testing";
        
        array.add(0,test);
        
        log.debug("Array size: " + array.size());
        
        Assert.assertEquals("The object is in the array at the correct index",
                array.get(0), test);
    }
    //WORKS
    @Test
    public void testAddAtIndexWhenOccupiedIndex(){
        String test = "FirstAdd";
        
        array.add(0,test);
        
        String test2 = "SecondAdd";
        
        array.add(0,test2);
        
        log.debug("Array at 0: " + array.get(0));
        
        Assert.assertEquals("The object is in the array at index",
                array.get(0), test2);
    }
    
    @Test(timeout = 1000, expected = IndexOutOfBoundsException.class)
    public void testAddAtInvalidIndex() {
        String test = "Testing";
        
        array.add(3,test);
        
        fail("Cannot set a value at index that is greater than the size.");
    }
    //WORKS
    @Test
    public void testGet(){
        String test = "Testing";
        
        array.add(test);
        
        Object x = array.get(0);
        
        Assert.assertEquals("The object is the same", x, test);
    }
    
    //WORKS
    //Only works if '==' and not '.equals'??
    @Test
    public void testIndexOf(){
        String test = "Testing";
        
        array.add(0,test);
        
        int index = array.indexOf(test);
        
        Assert.assertEquals("The object has been found at the correct index",
                0, index);
    }
    //WORKS
    @Test
    public void testRemoveAtIndex(){
        String test = "Testing";
        
        array.add(0,test);
        
        array.remove(0);
        
        Object x = null;
        
        Assert.assertEquals("The object at the index has been removed", x, array.get(0));
    }
    //WORKS
    @Test
    public void testRemoveAtTheEnd(){
        String test = "Testing";
        
        array.add(test);
        
        array.remove();
        
        Object x = null;
        
        Assert.assertEquals("The object at the end has been removed", x, array.get(0));
    }
    //RESTRICTION WORKS
    @Test(timeout = 1000, expected = IndexOutOfBoundsException.class)
    public void testSetAtInvalidIndexPosition(){
        String test = "Testing";
        String switched = "Switched";
        
        array.add(3,test);
        array.set(3,switched);
        
        fail("Cannot set a value at index that is greater than the size.");
        
    }
    
    @Test
    public void testSet(){
        String test = "Testing";
        String switched = "Switched";
        
        array.add(test);
        array.set(0,switched);
        
        Assert.assertEquals("The object has been switched correctly", 
                switched, array.get(0));
    }
    
}
