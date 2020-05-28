package lab2.test;

import java.util.ArrayList;
import java.util.Collections;

import lab2.model.AdjacentMatrix;
import lab2.model.Edge;

public class TestAdjacentMatrix {
    public static void test(){
        AdjacentMatrix matrix = new AdjacentMatrix(5);
        for(int i = 0; i < matrix.size(); i++)
            for(int j = i + 1; j < matrix.size(); j++)
                matrix.set(i, j, i + j);

        System.out.println(matrix);
        System.out.println(matrix.getMinAdjacentVertexWeightIndex(3));
        System.out.println(matrix.getMaxAdjacentVertexWeightIndex(3));
        ArrayList<Edge> edge = matrix.getEdges();
        System.out.println(edge);
        Collections.sort(edge);
        System.out.println(edge);
    }
}