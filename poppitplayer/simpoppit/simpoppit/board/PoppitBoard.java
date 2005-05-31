package simpoppit.board;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class PoppitBoard implements GameGridConstants, Comparable, Cloneable {

	private static Logger _logger = Logger.getLogger(PoppitBoard.class);

	/**
	 * Construct a new board, given a game grid. A random color will be assigned
	 * to each space.
	 * 
	 * @param grid
	 */
	public PoppitBoard(GameGrid grid) {
		_grid = grid;
		_board = new short[_grid.size()];
		_empty = new short[_grid.size()];
		for (int i = 0; i < _empty.length; i++) {
			_empty[i] = POPPED;
		}
		for (int i = 0; i < _board.length; i++) {
			_board[i] = getGrid().randomColor();
		}
	}

	/**
	 * construct a new board, that is identical to the given board.
	 * 
	 * @param other
	 */
	public PoppitBoard(PoppitBoard other) {
		_grid = other._grid;
		_board = new short[other._board.length];
		System.arraycopy(other._board, 0, _board, 0, other._board.length);
	}

	/**
	 * create a copy of this board.
	 */
	public Object clone() {
		return new PoppitBoard(this);
	}

	/**
	 * Define a natural ordering between boards. Smaller boards come first; if
	 * they are the same size, each space state is compared in gamegrid index
	 * order until a difference is found. The different states of the space are
	 * compoared, and an ordering determined.
	 * 
	 * @param o
	 * @return a negative integer if this board 'comes first,' 0 if they are
	 *         equal, or a positive integer if this board 'comes after'
	 */
	public int compareTo(Object o) {
		PoppitBoard other = (PoppitBoard) o;
		int result = _board.length - other._board.length;
		for (int i = 0; result == 0 && i < _board.length; i++) {
			result = _board[i] - other._board[i];
		}
		return result;
	}

	/**
	 * Check that the given space is a valid position on this board.
	 * 
	 * @param space
	 * @return
	 */
	public boolean contains(Space space) {
		return getGrid().contains(space);
	}

	/**
	 * return the maximum height of any space on this board. The 'y' value of
	 * all valid spaces of this board are < height.
	 * 
	 * @return
	 */
	public int getHeight() {
		return _grid.getHeight();
	}

	/**
	 * Given a space, return a set of all the spaces that would be 'popped' if
	 * that space were clicked.
	 * 
	 * @param space
	 * @return
	 */
	public SpaceSet getPopSet(Space space) {
		if (!contains(space)) {
			throw new IllegalArgumentException(
					"The board does not contain the given space " + space);
		}
		SpaceSet result = new SpaceSet();
		getPopSet(space, result, getState(space));
		return result;
	}

	/**
	 * get the space instance correspnding to the given coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Space getSpace(int x, int y) {
		return _grid.getSpace(x, y);
	}

	/**
	 * get the state of the given position.
	 * 
	 * @param space
	 * @return
	 */
	public short getState(Space space) {
		if (contains(space)) {
			int index = getGrid().index(space.getX(), space.getY());
			return _board[index];
		}
		throw new IllegalArgumentException(
				"The board does not contain the given space " + space);
	}

	/**
	 * get the max width of the board. All x coordinates of valid spaces are <
	 * this.
	 * 
	 * @return
	 */
	public int getWidth() {
		return getGrid().getWidth();
	}

	/**
	 * transform the board by popping all the balloons in the given set.
	 * remaining balloons 'below' the set are moved up; then empty columns
	 * are'collapsed.'
	 * 
	 * @param popSet
	 */
	public void pop(SpaceSet popSet) {
		popImpl(popSet);
		collapse();
	}

	protected void popImpl(SpaceSet popSet) {
		if (_logger.isDebugEnabled()) {
			String msg = "Popping " + popSet;
			_logger.debug(msg);
		}
		SpaceSet column = new SpaceSet();
		Space top = popSet.get(0);
		column.add(top);
		popSet.remove(top);
		while (popSet.size() > 0) {
			Space space = popSet.get(0);
			Space up = getGrid().up(space);
			if (up != null && column.contains(up)) {
				column.add(space);
				popSet.remove(space);
			} else {
				popImpl(popSet);
			}
		}
		if (_logger.isDebugEnabled()) {
			String msg = "Identified column " + column + " to pop.";
			_logger.debug(msg);
		}
		popColumn(column);
	}

	/**
	 * manually change the state of the given space.
	 * 
	 * @param space
	 * @param state
	 * @return
	 */
	public short setState(Space space, short state) {
		if (contains(space)) {
			int index = getGrid().index(space.getX(), space.getY());
			short result = _board[index];
			_board[index] = state;
			return result;
		}
		throw new IllegalArgumentException(
				"The board does not contain the given space " + space);
	}

	/**
	 * Get the total number of spaces in this board.
	 * 
	 * @return
	 */
	public int size() {
		return _board.length;
	}

	/**
	 * construct a string rep of the board.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\t|");
		for (int i = 0; i < getWidth(); i++) {
			buffer.append(i + "\t|");
		}
		buffer.append("\n");
		for (int i = 0; i < getWidth(); i++) {
			buffer.append("\t___");
		}
		buffer.append("\n");
		for (int y = 0; y < getHeight(); y++) {
			buffer.append(y + "\t|");
			for (int x = 0; x < getWidth(); x++) {
				Space space = getSpace(x, y);
				short color = getState(space);
				if (color > 0) {
					buffer.append(Short.toString(color) + "\t|");
				} else {
					buffer.append("\t|");
				}
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

	/**
	 * given a space set of continuous spaces, all in the same column, 'pop'
	 * them. Moves any remaining balloons up, then fills the bottom of the
	 * column with the POPPED value.
	 * 
	 * @param column
	 */
	protected void popColumn(SpaceSet column) {
		Space top = column.get(0);
		int count = column.size();
		int x = top.getX();
		int toY = top.getY();
		int fromY = toY + count;
		int copyCount = getHeight() - fromY;
		// BUG FIX: if the bottom balloon of a column was popped, the 'copy
		// from' y value is >= Height, leading to an out of bounds exception.
		if (fromY < getHeight()) {
			if (_logger.isDebugEnabled()) {
				Space to = _grid.getSpace(x, toY);
				Space from = _grid.getSpace(x, fromY);
				_logger.debug("Moving " + copyCount + " cells  from " + from
						+ " to " + to);
			}
			System.arraycopy(_board, _grid.index(x, fromY), _board, _grid
					.index(x, toY), copyCount);
		}
		toY += copyCount;
		copyCount = getHeight() - toY;
		if (_logger.isDebugEnabled()) {
			Space to = _grid.getSpace(x, toY);
			_logger.debug("Filling " + copyCount + " cells  to " + to);
		}
		System.arraycopy(_empty, 0, _board, _grid.index(x, toY), copyCount);
	}

	/**
	 * access the grid instance providing the grid structure for this board.
	 * 
	 * @return
	 */
	protected GameGrid getGrid() {
		return _grid;
	}

	/**
	 * construct the set of spaces that would be popped if the given space were
	 * popped. Adds the given
	 * 
	 * @param space
	 *            the space to consider
	 * @param popSet
	 *            a set of spaces already known to need 'popping'
	 * @param color
	 *            the color of the popset.
	 * @return
	 */
	protected SpaceSet getPopSet(Space space, SpaceSet popSet, short color) {
		short spaceColor = getState(space);
		if (spaceColor != color) {
			return popSet;
		}
		if (popSet.contains(space) || spaceColor == POPPED) {
			return popSet;
		}
		popSet.add(space);
		Space neighbor = getGrid().left(space);
		if (neighbor != null) {
			getPopSet(neighbor, popSet, color);
		}
		neighbor = getGrid().right(space);
		if (neighbor != null) {
			getPopSet(neighbor, popSet, color);
		}
		neighbor = getGrid().up(space);
		if (neighbor != null) {
			getPopSet(neighbor, popSet, color);
		}
		neighbor = getGrid().down(space);
		if (neighbor != null) {
			getPopSet(neighbor, popSet, color);
		}
		return popSet;
	}

	private void collapse() {
		Space toTop = null;
		Space fromTop = null;
		for (int x = 0; x < getWidth(); x++) {
			Space top = getSpace(x, 0);
			if (getState(top) == POPPED) {
				if (toTop == null) {
					toTop = top;
				}
			} else {
				if (toTop != null) {
					fromTop = top;
					break;
				}
			}
		}
		int toIndex = toTop != null ? getGrid().index(toTop) : -1;
		int fromIndex = fromTop != null ? getGrid().index(fromTop) : -1;
		if (toIndex >= 0 && fromIndex >= 0) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Found an opportunity to collapse: columns "
						+ toTop + " through " + fromTop);
				_logger.debug("Before collapse:\n" + toString());
				_logger.debug("Copying " + (_board.length - fromIndex)
						+ " cells from " + fromIndex + " to " + toIndex);
			}
			// First, copy over data
			System.arraycopy(_board, fromIndex, _board, toIndex, _board.length
					- fromIndex);
			if (_logger.isDebugEnabled()) {
				_logger.debug("After copy:\n" + toString());
			}

			int toFill = fromIndex - toIndex;

			// Then, fill the vacated spaces w/ 'popped'
			System.arraycopy(_empty, 0, _board, _board.length - toFill, toFill);
			// finally, we need to check for additional opportunities to
			// collapse
			if (_logger.isDebugEnabled()) {
				_logger.debug("After fill:\n" + toString());
			}
			collapse();
		}

	}

	short[] _board;

	short[] _empty;

	GameGrid _grid;
}
