import java.util.*;
/**
 * 
 */

/**
 * @author datacomm
 *
 */
public class BalloonBits implements Comparable{
	
	public BalloonBits(){
		this.bitArray = new BitSet(100);
	}
	
	public BalloonBits(int size){
		this.bitArray = new BitSet(size);
	}
	
	public BalloonBits(ArrayList<Balloon> aList){
		
	}
	
	public BitSet getSet(){
		return this.bitArray;
	}
	
	private void setBalloon(int anIndex, Balloon aBalloon){
		this.bitArray.clear(anIndex, anIndex+3);
		this.bitArray.or(this.getMask(anIndex, aBalloon));
	}
	
	private BitSet getMask(int anIndex, Balloon aBalloon){
		BitSet result = new BitSet(this.bitArray.length());
		int balloonVal = aBalloon.getNumber();
		
		if (balloonVal == 1 || balloonVal == 3 || balloonVal == 5){
			result.set(anIndex);
		}
		anIndex++;
		if (balloonVal == 2 || balloonVal == 3 || balloonVal == 6){
			result.set(anIndex);
		}
		anIndex++;
		if (balloonVal == 4 || balloonVal == 5 || balloonVal == 6){
			result.set(anIndex);
		}
		return result;
	}
	
	
	static private int getIndex(int anIndex){
		if (anIndex == 1) return 1;
		int result = 3 + getIndex(--anIndex);
		return result;
	}
	
	public int compareTo(Object aBalloonBits){
		if (this.bitArray.equals(aBalloonBits)) return 0;
		int val1 = 0;
		int val2 = 0;
		for (int i=1; i<=this.bitArray.length(); i++){
			if (this.bitArray.get(i)) val1 += i;
			if (((BalloonBits)aBalloonBits).getSet().get(i)) val2 += i;
		}
		return val1 - val2;
	}
	
	public String toString(){
		return this.bitArray.toString();
	}
	
	private BitSet bitArray;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BalloonBits test = new BalloonBits(13);
		Balloon test2 = new Balloon();
		BalloonBits test3 = new BalloonBits(13);
		test.setBalloon(BalloonBits.getIndex(4), test2);
		System.out.println(test2.getNumber());
		test2 = new Balloon();
		test3.setBalloon(BalloonBits.getIndex(4), test2);
		//test3.getSet().set(13);
		//System.out.println(BalloonBits.getIndex(2));
		System.out.println(test2.getNumber());
		System.out.println(test);
		System.out.println(test.compareTo(test3));
		//System.out.println(test3.compareTo(test));
	}

}
