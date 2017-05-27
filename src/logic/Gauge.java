package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import thread.PowerGauge;
import thread.ThreadHolder;

public class Gauge extends Entity{
	private Color gaugeColor;
	private double gaugeValue;
	private double gaugeMax;
	private Slasher slasher;
	private static final Font font = Font.font("Verdana", FontWeight.MEDIUM, 15);

	
	public Gauge(double x, double y, Color color, Slasher slasher){
		super(x,y);
		this.gaugeColor = color;
		this.gaugeMax = 500;
		this.slasher = slasher;
		PowerGauge powerGauge = new PowerGauge(this);
		Thread autoIncrease = new Thread(powerGauge);
		autoIncrease.start();
		ThreadHolder.instance.getThreads().add(autoIncrease);
	}
	
	public synchronized void increaseGauge(double amount){
		if(this.gaugeValue + amount <= gaugeMax){
			this.gaugeValue += amount;
		}
		else{
			this.gaugeValue = this.gaugeMax;
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
	
	public Slasher getSlasher(){
		return slasher;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(gaugeColor);
		gc.setFont(font);
		gc.fillText("Gauge", x - 50, y + 10);
		gc.fillRect(x, y, gaugeValue, 10);
	}

}
