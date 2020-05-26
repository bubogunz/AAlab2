package lab2.test;

import java.util.TreeSet;

import lab2.model.DistanceSet;

public class TestDistanceSet {
    public static void test(){
        DistanceSet matrix = new DistanceSet(5);
        System.out.println(matrix);
        
        //test della copia profonda di TreeSet
        TreeSet<Integer> v = new TreeSet<Integer>();
        for(int i = 0; i < 5; i++)
            v.add(i);
        TreeSet<Integer> tmp_v = (TreeSet<Integer>) v.clone();
        tmp_v.remove(0);
        
        System.out.println(v);
        System.out.println(tmp_v);

        matrix.setDistance(0, tmp_v, 3);
        System.out.println(matrix.getDistance(0, tmp_v));
    }
}