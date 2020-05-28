package lab2.model;

import java.util.ArrayList;

public class AdjacentMatrix{
    private ArrayList<ArrayList<Integer>> matrix;
    private int size;
    
    public AdjacentMatrix(int n){
        matrix = new ArrayList<ArrayList<Integer>>();
        size = n;
        /*dato che la matrice è quatrada e il grafo non orientato,
        creo una matrice quatrata trinagolare superiore inizializzando i valori a null
        tale matrice è costruita con arraylist di valore sempre più grande*/
//        for(int i = 0; i < n - 1; i++){
//            ArrayList<Integer> row = new ArrayList<Integer>(i + 1);
//            matrix.add(row);
//            for(int j = 0; j < i + 1; j++)
//                row.add(null);
//        }
        n--;
        for(int i=0; i<n; ++i) {
        	ArrayList<Integer> row = new ArrayList<Integer>(n - i);
        	for(int j=0; j<n-i; ++j) {
        		row.add(null);
        	}
        	matrix.add(row);
        }
        System.out.println();
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

    @Override
    public String toString(){
        String tmp = "";
        for(int i = 0; i < matrix.size(); i++){
            for(int j = 0; j < i + 1; j++)
                tmp += 0 + "\t";
            for(int j = i + 1; j < matrix.size(); j++)
                tmp += matrix.get(j).get(i) + "\t";
            tmp += "\n";
        }
        return tmp;
    }
}