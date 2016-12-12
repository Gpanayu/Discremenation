package logic;

import input.InputUtility;
import javafx.animation.AnimationTimer;
import main.Main;
 

public class GameLoopUtility {
//	private static final int REFRESH_DELAY = 10;
//	public static final int TICK_PER_SECONDS = 100;
	private static AnimationTimer animationTimer;
	public static void runGameLoop(final GameLogic logic){
		animationTimer = new AnimationTimer(){
			private long lastUpdate = 0;
			public void handle(long now){
				if (now - lastUpdate >= 410000000){
					try{
						logic.logicUpdate();
						Main.instance.drawGameScreen();
					}catch(Exception e){
						e.printStackTrace();
						GameLoopUtility.animationTimer.stop();
					}
				}
				
			}
		};
		animationTimer.start();
		
//		Thread animation = new Thread(() -> {
//			while(true){
//				try{
//					Thread.sleep(24);
//				}catch(InterruptedException e){}
//				logic.logicUpdate();
//				Main.instance.drawGameScreen();
//			}
//		});
//		animation.start();
	}

}
