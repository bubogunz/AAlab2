package lab2.model;

public class Edge implements Comparable<Edge>{
    private Integer nodeA;
    private Integer nodeB;
    private Integer weight;

    public Edge(Integer nodeA, Integer nodeB, Integer weight) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = weight;
    }

    public Integer getNodeA() {
        return this.nodeA;
    }

    public void setNodeA(Integer nodeA) {
        this.nodeA = nodeA;
    }

    public Integer getNodeB() {
        return this.nodeB;
    }

    public void setNodeB(Integer nodeB) {
        this.nodeB = nodeB;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge e){
        if(weight > e.weight)
			return 1;
		else if(weight == e.weight)
			return 0;
		return -1;
    }

    @Override
    public String toString(){
        return "(" + getNodeA() + "," + getNodeB() + "; " + getWeight() + ")";
    }
}
