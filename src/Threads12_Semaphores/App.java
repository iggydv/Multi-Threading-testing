package Threads12_Semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//		Semaphore sem = new Semaphore(1);
		//		
		//		sem.acquire();
		//		sem.release();

		// everytime i call submit creates new thread and will reuse threads
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i=0; i<200; i++) {
			executor.submit(new Runnable() {

				@Override
				public void run() {
					Connection.getInstance().connect();
				}
			});
		}
		executor.shutdown();

		executor.awaitTermination(1, TimeUnit.DAYS);
	}
}
