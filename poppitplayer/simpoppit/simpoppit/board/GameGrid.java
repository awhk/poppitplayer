package simpoppit.board;

import org.apache.log4j.Logger;

/**
 * This class represents the physical structure of the board for a game of
 * poppit. It is initialized with the desired dimensions of the play space;
 * these dimensions are then fixed for the lifetime of the poppit game. <br>
 * The Grid manages a finite, static set of 'space' objects. These objects
 * represent each possible position of a balloon in the game grid. Over the
 * course of a game of 'poppit,' a separate board structure will record the
 * state information for each space. This structure info can be reused by every
 * board instance, reducing the amount of java object creation & disposal.
 */
public class GameGrid implements GameGridConstants {

	/**
	 * create a new board, with the given number of columns (width) and rows
	 * (height)
	 * 
	 * @param width
	 * @param height
	 */
	public GameGrid(int width, int height) {
		_width = width;
		_height = height;
		initSpaces();
		_logger.info("Created new grid; dimensions " + _width + " X " + height);
	}

	/**
	 * Check that the given 'space' object referes to a position in this game
	 * grid (i.e., the coordinates are non-negative and less than the max value.
	 * 
	 * @param space
	 * @return
	 */
	public boolean contains(Space space) {
		return space.getX() >= 0 && space.getX() < getWidth()
				&& space.getY() >= 0 && space.getY() < getHeight();
	}

	/**
	 * return the space 'down' from the given space.
	 * 
	 * @param space
	 *            a position (x, y)
	 * @return the space at position (x, y + 1), or null if down is 'off the
	 *         grid'
	 */
	public Space down(Space space) {
		int x = space.getX();
		int y = space.getY() + 1;
		if (y < getHeight()) {
			int index = x * getHeight() + y;
			return _spaces[index];
		}
		return null;
	}

	/**
	 * return the 'max' height. The Y coordinate of all valid spaces will be
	 * less than this.
	 * 
	 * @return
	 */
	public int getHeight() {
		return _height;
	}

	/**
	 * return the space object representing the given position. Note that
	 * getSpace(x, y) == getSpace(x, y); multiple calls w/ the same args will
	 * return the same object instance.
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws IllegalArgumentException
	 *             if either X or Y is 'off the grid'
	 */
	public Space getSpace(int x, int y) {
		if (x < 0 || x >= getWidth()) {
			throw new IllegalArgumentException("X is out of bounds");
		} else if (y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("Y is out of bounds");
		}
		int index = x * getHeight() + y;
		return _spaces[index];
	}

	/**
	 * The width of the grid. The X value for all valid positions will be < this
	 * number.
	 * 
	 * @return
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * The game grid can be translated into an array, where each cell has its
	 * own index. The numbering scheme starts at the top left, and counts down
	 * the column. When the bottom is reached, the count starts at the top of
	 * the next column.
	 * 
	 * @param x
	 * @param y
	 * @return a unique index for the given coordinates.
	 */
	public int index(int x, int y) {
		return x * getHeight() + y;
	}

	/**
	 * return the space 'left' from the given space.
	 * 
	 * @param space
	 *            a position (x, y)
	 * @return the space at position (x - 1, y), or null if left is 'off the
	 *         grid'
	 */
	public Space left(Space space) {
		int x = space.getX() - 1;
		int y = space.getY();
		if (x >= 0) {
			int index = x * getHeight() + y;
			return _spaces[index];
		}
		return null;
	}

	/**
	 * A helper class to select a random 'color' state from the constants
	 * defined in GameGridConstants.
	 * 
	 * @return a color from the ALL_COLORS array
	 */
	public short randomColor() {
		int index = (int) Math.floor(Math.random() * ALL_COLORS.length);
		return ALL_COLORS[index];
	}

	/**
	 * return the space 'right' from the given space.
	 * 
	 * @param space
	 *            a position (x, y)
	 * @return the space at position (x + 1, y), or null if right is 'off the
	 *         grid'
	 */
	public Space right(Space space) {
		int x = space.getX() + 1;
		int y = space.getY();
		if (x < getWidth()) {
			int index = x * getHeight() + y;
			return _spaces[index];
		}
		return null;
	}

	public int size() {
		return getWidth() * getHeight();
	}

	/**
	 * return the space 'up' from the given space.
	 * 
	 * @param space
	 *            a position (x, y)
	 * @return the space at position (x, y - 1), or null if up is 'off the grid'
	 */
	public Space up(Space space) {
		int x = space.getX();
		int y = space.getY() - 1;
		if (y >= 0) {
			int index = x * getHeight() + y;
			return _spaces[index];
		}
		return null;
	}

	/**
	 * construct the space instances to be used for this game grid.
	 */
	private void initSpaces() {
		_spaces = new Space[getWidth() * getHeight()];
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				_spaces[index(x, y)] = new Space(x, y);
			}
		}
	}

	Logger _logger = Logger.getLogger(GameGrid.class.getName());

	private int _height;

	private Space[] _spaces;

	private int _width;

	public int index(Space space) {
		return index(space.getX(), space.getY());
	}
}
