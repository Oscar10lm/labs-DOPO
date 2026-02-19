import java.util.HashMap;

/** BabyPandas.java
 * 
 * @author ESCUELA 2026-01
 */
    
public class BabyPandas{
    
    private HashMap<String,DataFrame> variables;
    
    public BabyPandas(){
    }

    //Definea new variable
    public void define(String name){
    }
     
    //Assign a DataFrame to an existing variable
    //a := DataFrame
    public void assign(String a, String [] [] dataFrame){
    }    
    
    //Return DataFrame's shape
    public int[] shape(String a){
        return null;
    }
    
    
    //Assigns the value of a unary operation to a variable
    // a = b op parameters
    //The operator characters are: 'r' select rows, 'c' select columns, '?' select condition
    //The parameters for 'r' are [index1, index2, ...]
    //The parameters for 'c' are [column1, column2, ...]
    //The parameters for '?' are [valueColumn1, valueColumn2, ...]

    public void assignUnary(String a, String b, char op, String [] parameters){
    }
      
    
    //Assigns the value of a binary operation to a variable
    // a = b op c
    //The operator characters are:  'r' concate by rows, 'c' concate by columns
    public void assignBinary(String a, String b, char op, String c){
    }
  
    
    //Return some rows of the DataFrame
    public String head(String variable, int rows){
        return null;
    }
    
    
    //If the last operation was successfully completed
    public boolean ok(){
        return false;
    }
}
    



