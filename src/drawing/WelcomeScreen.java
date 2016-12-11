package drawing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import input.InputUtility;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import lib.ConfigurableOption;
import lib.ImageSizeNotValidException;
import lib.NotImageException;
import lib.Utility;
import logic.GameLogic;
import logic.GameLoopUtility;
import main.Main;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class WelcomeScreen extends Pane{
	public static WelcomeScreen instance;
	private Canvas canvas;
	private GameLogic gameLogic;
	public WelcomeScreen(double width, double height){
		super();
		instance = this;
		canvas = new Canvas(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		canvas.setFocusTraversable(true);
		drawWelcome();
		this.getChildren().add(canvas);
		this.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				if(event.getCode() == KeyCode.ENTER){
					gameLogic = new GameLogic();
					GameLoopUtility gameLoop = new GameLoopUtility();
					gameLoop.runGameLoop(gameLogic);
					Main.instance.toggleScene();
				}
			}
		});
		Button btn = new Button("Select");
		Label label = new Label("Select Background");
		FlowPane fp = new FlowPane();
		fp.getChildren().add(label);
		fp.getChildren().add(btn);
		fp.setAlignment(Pos.BASELINE_RIGHT);
		this.getChildren().add(fp);
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent ae){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open");
				try{
					File file = fileChooser.showOpenDialog(null);
					if(Utility.isImageFile(file)){
						BufferedImage bufferedImage = ImageIO.read(file);
						Image image = SwingFXUtils.toFXImage(bufferedImage, null);
						if(Utility.isImageValid(image)){
							ConfigurableOption.firstBackground = image;
						}
						else{
							throw new ImageSizeNotValidException();
						}
					}
					else{
						throw new NotImageException();
					}
				} catch(ImageSizeNotValidException e){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Image has to be 1500x800 pixels.");
					alert.showAndWait();
				} catch(NotImageException nie){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Background has to be an image.");
					alert.showAndWait();
				
				} catch (Exception e){
					System.out.println(e.getMessage());
				}
			}
		});
		
	}
	
	@SuppressWarnings("restriction")
	public void drawWelcome() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 50));
		FontLoader fld = Toolkit.getToolkit().getFontLoader();
		double w1 = fld.computeStringWidth("DISCREMENATION", gc.getFont());
		gc.fillText("DISCREMENATION", (ConfigurableOption.SCREEN_WIDTH / 2) - (w1/2), (ConfigurableOption.SCREEN_HEIGHT / 2) - 50);
		gc.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
		double w2 = fld.computeStringWidth("Press Enter", gc.getFont());		
		gc.fillText("Press Enter", (ConfigurableOption.SCREEN_WIDTH / 2) - (w2/2), (ConfigurableOption.SCREEN_HEIGHT / 2) + 30);
	}
	
	public GameLogic gameLogic(){
		return this.gameLogic;
	}
}
