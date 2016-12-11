package thread;

import lib.ConfigurableOption;

public class TimeRunnable implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int currentTime = ConfigurableOption.TIME_PER_ROUND;
		// We need to add the condition when the game is over, it will stop counting.
		while(true){//This needs to be edited.
			try {
				Thread.sleep(1000);
				currentTime --;
				//draw this timer, too.
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Round's end");
				break ;
			}
			
		}
	}

}
