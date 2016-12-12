package drawing;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lib.ConfigurableOption;
import lib.ImageSizeNotValidException;
import lib.Utility;
import main.Main;
import sharedObject.RenderableHolder;

public class MapScreen extends GridPane{
	private Image barcode = RenderableHolder.menu_barcode;
	private Image yinyang = RenderableHolder.yinyang_menu;
	private Image duel = RenderableHolder.duel_menu;
	private Image decoy = RenderableHolder.decoy_menu;
	
	private Button barcodeButton = new Button();
	private Button yinyangButton = new Button();
	private Button duelButton = new Button();
	private Button decoyButton = new Button();
	
	private Canvas canvas = new Canvas(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
	protected static final double transcluentWhite = 0.7;
	protected static final double opaque = 1;
	
	protected static final Font standardFont = Font.font("Verdana", FontWeight.BOLD, 30);
	protected static final Font smallFont = Font.font("Verdana", FontWeight.MEDIUM, 20);

	
	public MapScreen(int x, int y){
		this.setPrefSize(x, y);
		this.setStyle(" -fx-background-color: rgb(255,255,255)");
		drawMenues();
		this.add(canvas, 0, 0);
		barcodeButton.setPrefSize(400, 200);
		yinyangButton.setPrefSize(400, 200);
		duelButton.setPrefSize(400, 200);
		decoyButton.setPrefSize(400, 200);
		
		barcodeButton.setStyle(" -fx-background-color: rgba(255,255,255,0)");
		yinyangButton.setStyle(" -fx-background-color: rgba(255,255,255,0)");
		duelButton.setStyle(" -fx-background-color: rgba(255,255,255,0)");
		decoyButton.setStyle(" -fx-background-color: rgba(255,255,255,0)");

		
		barcodeButton.setOnAction((event) -> {
			if(Utility.isImageValid(RenderableHolder.barcode)){
				ConfigurableOption.firstBackground = RenderableHolder.barcode;
			}
			Main.instance.toggleMap();
		});
		barcodeButton.setOnMouseEntered((event) -> {
			setWhite(barcodeButton);
		});
		barcodeButton.setOnMouseExited((event) -> {
			setNorm(barcodeButton);
		});
		yinyangButton.setOnAction((event) -> {
			if(Utility.isImageValid(RenderableHolder.yinyang)){
				ConfigurableOption.firstBackground = RenderableHolder.yinyang;
			}
			Main.instance.toggleMap();
		});
		yinyangButton.setOnMouseEntered((event) -> {
			setWhite(yinyangButton);
		});
		yinyangButton.setOnMouseExited((event) -> {
			setNorm(yinyangButton);
		});
		

		duelButton.setOnAction((event) -> {
			if(Utility.isImageValid(RenderableHolder.duel)){
				ConfigurableOption.firstBackground = RenderableHolder.duel;
			}
			Main.instance.toggleMap();
		});
		duelButton.setOnMouseEntered((event) -> {
			setWhite(duelButton);
		});
		duelButton.setOnMouseExited((event) -> {
			setNorm(duelButton);
		});
		
		decoyButton.setOnAction((event) -> {
			if(Utility.isImageValid(RenderableHolder.decoy)){
				ConfigurableOption.firstBackground = RenderableHolder.decoy;
			}
			Main.instance.toggleMap();
		});
		decoyButton.setOnMouseEntered((event) -> {
			setWhite(decoyButton);
		});
		decoyButton.setOnMouseExited((event) -> {
			setNorm(decoyButton);
		});
		
		
		GridPane gp = new GridPane();
		gp.setHgap(1);
		gp.setVgap(1);
		gp.setPrefSize(ConfigurableOption.SCREEN_WIDTH, ConfigurableOption.SCREEN_HEIGHT);
		gp.add(barcodeButton, 200, 100);
		gp.add(yinyangButton, ConfigurableOption.SCREEN_WIDTH/2 - 200 , 100);
		gp.add(duelButton, 200, 250);
		gp.add(decoyButton,  ConfigurableOption.SCREEN_WIDTH/2 - 200, 250);
		
		this.getChildren().add(gp);

	}
	private static void setWhite(Button button) {
		button.setStyle(" -fx-background-color: rgba(255,255,255,0.7)");
	}
	private static void setNorm(Button button){
		button.setStyle(" -fx-background-color: rgba(255,255,255,0)");
	}
	
	public void drawMenues(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setFont(smallFont);
		gc.drawImage(barcode, 200, 100);
		gc.fillText("Barcode", 350, 350);
		gc.drawImage(yinyang, ConfigurableOption.SCREEN_WIDTH/2 + 200, 100);
		gc.fillText("Yinyang", ConfigurableOption.SCREEN_WIDTH/2 + 350, 350);
		gc.drawImage(duel, 200, ConfigurableOption.SCREEN_HEIGHT/2 + 50);
		gc.fillText("Duel(default)", 325 , ConfigurableOption.SCREEN_HEIGHT/2 + 300);
		gc.drawImage(decoy, ConfigurableOption.SCREEN_WIDTH/2 + 200,ConfigurableOption.SCREEN_HEIGHT/2 + 50);
		gc.fillText("Decoy", ConfigurableOption.SCREEN_WIDTH/2 + 350, ConfigurableOption.SCREEN_HEIGHT/2 + 300);
	}

}
