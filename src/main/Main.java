package main;


import drawing.GameScreen;
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
	private GameScreen gameScreen;
	private Scene gameScene;
	private WelcomeScreen welcomeScreen;
	private Scene welcomeScene;
	private Stage theStage;
	private GameLogic gameLogic;
	
	@Override
	public void start(Stage primaryStage) {
		System.out.println("start");
		instance = this;
		isGameScreen = false;
		theStage = primaryStage;
		theStage.setTitle("Discremenation");
		theStage.setResizable(false);
		theStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		
		StackPane root1 = new StackPane();
		StackPane root2 = new StackPane();
		
		gameScreen = new GameScreen(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		welcomeScreen = new WelcomeScreen(ConfigurableOption.SCREEN_WIDTH , ConfigurableOption.SCREEN_HEIGHT);
		
		root1.getChildren().add(welcomeScreen);
		root2.getChildren().add(gameScreen);
		
		welcomeScene = new Scene(root1);
		gameScene = new Scene(root2);
		
		theStage.setScene(welcomeScene);
		theStage.sizeToScene();
		
		theStage.show();
	}
	
	public void stop() throws Exception{
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
