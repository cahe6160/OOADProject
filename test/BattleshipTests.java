//import edu.colorado.mtoftheholycross.Ship;
//import edu.colorado.mtoftheholycross.Grid;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import java.lang.*;
//import static org.junit.Assert.assertEquals;
//
//public class BattleshipTests {
//
//    private Grid p0Grid;
//    private Grid p1Grid;
//
//    @Before
//    public void init() {
//        p0Grid = new Grid(false);
//        p1Grid = new Grid(true);
//        p0Grid.addShip("A2", "A5");
//        p1Grid.addShip("E6", "G6");
//    }
//
//    @Test
//    public void TestHit() {
//        Boolean result = p1Grid.checkHit("F6");
//        assertEquals(true, result);
//    }
//
//    @Test
//    public void TestMiss() {
//        Boolean result = p0Grid.checkHit("A1");
//        assertEquals(false, result);
//    }
//}
//
