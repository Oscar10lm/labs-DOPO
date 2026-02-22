import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/** BabyPandas.java
 * Pruebas Unitarias para la clase BabyPandas
 *
 * @author Juan Gaitan- Oscar Lasso
 */

public class BabyPandasTest {

    private BabyPandas bp;

    @Before
    public void setUp() {
        bp = new BabyPandas();
    }

    // Ciclo 1
    @Test
    public void shouldDefineAssignShapeAndHead() {
        bp.define("a");
        String[][] data = {
            {"Carlos", "35", "Profesor"},
            {"Ana", "42", "Doctor"}
        };
        bp.assign("a", data);

        assertTrue(bp.ok());
        assertArrayEquals(new int[] {2, 3}, bp.shape("a"));
        assertNotNull(bp.head("a", 1));
    }

    @Test
    public void shouldNotAssignUndefinedVariable() {
        bp.assign("x", new String[][] {{"A"}});
        assertFalse(bp.ok());
    }

    // Ciclo 2
    @Test
    public void shouldDoUnaryOperations() {
        bp.define("a");
        bp.define("b");
        bp.define("c");

        String[][] data = {
            {"Carlos", "35", "Profesor"},
            {"Ana", "42", "Doctor"},
            {"Jorge", "30", "Arquitecto"}
        };
        bp.assign("a", data);

        bp.assignUnary("b", "a", 'r', new String[] {"0", "2"});
        assertTrue(bp.ok());
        assertArrayEquals(new int[] {2, 3}, bp.shape("b"));

        bp.assignUnary("c", "a", '?', new String[] {"Doctor"});
        assertTrue(bp.ok());
        assertArrayEquals(new int[] {1, 3}, bp.shape("c"));
    }

    @Test
    public void shouldNotDoUnaryWhenRowsAreInvalidNumbers() {
        bp.define("a");
        bp.define("b");
        bp.assign("a", new String[][] {{"Carlos", "35"}});

        bp.assignUnary("b", "a", 'r', new String[] {"x"});
        assertFalse(bp.ok());
    }

    // Ciclo 3
    @Test
    public void shouldConcatByRowsAndColumns() {
        bp.define("a");
        bp.define("b");
        bp.define("r");
        bp.define("c");

        bp.assign("a", new String[][] {{"A", "1"}, {"B", "2"}});
        bp.assign("b", new String[][] {{"C", "3"}, {"D", "4"}});

        bp.assignBinary("r", "a", 'r', "b");
        assertTrue(bp.ok());
        assertArrayEquals(new int[] {4, 2}, bp.shape("r"));

        bp.assignBinary("c", "a", 'c', "b");
        assertTrue(bp.ok());
        assertArrayEquals(new int[] {2, 4}, bp.shape("c"));
    }

    @Test
    public void shouldNotConcatWithInvalidOperator() {
        bp.define("a");
        bp.define("b");
        bp.define("r");
        bp.assign("a", new String[][] {{"A"}});
        bp.assign("b", new String[][] {{"B"}});

        bp.assignBinary("r", "a", 'x', "b");
        assertFalse(bp.ok());
    }
}