package simpoppit.board;

public class Space implements Comparable, GameGridConstants {

	protected Space(int x, int y) {
		_x = x;
		_y = y;
	}

	protected Space(Space other) {
		_x = other._x;
		_y = other._y;
	}

	public int compareTo(Object o) {
		int result = _x - ((Space) o)._x;
		if (result == 0) {
			result = _y - ((Space) o)._y;
		}
		return result;
	}

	

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}



	
	private int _x;

	private int _y;
}
