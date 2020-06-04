package lab2.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.util.Pair;
import lab2.model.AdjacentMatrix;
import lab2.model.DisjointSet;
import lab2.model.Edge;
import lab2.model.Graph;
import lab2.model.Node;

public class TSP {
	private HashMap<Pair<Integer, ArrayList<Integer>>, Integer> d = new HashMap<>();
	private HashMap<Pair<Integer, ArrayList<Integer>>, Integer> pi = new HashMap<>();
	private AdjacentMatrix w = null;

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

	/**
	 * Finds the exact solution for TSP problem in O(n^2*2^n)
	 * @return cost of path of the solution
	*/
	public void HeldKarp() {
		int size = w.size();
		ArrayList<Integer> S = new ArrayList<Integer>(size-1);
		for(int i=1; i<size; ++i) {
			S.add(i);
		}
		try {
			HeldKarpCore(0, S);
		}catch (OutOfMemoryError e) {
			printHeapInfo();
			System.out.println("Need more memory!");
			System.out.println();
		}
	} 

	private Integer HeldKarpCore(Integer v, ArrayList<Integer> S){
		if(S.size() == 1 && S.contains(v)) 
			return w.get(v,0);
		Pair<Integer, ArrayList<Integer>> index = new Pair<Integer, ArrayList<Integer>>(v,S);
		if(d.containsKey(index)) 
			return d.get(index);
		Integer mindist = Integer.MAX_VALUE;
		Integer minprec = null;

		ArrayList<Integer> S_new = copyWithoutV(S, v);

		for(Integer u : S_new) {
			Integer dist = HeldKarpCore(u,S_new);
			if(Integer.sum(dist, w.get(u,v)) < mindist.intValue()) {
				mindist = Integer.sum(dist, w.get(u, v));
				minprec = Integer.valueOf(u);
			}
			if(Thread.currentThread().isInterrupted()) {
				break;
			}
		}
		d.put(index, mindist);
		pi.put(index, minprec);

		return mindist;
	}


	private ArrayList<Integer> copyWithoutV(ArrayList<Integer> old, Integer v){
		ArrayList<Integer> ret = new ArrayList<Integer>(old.size());
		for(Integer item : old)
			if(!item.equals(v)) 
				ret.add(item);
			
		return ret;
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

	/**
	 * Finds the 2-approximate solution for TSP problem in O(n^3)
	 * @return cost of path of the solution
	*/
	public int CheapestInsertion(){
        ArrayList<Integer> path = new ArrayList<Integer>(w.size() + 1);
		ArrayList<Integer> notVisited = new ArrayList<Integer>(w.size());
		for(int i = 0; i < w.size(); i++)
			notVisited.add(i, i);
        int cost = 0;

        Integer nextNode = w.getMinAdjacentVertexWeightIndex(0);
        
        path.add(0);
        notVisited.remove(Integer.valueOf(0));
        path.add(nextNode);
        notVisited.remove(nextNode);
        path.add(0);

        for(int i = 0; i < w.size() - 2; i++){
            Integer tmpNode = 0;
            int minCost = Integer.MAX_VALUE;
            for(Integer nodeK : notVisited){
                for(int j = 0; j < path.size() - 1; j++){
					int tmpCost = w.get(path.get(j), nodeK)
					+ w.get(nodeK, path.get(j + 1)) - w.get(path.get(j), path.get(j + 1));
					if(tmpCost < minCost){
						tmpNode = j;
						nextNode = nodeK;
						minCost = tmpCost;
					}
				}
            }
            path.add(tmpNode + 1, nextNode);
            notVisited.remove(nextNode);
        }

        for(int i = 0; i < path.size() - 1; i++)
            cost += w.get(path.get(i), path.get(i + 1));

        return cost;
    }

	/**
	 * Finds the 2-approximate solution for TSP problem in O(mlog n + n)
	 * @return cost of path of the solution
	*/
    public int TriangleTSP(){
        Node tree = Kruskal();//O(mlog n)
        int cost = 0;
        ArrayList<Integer> path = preorder(tree, new ArrayList<Integer>());//O(n)
        path.add(0);

        for(int i = 0; i < path.size() - 1; i++)
            cost += w.get(path.get(i), path.get(i + 1));

        return cost;
    }

    private static ArrayList<Integer> preorder(Node tree, ArrayList<Integer> arr){
        arr.add(tree.getID());
        if(!tree.isLeaf())
            for(int i = 0; i < tree.getAdjacentNodes().size(); i++)
                    preorder(tree.getAdjacentNodes().get(i), arr);

        return arr;
    }

    private Node Kruskal(){
        AdjacentMatrix mst= new AdjacentMatrix(w.size());
		ArrayList<Edge> sortedEdges = w.getEdges();
		Collections.sort(sortedEdges);

		//O(n)
		DisjointSet ds = new DisjointSet(w.size());
		
		//O(mlog n)
        for(Edge edge : sortedEdges){//O(m)
            int nodeA = edge.getNodeA();
            int nodeB = edge.getNodeB(); 
			if(ds.find(nodeA) != ds.find(nodeB)){//O(log n)
				mst.set(nodeA, nodeB, 1);
				ds.union(nodeA, nodeB);//O(log n)
			}
        }
        Node root = new Node(0, null);
		return buildTree(mst, root, root.getFather());
    }
    
    private static Node buildTree(AdjacentMatrix matrix, Node v, Node father){
        for(int i = 0; i < matrix.size(); i++)
			if(matrix.get(i, v.getID()) != null && matrix.get(i, v.getID()) != 0 &&
				(v.getFather() == null || i != v.getFather().getID())){
                Node children = new Node(i, v);
                v.addAdjacentNode(children);
                buildTree(matrix, children, children.getFather());
            }
        return v;
    }

}