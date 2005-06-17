package simpoppit.gameboard;

/**
 * <p>
 * Title: GameConsts
 * </p>
 * 
 * <p>
 * Description: Interface defining constants pertinent to the Poppit game,
 * specifically the possible colors of the balloons, and defines a mapping of
 * the colors to numerical byte values.
 * 
 * @author Andrew
 * @version 1.0
 */
public interface GameConsts {

    public static byte EMPTY = 0;

    public static byte RED = 1;

    public static byte YELLOW = 2;

    public static byte PURPLE = 3;

    public static byte GREEN = 4;

    public static byte BLUE = 5;

    public static byte[] COLORS = { EMPTY, RED, YELLOW, PURPLE, GREEN, BLUE };

}
