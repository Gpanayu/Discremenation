package main;


import drawing.CommandScreen;
import drawing.GameScreen;
import drawing.MapScreen;
import drawing.WelcomeScreen;
import input.InputUtility;
import lib.ConfigurableOption;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GameLogic;
import sharedObject.RenderableHolder;
import thread.ThreadHolder;

public class Main extends Application {
	public static Main instance;
	private boolean isGameScreen;
	private boolean isMapScreen;
	private boolean isCommandScreen;
	private GameScreen gameScreen;
	private Scene gameScene;
	private WelcomeScreen welcomeScreen;
	private Scene welcomeScene;
	private Stage theStage;
	private MapScreen mapScreen;
	private Scene mapScene;
	private  CommandScreen commandScreen;
	private Scene commandScene;
	
	@Override
	public void start(Stage primaryStage) {
		System.out.println("start");
		instance = this;
		isGameScreen = false;
		isMapScreen = false;
		isCommandScreen = false;

		theStage = primaryStage;
		theStage.setTitle("Indivisible");
		theStage.setResizable(false);
		theStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		
		StackPane root1 = new StackPane();
		StackPane root2 = new StackPane();
		StackPane root3 = new StackPane();
		StackPane root4 = new StackPane();

		
		gameScreen = new GameScreen(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		welcomeScreen = new WelcomeScreen(ConfigurableOption.SCREEN_WIDTH , ConfigurableOption.SCREEN_HEIGHT);
		mapScreen = new MapScreen(ConfigurableOption.SCREEN_WIDTH , ConfigurableOption.SCREEN_HEIGHT);
		commandScreen = new CommandScreen(ConfigurableOption.SCREEN_WIDTH , ConfigurableOption.SCREEN_HEIGHT);

		
		root1.getChildren().add(welcomeScreen);
		root2.getChildren().add(gameScreen);
		root3.getChildren().add(mapScreen);
		root4.getChildren().add(commandScreen);

		
		welcomeScene = new Scene(root1);
		gameScene = new Scene(root2);
		mapScene = new Scene(root3);
		commandScene = new Scene(root4);

		
		theStage.setScene(welcomeScene);
		theStage.sizeToScene();
		theStage.show();
	}
	
	public void stop() throws Exception{
		System.out.println("STOP");
		for(Thread t : ThreadHolder.instance.getThreads()){
			if(t.isAlive()){
				t.interrupt();
			}
		}
	}
	
	public void toggleScene(){
		if (isGameScreen){
			theStage.setScene(welcomeScene);
			this.isGameScreen = false;
		}else{
			theStage.setScene(gameScene);
			this.isGameScreen = true;
		}
	}
	
	public void toggleMap(){
		if (isMapScreen){
			theStage.setScene(welcomeScene);
			this.isMapScreen = false;
		}else{
			theStage.setScene(mapScene);
			this.isMapScreen = true;
		}
	}
	
	public void toggleCommand(){
		if (isCommandScreen){
			theStage.setScene(welcomeScene);;
			this.isCommandScreen = false;
		}else{
			theStage.setScene(commandScene);
			this.isCommandScreen = true;
		}
	}
	
	public Stage getTheStage(){
		return theStage;
	}
	
	public void drawGameScreen(){
		gameScreen.paintComponent();
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
