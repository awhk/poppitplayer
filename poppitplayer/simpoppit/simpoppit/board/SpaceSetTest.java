package simpoppit.board;

import junit.framework.TestCase;

public class SpaceSetTest extends TestCase {

	public static int WIDTH = 6;

	public static int HEIGHT = 7;

	/*
	 * Class under test for boolean add(Space)
	 */
	public void testAddSpace() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		SpaceSet set = new SpaceSet();
		set.add(grid.getSpace(0, 0));
		set.add(grid.getSpace(0, 4));
		set.add(grid.getSpace(1, 2));
		set.add(grid.getSpace(2, 1));
		assertFalse(set.add(grid.getSpace(0, 0)));
		assertEquals(set.size(), 4);
		for (int i = 1; i < set.size(); i++) {
			assertTrue(set.get(i - 1).compareTo(set.get(i)) < 0);
		}
	}

	/*
	 * Class under test for boolean add(SpaceSet)
	 */
	public void testAddSpaceSet() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		SpaceSet set1 = new SpaceSet();
		set1.add(grid.getSpace(0, 0));
		set1.add(grid.getSpace(0, 4));
		set1.add(grid.getSpace(1, 2));
		set1.add(grid.getSpace(2, 1));

		SpaceSet set2 = new SpaceSet();
		set2.add(grid.getSpace(0, 0));
		set2.add(grid.getSpace(0, 4));
		set2.add(grid.getSpace(1, 2));
		set2.add(grid.getSpace(2, 1));

		set1.add(set2);
		assertEquals(set1.size(), 4);

		SpaceSet set3 = new SpaceSet();
		set3.add(grid.getSpace(0, 1));
		set3.add(grid.getSpace(3, 3));
		set1.add(set3);
		assertEquals(set1.size(), 6);

		for (int i = 1; i < set1.size(); i++) {
			assertTrue(set1.get(i - 1).compareTo(set1.get(i)) < 0);
		}
	}

	public void testContains() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		SpaceSet set1 = new SpaceSet();
		Space s00 = grid.getSpace(0, 0);
		Space s04 = grid.getSpace(0, 4);
		Space s12 = grid.getSpace(1, 2);
		Space s21 = grid.getSpace(2, 1);

		set1.add(s00);
		assertTrue(set1.contains(s00));
		assertFalse(set1.contains(s04));
		set1.add(s04);
		assertTrue(set1.contains(s04));
		set1.add(s12);
		set1.add(s21);
		set1.remove(s04);
		assertTrue(set1.contains(s00));
		assertFalse(set1.contains(s04));

	}

	/*
	 * Class under test for boolean equals(Object)
	 */
	public void testEqualsObject() {
		GameGrid grid = new GameGrid(WIDTH, HEIGHT);
		SpaceSet set1 = new SpaceSet();
		set1.add(grid.getSpace(0, 0));
		set1.add(grid.getSpace(0, 4));
		set1.add(grid.getSpace(1, 2));
		set1.add(grid.getSpace(2, 1));

		SpaceSet set2 = new SpaceSet();
		set2.add(grid.getSpace(0, 0));
		set2.add(grid.getSpace(0, 4));
		set2.add(grid.getSpace(1, 2));
		set2.add(grid.getSpace(2, 1));

		assertEquals(set1, set2);
		set2.add(grid.getSpace(0, 1));
		assertFalse(set1.equals(set2));
		set2.remove(grid.getSpace(0, 1));
		assertEquals(set1, set2);
	}

}
