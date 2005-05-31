package simpoppit.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpaceSet {

	public SpaceSet() {
		_spaces = new ArrayList<Space>();
	}

	public boolean add(Space space) {
		int index = index(space);
		if (index < 0) {
			_spaces.add(-(index + 1), space);
			return true;
		}
		return false;
	}

	public boolean add(SpaceSet spaces) {
		boolean result = false;
		for (int i = 0; i < spaces.size(); i++) {
			result |= add(spaces.get(i));
		}
		return result;
	}

	public boolean contains(Space space) {
		return index(space) >= 0;
	}

	public boolean equals(Object object) {
		SpaceSet other = (SpaceSet) object;
		return _spaces.size() == other._spaces.size()
				&& _spaces.containsAll(other._spaces);
	}

	public Space get(int index) {
		return _spaces.get(index);
	}

	public boolean remove(Space space) {
		int index = index(space);
		if (index >= 0) {
			_spaces.remove(index);
			return true;
		}
		return false;
	}

	public int size() {
		return _spaces.size();
	}

	public String toString() {
		return _spaces.toString();
	}
	
	protected int index(Space space) {
		return Collections.binarySearch(_spaces, space);
	}

	private List<Space> _spaces;

}
