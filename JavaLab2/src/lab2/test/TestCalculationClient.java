package lab2.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestCalculationClient {
	public void test() {
		List<Integer> integerCalculation = new ArrayList<>();
		ExecutorService executor = Executors.newSingleThreadExecutor();
		List<Future<Integer>> futuresList = new ArrayList<>();

		for(int i = 0; i < 2; ++i) {
			Callable<Integer> task = new CalculationTask(new ArrayList<String>(i));
			Future<Integer> future = executor.submit(task);
			futuresList.add(future);
		}
		for (Future<Integer> future : futuresList) { 
			Integer calculationResult = null;
			try {
				calculationResult = future.get(3, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				System.out.println("wtf");
//				e.printStackTrace();
			}
			integerCalculation.add(calculationResult);
		}
		executor.shutdown();
		futuresList.stream().forEach(future -> future.cancel(true));
	}
}
