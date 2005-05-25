import java.util.*;
import java.math.BigInteger;
/**
 * 
 */

/**
 * @author datacomm
 *
 */
public class BalloonInt implements Comparable{

	public BalloonInt(Balloon[] anArray){
		this.bitArray = new BigInteger(anArray.length*3, new Random());
		this.bitArray = this.bitArray.subtract(this.bitArray);
		this.size = anArray.length*3;
		
		for (int i=0; i<anArray.length; i++){
            this.setBalloon(BalloonInt.getIndex(i), anArray[i]);
        }
	}
	
	private BigInteger getInt(){
		return this.bitArray;
	}
    
    public int getSize(){
        return this.size;
    }
	
	private void setBalloon(int anIndex, Balloon aBalloon){
		this.bitArray = this.bitArray.clearBit(anIndex);
		this.bitArray = this.bitArray.clearBit(++anIndex);
		this.bitArray = this.bitArray.clearBit(++anIndex);
		this.bitArray = this.bitArray.or(this.getMask(anIndex, aBalloon));
	}
	
	private BigInteger getMask(int anIndex, Balloon aBalloon){
		BigInteger result = new BigInteger(this.getSize()*3, new Random());
		result = result.subtract(result);
		int balloonVal = aBalloon.getNumber();
		
		if (balloonVal == 1 || balloonVal == 3 || balloonVal == 5){
			result = result.setBit(anIndex);
		}
		if (balloonVal == 2 || balloonVal == 3 || balloonVal == 6){
			result = result.setBit(++anIndex);
		}
		if (balloonVal == 4 || balloonVal == 5 || balloonVal == 6){
			result = result.setBit(++anIndex);
		}
		return result;
	}
	
	static private int getIndex(int anIndex){
		if (anIndex == 0) return 0;
		int result = 3 + getIndex(--anIndex);
		return result;
	}
	
	public String toString(){
		return this.bitArray.toString();
	}
	
	public int compareTo(Object aBalloonInt){
		return this.bitArray.compareTo(((BalloonInt)aBalloonInt).getInt());
	}
	
	private BigInteger bitArray;
	private int size;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Balloon[] array = new GameGrid().getGridAsBalloonArray();
        BalloonInt test = new BalloonInt(array);
		//BalloonBits test = new BalloonBits(13);
		//Balloon test2 = new Balloon(Balloon.Color.PURPLE);
        array = new GameGrid().getGridAsBalloonArray();
        Balloon test2 = new Balloon();
		//BalloonBits test3 = new BalloonBits(13);
        BalloonInt test3 = new BalloonInt(array);
		//test.setBalloon(BalloonBits.getIndex(4), test2);
		System.out.println(test2.getNumber());
		//test2 = new Balloon(Balloon.Color.YELLOW);
        //test2 = new Balloon();
		//test3.setBalloon(BalloonBits.getIndex(4), test2);
		//test3.getSet().set(13);
		//System.out.println(BalloonBits.getIndex(3));
		//System.out.println(test2.getNumber());
		System.out.println(test);
        System.out.println(test3);
		System.out.println(test.compareTo(test3));
		//System.out.println(test3.compareTo(test));
	}

}
