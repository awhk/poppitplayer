import java.util.*;
import simpoppit.gameboard.Coord;

/**
 * @author Andrew
 *
 */
public class test {
    
    public test(){
        Random rand = new Random();
        for (int i=0; i<100; i++){
            Coord temp = new Coord(rand.nextInt(100), rand.nextInt(100));
            set1.add(temp);
        }
    }
    
    private TreeSet<Coord> set1 = new TreeSet<Coord>();
    private ArrayList<Coord> list1 = new ArrayList<Coord>();
    private TreeSet<Coord> set2 = new TreeSet<Coord>();

    public static void main(String[] args) {
        Integer test = 5;
        Integer test2 = test;
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        
        list1.add(test);
        list2.add(test2);
        
        System.out.println(list1.get(0));
        System.out.println(list2.get(0));
        
        test = 9;
        
        System.out.println(list1.get(0));
        System.out.println(list2.get(0));

    }

}
