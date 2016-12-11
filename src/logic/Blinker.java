package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import sharedObject.RenderableHolder;

public class Blinker extends Slasher{
	private Image yellowSheet;
	private int frameDelay;
	private int currentFrame, frameDelayCount;
	private boolean isBlack;
	private boolean visible = false, playing = false;
	private Image rightSheet;
	private Image leftSheet;

	public Blinker (int x, int y, int direction, boolean isBlack){
		super(x, y, direction);
		this.isBlack = isBlack;
		if(isBlack){
			this.rightSheet = RenderableHolder.blackRight;
			this.leftSheet = RenderableHolder.blackLeft;
			super.hp = new HP(x, 5, Color.BLACK, (Slasher)this, 6);
			super.gauge = new Gauge(x, 10, Color.BLACK, (Slasher)this);
		}else{
			this.rightSheet = RenderableHolder.whiteRight;
			this.leftSheet = RenderableHolder.whiteLeft;
			super.hp = new HP(x, 5, Color.WHITE, (Slasher)this, 6);
			super.gauge = new Gauge(x, 10, Color.WHITE, (Slasher)this);
		}
		this.frameDelay = frameDelay;
		this.currentFrame = 0;
		this.frameDelayCount = 0;
//		if(this.sheet == null){
//			this.frameWidth = 0;
//			this.frameHeight = 0;
//		}else{
//			this.frameWidth = (int)sheet.getWidth()/7;
//			this.frameHeight = (int)sheet.getHeight()/3;
//		}
		super.slashTime = 5;
		super.stunTime = 10;
		super.immuneTime = 20;
		
	}
	
	public boolean isBlack() {
		return isBlack;
	}

	public void play() {
		currentFrame = 0;
		playing = true;
		visible = true;
	}
	
	public void stop() {
		currentFrame = 0;
		playing = false;
		visible = false;
	}

	
	public void updateAnimation() {
		if (!playing)
			return;
		if (frameDelayCount > 0) {
			frameDelayCount--;
			return;
		}
		frameDelayCount = frameDelay;
		currentFrame++;
		if(this.checkSameStates() == false){
			currentFrame = 0;
		}
	}



	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (states[0]){
			gc.setFill(Color.RED);
			gc.fillRect(x, frameDelay, width, height);
			for(Entity e : GameLogic.getGameObjectContainer()){
				if(e instanceof Slasher){
					if(this.equals((Blinker)e)){
						gc.setFill(Color.BLACK);
						gc.fillText(Boolean.toString(isBlack), x, y+40);
					}
				}
			}
		}else if(states[1]){
			gc.setFill(Color.GREEN);
			gc.fillRect(x, frameDelay, width, height);
		}else if(states[2]){
			gc.setFill(Color.BLUE);
			gc.fillRect(x, frameDelay, width, height);
		}else if(states[3]){
			gc.setFill(Color.PINK);
			gc.fillRect(x, frameDelay, width, height);
		}else if(states[4]){
			gc.setFill(Color.ORANGE);
			gc.fillRect(x, frameDelay, width, height);
		}else if(states[5]){
			gc.setFill(Color.CYAN);
			gc.fillRect(x, frameDelay, width, height);
		}
		
	}

	@Override
	protected void useSkill() {
		// TODO Auto-generated method stub
		x += directionX * (2/3) * ConfigurableOption.SCREEN_WIDTH;
		if(x >= ConfigurableOption.SCREEN_WIDTH){
			x = ConfigurableOption.SCREEN_WIDTH;
		}
		else if(x <= 0){
			x = 0;
		}
		idle();
	}
}
