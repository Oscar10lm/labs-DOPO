import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;





public class DataFrameTest{

    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        
    }

    
     @Test
    public void shouldCreateSmallestDataFrame(){
        String [] columns = {"Nombre", "Edad", "Profesión"};
        String [][] data={};
        int [] shape={0,3};
        DataFrame df=new DataFrame(data,columns);
        assertEquals(shape, df.shape());     
    }    
   
    @Test
    public void shouldCreateOtherDataFrame(){
        String [] columns = {"Nombre", "Edad", "Profesión"};
        String [][] data = {{"Carlos", "35", "Profesor"}, 
        {"Ana", "42", "Doctor"}, 
        {"Jorge", "30", "Arquitecto"},
        {"Elena", "25", "Diseñador"}};
        int [] shape={4,3};
        DataFrame df=new DataFrame(data,columns);
        assertEquals(shape, df.shape());   
    }    
    
    @Test
    public void shouldNotCreateBadDataFrame(){
        String [] columns = {"Nombre", "Edad", "Profesión"};
        String [][] data = {{"Carlos", "35"}, 
        {"Ana", "42", "Doctor"}, 
        {"30", "Arquitecto"},
        {"Elena", "25", "Diseñador"}};
        int [] shape={2,3};
        DataFrame df=new DataFrame(data,columns);
        assertEquals(shape, df.shape());   
    }      
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
