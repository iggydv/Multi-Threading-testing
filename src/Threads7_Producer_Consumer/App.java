package Threads7_Producer_Consumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

	// thread safe
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		t1.start();

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					consumer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		t2.start();

	}

	private static void producer() throws InterruptedException {
		Random ran = new Random();

		while (true) {
			queue.put(ran.nextInt(100));
		}
	}

	private static void consumer() throws InterruptedException {
		Random rand = new Random();

		while (true) {
			Thread.sleep(100);

			if (rand.nextInt(10) == 0) {
				Integer value = queue.take();
				System.out.println("Taken value: "+value+"; Queue size is: "+queue.size());
			}

		}
	}

}
