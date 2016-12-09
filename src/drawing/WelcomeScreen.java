package drawing;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lib.ConfigurableOption;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class WelcomeScreen extends Canvas{
	public WelcomeScreen(double width, double height){
		super(width, height);
		this.setVisible(true);
		addListener();
	}
	
	public void addListener(){
		this.setOnKeyPressed((KeyEvent event) -> {
			if(event.getCode() != KeyCode.ENTER){
				return ;
			}
			InputUtility.setKeyPressed(event.getCode(), true);
		});

		this.setOnKeyReleased((KeyEvent event) -> {
			if(event.getCode() != KeyCode.ENTER){				
				return ;				
			}
			InputUtility.setKeyPressed(event.getCode(), false);
		});
	}
	
	@SuppressWarnings("restriction")
	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 50));
		FontLoader fld = Toolkit.getToolkit().getFontLoader();
		double w = fld.computeStringWidth("Press Enter", gc.getFont());
		gc.fillText("Press Enter", (ConfigurableOption.SCREEN_WIDTH / 2) - (w/2), (ConfigurableOption.SCREEN_HEIGHT / 2) + 30);

	}
}
