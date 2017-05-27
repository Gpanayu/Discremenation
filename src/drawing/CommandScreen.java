package drawing;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.Main;
import sharedObject.RenderableHolder;

public class CommandScreen extends GridPane{
	private Canvas canvas;
	private Button back;
	private GridPane gridPane;
	public CommandScreen(int x, int y){
		super();
		back = new Button("Back");
		canvas = new Canvas(1500,800);
		this.setPrefSize(x , y);
		drawCommandList();
		back.setOnAction((event) -> {
			Main.instance.toggleCommand();
		});
		this.add(canvas,0,0);
		gridPane = new GridPane();
		gridPane.setVgap(1);
		gridPane.setHgap(1);
		gridPane.add(back, 100, 600);
		this.getChildren().add(gridPane);
		
	}
	
	public void drawCommandList(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(RenderableHolder.commandList, 0, 0);
		
	}
	

}
