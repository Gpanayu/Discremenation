package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HP extends Entity {
	private double HPValue;
	private Color HPColor;
	private Slasher slasher;
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
			this.slasher.setIsDead(true);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(HPColor);
		gc.fillText("HP", x - 30, y + 10);
		gc.fillRect(x, y, HPValue, 10);
		
	}

}
