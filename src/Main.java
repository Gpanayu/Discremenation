
import drawing.GameScreen;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.GameLogic;
import sharedObject.RenderableHolder;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Black & White");

		GameLogic logic = new GameLogic();
		GameScreen gameScreen = new GameScreen(1500, 800);
		root.getChildren().add(gameScreen);
		gameScreen.requestFocus();
		
		primaryStage.show();
		
		AnimationTimer animation = new AnimationTimer() {
			public void handle(long now) {
				gameScreen.paintComponent();
				logic.logicUpdate();
				RenderableHolder.getInstance().update();
				InputUtility.updateInputState();
			}
		};
		animation.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
