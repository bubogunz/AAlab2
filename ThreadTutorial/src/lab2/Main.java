package lab2;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void tutorial1() {
		ExecutorService executor = Executors.newCachedThreadPool();

		//if we don't want to retrieve a result from the Callable operation
		//Future<?> future =  executor.submit(new Callable<Void> () {
		Future<Integer> future =  executor.submit(new Callable<Integer> () {
			@Override
			public Integer call() throws Exception {
				System.out.println("Starting..");
				Random rnd = new Random();
				int duration = rnd.nextInt(4000);

				if(duration > 2000) {
					throw new IOException("Sleeping for too long.");
				}

				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Finished.");
				return duration;
			}

		});

		//want to terminate main() when thread finishes
		executor.shutdown();

		//wait for the termination of all threads
		//		try {
		//			executor.awaitTermination(10, TimeUnit.SECONDS);
		//		} catch (InterruptedException e1) {
		//			System.out.println(e);
		//		}
		try {
			System.out.println("Result is " + future.get());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
	}
	
	public static void tutorial2() throws InterruptedException {
		System.out.println("Starting...");
		
		Thread thread = new Thread(new Runnable() {			
			@Override
			public void run() {
				Random rnd = new Random();
				
				for(int i=0; i<1E8; ++i) {
					//hard stop
//					try {
//						Thread.sleep(1);
//					} catch (InterruptedException e) {
//						System.out.println("The thread has been interrupted.");
//						break; 
//					}
					//graceful stop
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("The thread has been interrupted.");
						break;
					}
					Math.sin(rnd.nextDouble());
				}
			}
		});
		thread.start();
		
		Thread.sleep(500);
		
		//this does not interrupt the thread outta nowhere, it waits for an interrupt
		//and then interrupts the specified thread.
		thread.interrupt();
		
		thread.join();
		
		
		System.out.println("Finished.");
	}
	
	public static void tutorial2withThreadPool() throws InterruptedException {
		System.out.println("Starting...");
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Future<Void> future  = executor.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				Random rnd = new Random();
				
				for(int i=0; i<1E8; ++i) {
					//catch the interrupted flag and breaks the computation
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("The thread has been interrupted.");
						break;
					}
					Math.sin(rnd.nextDouble());
				}
				return null;
			}
		});
		
		//does not brutally shut down the program, it waits until the computation is finished
		executor.shutdown();
		
		//Sets the "interrupted flag" on all the running threads
		//practically a future.cancel(true); instruction on all the running threads
		executor.shutdownNow();
		
		Thread.sleep(500);
		//Cancels the thread if it is not already running
//		future.cancel(false); 
		//Sets the "interrupted flag" that will break computation of the thread gracefully
		future.cancel(true);
		
		executor.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("Finished.");
	}
	
	public static void main(String[] args) throws InterruptedException {
		tutorial2withThreadPool();
	}
}
