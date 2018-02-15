package ie.gmit.sw.utilities;

import ie.gmit.sw.chess.board.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testChessNotationToPositionConversion() {
        assertEquals(new Position(0, 0), Util.stringToPosition("A8"));
        assertEquals(new Position(7, 7), Util.stringToPosition("H1"));
        assertEquals(new Position(1, 4), Util.stringToPosition("B4"));
    }

    @Test
    public void testConversionToChessNotation() {
        assertEquals("A8", Util.positionToString(new Position(0, 0)));
        assertEquals("H1", Util.positionToString(new Position(7, 7)));
        assertEquals("B4", Util.positionToString(new Position(1, 4)));
    }
}

