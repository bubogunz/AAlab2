package lab2.algorithm;

import java.util.TreeSet;

import lab2.model.DistanceSet;
import lab2.model.Graph;

public class TSP {
    public static Integer HeldKarp(Graph g) throws InterruptedException {
        DistanceSet D = new DistanceSet(g.getDimension());
        DistanceSet P = new DistanceSet(g.getDimension());
        TreeSet<Integer> V = new TreeSet<Integer>();
        for(int i = 0; i < g.getDimension(); i++)
            V.add(i);
        int result = HeldKarpCore(0, V, D, P, g);
        // System.out.println("D:\n" + D);
        // System.out.println("P:\n" + P);
        return result;
    }
    
    private static Integer HeldKarpCore(Integer v, TreeSet<Integer> S, DistanceSet D, DistanceSet P, Graph g)
            throws InterruptedException {
        if(S.size() == 1 && S.contains(v))
            return g.getAdjacentmatrixIndex(v, 0);
        else if(D.getDistance(v, S) != null)
            return D.getDistance(v, S);

        Integer mindist = Integer.MAX_VALUE;
        Integer minprec = null;
        TreeSet<Integer> copy = (TreeSet<Integer>) S.clone();
        copy.remove(v);
        for(Integer u:copy){
            Integer dist = HeldKarpCore(u, copy, D, P, g);
            if(dist + g.getAdjacentmatrixIndex(u, v) < mindist){
                mindist = dist + g.getAdjacentmatrixIndex(u, v);
                minprec = u;
            }
        }
        D.setDistance(v, S, mindist);
        P.setDistance(v, S, minprec);
        return mindist;
    }
    
}