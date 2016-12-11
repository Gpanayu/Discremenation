package lib;

import javafx.scene.image.Image;

public class ConfigurableOption {
	public static final int SCREEN_WIDTH = 1500 * 3 / 4;
	public static final int SCREEN_HEIGHT = 800 * 3 / 4;
	public static final double HIT_WIDTH = 98;
	public static final double HIT_HEIGHT = 195.95;
	public static final double HIT_X = 214.1;
	public static final double HIT_Y = 149;
	public static final double DAMAGE_X = 312;
	public static final double DAMAGE_Y = 33;
	public static final double DAMAGE_WIDTH = 161;
	public static final double DAMAGE_HEIGHT = 264.95;
	public static final int FLOOR = 30;
	public static final int SLASHER_WIDTH = 550;
	public static final int SLASHER_HEIGHT = 400;
	public static Image firstBackground = new Image(ClassLoader.getSystemResource("bg.png").toString());
	public static final int TIME_PER_ROUND = 60;
	public static void setBackground(String source){
		firstBackground = new Image(ClassLoader.getSystemResource(source).toString());
	}
}
