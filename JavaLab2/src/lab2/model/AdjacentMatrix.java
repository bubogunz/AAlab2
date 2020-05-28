package lab2.model;

import java.util.ArrayList;

public class AdjacentMatrix{
    private ArrayList<ArrayList<Integer>> matrix;

    public AdjacentMatrix(int n){
        matrix = new ArrayList<ArrayList<Integer>>();
       
        n--;
        for(int i=0; i<n; ++i) {
        	ArrayList<Integer> row = new ArrayList<Integer>(n - i);
        	for(int j=0; j<n-i; ++j) 
        		row.add(null);
        	
        	matrix.add(row);
        }
    }

    public void set(int n, int m, int v){
    	if(n != m) {
    		if(n > m)
    			setUtil(m, n, v);
    		else
    			setUtil(n, m, v);
    	}
    }
    
    private void setUtil(int n, int m, int v) {
    	matrix.get(n).set(m - (n + 1), v);
    }

    public Integer get(int n, int m){
    	if(n == m)
    		return 0;
    	return n > m ? getUtil(m, n)
    			: getUtil(n, m);
    	
    }
    
    private Integer getUtil(int n, int m) {
    	return matrix.get(n).get(m - (n+1));
    }

    public Integer size(){
        return matrix.size() + 1;
    }

    public Integer getMaxAdjacentVertexWeightIndex(int n){
        Integer max = -1;
        for(int i = 0; i < size(); i++){
            Integer tmp = get(n, i);
            if(tmp != null && i != n && tmp > max)
                max = i;
        }
        if(max == -1)
            return null;
        return max;
    }

    public Integer getMinAdjacentVertexWeightIndex(int n){
        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < size(); i++){
            Integer tmp = get(n, i); 
            if(tmp != null && n != i && tmp < min)
                min = i;
        }
        if(min == Integer.MAX_VALUE)
            return null;
        return min;
    }

    public ArrayList<Edge> getEdges(){
        ArrayList<Edge> result = new ArrayList<Edge>();
        for(int i = 1; i < size(); i++)
            for(int j = 0; j < i; j++)
                result.add(new Edge(j, i, get(j, i)));

        return result;
    }

    public static AdjacentMatrix copy(AdjacentMatrix m){
        AdjacentMatrix tmp = new AdjacentMatrix(m.size());
        for(int i = 0; i < m.size(); i++){
            ArrayList<Integer> row = new ArrayList<Integer>(i + 1);
            tmp.matrix.add(row);
            for(int j = 0; j < i + 1; j++)
                row.add(m.get(i + 1, j));
        }
        return tmp;
    }

    @Override
    public String toString(){
        String tmp = "";
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++)
                tmp += get(i, j) + "\t";
            tmp += "\n";
        }
        return tmp;
    }
}