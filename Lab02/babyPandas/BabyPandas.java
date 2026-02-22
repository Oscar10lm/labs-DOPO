import java.util.HashMap;

/** BabyPandas.java
 * Mini motor para administrar variables tipo DataFrame.
 *
 * @author ESCUELA 2026-01
 */
    
public class BabyPandas{

    private final HashMap<String,DataFrame> variables;
    private boolean lastOk;

    public BabyPandas(){
        this.variables = new HashMap<>();
        this.lastOk = true;
    }

    //Define a new variable
    public void define(String name){
        if (name == null || name.isBlank() || variables.containsKey(name)) {
            lastOk = false;
            return;
        }
        variables.put(name, new DataFrame(new String[0][0], new String[0]));
        lastOk = true;
    }
     

    //Assign a DataFrame to an existing variable
    //a := DataFrame
    
    public void assign(String a, String[][] dataFrame){
        if (!variables.containsKey(a)) {
            lastOk = false;
            return;
        }

        int width = 0;
        if (dataFrame != null) {
            for (String[] row : dataFrame) {
                if (row != null && row.length > width) {
                    width = row.length;
                }
            }
        }

        String[] columns = new String[width];
        for (int i = 0; i < width; i++) {
            columns[i] = "col" + i;
        }

        variables.put(a, new DataFrame(dataFrame, columns));
        lastOk = true;
    }

    //Return DataFrame's shape
    public int[] shape(String a){
        DataFrame df = variables.get(a);
        if (df == null) {
            lastOk = false;
            return null;
        }
        lastOk = true;
        return df.shape();
    }
    
    //Assigns the value of a unary operation to a variable
    // a = b op parameters
    //The operator characters are: 'r' select rows, 'c' select columns, '?' select condition
    //The parameters for 'r' are [index1, index2, ...]
    //The parameters for 'c' are [column1, column2, ...]
    //The parameters for '?' are [valueColumn1, valueColumn2, ...]

    public void assignUnary(String a, String b, char op, String[] parameters){
        if (!variables.containsKey(a) || !variables.containsKey(b)) {
            lastOk = false;
            return;
        }

        DataFrame source = variables.get(b);
        DataFrame result;
        if (op == 'r') {
            int[] rows = parseRows(parameters);
            if (rows == null) {
                lastOk = false;
                return;
            }
            // Selección de filas usando concat de loc por columnas.
            result = new DataFrame(new String[0][0], new String[0]);
            int cols = source.shape()[1];
            for (int c = 0; c < cols; c++) {
                DataFrame partial = source.loc(rows, "col" + c);
                result = (c == 0) ? partial : result.concat(new DataFrame[] {partial}, (byte) 'c');
            }
        } else if (op == 'c') {
            result = selectColumns(source, parameters);
        } else if (op == '?') {
            result = source.select(parameters);
        } else {
            lastOk = false;
            return;
        }

        variables.put(a, result);
        lastOk = true;
    }

    //Assigns the value of a binary operation to a variable
    // a = b op c
    //The operator characters are:  'r' concate by rows, 'c' concate by columns
    public void assignBinary(String a, String b, char op, String c){
        if (!variables.containsKey(a) || !variables.containsKey(b) || !variables.containsKey(c)) {
            lastOk = false;
            return;
        }

        byte axis;
        if (op == 'r') {
            axis = (byte) 'r';
        } else if (op == 'c') {
            axis = (byte) 'c';
        } else {
            lastOk = false;
            return;
        }

        DataFrame result = variables.get(b).concat(new DataFrame[] {variables.get(c)}, axis);
        variables.put(a, result);
        lastOk = true;
    }
  
    


    //Return some rows of the DataFrame
    public String head(String variable, int rows){
        DataFrame df = variables.get(variable);
        if (df == null) {
            lastOk = false;
            return null;
        }
        lastOk = true;
        return df.head(rows);
    }
    
    //If the last operation was successfully completed
    public boolean ok(){
        return lastOk;
    }

    private int[] parseRows(String[] parameters) {
        if (parameters == null) {
            return new int[0];
        }
        int[] rows = new int[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            try {
                rows[i] = Integer.parseInt(parameters[i]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return rows;
    }

    private DataFrame selectColumns(DataFrame source, String[] columns) {
        if (columns == null || columns.length == 0) {
            return new DataFrame(new String[0][0], new String[0]);
        }

        DataFrame result = null;
        for (String col : columns) {
            DataFrame partial = source.loc(allRows(source.shape()[0]), col);
            result = (result == null) ? partial : result.concat(new DataFrame[] {partial}, (byte) 'c');
        }
        return result == null ? new DataFrame(new String[0][0], new String[0]) : result;
    }

    private int[] allRows(int count) {
        int[] rows = new int[count];
        for (int i = 0; i < count; i++) {
            rows[i] = i;
        }
        return rows;
    }
}