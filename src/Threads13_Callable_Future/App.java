package Threads13_Callable_Future;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
	/*
	 * Future is used if we want to get an exit result from a thread
	 */

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		
		/*
		 *  no return type:
		 *  Future<?> future = executor.submit(new Callable<Void>() {
		 *  
		 *  public Void call() throws Exception { ...
		 */
		
		Future<Integer> future = executor.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {

				Random rand = new Random();
				int duration = rand.nextInt(4000);
				
				if (duration > 2000 ) {
					throw new IOException("Sleeping too long");
				}

				System.out.println("Starting");
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("finished");

				return duration;
			}
		});

		executor.shutdown();

		try {
			// get is blocked until the thread finished
			System.out.println("Result is: " + future.get());
			// get will throw execution exception with cause, actual exception!
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
