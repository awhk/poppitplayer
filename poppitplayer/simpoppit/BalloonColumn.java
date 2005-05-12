import java.util.*;
/**
 * <p>Title: BalloonColumn</p>
 *
 * <p>Description: Balloon column class</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Andrew W. Henry
 * @version 1.0
 */

public class BalloonColumn implements Cloneable{
    public BalloonColumn(){
        this(10);
    }
    
    public BalloonColumn(int aLength){
        this.column.ensureCapacity(aLength);
        for (int i=0; i<aLength; i++){
            this.column.add(new Balloon());
        }
        //this.salt = (new Random().nextInt(50) + 1);
        this.computeChecksum();
    }
    
    public int count(){
        return this.column.size();
    }
    
    public Balloon.Color color(int aBalloon){
        //System.out.print("Accessing color for balloon ");
        //System.out.println(aBalloon);
        return this.column.get(aBalloon).color();
    }
    
    public int getSize(){
        return this.column.size();
    }
    
    public String toString() {
        String result = "";
        result += "Checksum is " + this.checksum + "\n";
        for (int i=0; i<this.getSize(); i++){
            result += "Row ";
            result += i + 1;
            result += " has color ";
            result += this.getBalloon(i).color();
            result += "\n";
        }
        return result;
    }
    
    public boolean isEmpty(){
        //if (this.column.isEmpty()) return true;
        int count = 0;
        for (Balloon t : this.column){
            if (!t.isPopped()) count++;
        }
        if (count == 0) return true;
        return false;
    }
    
    public boolean equals(Object aColumn){
        if (!(aColumn instanceof BalloonColumn)) return false;
        if (!(((BalloonColumn)aColumn).getSize() == this.getSize())) return false;
            for (int i=0; i<this.getSize(); i++){
                if (!((BalloonColumn)aColumn).getBalloon(i).equals(this.getBalloon(i))) return false;
            }
        return true;
    }
    
    public Balloon getBalloon(int aBalloon){
        return this.column.get(aBalloon);
    }
    
    public int getChecksum(){
        return this.checksum;
    }
    
    public void pop(int aBalloon){
        this.column.get(aBalloon).pop();
    }
    
    public ArrayList<Balloon> getBalloons(){
        return this.column;
    }
    
    private void setBalloons(ArrayList<Balloon> aNewColumn){
        for (int i=0; i<aNewColumn.size(); i++){
            this.column.set(i, (Balloon)aNewColumn.get(i).clone());
        }
    }
    
    private void computeChecksum(){
        int myChecksum = 0;
        for (int i=0; i<this.getSize(); i++){
            myChecksum += (this.getBalloon(i).getNumber() * (this.getSize() - i));
        }
        this.checksum = myChecksum;
        if (this.checksum == 0){
            System.out.println("Uh-oh...");
        }
    }
    
//    public void setSalt(int aSalt){
//        this.salt = aSalt;
//    }
    
    public void squeeze(){
        if (this.isEmpty()) {
            this.checksum = this.getSize();
            return;
        }
		
        boolean sawPopped = false;
        for (int i=0; i<this.getSize(); i++){
            if (this.getBalloon(i).isPopped()){
                sawPopped = true;
                continue;
            }
            if (sawPopped){
                Collections.swap(this.column, i-1, i);
                squeeze();
                break;
            }
        }
        this.computeChecksum();
    }
    
    public Object clone(){
        BalloonColumn result = new BalloonColumn(this.getSize());
        //result.setSalt(this.salt);
        result.setBalloons(this.column);
        result.computeChecksum();
        return result;
    }
    
    private ArrayList<Balloon> column = new ArrayList<Balloon>(10);
    private int checksum;
    //private int salt;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        BalloonColumn test = new BalloonColumn(8);
        BalloonColumn test2;
        System.out.println(test);
        System.out.println("Popping all balloons in column...");
        for (int i=0; i<test.count(); i++){
            test.pop(i);
        }
        System.out.print("Column empty?  ");
        System.out.println(test.isEmpty());
        System.out.println("Making new column...");
        test = new BalloonColumn(8);
        System.out.println(test);
        System.out.println("Popping balloon 5...");
        test.pop(4);
        System.out.println(test);
        System.out.println("Squeezing...");
        test.squeeze();
        System.out.println(test);
        System.out.println("Popping balloons 2 and 3...");
        test.pop(1);
        test.pop(2);
        System.out.println(test);
        System.out.println("Squeezing...");
        test.squeeze();
        System.out.println(test);
        System.out.println("Making new column...");
        test = new BalloonColumn(8);
        System.out.println("Cloning new column...");
        test2 = (BalloonColumn)test.clone();
        System.out.println("Testing equality...");
        if (test.equals(test2)){
            System.out.println("Equal.");
        }else{
            System.out.println("Not equal");
        }
    }

}
