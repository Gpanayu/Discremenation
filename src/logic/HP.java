package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HP extends Entity {
	private double HPValue;
	private Color HPColor;
	private Slasher slasher;
	private static final Font font = Font.font("Verdana", FontWeight.MEDIUM, 15);

	
	public HP(double x, double y, Color color, Slasher slasher, double hpValue){
		super(x,y);
		this.HPValue = hpValue;
		this.HPColor = color;
		this.slasher = slasher;
		
	}
	
	public void decreaseHP(double amount){
		if (this.HPValue - amount >= 0){
			this.HPValue -= amount;
		}
		if (this.HPValue - amount <= 0 ){
			this.HPValue = 0;
		}
	}
	
	public double getHPValue() {
		return HPValue;
	}

	public Color getHPColor() {
		return HPColor;
	}

	public Slasher getSlasher() {
		return slasher;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(HPColor);
		gc.setFont(font);
		gc.fillText("HP", x - 30, y + 10);
		gc.fillRect(x, y, HPValue, 10);
		
	}

}
