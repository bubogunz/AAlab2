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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lab2.algorithm.TSP;
import lab2.model.Distancies;
import lab2.model.Graph;
import lab2.test.TestKruskal;
import lab2.test.TestTSP;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		//dare l'opzione -Xmx8192m per dire alla JVM di riservare 8GB di RAM (serve a HeldKarp) 
		// printHeapInfo();
		
		compute("2Approx"); 
		// test();
	}

	public static void printHeapInfo() {
		
		long heapSize = Runtime.getRuntime().totalMemory(); 
		
		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();

		// Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
		long heapFreeSize = Runtime.getRuntime().freeMemory(); 

		System.out.println("heapsize " + formatSize(heapSize));
		System.out.println("heapmaxsize " + formatSize(heapMaxSize));
		System.out.println("heapFreesize " + formatSize(heapFreeSize));
		System.out.println("usedHeap " + formatSize(heapMaxSize - heapFreeSize));
	}

	public static String formatSize(long v) {
		if (v < 1024) return v + " B";
		int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
		return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
	}

	/**
	 * @param algorithm = the algorithm to compute. This would be:
	 * - HeldKarp -> HeldKarp algorithm to get correct solution of TSP problem
	 * - Heuristic -> CheapestInsertion heuristic for nlogn approximation of TSP problem
	 * - 2Approx -> 2-approximation algorithm using MST structure
	 * @throws InterruptedException
	 * 
	 * This function executes the algorithm choosen in 68 graphs that are
	 * builded with mst_dataset folder. The results are stored in a file with the name of
	 * algorithm choosen. 
	 */
	public static void compute(String algorithm) throws InterruptedException {
		int minutes = 1;
		// fetch files
		try (Stream<Path> walk = Files.walk(Paths.get("tsp_dataset"))) {
			List<String> tsp_dataset = walk.filter(Files::isRegularFile).map(x -> x.toString()).sorted()
					.collect(Collectors.toList());  
			final File outputPath;

			switch (algorithm) {
			case "HeldKarp":
				outputPath = new File("HeldKarp.txt");
				break;
			case "Heuristic":
				outputPath = new File("Heuristic.txt");
				break;
			case "2Approx":
				outputPath = new File("2Approx.txt");
				break;
			default:
				throw new InvalidParameterException("Wrong choice of algorithm");
			}

			FileWriter fw = new FileWriter(outputPath, false);
			fw.write("Dataset\tSolution\tTime(s)\tError(%)\n");
			boolean testResult = true;

			System.out.println("Executing " + algorithm + " algorithm");

			for(int k = 0; k < tsp_dataset.size(); k++){
			try {
				int example = k;
				String entryset = tsp_dataset.get(example);
				System.out.println("Input: " + entryset);
				int cost = 0;

				File myObj = new File(entryset);
				Scanner myReader = new Scanner(myObj);
				String line = myReader.nextLine();
				String name = line.split(" ")[1];

				while(myReader.hasNextLine() && !line.split(" ")[0].equals("DIMENSION:"))
					line = myReader.nextLine();
				Integer size_graph = Integer.valueOf(line.split(" ")[1]);
				
				Graph graph = new Graph(size_graph);
				
				double[][] nodes = new double[size_graph][2];

				line = myReader.nextLine();
				String[] modalities = line.split(" ");
				String mode = "";
				for(int i=0; i<modalities.length; ++i) {
					if(modalities[i].equals("EUC_2D"))
						mode = "EUC_2D";
					else if(modalities[i].equals("GEO"))
						mode = "GEO";
				}

				while(myReader.hasNextLine() && !line.equals("NODE_COORD_SECTION"))
					line = myReader.nextLine();

				for(int i = 0; i < size_graph && myReader.hasNextLine() && !line.equals("EOF"); i++) {
					line = myReader.nextLine();
					String[] data = line.split(" ");
					nodes[i][0] = Double.valueOf(data[1]);
					nodes[i][1] = Double.valueOf(data[2]);
				}

				myReader.close();

				for(int i = 0; i < size_graph; i++){
					for(int j = i + 1; j < size_graph; j++){
						switch (mode){
						case "EUC_2D":
							graph.setAdjacentmatrixWeight(i, j, Distancies.euclidean(nodes[i][0], nodes[i][1], nodes[j][0], nodes[j][1]));
							break;
						case "GEO":
							graph.setAdjacentmatrixWeight(i, j, Distancies.geo(nodes[i][0], nodes[i][1], nodes[j][0], nodes[j][1]));
							break;
						default:
						}
					}
				}
				
				long start = System.nanoTime();

				TSP tsp = new TSP(graph);

				long stop = 0;

				switch (algorithm){
					case "HeldKarp":
					
					ExecutorService executor = Executors.newCachedThreadPool();
					Future<Void> future  = executor.submit(new Callable<Void>() {
						@Override

						public Void call() throws Exception {
							tsp.HeldKarp();
							return null;

						}
					});
					//does not brutally shut down the program, it waits until the last l computation is finished
					executor.shutdown();
					
					if(entryset.contains("14.tsp") || entryset.contains("16.tsp") || entryset.contains("22.tsp"))
						for(int i=0; i<minutes*1200 && !future.isDone(); ++i)
							Thread.sleep(50);
					else 
						Thread.sleep(30000);
				
					//Sets the "interrupted flag" that will break computation of the thread gracefully
					future.cancel(true);
					stop = System.nanoTime();
					long timeElapsed = stop - start;
					double time = timeElapsed;
					time = time / 1000000000;
					System.out.println("Stopped in " + time + "s");
					executor.awaitTermination(1, TimeUnit.DAYS);

					cost = tsp.getResult();
					break;
				case "Heuristic":
					cost = tsp.CheapestInsertion();
					break;
				case "2Approx":
					cost = tsp.TriangleTSP();
					break;
				default:
					throw new InvalidParameterException("Wrong choice of algorithm");	
				}
				
				if(stop == 0)
					stop = System.nanoTime();

				long timeElapsed = stop - start;
				double time = timeElapsed;
				time = time / 1000000000;

				fw.write(name + "\t" + cost + "\t" + time + "\t");

				if(!TestTSP.test(algorithm, example, cost, graph.getDimension(), fw))
					testResult = false;
				
				printHeapInfo();
				System.out.println("Time elapsed: " + time + "s");
				System.out.println("cost: " + cost);
				System.out.println();
			} catch (FileNotFoundException e) {}
			//garbage collector
			System.gc();
			}
			//  });
			fw.close();
			if(testResult)
				System.out.println("All tests passed!");
			else
				System.out.println("Some tests not passed.");
			System.out.println("Finish!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void test(){
		TestKruskal.test();
	}
}
