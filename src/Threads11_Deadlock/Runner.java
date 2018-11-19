package Threads11_Deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
	
	private Account a1 = new Account();
	private Account a2 = new Account();
	
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	
	private void acquireLocks(Lock l1, Lock l2) throws InterruptedException {
		while(true) {
			boolean gotFirstLock = false;
			boolean gotSecondLock = false;
			// try and acquire the locks
			try {
				gotFirstLock = lock1.tryLock();
				gotSecondLock =lock2.tryLock();
			} finally {
				if (gotFirstLock && gotSecondLock) {
					break;
				}
				
				if (gotFirstLock) {
					lock1.unlock();
				}
				
				if (gotSecondLock) {
					lock2.unlock();
				}
			}
			
			Thread.sleep(1);
		}
	}
	
	public void firstThread() throws InterruptedException {
		Random r = new Random();
		
		for (int i=0;i < 10000; i++) {
			acquireLocks(lock2, lock1);
			try {
				Account.transfer(a1, a2, r.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
			
		}

	}
	
	public void secondThread() throws InterruptedException {
		Random r = new Random();
		for (int i=0;i < 10000; i++) {
			acquireLocks(lock1, lock2);
			try {
				Account.transfer(a2, a1, r.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
		
	}
	
	public void finished() {
		System.out.printf("Account 1 balance: $%d\n", a1.getBalance());
		System.out.printf("Account 2 balance: $%d\n", a2.getBalance());
		System.out.printf("Account totlal: $%d \n", a1.getBalance()+a2.getBalance());
	}
}


// we don't want a lock for each object. That's how deadlocks can occur, if locks are called in different orders
// ALWAYS lock your locks in the same order
