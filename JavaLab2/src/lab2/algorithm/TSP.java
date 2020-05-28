package lab2.algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import javafx.util.Pair;
import lab2.model.AdjacentMatrix;
import lab2.model.Graph;

public class TSP {
	private HashMap<Pair<Integer, ArrayList<Integer>>, Integer> d = new HashMap<>();
	private HashMap<Pair<Integer, ArrayList<Integer>>, Integer> pi = new HashMap<>();
	private AdjacentMatrix w = null;
//	private Integer result = new Integer(0);

	public TSP(Graph g) {
		w = g.getAdjacentMatrix();
	}
	
	public Integer getResult() {
		ArrayList<Integer> S = new ArrayList<Integer>(w.size()-1);
		for(int i=1; i<w.size(); ++i) 
			S.add(i);
		Pair<Integer, ArrayList<Integer>> key = new Pair<Integer, ArrayList<Integer>>(0, S);
		Integer res = d.get(key);
		return res;
	}

	public Integer HeldKarp(Graph g) {
		int size = g.getAdjacentMatrix().size();
		ArrayList<Integer> S = new ArrayList<Integer>(size-1);
		for(int i=1; i<size; ++i) {
			S.add(i);
		}
		try {
			int result = HeldKarpCore(0, S);
			return result;
		}catch (OutOfMemoryError e) {
			printHeapInfo();
			System.out.println();
		}
		return null;
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
			if(Thread.currentThread().isInterrupted()) {
				System.out.println("Timeout reached.");
				break;
			}
		}
		d.put(index, mindist);
		pi.put(index, minprec);

		return mindist;
	}

	public static void printHeapInfo() {

		long heapSize = Runtime.getRuntime().totalMemory(); 

		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();

		// Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
		long heapFreeSize = Runtime.getRuntime().freeMemory(); 

		System.out.println("heapsize " + formatSize(heapSize));
		System.out.println("heapmaxsize " + formatSize(heapMaxSize));
		System.out.println("heapFreesize " + formatSize(heapFreeSize));
	}

	public static String formatSize(long v) {
		if (v < 1024) return v + " B";
		int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
		return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
	}
}