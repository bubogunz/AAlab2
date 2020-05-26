package lab2.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lab2.algorithm.TSP;
import lab2.model.Distancies;
import lab2.model.Graph;
import lab2.test.TestDistanceSet;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		compute("TSP"); 
		// test();
	}

	/**
	 * @param algorithm = the algorithm to compute. This would be:
	 * - prim -> Prim algorithm with Priority queue data structure
	 * - naivekruskal -> Naive Kruskal algorithm
	 * - kruskal -> Kruskal algorithm with DisjoindSet data structure
	 * @throws InterruptedException
	 * 
	 * This function executes the algorithm choosen in 68 graphs that are
	 * builded with mst_dataset folder. The results are stored in a file with the name of
	 * algorithm choosen. 
	 */
	public static void compute(String algorithm) throws InterruptedException {
		// fetch files
		try (Stream<Path> walk = Files.walk(Paths.get("tsp_dataset"))) {
			List<String> tsp_dataset = walk.filter(Files::isRegularFile).map(x -> x.toString()).sorted()
					.collect(Collectors.toList());  
			final File outputPath ;
			
			switch (algorithm) {
				case "TSP":
					outputPath = new File("TSP.xml");
					break;
				case "Heuristic":
					outputPath = new File("Heuristic.xml");
					break;
				case "2Approx":
					outputPath = new File("2Approx.xml");
					break;
				default:
					throw new InvalidParameterException("Wrong choice of algorithm");
			}
							
			FileWriter fw = new FileWriter(outputPath, false);

			System.out.println("Executing " + algorithm + " algorithm");

			// tsp_dataset.stream().forEach(entryset -> {
				try {
					String entryset = tsp_dataset.get(5);
					System.out.println("Input: " + entryset);
					int cost = 0;
					String buffer = new String("File:" + entryset + "\n");

					File myObj = new File(entryset);
					Scanner myReader = new Scanner(myObj);
					String line = myReader.nextLine();
					
					while(myReader.hasNextLine() && !line.split(" ")[0].equals("DIMENSION:"))
						line = myReader.nextLine();
					Integer size_graph = Integer.valueOf(line.split(" ")[1]);
					
					Graph graph = new Graph(size_graph);
					//ESEMPIO del pdf
					// Graph graph = new Graph(size_graph);
					double[][] nodes = new double[size_graph][2];

					line = myReader.nextLine();
					String mode = line.split(" ")[1];

					while(myReader.hasNextLine() && !line.equals("NODE_COORD_SECTION"))
						line = myReader.nextLine();
					
					for(int i = 0; i < size_graph && myReader.hasNextLine() && !line.equals("EOF"); i++) {
						line = myReader.nextLine();
						String[] data = line.split(" ");
						nodes[i][0] = Double.valueOf(data[1]);
						nodes[i][1] = Double.valueOf(data[2]);
					}

					myReader.close();
					
					//ESEMPIO del pdf (commentare però il for)
					// graph.setAdjacentmatrixIndex(0, 1, 4);
					// graph.setAdjacentmatrixIndex(0, 2, 1);
					// graph.setAdjacentmatrixIndex(0, 3, 3);
					// graph.setAdjacentmatrixIndex(1, 2, 2);
					// graph.setAdjacentmatrixIndex(1, 3, 1);
					// graph.setAdjacentmatrixIndex(2, 3, 5);
					for(int i = 0; i < size_graph; i++)
						for(int j = i + 1; j < size_graph; j++){
							switch (mode){
								case "EUC_2D":
									graph.setAdjacentmatrixIndex(i, j, Distancies.euclidean(nodes[i][0], nodes[i][1], nodes[j][0], nodes[j][1]));
									break;
								case "GEO":
									graph.setAdjacentmatrixIndex(i, j, Distancies.geo(nodes[i][0], nodes[i][1], nodes[j][0], nodes[j][1]));
									break;
								default:
							}
						}

					// System.out.println("Matrice di adiacenza:\n" + graph.printAdjacentmatrix());
					long start = System.nanoTime();
					switch (algorithm){
						case "TSP":
							cost = TSP.HeldKarp(graph);
							break;
						case "Heuristic":
							cost = 0;
							break;
						case "2Approx":
							cost = 0;
							break;
						default:
							throw new InvalidParameterException("Wrong choice of algorithm");	
					}

					System.out.println(cost);

				} catch (FileNotFoundException e) {
				}
			//  });
			fw.close();
			System.out.println("Finish!");
			// test(algorithm);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void test() throws InterruptedException {
		TestDistanceSet.test();
	}
}
