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

public class BalloonColumn {
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
    
    public String toString() {
        String result = "";
        for (Balloon t : this.column){
            result += "Row ";
            result += this.column.indexOf(t) +  1;
            result += " has color ";
            result += t.color();
            result += "\n";
        }
        return result;
    }
    
    public boolean isEmpty(){
        if (this.column.isEmpty()) return true;
        int count = 0;
        for (Balloon t : this.column){
            if (!t.isPopped()) count++;
        }
        if (count == 0) return true;
        return false;
    }
    
    public void pop(int aBalloon){
        this.column.get(aBalloon).pop();
    }
    
    public void squeeze(){
        if (this.isEmpty()) return;
        boolean sawPopped = false;
        for (Balloon t : this.column){
            if (t.isPopped()){
                sawPopped = true;
                continue;
            }
            if (sawPopped){
                Collections.swap(this.column, this.column.indexOf(t)-1, this.column.indexOf(t));
                squeeze();
                break;
            }
        }
    }
    
    private ArrayList<Balloon> column = new ArrayList<Balloon>(10);
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        BalloonColumn test = new BalloonColumn(8);
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
    }

}
