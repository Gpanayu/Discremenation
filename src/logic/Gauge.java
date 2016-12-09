package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Gauge extends Entity{
	private Color gaugeColor;
	private double gaugeValue;
	private double gaugeMax;
	private Slasher slasher;
	
	public Gauge(double x, double y, Color color, Slasher slasher){
		super(x,y);
		this.gaugeColor = color;
		this.gaugeMax = 100;
		this.slasher = slasher;
	}
	
	public void increaseGauge(double amount){
		if(this.gaugeValue + amount <= gaugeMax){
			this.gaugeValue += amount;
		}
	}
	
	public void decreaseGuage(double  amount){
		if(this.gaugeValue - amount >= 0){
			this.gaugeValue -= amount;
		}
	}
	
	public double getGaugeValue() {
		return gaugeValue;
	}

	public void setGaugeValue(double gaugeValue) {
		this.gaugeValue = gaugeValue;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(gaugeColor);
		gc.fillText("Gauge", x - 50, y + 10);
		gc.fillRect(x, y, gaugeValue, 10);
	}

}
