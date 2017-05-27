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
import javafx.scene.layout.GridPane;
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
	private boolean isOnStart;
	private boolean isOnMap;
	private boolean isOnCommandList;
	public WelcomeScreen(double width, double height){
		super();
		RenderableHolder.getInstance().getSound("yoshida_song").play();
		this.isOnStart = true;
		this.isOnMap = false;
		this.isOnCommandList = false;
		instance = this;
		canvas = new Canvas(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		canvas.setFocusTraversable(true);
		drawRect((ConfigurableOption.SCREEN_HEIGHT / 2) + 30);
		this.getChildren().add(canvas);
		this.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
				if(event.getCode().equals(KeyCode.DOWN)){
					keyDown();
				}
				else if(event.getCode().equals(KeyCode.UP)){
					keyUp();
				}
				else if(event.getCode().equals(KeyCode.ENTER)){
					keyEnter();
				}
			}
		});

		Button btn = new Button("Select");
		GridPane gp = new GridPane();
		gp.setHgap(1);
		gp.setVgap(1);
		gp.add(btn,(ConfigurableOption.SCREEN_WIDTH / 2)  + 125, (ConfigurableOption.SCREEN_HEIGHT / 2) + 225);
		gp.setAlignment(Pos.BASELINE_RIGHT);
		this.getChildren().add(gp);
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
		gc.drawImage(RenderableHolder.welcomeBackGround, 0, 0);
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 50));
		FontLoader fld = Toolkit.getToolkit().getFontLoader();
		double w1 = fld.computeStringWidth("INDIVISIBLE", gc.getFont());
		gc.fillText("INDIVISIBLE", (ConfigurableOption.SCREEN_WIDTH / 2) - (w1/2), (ConfigurableOption.SCREEN_HEIGHT / 2) - 200);
		gc.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
		double w2 = fld.computeStringWidth("Play", gc.getFont());		
		gc.fillText("Play", (ConfigurableOption.SCREEN_WIDTH / 2) - (w2/2), (ConfigurableOption.SCREEN_HEIGHT / 2) + 30);
		double w3 = fld.computeStringWidth("Select Map",gc.getFont());
		gc.fillText("Select Map", (ConfigurableOption.SCREEN_WIDTH / 2) - (w3/2), (ConfigurableOption.SCREEN_HEIGHT / 2) + 100);
		double w4 = fld.computeStringWidth("CommandList",gc.getFont());
		gc.fillText("CommandList", (ConfigurableOption.SCREEN_WIDTH / 2) - (w4/2), (ConfigurableOption.SCREEN_HEIGHT / 2) + 175);
		double w5 = fld.computeStringWidth("Select Background",gc.getFont());
		gc.fillText("Select Background", (ConfigurableOption.SCREEN_WIDTH / 2) - (w5/2) - 50, (ConfigurableOption.SCREEN_HEIGHT / 2) + 250);
		

	}
	
	
	public GameLogic gameLogic(){
		return this.gameLogic;
	}
	
	public void drawRect(int y){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		drawWelcome();
		gc.setFill(Color.rgb(176,224,230, 0.3));
		gc.fillRect(ConfigurableOption.SCREEN_WIDTH/2 - 125, y - 35, 250, 50);
	}
	
	public void keyDown(){
		if (isOnStart){
			isOnStart = false;
			isOnMap = true;
			drawRect((ConfigurableOption.SCREEN_HEIGHT / 2) + 100);
			
		}else if (isOnMap){
			isOnMap = false;
			isOnCommandList = true;
			drawRect((ConfigurableOption.SCREEN_HEIGHT / 2) + 175);
		}else if (isOnCommandList){
			isOnCommandList = false;
			isOnStart = true;
			drawRect((ConfigurableOption.SCREEN_HEIGHT / 2) + 30);
		}
	}
	
	public void keyUp(){
		if (isOnStart){
			isOnStart = false;
			isOnCommandList = true;
			drawRect((ConfigurableOption.SCREEN_HEIGHT / 2) + 175);
		}else if (isOnMap){
			isOnMap = false;
			isOnStart = true;
			drawRect((ConfigurableOption.SCREEN_HEIGHT / 2) + 30);
		}else if (isOnCommandList){
			isOnCommandList = false;
			isOnMap = true;
			drawRect((ConfigurableOption.SCREEN_HEIGHT / 2) + 100);
		}
	}
	
	public void keyEnter(){
		if (isOnStart){
			RenderableHolder.getInstance().getSound("yoshida_song").stop();
			RenderableHolder.getInstance().getSound("instinct_song").play();
			RenderableHolder.getInstance().getEntities().clear();
			gameLogic = new GameLogic();
			new GameLoopUtility();
			GameLoopUtility.runGameLoop(gameLogic);
			Main.instance.toggleScene();
		}else if(isOnMap){
			Main.instance.toggleMap();
		}else if(isOnCommandList){
			Main.instance.toggleCommand();
		}
	}
	
}
