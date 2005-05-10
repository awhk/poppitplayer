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
    
    public void squeeze(){
        if (this.isEmpty()) return;
		
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
    }
    
    public Object clone(){
        BalloonColumn result = new BalloonColumn(this.getSize());
        result.setBalloons(this.column);
        return result;
    }
    
    private ArrayList<Balloon> column = new ArrayList<Balloon>(10);
    
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
