import java.util.*;

/**
 * @author Andrew
 *
 */
public class test {

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
