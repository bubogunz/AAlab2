package lab2.model;

public final class Graph {

	private AdjacentMatrix adjacentMatrix;

	public Graph(int n) {
		super();
		adjacentMatrix = new AdjacentMatrix(n);
	}

    public AdjacentMatrix getAdjacentMatrix(){
        return adjacentMatrix;
	}

	public int getAdjacentmatrixIndex(int x, int y) {
		return adjacentMatrix.get(x, y);
	}

	public void setAdjacentmatrixIndex(int x, int y, int value) {
		adjacentMatrix.set(x, y, value);
	}

	public String printAdjacentmatrix() {
		String tmp = "";
		for (int i = 0; i < adjacentMatrix.size(); i++) {
			for(int j = 0; j < i + 1; j++)
				tmp += 0 + "\t";
			for (int j = i + 1; j < adjacentMatrix.size(); j++)
				tmp += adjacentMatrix.get(i, j) + "\t";
			tmp += "\n";
		}
		return tmp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adjacentMatrix == null) ? 0 : adjacentMatrix.hashCode());
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
		if (adjacentMatrix == null) {
			if (other.adjacentMatrix != null)
				return false;
		} else if (!adjacentMatrix.equals(other.adjacentMatrix))
			return false;
		return true;
	}

	public int getDimension() {
		return adjacentMatrix.size();
	}
}
