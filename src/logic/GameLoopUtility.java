package logic;

import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import sharedObject.RenderableHolder;
import thread.Timer;
 

public class GameLoopUtility {
	private static AnimationTimer animationTimer;
	public static void runGameLoop(final GameLogic logic){
		animationTimer = new AnimationTimer(){
			public void handle(long now){
				try{
					logic.logicUpdate();
					Main.instance.drawGameScreen();
					RenderableHolder.getInstance().update();
					if(GameLogic.getPlayer1().getIsDead() && GameLogic.getPlayer2().getIsDead() || Timer.isZero()){
						GameLoopUtility.animationTimer.stop();
						RenderableHolder.getInstance().getSound("instinct_song").stop();
						RenderableHolder.getInstance().getSound("yoshida_song").play();
						InputUtility.clear();
						Platform.runLater(new Runnable(){
							@Override
							public void run(){
								System.out.println("draw");
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Draw");
								alert.setHeaderText(null);
								alert.setContentText("Draw!");
								alert.showAndWait();
								Main.instance.toggleScene();
							}
						});
						GameLogic.getGameObjectContainer().clear();
						try {
							Main.instance.stop();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(GameLogic.getPlayer2().getIsDead()){
						GameLoopUtility.animationTimer.stop();
						RenderableHolder.getInstance().getSound("instinct_song").stop();
						RenderableHolder.getInstance().getSound("yoshida_song").play();
						InputUtility.clear();
						Platform.runLater(new Runnable(){
							@Override
							public void run(){
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Result");
								alert.setHeaderText(null);
								alert.setContentText("Light is Victorious");
								alert.showAndWait();
								Main.instance.toggleScene();
							}
						});
						GameLogic.getGameObjectContainer().clear();
						try {
							Main.instance.stop();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(GameLogic.getPlayer1().getIsDead()){
						GameLoopUtility.animationTimer.stop();
						RenderableHolder.getInstance().getSound("instinct_song").stop();
						RenderableHolder.getInstance().getSound("yoshida_song").play();
						InputUtility.clear();
						Platform.runLater(new Runnable(){
							@Override
							public void run(){
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Result");
								alert.setHeaderText(null);
								alert.setContentText("Shadow is Victorious");
								alert.showAndWait();
								Main.instance.toggleScene();
							}
						});
						GameLogic.getGameObjectContainer().clear();
						try {
							Main.instance.stop();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					GameLoopUtility.animationTimer.stop();
				}
		}
			
			};
		animationTimer.start();
			
		
	}

}
