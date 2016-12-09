package drawing;

import input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class GameScreen extends Canvas{

	public GameScreen(double width, double height) {
		super(width, height);
		this.setVisible(true);
		addListerner();
	}
	public void addListerner() {
		this.setOnKeyPressed((KeyEvent event) -> {
			if(event.getCode() != KeyCode.W && event.getCode() != KeyCode.A && event.getCode() != KeyCode.D
					&& event.getCode() != KeyCode.ALT && event.getCode() != KeyCode.SPACE
					&& event.getCode() != KeyCode.LEFT && event.getCode() != KeyCode.UP
					&& event.getCode() != KeyCode.RIGHT && event.getCode() != KeyCode.ENTER
					&& event.getCode() != KeyCode.BACK_SLASH){
				
				return ;
				
			}
			InputUtility.setKeyPressed(event.getCode(), true);
		});

		this.setOnKeyReleased((KeyEvent event) -> {
			if(event.getCode() != KeyCode.W && event.getCode() != KeyCode.A && event.getCode() != KeyCode.D
					&& event.getCode() != KeyCode.ALT && event.getCode() != KeyCode.SPACE
					&& event.getCode() != KeyCode.LEFT && event.getCode() != KeyCode.UP
					&& event.getCode() != KeyCode.RIGHT && event.getCode() != KeyCode.ENTER
					&& event.getCode() != KeyCode.BACK_SLASH){
				
				return ;
				
			}
			InputUtility.setKeyPressed(event.getCode(), false);
		});

		this.setOnMousePressed((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftDown();
		});

		this.setOnMouseReleased((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftRelease();
		});

		this.setOnMouseEntered((MouseEvent event) -> {
			InputUtility.mouseOnScreen = true;
		});

		this.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnScreen = false;
		});
	}
	
	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		for (IRenderable entity : RenderableHolder.getInstance().getEntities()) {
			if (entity.isVisible() && !entity.isDestroyed()) {
				entity.draw(gc);
			}
		}

	}
}
