package lab2.model;

import java.util.ArrayList;

public class AdjacentMatrix{
    private ArrayList<ArrayList<Integer>> matrix;
    
    public AdjacentMatrix(int n){
        matrix = new ArrayList<ArrayList<Integer>>();
        /*dato che la matrice è quatrada e il grafo non orientato,
        creo una matrice quatrata trinagolare superiore inizializzando i valori a null
        tale matrice è costruita con arraylist di valore sempre più grande*/
        for(int i = 0; i < n - 1; i++){
            ArrayList<Integer> row = new ArrayList<Integer>(i + 1);
            matrix.add(row);
            for(int j = 0; j < i + 1; j++)
                row.add(null);
        }
    }

    public void set(int n, int m, int v){
        //impongo che n sia minore di m
        if(n > m){
            int tmp = n;
            n = m;
            m = tmp;
        }
        /*questo decremento è dovuto al fatto che, immaginando la matrice,
        la prima colonna ha tutti valori nulli, e dunque nella matrice costruita non viene
        contata, dunque si decrementa il valore delle colonne della matrce*/
        m--;
        try{
            matrix.get(m).set(n, v);
        }catch(IndexOutOfBoundsException e ){
            System.out.println("Selected a loop edge or out of bound endge");
        }
    }

    public Integer get(int n, int m){
        //impongo che n sia minore di m
        if(n > m){
            int tmp = n;
            n = m;
            m = tmp;
        }
        m--;
        try{
            return matrix.get(m).get(n);
        }catch(IndexOutOfBoundsException e ){
            System.out.println("Selected a loop edge or out of bound edge");
        }
        return null;
    }

    public Integer size(){
        return matrix.size() + 1;
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