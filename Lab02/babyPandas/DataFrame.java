import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Clase que representa una estructura tipo tabla,
 * compuesta por columnas y filas de datos en formato String.
 * Permite almacenar información tabular y consultar su dimensión.
 * @autor juan gaitan - oscar lasso
 */

public class DataFrame {
    
    private final String[][] data;
    private final String[] columns;

    /**
     * Constructor que crea un DataFrame a partir de una matriz de datos
     * y un arreglo de nombres de columnas.
     * 
     * Valida que cada fila tenga la misma cantidad de elementos que columnas.
     * Si una fila no cumple con el tamaño correcto, no debe ser incluida.
     * 
     */
    
    public DataFrame(String[][] data, String[] columns){
        this.columns = columns == null ? new String[0] : Arrays.copyOf(columns, columns.length);

        List<String[]> validRows = new ArrayList<>();
        if (data != null) {
            for (String[] row : data) {
                if (row != null && row.length == this.columns.length) {
                    validRows.add(Arrays.copyOf(row, row.length));
                }
            }
        }

        this.data = new String[validRows.size()][];
        for (int i = 0; i < validRows.size(); i++) {
            this.data[i] = validRows.get(i);
        }
    }

    public DataFrame loc(int[] rows, String column){
        if (column == null) {
            return new DataFrame(new String[0][0], new String[0]);
        }

        int columnIndex = -1;
        for (int i = 0; i < columns.length; i++) {
            if (column.equals(columns[i])) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex < 0 || rows == null) {
            return new DataFrame(new String[0][0], new String[] {column});
        }

        List<String[]> selectedRows = new ArrayList<>();
        for (int rowIndex : rows) {
            if (rowIndex >= 0 && rowIndex < data.length) {
                selectedRows.add(new String[] {data[rowIndex][columnIndex]});
            }
        }

        String[][] selectedData = selectedRows.toArray(new String[0][]);
        return new DataFrame(selectedData, new String[] {column});
    }

    public DataFrame select(String[] values){
        if (values == null || values.length == 0) {
            return new DataFrame(new String[0][0], Arrays.copyOf(columns, columns.length));
        }
        
        Set<String> wanted = new HashSet<>(Arrays.asList(values));
        List<String[]> selectedRows = new ArrayList<>();
        for (String[] row : data) {
            boolean match = false;
            for (String cell : row) {
                if (wanted.contains(cell)) {
                    match = true;
                    break;
                }
            }
            if (match) {
                selectedRows.add(Arrays.copyOf(row, row.length));
            }
        }

        String[][] selectedData = selectedRows.toArray(new String[0][]);
        return new DataFrame(selectedData, Arrays.copyOf(columns, columns.length));
    }
    

    public DataFrame concat(DataFrame[] dfs, byte axis){
        List<DataFrame> all = new ArrayList<>();
        all.add(this);
        if (dfs != null) {
            for (DataFrame df : dfs) {
                if (df != null) {
                    all.add(df);
                }
            }
        }

        boolean byColumns = (axis == 1 || axis == 'c');
        boolean byRows = (axis == 0 || axis == 'r');
        if (!byColumns && !byRows) {
            return new DataFrame(new String[0][0], new String[0]);
        }

        if (byColumns) {
            int rows = this.data.length;
            for (DataFrame df : all) {
                if (df.data.length != rows) {
                    return new DataFrame(new String[0][0], new String[0]);
                }
            }

            int totalColumns = 0;
            for (DataFrame df : all) {
                totalColumns += df.columns.length;
            }

            String[] newColumns = new String[totalColumns];
            int colPos = 0;
            for (DataFrame df : all) {
                for (String col : df.columns) {
                    newColumns[colPos++] = col;
                }
            }

            String[][] newData = new String[rows][totalColumns];
            for (int r = 0; r < rows; r++) {
                colPos = 0;
                for (DataFrame df : all) {
                    for (int c = 0; c < df.columns.length; c++) {
                        newData[r][colPos++] = df.data[r][c];
                    }
                }
            }
            return new DataFrame(newData, newColumns);
        }

        int cols = this.columns.length;
        for (DataFrame df : all) {
            if (df.columns.length != cols) {
                return new DataFrame(new String[0][0], new String[0]);
            }
        }

        int totalRows = 0;
        for (DataFrame df : all) {
            totalRows += df.data.length;
        }

        String[][] newData = new String[totalRows][cols];
        int rowPos = 0;
        for (DataFrame df : all) {
            for (String[] row : df.data) {
                newData[rowPos++] = Arrays.copyOf(row, row.length);
            }
        }

        return new DataFrame(newData, Arrays.copyOf(this.columns, this.columns.length));
    }

    /**
     * Retorna la dimensión del DataFrame.
     * 
     * El primer valor representa el número de filas válidas.
     * El segundo valor representa el número de columnas.
     * 
     */
    
    public int[] shape(){
        return new int[] {data.length, columns.length};
    }


    // The columns are aligned, separated by three spaces, and include the index.
    //     Nombre   Edad    Profesion
    // 0    Lucía     28    Ingeniero
    // 1   Carlos     35     Profesor
    // 2      Ana     42       Doctor
    // 3    Jorge     30   Arquitecto
    // 4    Elena     25    Diseñador
    public String head(int rows) {
      int n = Math.max(0, Math.min(rows, data.length));
      int[] widths = new int[columns.length];
      for (int c = 0; c < columns.length; c++) {
          widths[c] = columns[c] == null ? 0 : columns[c].length();
      }
      for (int r = 0; r < n; r++) {
          for (int c = 0; c < columns.length; c++) {
              String value = data[r][c] == null ? "" : data[r][c];
              widths[c] = Math.max(widths[c], value.length());
          }
      }

      StringBuilder sb = new StringBuilder();
      sb.append("   ");
      for (int c = 0; c < columns.length; c++) {
          if (c > 0) {
              sb.append("   ");
          }
          sb.append(String.format("%" + widths[c] + "s", columns[c] == null ? "" : columns[c]));
      }

      for (int r = 0; r < n; r++) {
          sb.append("\n").append(r).append("   ");
          for (int c = 0; c < columns.length; c++) {
              if (c > 0) {
                  sb.append("   ");
              }
              String value = data[r][c] == null ? "" : data[r][c];
              sb.append(String.format("%" + widths[c] + "s", value));
          }
      }

      return sb.toString();
    }
    

    public boolean equals(DataFrame df){
        if (df == null) {
            return false;
        }
        return Arrays.equals(this.columns, df.columns) && Arrays.deepEquals(this.data, df.data);
    }
    

    public boolean equals(Object o){
        if (!(o instanceof DataFrame)) {
            return false;
        }
        return equals((DataFrame) o);
    }
}