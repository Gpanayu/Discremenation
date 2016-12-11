package thread;

import logic.Gauge;

public class PowerGauge implements Runnable{
	private Gauge gauge;
	public PowerGauge(Gauge gauge){
		this.gauge = gauge;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Thread.sleep(16);
			}catch(InterruptedException e){}
			this.gauge.increaseGauge(0.5);
		}
		
	}

}
