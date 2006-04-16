package poppitplayer.simplesearch;
import java.util.*;

import ec.util.MersenneTwisterFast;
import simpoppit.gameboard.*;

/**
 * @author Andrew
 *
 */
public class test {
    
    public test(){
        MersenneTwisterFast rand = new MersenneTwisterFast();
        for (int i=0; i<10; i++){
//            Coord temp = new Coord(rand.nextInt(100), rand.nextInt(100));
            GameInterface temp = new GameInterface();
            set1.add(temp);
            set2.add(temp);
//            list1.add(temp);
//            list2.add(temp);
//            list1.add(temp);
//            list2.add(temp);
        }
    }
    
    private TreeSet<GameInterface> set1 = new TreeSet<GameInterface>();
    private ArrayList<GameInterface> list1 = new ArrayList<GameInterface>();
    private TreeSet<GameInterface> set2 = new TreeSet<GameInterface>();
    private ArrayList<GameInterface> list2 = new ArrayList<GameInterface>();

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
