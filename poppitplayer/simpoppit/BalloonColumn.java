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
        for (int i=0; i<10; i++){
            this.myColumn.add(new Balloon());
        }
    }
    
    public BalloonColumn(int aLength){
        this.myColumn.ensureCapacity(aLength);
        for (int i=0; i<aLength; i++){
            this.myColumn.add(new Balloon());
        }
    }
    
    public String toString() {
        String result = "";
        for (Balloon t : this.myColumn){
            result += this.myColumn.indexOf(t) +  1;
            result += " has color ";
            result += t.color();
            result += "\n";
        }
        return result;
    }
    
    private ArrayList<Balloon> myColumn = new ArrayList<Balloon>(10);
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        BalloonColumn test = new BalloonColumn();
        System.out.println(test);
    }

}
