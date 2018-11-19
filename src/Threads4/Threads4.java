package Threads4;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Threads4 {
	
	private static Random random = new Random();
	private static List<Integer> list1 = new ArrayList<Integer>();
	private static List<Integer> list2 = new ArrayList<Integer>();
	
	
	// sync on different objects (seperate objects)
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	// this slows things down, only one intrinsic lock for different methods
	public static void stageOne() {
		
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}
	
	public static void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
		
	}
	
	public static void process() {
		for (int i=0; i<1000; i++) {
			stageOne();
			stageTwo();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Starting");
		long start = System.currentTimeMillis();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				process();
				
			}
		});
	
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				process();
				
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: "+ (end -start));
	}
}
