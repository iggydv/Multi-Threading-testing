package Threads12_Semaphores;

import java.util.concurrent.Semaphore;

public class Connection {
	private static Connection instance = new Connection();
	private int connections = 0;

	private Semaphore sem = new Semaphore(20, true);
	
	private Connection() {

	}

	public static Connection getInstance() {
		return instance;
	}
	
	public void connect() {
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			doConnect();
		} finally {
			sem.release();
		}
	}

	public void doConnect() {
		
		synchronized (this) {
			connections++;
			System.out.println("Current connections: "+ connections);
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		synchronized (this) {
			connections --;
		}
	}
}

