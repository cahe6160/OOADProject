import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.*;
import static org.junit.Assert.assertEquals;

public class BattleshipTests {

    private Grid shipGrid1;

    @Before
    public void init() {
        shipGrid1 = new Grid("MyShips");
        shipGrid1.addShip("A2", "A5");
        shipGrid1.addShip("E6", "G6");
        //shipGrid1.printBoard();
    }

    @Test
    public void TestHit() {
        String result = shipGrid1.checkHit("F6");
        assertEquals("HIT", result);

    }

    @Test
    public void TestMiss() {
        String result = shipGrid1.checkHit("A1");
        assertEquals("MISS", result);
    }
}

