package logic;


import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lib.ConfigurableOption;
import thread.ThreadHolder;

public class Timer extends Entity{
	private static int time;
	private String timeString;
	Thread timeCounter;
	private static final Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 20);
	
	public Timer(double x, double y){
		super(x,y);
		time = ConfigurableOption.TIME_PER_ROUND;
		timeCounter = new Thread(() -> {
			while (time >= 1){
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
				}
				if((time - 1 >= 0)){
					time --;
				}
			}
		});
		ThreadHolder.instance.getThreads().add(timeCounter);
		timeCounter.start();
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		timeString = new String (String.format("%02d",time));
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		double font_width = fontLoader.computeStringWidth(timeString, gc.getFont());
		gc.setFill(Color.WHITE);
		gc.fillRect(x -font_width/2 - 10, y-30, font_width + 50, 40);
		gc.setFill(Color.BLACK);
		gc.setFont(font);
		gc.fillText(timeString, x - font_width, y);
		
	}
	
	public static boolean isZero(){
		if (time <= 0){
			return true;
		}
		return false;
	}

}
