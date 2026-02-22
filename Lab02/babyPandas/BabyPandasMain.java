/**
 * Programa de demostración para visualizar el comportamiento básico de BabyPandas.
 * @autor juan gaitan - oscar lasso
 */
public class BabyPandasMain {

    public static void main(String[] args) {
        BabyPandas bp = new BabyPandas();

        // Ciclo 1: definir, asignar, shape y head
        bp.define("empleados");
        String[][] empleados = {
            {"Carlos", "35", "Profesor"},
            {"Ana", "42", "Doctor"},
            {"Jorge", "30", "Arquitecto"},
            {"Elena", "25", "Diseñador"}
        };
        bp.assign("empleados", empleados);

        System.out.println("== DataFrame empleados ==");
        int[] shape = bp.shape("empleados");
        System.out.println("shape: (" + shape[0] + ", " + shape[1] + ")");
        System.out.println(bp.head("empleados", 4));

        // Ciclo 2: operación unaria por condición
        bp.define("doctores");
        bp.assignUnary("doctores", "empleados", '?', new String[] {"Doctor"});
        System.out.println("\n== Filtrado por condición (Doctor) ==");
        System.out.println(bp.head("doctores", 5));

        // Ciclo 3: operación binaria (concat por filas)
        bp.define("empleados2");
        bp.assign("empleados2", new String[][] {
            {"Luisa", "29", "Ingeniera"},
            {"Mario", "31", "Analista"}
        });
        bp.define("todos");
        bp.assignBinary("todos", "empleados", 'r', "empleados2");

        System.out.println("\n== Concatenación por filas ==");
        int[] shapeTodos = bp.shape("todos");
        System.out.println("shape: (" + shapeTodos[0] + ", " + shapeTodos[1] + ")");
        System.out.println(bp.head("todos", 10));

        System.out.println("\nÚltima operación ok(): " + bp.ok());
    }
}