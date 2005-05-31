package simpoppit.board;

import junit.framework.TestCase;

public class GameGridTest extends TestCase {

	public static int WIDTH = 6;

	public static int HEIGHT = 8;

	public void testGameGrid() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		assertEquals("Width should be " + WIDTH, grid.getWidth(), WIDTH);
		assertEquals("Height should be " + HEIGHT, grid.getHeight(), HEIGHT);
		assertEquals("Size should be " + WIDTH * HEIGHT, grid.size(), WIDTH
				* HEIGHT);
	}

	public void testContains() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Space space = grid.getSpace(x, y);
				assertTrue("Grid contains space (" + x + ", " + y + ")", grid
						.contains(space));
			}
		}
		Exception failure = null;
		try {
			Space space = grid.getSpace(-1, -1);
		} catch (RuntimeException ex) {
			failure = ex;
		}
		assertNotNull("Fetching space (-1, -1) fails", failure);
		failure = null;
		try {
			Space space = grid.getSpace(-1, 0);
		} catch (RuntimeException ex) {
			failure = ex;
		}
		assertNotNull("Fetching space (-1, 0) fails", failure);
		failure = null;
		try {
			Space space = grid.getSpace(0, -1);
		} catch (RuntimeException ex) {
			failure = ex;
		}
		assertNotNull("Fetching space (0, -1) fails", failure);
		failure = null;
		try {
			Space space = grid.getSpace(WIDTH, 0);
		} catch (RuntimeException ex) {
			failure = ex;
		}
		assertNotNull("Fetching space (" + WIDTH + ", 0) fails", failure);
		failure = null;
		try {
			Space space = grid.getSpace(0, HEIGHT);
		} catch (RuntimeException ex) {
			failure = ex;
		}
		assertNotNull("Fetching space (0, " + HEIGHT + ") fails", failure);
		failure = null;
		try {
			Space space = grid.getSpace(WIDTH, HEIGHT);
		} catch (RuntimeException ex) {
			failure = ex;
		}
		assertNotNull("Fetching space (" + WIDTH + ", " + HEIGHT + ") fails",
				failure);

	}

	public void testNeighbors() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		Space topLeft = grid.getSpace(0, 0);
		Space bottomLeft = grid.getSpace(0, HEIGHT - 1);
		Space topRight = grid.getSpace(WIDTH - 1, 0);
		Space bottomRight = grid.getSpace(WIDTH - 1, HEIGHT - 1);

		assertNull(grid.left(topLeft));
		assertNull(grid.up(topLeft));
		assertEquals(grid.right(topLeft), grid.getSpace(1, 0));
		assertEquals(grid.down(topLeft), grid.getSpace(0, 1));

		assertNull(grid.up(topRight));
		assertNull(grid.right(topRight));
		assertEquals(grid.down(topRight), grid.getSpace(WIDTH - 1, 1));
		assertEquals(grid.left(topRight), grid.getSpace(WIDTH - 2, 0));

		assertNull(grid.down(bottomLeft));
		assertNull(grid.left(bottomLeft));
		assertEquals(grid.up(bottomLeft), grid.getSpace(0, HEIGHT - 2));
		assertEquals(grid.right(bottomLeft), grid.getSpace(1, HEIGHT - 1));

		assertNull(grid.down(bottomRight));
		assertNull(grid.right(bottomRight));
		assertEquals(grid.up(bottomRight), grid.getSpace(WIDTH - 1, HEIGHT - 2));
		assertEquals(grid.left(bottomRight), grid.getSpace(WIDTH - 2,
				HEIGHT - 1));

	}

	public void testDimensions() {
		int w = WIDTH;
		int h = HEIGHT;
		GameGrid grid = new GameGrid(w, h);
		assertEquals("Width should be " + w, grid.getWidth(), w);
		assertEquals("Height should be " + h, grid.getHeight(), h);
		assertEquals("Size should be " + w * h, grid.size(), w * h);
		w = 1;
		h = 1;
		grid = new GameGrid(w, h);
		assertEquals("Width should be " + w, grid.getWidth(), w);
		assertEquals("Height should be " + h, grid.getHeight(), h);
		assertEquals("Size should be " + w * h, grid.size(), w * h);
		w = 7;
		h = 10;
		grid = new GameGrid(w, h);
		assertEquals("Width should be " + w, grid.getWidth(), w);
		assertEquals("Height should be " + h, grid.getHeight(), h);
		assertEquals("Size should be " + w * h, grid.size(), w * h);
		w = 0;
		h = 8;
		grid = new GameGrid(w, h);
		assertEquals("Width should be " + w, grid.getWidth(), w);
		assertEquals("Height should be " + h, grid.getHeight(), h);
		assertEquals("Size should be " + w * h, grid.size(), w * h);
		w = 8;
		h = 0;
		grid = new GameGrid(w, h);
		assertEquals("Width should be " + w, grid.getWidth(), w);
		assertEquals("Height should be " + h, grid.getHeight(), h);
		assertEquals("Size should be " + w * h, grid.size(), w * h);
	}

	public void testGetSpace() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		assertSame(grid.getSpace(0, 0), grid.getSpace(0, 0));
		assertSame(grid.getSpace(WIDTH - 1, HEIGHT - 1), grid.getSpace(
				WIDTH - 1, HEIGHT - 1));
		assertNotSame(grid.getSpace(0, 0), grid.getSpace(WIDTH - 1, HEIGHT - 1));
	}

	public void testIndex() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		assertEquals(grid.index(0, 0), 0);
		assertEquals(grid.index(0, HEIGHT - 1), HEIGHT - 1);
		int index = grid.index(WIDTH - 1, HEIGHT - 1);
		int size = grid.size();
		assertEquals(index, size - 1);
	}

	public void testRandomColor() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		Exception failure = null;
		try {
			for (int i = 0; i < 100; i++) {
				short color = grid.randomColor();
			}
		} catch (RuntimeException ex) {
			failure = ex;
		}
		assertNull("randomColor should not throw exceptions", failure);
	}

}
