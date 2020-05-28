package lab2.test;

import java.util.TreeSet;

import lab2.model.DistanceSet;

public class TestDistanceSet {
    @SuppressWarnings("unchecked")
	public static void test() throws InterruptedException {
        DistanceSet matrix = new DistanceSet(5);
        System.out.println(matrix);
        
        //test della copia profonda di TreeSet
        TreeSet<Integer> v = new TreeSet<Integer>();
        for(int i = 0; i < 5; i++)
            v.add(i);
        TreeSet<Integer> tmp_v = (TreeSet<Integer>) v.clone();
        tmp_v.remove(0);

        matrix.setDistance(0, tmp_v, 3);
        matrix.setDistance(1, tmp_v, 5);
        
        System.out.println("Matrice inizializzata:\n" + matrix);
        System.out.println("d[0, v]: " + matrix.getDistance(0, tmp_v));
        System.out.println("d[1, v\\{0}]: " + matrix.getDistance(1, tmp_v));
    }
}