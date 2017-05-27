package logic;

import java.util.ArrayList;
import java.util.List;

import input.InputUtility;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import main.Main;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;
import thread.ThreadHolder;
import thread.Timer;

public class GameLogic {
	private static List<Entity> gameObjectContainer;
	
	private static Slasher player1;
	private static Slasher player2;
	private static Gauge gauge1;
	private static Gauge gauge2;
	private static HP hp1;
	private static HP hp2;
	private Field field;
	private static Timer timer;

	
	public GameLogic(){
		GameLogic.gameObjectContainer = new ArrayList<Entity>();
		
		field = new Field(ConfigurableOption.firstBackground);
		RenderableHolder.getInstance().add(field);
		timer = new Timer(ConfigurableOption.SCREEN_WIDTH/2 ,104);
		addNewObject(timer);
		player2 = new Blinker(5, ConfigurableOption.SCREEN_HEIGHT, Slasher.DIRECTION_RIGHT, true);	
		addNewObject(player2);
		player1 = new Blinker(ConfigurableOption.SCREEN_WIDTH - (int)ConfigurableOption.HIT_WIDTH - 30, ConfigurableOption.SCREEN_HEIGHT, Slasher.DIRECTION_LEFT, false);
		addNewObject(player1);
		hp1 = new HP(ConfigurableOption.SCREEN_WIDTH/2 + 100, 20, Color.WHITE, player2, 500);
		hp2 = new HP(100, 20, Color.BLACK, player2, 500);
		addNewObject(hp1);
		addNewObject(hp2);
		gauge1 = new Gauge(ConfigurableOption.SCREEN_WIDTH/2 + 100, 40, Color.WHITE, player1);
		addNewObject(gauge1);
		gauge2 = new Gauge(100, 40, Color.BLACK, player2);
		addNewObject(gauge2);
		
	}
	
	public static Slasher getPlayer1() {
		return player1;
	}

	public static Slasher getPlayer2() {
		return player2;
	}

	public static HP getHPPlayer1(){
		return hp1;
	}
	
	public static HP getHPPlayer2(){
		return hp2;
	}
	
	public static Gauge getGaugePlayer1(){
		return gauge1;
	}
	
	public static Gauge getGaugePlayer2(){
		return gauge2;
	}
	
	
	public static Timer getTimer() {
		return timer;
	}

	protected void addNewObject(Entity entity){
		gameObjectContainer.add(entity);
		RenderableHolder.getInstance().add(entity);
	}
	
	public void logicUpdate(){
		field.updateField();
		player1.update();
		player2.update();
		
		InputUtility.updateInputState();
	}
	
	public static List<Entity> getGameObjectContainer(){
		return gameObjectContainer;
	}
}
