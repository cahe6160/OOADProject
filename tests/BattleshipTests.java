import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.lang.*;

public class BattleshipTests {

    @Before
    public void init() {
        Grid shipGrid1 = new Grid("ships");
        shipGrid1.printBoard();
        shipGrid1.addShip("A1", "A4");
        shipGrid1.printBoard();
        //shipGrid1.addShip("D2", "G2");
        //Grid shotsGrid1 = new Grid("shots");
    }
}

