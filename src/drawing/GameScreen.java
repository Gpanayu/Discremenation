package drawing;

import input.InputUtility;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import logic.GameLogic;
import logic.GameLoopUtility;
import main.Main;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class GameScreen extends Pane{
	private static Canvas canvas;

	public GameScreen(double width, double height) {
		super();
		canvas = new Canvas(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		this.getChildren().add(canvas);
		addListener();
		this.setFocusTraversable(true);
	}
	public void addListener() {
		this.setOnKeyPressed((KeyEvent event) -> {
			if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.A || event.getCode() == KeyCode.D
					|| event.getCode() == KeyCode.ALT || event.getCode() == KeyCode.SPACE
					|| event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP
					|| event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.ENTER
					|| event.getCode() == KeyCode.BACK_SLASH){
				
				if (!InputUtility.getKeyPressed(event.getCode()))
					InputUtility.setKeyTriggered(event.getCode(), true);
				InputUtility.setKeyPressed(event.getCode(), true);
				
			}
		});

		this.setOnKeyReleased((KeyEvent event) -> {
			if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.A || event.getCode() == KeyCode.D
					|| event.getCode() == KeyCode.ALT || event.getCode() == KeyCode.SPACE
					|| event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP
					|| event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.ENTER
					|| event.getCode() == KeyCode.BACK_SLASH){
				
				InputUtility.setKeyPressed(event.getCode(), false);
			}
		});

		canvas.setOnMousePressed((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftDown();
		});

		canvas.setOnMouseReleased((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftRelease();
		});
		
		canvas.setOnMouseEntered((MouseEvent event) -> {
			InputUtility.mouseOnScreen = true;
		});

		canvas.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnScreen = false;
		});
	}
	
	public void paintComponent() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		gc.setFill(Color.BLACK);
		for (IRenderable entity : RenderableHolder.getInstance().getEntities()) {
			if (entity.isVisible() && !entity.isDestroyed()) {
				entity.draw(gc);
			}
		}

	}
	public static Canvas getCanvas(){
		return canvas;
	}
}
