package simpoppit.board;

import junit.framework.TestCase;

public class SpaceTest extends TestCase {

	public static int WIDTH = 6;

	public static int HEIGHT = 8;

	public void testCompareTo() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		Space s00 = grid.getSpace(0, 0);
		Space sWH = grid.getSpace(WIDTH - 1, HEIGHT - 1);
		Space s0H = grid.getSpace(0, HEIGHT - 1);
		Space sW0 = grid.getSpace(WIDTH - 1, 0);
		Space s33 = grid.getSpace(3, 3);

		assertTrue(s00.compareTo(s00) == 0);
		assertTrue(s00.compareTo(sWH) < 0);
		assertTrue(s00.compareTo(s0H) < 0);
		assertTrue(s00.compareTo(sW0) < 0);
		assertTrue(s00.compareTo(s33) < 0);

		assertTrue(sWH.compareTo(s00) > 0);
		assertTrue(sWH.compareTo(sWH) == 0);
		assertTrue(sWH.compareTo(s0H) > 0);
		assertTrue(sWH.compareTo(sW0) > 0);
		assertTrue(sWH.compareTo(s33) > 0);
		
		assertTrue(s0H.compareTo(s00) > 0);
		assertTrue(s0H.compareTo(sWH) < 0);
		assertTrue(s0H.compareTo(s0H) == 0);
		assertTrue(s0H.compareTo(sW0) < 0);
		assertTrue(s0H.compareTo(s33) < 0);
		
		assertTrue(sW0.compareTo(s00) > 0);
		assertTrue(sW0.compareTo(sWH) < 0);
		assertTrue(sW0.compareTo(s0H) > 0);
		assertTrue(sW0.compareTo(sW0) == 0);
		assertTrue(sW0.compareTo(s33) > 0);
		
		assertTrue(s33.compareTo(s00) > 0);
		assertTrue(s33.compareTo(sWH) < 0);
		assertTrue(s33.compareTo(s0H) > 0);
		assertTrue(s33.compareTo(sW0) < 0);
		assertTrue(s33.compareTo(s33) == 0);
	}

}
