package logic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import sharedObject.RenderableHolder;

public class GameLogic {
	private static List<Entity> gameObjectContainer;
	
	private static Slasher player1;
	private static Slasher player2;
	private static Gauge gauge1;
	private static Gauge gauge2;
	private static HP hp1;
	private static HP hp2;
	private Field field;
	private Timer timer;
	
	public GameLogic(){
		GameLogic.gameObjectContainer = new ArrayList<Entity>();
		
		field = new Field(ConfigurableOption.firstBackground);
		RenderableHolder.getInstance().add(field);
		timer = new Timer(ConfigurableOption.SCREEN_WIDTH/2 ,104);
		addNewObject(timer);
		//Set player1 be white and player2 be black.
		player1 = new Blinker(5, 300, Slasher.DIRECTION_RIGHT, false);
		hp1 = new HP(100, 20, Color.BLACK, player1, 500);
		hp2 = new HP(ConfigurableOption.SCREEN_WIDTH/2 + 100, 20, Color.WHITE, player2, 500);
		player2 = new Blinker(500, 300, Slasher.DIRECTION_LEFT, true);
		addNewObject(player1);
		addNewObject(hp1);
		addNewObject(hp2);
		addNewObject(player2);
		gauge1 = new Gauge(100, 40, Color.BLACK, player1);
		addNewObject(gauge1);
		gauge2 = new Gauge(ConfigurableOption.SCREEN_WIDTH/2 + 100, 40, Color.WHITE, player2);
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
	protected void addNewObject(Entity entity){
		gameObjectContainer.add(entity);
		RenderableHolder.getInstance().add(entity);
	}
	
	public void logicUpdate(){
		field.updateField();
		player1.update();
		player2.update();
	}
	
	public static List<Entity> getGameObjectContainer(){
		return gameObjectContainer;
	}
}
