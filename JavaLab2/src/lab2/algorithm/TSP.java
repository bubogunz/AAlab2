package lab2.algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;
import lab2.model.AdjacentMatrix;
import lab2.model.Graph;

public class TSP {
	private HashMap<Pair<Integer, ArrayList<Integer>>, Integer> d = new HashMap<>();
	private HashMap<Pair<Integer, ArrayList<Integer>>, Integer> pi = new HashMap<>();
	private AdjacentMatrix w = null;
	
	public TSP(Graph g) {
		w = g.getAdjacentMatrix();
	}
	
//	public static Integer HeldKarp(Graph g) throws InterruptedException {
//		DistanceSet D = new DistanceSet(g.getDimension());
//		DistanceSet P = new DistanceSet(g.getDimension());
//		TreeSet<Integer> V = new TreeSet<Integer>();
//		for(int i = 0; i < g.getDimension(); i++)
//			V.add(i);
//		int result = HeldKarpCore(0, V, D, P, g);
//		System.out.println("D:\n" + D);
//		System.out.println("P:\n" + P);
//		return result;
//	}
//
//	private static Integer HeldKarpCore(Integer v, TreeSet<Integer> S, DistanceSet D, DistanceSet P, Graph g)
//			throws InterruptedException {
//		if(S.size() == 1 && S.contains(v))
//			return g.getAdjacentmatrixIndex(v, 0);
//		else if(D.getDistance(v, S) != null)
//			return D.getDistance(v, S);
//
//		Integer mindist = Integer.MAX_VALUE;
//		Integer minprec = null;
//		TreeSet<Integer> copy = (TreeSet<Integer>) S.clone();
//		copy.remove(v);
//		for(Integer u:copy){
//			Integer dist = HeldKarpCore(u, copy, D, P, g);
//			if(dist + g.getAdjacentmatrixIndex(u, v) < mindist){
//				mindist = dist + g.getAdjacentmatrixIndex(u, v);
//				minprec = u;
//			}
//		}
//		D.setDistance(v, S, mindist);
//		P.setDistance(v, S, minprec);
//		return mindist;
//	}
	
	public Integer HeldKarp(Graph g) {
		int size = g.getAdjacentMatrix().size();
		ArrayList<Integer> S = new ArrayList<Integer>(size-1);
		for(int i=1; i<size; ++i) {
			S.add(i);
		}
		
		int result = HeldKarpCore(0, S);
		return result;
	} 
	
	private ArrayList<Integer> deepCopyWithoutV(ArrayList<Integer> old, Integer v){
		ArrayList<Integer> ret = new ArrayList<Integer>(old.size());
		for(Integer item : old)
			if(!item.equals(v))
				ret.add(item);
		
		return ret;
	}
	
	private Integer HeldKarpCore(Integer v, ArrayList<Integer> S) {
		if(S.size() == 1 && S.contains(v)) 
			return w.get(v,0);
		Pair<Integer, ArrayList<Integer>> index = new Pair<Integer, ArrayList<Integer>>(v,S);
		if(d.containsKey(index)) 
			return d.get(index);
		Integer mindist = new Integer(Integer.MAX_VALUE);
		Integer minprec = null;
		
		ArrayList<Integer> S_new = deepCopyWithoutV(S, v);
		
		for(Integer u : S_new) {
			Integer dist = HeldKarpCore(u,S_new);
			if(Integer.sum(dist, w.get(u,v)) < mindist.intValue()) {
				mindist = new Integer(Integer.sum(dist, w.get(u, v)));
				minprec = new Integer(u);
			}
		}
		d.put(index, mindist);
		pi.put(index, minprec);
		
		return mindist;
	}
}