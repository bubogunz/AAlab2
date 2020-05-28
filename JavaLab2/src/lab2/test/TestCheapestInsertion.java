package lab2.test;

import lab2.algorithm.TSP;
import lab2.model.Graph;

public class TestCheapestInsertion {
    public static void test(){
        Graph g = new Graph(5);
        g.setAdjacentmatrixWeight(0, 1, 1);
        g.setAdjacentmatrixWeight(0, 2, 3);
        g.setAdjacentmatrixWeight(0, 3, 3);
        g.setAdjacentmatrixWeight(0, 4, 2);
        g.setAdjacentmatrixWeight(1, 2, 2);
        g.setAdjacentmatrixWeight(1, 3, 4);
        g.setAdjacentmatrixWeight(1, 4, 3);
        g.setAdjacentmatrixWeight(2, 3, 2);
        g.setAdjacentmatrixWeight(2, 4, 1);
        g.setAdjacentmatrixWeight(3, 4, 1);

        System.out.println(TSP.CheapestInsertion(g));
    } 
}