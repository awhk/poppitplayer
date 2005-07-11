import java.util.*;
/**
 * 
 */

/**
 * @author datacomm
 *
 */
public class BalloonBits implements Comparable{
	
	public BalloonBits(int aSize){
		this.bitArray = new BitSet(aSize*3);
        this.size = aSize*3;
	}
	
	public BalloonBits(ArrayList<Balloon> aList){
        this.bitArray = new BitSet(aList.size()*3);
        this.size = aList.size()*3;
        
        for (int i=0; i<aList.size(); i++){
            this.setBalloon(BalloonBits.getIndex(i), aList.get(i));
        }		
	}
    
    public BalloonBits(Balloon[] anArray){
        this.bitArray = new BitSet(anArray.length*3);
        this.size = anArray.length*3;
        
        for (int i=0; i<anArray.length; i++){
            this.setBalloon(BalloonBits.getIndex(i), anArray[i]);
        }
    }
	
	private BitSet getSet(){
		return this.bitArray;
	}
    
    public int getSize(){
        return this.size;
    }
	
	private void setBalloon(int anIndex, Balloon aBalloon){
		this.bitArray.clear(anIndex, anIndex+3);
		this.bitArray.or(this.getMask(anIndex, aBalloon));
	}
	
	private BitSet getMask(int anIndex, Balloon aBalloon){
		BitSet result = new BitSet(this.getSize()*3);
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
		if (anIndex == 0) return 0;
		int result = 3 + getIndex(--anIndex);
		return result;
	}
	
	public int compareTo(Object aBalloonBits){
		if (this.bitArray.equals(((BalloonBits)aBalloonBits).getSet())) return 0;
//		int val1 = this.getSize();
//		int val2 = val1;
        //this.bitArray.andNot(((BalloonBits)aBalloonBits).getSet());
        //System.out.println("length is " + val1);
//		for (int i=val1; i>0; i--){
//            //System.out.println("val1=" + val1 + " , val2=" + val2);
//			if ((this.bitArray.get(i))) val1 = val1 - i;
//			if ((((BalloonBits)aBalloonBits).getSet().get(i))) val2 = val2 - i;
//		}
//		return val1 - val2;
        //return this.bitArray.length() - ((BalloonBits)aBalloonBits).getSet().length();
        //return this.bitArray.or(((BalloonBits)aBalloonBits).getSet().flip(0, this.getSize()));
        int result = this.bitArray.hashCode() - ((BalloonBits)aBalloonBits).getSet().hashCode();
        if (result == 0){
            System.out.println("Oh no! " + this.bitArray.hashCode() + "," + ((BalloonBits)aBalloonBits).getSet().hashCode());
            //System.exit(0);
            result = 1;
        }
        return result;
	}
	
	public String toString(){
		return this.bitArray.toString();
	}
	
	private BitSet bitArray;
    private int size;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Balloon[] array = new GameGrid().getGridAsBalloonArray();
        BalloonBits test = new BalloonBits(array);
		//BalloonBits test = new BalloonBits(13);
		//Balloon test2 = new Balloon(Balloon.Color.PURPLE);
        //array = new GameGrid().getGridAsBalloonArray();
        Balloon test2 = new Balloon();
		//BalloonBits test3 = new BalloonBits(13);
        BalloonBits test3 = new BalloonBits(array);
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
