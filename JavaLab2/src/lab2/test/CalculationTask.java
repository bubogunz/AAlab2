package lab2.test;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CalculationTask implements Callable<Integer> {
	ArrayList<String> result = new ArrayList<>();
	
	public CalculationTask(ArrayList<String> result) {
		super();
		result.stream().forEach(item -> this.result.add(item));
	}

	private Integer test() {
		for(int i = 0; i<Integer.MAX_VALUE; ++i) {
			Long start = System.nanoTime();
			Long end = System.nanoTime();
			while((end - start)/1000000000 < 1) 
				end = System.nanoTime();	
			System.out.println("Hello!");
			result.add("Hello!");
		}
		return result.size();
	}
	
	@Override
	public Integer call() throws Exception {
		return test();
	}
}

