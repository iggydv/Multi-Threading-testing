package Threads10_ReEntrant_Locks;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
	
	private int count;
	// this is basically your object lock now
	// alternative to using the sync key word
	// Reentrant lock - await: wait
	//				  - signal: notify
	// you can only call await or signal if you have the lock
	
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	
	private void increment() {
		for (int i =0; i< 100000; i++) {
			count++;
		}
	}
	
	public void firstThread() throws InterruptedException {
		lock.lock();
		System.out.println("Awaiting");
		
		// unlocks and waits. Hands the object lock back to the reentrant lock
		cond.await();
		System.out.println("Woken up!");
		
		try {
			increment();
		} finally {
			lock.unlock();
		}	
	}
	
	@SuppressWarnings("resource")
	public void secondThread() throws InterruptedException {
		Thread.sleep(100);
		lock.lock();
		System.out.println("Press the return key");
		new Scanner(System.in).nextLine();
		System.out.println("GOt the return key");		
		cond.signal();
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}
	
	public void finished() {
		System.out.println("Count is: "+ count);	
	}
}
