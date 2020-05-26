package lab2.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

public class DistanceSet {
    private Integer[][] matrix;
    private ArrayList<TreeSet<Integer>> sets;
    private Integer final_distance;

    public DistanceSet(int n){
        //nella matrice non considero il vertice 0, perciò
        //la mia matrice avrà lunghezza n - 1
        int[] tmp = new int[n - 1];
        sets = new ArrayList<TreeSet<Integer>>();
        //tmp contiene i vertici da 1 a n
        for(int i = 0; i < n - 1; i++)
            tmp[i] = i + 1;
        for(int i = 2; i < n; i++)
            createCombinations(tmp, tmp.length, i);
        matrix = new Integer[n - 1][sets.size()];
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

    public Integer getDistance(int u, TreeSet<Integer> S)throws InterruptedException{
        int y = sets.indexOf(S);
        if(u == 0)
            return final_distance;
        if(y == -1 || u < 0 || u > matrix.length)
            throw new InvalidParameterException("Wrong vertex or set parameter. SetIndex: " + y + "/" + sets.size() + " Vertex: " + u + "/" + matrix.length);
        return matrix[u - 1][y];
    }

    public void setDistance(int u, TreeSet<Integer> S, int value) throws InterruptedException{
        int y = sets.indexOf(S);
        if(u == 0){
            final_distance = value;
            return;
        }
        if(y == -1 || u < 0 || u > matrix.length)
            throw new InvalidParameterException("Wrong vertex or set parameter. SetIndex: " + y + "/" + sets.size() + " Vertex: " + u + "/" + matrix.length);
        matrix[u - 1][y] = value;
        return;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DistanceSet)) {
            return false;
        }
        DistanceSet distanceSet = (DistanceSet) o;
        return Objects.equals(matrix, distanceSet.matrix) && Objects.equals(sets, distanceSet.sets) && final_distance == distanceSet.final_distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrix, sets, final_distance);
    }

    @Override
    public String toString() {
        String tmp = "\t";
        for(int i = 0; i < sets.size(); i++)
            tmp += sets.get(i).toString() + "\t";
        tmp += "\n";
        for(int i = 0; i < matrix.length; i++){
            tmp += (i + 1) + "\t";
            for(int j = 0; j < matrix[0].length; j++)
                tmp += matrix[i][j] + "\t";
            tmp += "\n";
        }
        tmp += "d[0, V]: " + final_distance;
        return tmp;
    }

}