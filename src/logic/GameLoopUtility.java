package logic;

import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import sharedObject.RenderableHolder;
 

public class GameLoopUtility {
//	private static final int REFRESH_DELAY = 10;
//	public static final int TICK_PER_SECONDS = 100;
	private static AnimationTimer animationTimer;
	public static void runGameLoop(final GameLogic logic){
		animationTimer = new AnimationTimer(){
			private long lastUpdate = 0;
			public void handle(long now){
				if (now - lastUpdate >= 4100000){
					try{
						lastUpdate = now;
						logic.logicUpdate();
						Main.instance.drawGameScreen();
						RenderableHolder.getInstance().update();
						if(GameLogic.getPlayer1().getIsDead() || GameLogic.getPlayer2().getIsDead()){
							Main.instance.stop();
							GameLoopUtility.animationTimer.stop();
						}
						if(GameLogic.getTimer().isZero()){
							Main.instance.stop();
							GameLoopUtility.animationTimer.stop();
						}
					}catch(Exception e){
						e.printStackTrace();
						GameLoopUtility.animationTimer.stop();
					}
				}
			}
		};
		animationTimer.start();
			
		
	}

}
