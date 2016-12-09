package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sharedObject.RenderableHolder;

public class Blinker extends Slasher{
	private int lives;
	private Image sheet = null;
	private Image yellowSheet;
	private int frameDelay;
	private int currentFrame, frameDelayCount;
	private int x, y, frameWidth, frameHeight;
	private boolean visible = false, playing = false;

	public Blinker (int x, int y, int direction, boolean isBlack){
		super(x, y, direction);
		if(isBlack){
			this.sheet = RenderableHolder.blackBlinkerSheet;
		}else{
			this.sheet = RenderableHolder.whiteBlinkerSheet;
		}
		this.yellowSheet = RenderableHolder.yellowBlinkerSheet;
		this.frameDelay = frameDelay;
		this.currentFrame = 0;
		this.frameDelayCount = 0;
		this.x = 0;
		this.y = 0;
		if(this.sheet == null){
			this.frameWidth = 0;
			this.frameHeight = 0;
		}else{
			this.frameWidth = (int)sheet.getWidth()/7;
			this.frameHeight = (int)sheet.getHeight()/3;
		}
		
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
			
		}else if(states[1]){
			
		}else if(states[2]){
			
		}else if(states[3]){
			
		}else if(states[4]){
			
		}else if(states[5]){
			
		}
		
	}

	@Override
	protected void useSkill() {
		// TODO Auto-generated method stub
		
	}
}
