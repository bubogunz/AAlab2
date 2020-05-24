package lab2.model;

public final class Graph {

    private AdjacentMatrix adjacentmatrix;

	public Graph(int n) {
		super();
        this.adjacentmatrix = new AdjacentMatrix(n);
	}
	
	//shallow copy
	public Graph(Graph graph) {
        this.adjacentmatrix = AdjacentMatrix.copy(graph.adjacentmatrix);
	}

    public AdjacentMatrix getAdjacentMatrix(){
        return adjacentmatrix;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adjacentmatrix == null) ? 0 : adjacentmatrix.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Graph other = (Graph) obj;
		if (adjacentmatrix == null) {
			if (other.adjacentmatrix!= null)
				return false;
		} else if (!adjacentmatrix.equals(other.adjacentmatrix))
			return false;
		return true;
	}

	public int getDimension() {
		return adjacentmatrix.size();
	}
}
