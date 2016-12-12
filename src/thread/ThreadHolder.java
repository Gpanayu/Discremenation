package thread;

import java.util.ArrayList;

public class ThreadHolder {
	public static ThreadHolder instance = new ThreadHolder();
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public boolean isAnyThreadAlive(){
		
		if (threads.isEmpty()){
			return false;
		}
		
		for (int i = 0; i < threads.size();i++) {
			if (threads.get(i).isAlive()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Thread> getThreads() {
		return threads;
	}
	
}
