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

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		WelcomeScreen welcomeScreen = new WelcomeScreen(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		StackPane root = new StackPane();
		root.getChildren().add(welcomeScreen);
		Scene scene = new Scene(root);
		GameLogic logic = new GameLogic();
		GameScreen gameScreen = new GameScreen(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		welcomeScreen.requestFocus();
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Discremenation");
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		primaryStage.show();
		
		AnimationTimer animation = new AnimationTimer() {
			public void handle(long now) {
				welcomeScreen.paintComponent();
				logic.logicUpdate();
//				RenderableHolder.getInstance().update();
				InputUtility.updateInputState();
			}
		};
		animation.start();
	}
	
	public void stop() throws Exception{
		//stop all threads
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
