package lab2.model;

import java.util.ArrayList;
import java.util.TreeSet;

public class DistanceSet {
    private Integer[][] matrix;
    private ArrayList<TreeSet<Integer>> sets;
    int final_distance;

    public DistanceSet(int n){
        int[] tmp = new int[n - 1];
        sets = new ArrayList<TreeSet<Integer>>();
        for(int i = 0; i < n - 1; i++)
            tmp[i] = i + 1;
        for(int i = 2; i < n; i++)
            createCombinations(tmp, tmp.length, i);
        matrix = new Integer[n][sets.size()];
    }

    private void combinationUtil(int arr[], int data[], int start, 
                                int end, int index, int r){ 
        if (index == r){
            TreeSet<Integer> newset = new TreeSet<Integer>();
            sets.add(newset);
            for (int j=0; j<r; j++) 
                newset.add(data[j]);
            return; 
        } 
  
        for (int i=start; i<=end && end-i+1 >= r-index; i++){ 
            data[index] = arr[i]; 
            combinationUtil(arr, data, i+1, end, index+1, r); 
        } 
    } 
  
    private void createCombinations(int arr[], int n, int r){
        int data[]=new int[r]; 
  
        combinationUtil(arr, data, 0, n-1, 0, r); 
    }

    public Integer getDistance(int u, TreeSet<Integer> S){
        int y = sets.indexOf(S);
        if(y == -1)
            return -1;
        if(u == 0)
            return final_distance;
        return matrix[u][y];
    }

    public void setDistance(int u, TreeSet<Integer> S, int value){
        int y = sets.indexOf(S);
        if(y == -1)
            return;
        if(u == 0){
            final_distance = value;
            return;
        }
        matrix[u][y] = value;
    }
}