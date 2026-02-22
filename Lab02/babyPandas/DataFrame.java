public class DataFrame {
    
    private String [] columns;
    private String [][] data;
    private int [] shape = {0,0};

    
    public DataFrame(String [][] data, String [] columns){
        this.columns = columns;
        this.data = data;
    
        for (int i = 0; i < columns.length;i++) {
            this.columns[i] = columns[i];
        }
        
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
        
        this.shape[0] = data.length;
        this.shape[1] = columns.length;

    }
    
    public DataFrame loc(int [] rows, String columns){
        return null;
    }    
    
    public DataFrame select(String [] values){
        return null;
    }      


    public DataFrame concat(DataFrame [] dfs, byte axis){
        return null;
    }

    public int [] shape(){
        return this.shape;
    }    
    
   
    // The columns are aligned, separated by three spaces, and include the index.
    //     Nombre   Edad    Profesion
    // 0    Lucía     28    Ingeniero
    // 1   Carlos     35     Profesor
    // 2      Ana     42       Doctor
    // 3    Jorge     30   Arquitecto
    // 4    Elena     25    Diseñador
    public String head(int rows) {
      return "";
    }
    
    public boolean equals(DataFrame df){
        return false;
    }
    
    public boolean equals(Object o){
        return equals((DataFrame)o);
    }
}
