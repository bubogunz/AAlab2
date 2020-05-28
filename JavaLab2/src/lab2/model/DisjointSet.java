package lab2.model;

import java.util.ArrayList;

public class DisjointSet{
    private ArrayList<Integer> parents = new ArrayList<Integer>();
    private ArrayList<Integer> ranks = new ArrayList<Integer>();

    //Creates n subset wuth one element di ascending order. O(n)
    public DisjointSet(Integer n){
        parents = new ArrayList<Integer>(n);
        ranks = new ArrayList<Integer>(n);
        for(int i = 0; i < n; i++){
            parents.add(i);
            ranks.add(0);
        }
    }

    public ArrayList<Integer> getParents(){
        return parents;
    }

    public ArrayList<Integer> getRanks(){
        return ranks;
    }

    //finds the subset to witch n-element belongs. O(log n)
    public Integer find(Integer n){
        if(parents.get(n) != n)
            parents.set(n, find(parents.get(n)));
        return parents.get(n);
    }

    //Joins subsets of x-element and y-element. O(log n)
    public void union(Integer x, Integer y){
        Integer xRoot = find(x);//O(log n)
        Integer yRoot = find(y);//O(log n)

        if(xRoot == yRoot)
            return;

        if(ranks.get(xRoot) < ranks.get(yRoot)){
            Integer tmp = xRoot;
            xRoot = yRoot;
            yRoot = tmp;
        }

        parents.set(yRoot, xRoot);
        if(ranks.get(xRoot) == ranks.get(yRoot))
            ranks.set(xRoot, xRoot + 1);
    }
}