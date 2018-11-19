package Threads8_Wait_and_Notify;

import java.util.Scanner;

public class Processor {
	
	/*
	 * wait & notify go hand-in-hand
	 * wait - watis for a notify
	 * notify - notify all other threads that the lock will be relinquished
	 */
	
	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Producer thread running...");
			wait(); // method of the Object class, resource efficient and only callable within sync code blocks
			System.out.println("Resumed.");
		}

	}
	
	public void consume() throws InterruptedException {
		Thread.sleep(2000);
		Scanner scanner = new Scanner(System.in);
		
		synchronized (this) {
			System.out.println("Waiting for return key");
			scanner.nextLine();
			System.out.println("Return key pressed.");
			notify(); // only callable in sync code blocks
			// this code block needs to exit before the other thread can continue
		}

	}

}
