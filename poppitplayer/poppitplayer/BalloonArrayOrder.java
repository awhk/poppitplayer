import java.util.Comparator;

/**
 * 
 */

/**
 * @author Andrew
 * 
 */
public class BalloonArrayOrder implements Comparator {

    public int compare(Object firstArray, Object secondArray) {
        Balloon[] thisArray = (Balloon[]) firstArray;
        Balloon[] otherArray = (Balloon[]) secondArray;

        int result = 0;
        for (int i = 0; i < thisArray.length; i++) {
            result = thisArray[i].getNumber() - otherArray[i].getNumber();
            if (result != 0)
                break;
        }
        return result;
    }

}
