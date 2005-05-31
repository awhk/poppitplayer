package simpoppit.board;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class PoppitBoardTest extends TestCase {

	private static Logger _logger = Logger.getLogger(PoppitBoard.class);

	public static int WIDTH = 6;

	public static int HEIGHT = 7;

	/*
	 * Class under test for Object clone()
	 */
	public void testClone() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		PoppitBoard board1 = new PoppitBoard(grid);
		PoppitBoard board2 = (PoppitBoard) board1.clone();
		for (int x = 0; x < grid.getWidth(); x++) {
			for (int y = 0; y < grid.getHeight(); y++) {
				Space space = grid.getSpace(x, y);
				assertEquals(board1.getState(space), board2.getState(space));
			}
		}
	}

	public void testCompareTo() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		PoppitBoard board1 = new PoppitBoard(grid);
		PoppitBoard board2 = (PoppitBoard) board1.clone();
		assertEquals(board1.compareTo(board2), 0);
		Space s22 = grid.getSpace(2, 2);
		board2.setState(s22, PoppitBoard.POPPED);
		assertTrue(board1.compareTo(board2) > 0);
	}

	/*
	 * Class under test for SpaceSet getPopSet(Space)
	 */
	public void testGetPopSetSpace() {
		GameGrid grid = new GameGrid(4, 4);
		PoppitBoard board = new PoppitBoard(grid);
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Space sp = grid.getSpace(x, y);
				board.setState(sp, PoppitBoard.BLUE);
			}
		}
		board.setState(grid.getSpace(2, 2), PoppitBoard.RED);
		board.setState(grid.getSpace(2, 1), PoppitBoard.RED);
		board.setState(grid.getSpace(3, 2), PoppitBoard.RED);
		board.setState(grid.getSpace(3, 3), PoppitBoard.RED);

		SpaceSet set = board.getPopSet(grid.getSpace(2, 2));
		assertEquals(set.size(), 4);
	}

	public void testGetState() {
	}

	public void testPop1() {
		GameGrid grid = new GameGrid(4, 4);
		PoppitBoard board = new PoppitBoard(grid);
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Space sp = grid.getSpace(x, y);
				board.setState(sp, PoppitBoard.BLUE);
			}
		}
		board.setState(grid.getSpace(2, 2), PoppitBoard.RED);
		board.setState(grid.getSpace(2, 1), PoppitBoard.RED);
		board.setState(grid.getSpace(3, 2), PoppitBoard.RED);
		board.setState(grid.getSpace(3, 3), PoppitBoard.RED);

		SpaceSet set = board.getPopSet(grid.getSpace(2, 2));
		_logger.debug("BEFORE POP:\n" + board.toString());
		board.pop(set);
		_logger.debug("AFTER POP:\n" + board.toString());

		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Space sp = grid.getSpace(x, y);
				board.setState(sp, PoppitBoard.BLUE);
			}
		}
		board.setState(grid.getSpace(2, 2), PoppitBoard.RED);
		board.setState(grid.getSpace(2, 1), PoppitBoard.RED);
		board.setState(grid.getSpace(3, 2), PoppitBoard.RED);
		board.setState(grid.getSpace(3, 3), PoppitBoard.RED);

		set = board.getPopSet(grid.getSpace(0, 0));
		_logger.debug("BEFORE POP:\n" + board.toString());
		board.pop(set);
		_logger.debug("AFTER POP:\n" + board.toString());
	}

	public void testPop2() {
		GameGrid grid = new GameGrid(4, 4);
		PoppitBoard board = new PoppitBoard(grid);
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Space sp = grid.getSpace(x, y);
				board.setState(sp, PoppitBoard.BLUE);
			}
		}
		board.setState(grid.getSpace(2, 0), PoppitBoard.RED);
		board.setState(grid.getSpace(2, 1), PoppitBoard.RED);
		board.setState(grid.getSpace(2, 2), PoppitBoard.RED);
		board.setState(grid.getSpace(2, 3), PoppitBoard.RED);
		SpaceSet set = board.getPopSet(grid.getSpace(2, 1));
		_logger.debug("BEFORE POP:\n" + board.toString());
		board.pop(set);
		_logger.debug("AFTER POP:\n" + board.toString());

	}

	public void testSetState() {
	}

	public void testPopColumn() {
	}

}
