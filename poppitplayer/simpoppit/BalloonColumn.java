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

import java.util.*;

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
    
    public Balloon.Color Color(int aBalloon){
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
    
    public void Pop(int aBalloon){
        this.column.get(aBalloon).Pop();
    }
    
    public void Squeeze (){
        if (this.isEmpty()) return;
        boolean sawPopped = false;
        for (Balloon t : this.column){
            if (t.isPopped()){
                sawPopped = true;
                continue;
            }
            if (sawPopped){
                Collections.swap(this.column, this.column.indexOf(t)-1, this.column.indexOf(t));
                Squeeze();
                break;
            }
        }
    }
    
    private ArrayList<Balloon> column = new ArrayList<Balloon>(10);
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        BalloonColumn test = new BalloonColumn();
        System.out.println(test);
        System.out.println("Popping all balloons in column...");
        for (int i=0; i<test.count(); i++){
            test.Pop(i);
        }
        System.out.print("Column empty?  ");
        System.out.println(test.isEmpty());
        System.out.println("Making new column...");
        test = new BalloonColumn();
        System.out.println(test);
        System.out.println("Popping balloon 5...");
        test.Pop(4);
        System.out.println(test);
        System.out.println("Squeezing...");
        test.Squeeze();
        System.out.println(test);
        System.out.println("Popping balloons 2 and 3...");
        test.Pop(1);
        test.Pop(2);
        System.out.println(test);
        System.out.println("Squeezing...");
        test.Squeeze();
        System.out.println(test);
    }

}
