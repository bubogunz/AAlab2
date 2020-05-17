package lab2.main;

import java.util.ArrayList;
public class Main {
	
	private static ArrayList<String> test() {
		ArrayList<String> ret = new ArrayList<String>();
		while(true) {
			Long start = System.nanoTime();
			Long end = System.nanoTime();
			while((end - start)/1000000000 < 1) 
				end = System.nanoTime();	
			System.out.println("Hello!");
			ret.add("Hello!");
		}
	}

	public static void main(String[] args) {
	}
}
