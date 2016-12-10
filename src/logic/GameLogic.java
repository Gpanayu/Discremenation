package logic;

import java.util.ArrayList;
import java.util.List;

import lib.ConfigurableOption;
import sharedObject.RenderableHolder;

public class GameLogic {
	private static List<Entity> gameObjectContainer;
	
	private static Slasher player1;
	private static Slasher player2;
	private Field field;
	
	public GameLogic(){
		GameLogic.gameObjectContainer = new ArrayList<Entity>();
		
		field = new Field(ConfigurableOption.firstBackground);
		RenderableHolder.getInstance().add(field);
		//Set player1 be white and player2 be black.
		player1 = new Blinker(5, 300, Slasher.DIRECTION_RIGHT, false);
		addNewObject(player1);
		player2 = new Blinker(500, 300, Slasher.DIRECTION_LEFT, true);
		addNewObject(player2);
	}
	
	public static Slasher getPlayer1() {
		return player1;
	}

	public static Slasher getPlayer2() {
		return player2;
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
