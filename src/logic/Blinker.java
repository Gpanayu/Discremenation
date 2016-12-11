package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import main.Main;
import sharedObject.RenderableHolder;

public class Blinker extends Slasher{
	private int frameDelay;
	private int currentFrame, frameDelayCount;
	private int x, y, frameWidth, frameHeight;
	private boolean isBlack;
	private boolean visible = false, playing = false;
	private Image rightSheet, leftSheet, yellowRightSheet, yellowLeftSheet;
	private Image[] normRight = new Image[17];
	private Image[] normLeft = new Image[17];
	private Image[] yellowRight = new Image[17];
	private Image[] yellowLeft = new Image[17];
	private Image[] Right = new Image[17];
	private Image[] Left = new Image[17];

	public Blinker (int x, int y, int direction, boolean isBlack){
		super(x, y, direction);
		this.isBlack = isBlack;
		
		
		if(isBlack){
			this.rightSheet = RenderableHolder.blackRight;
			this.leftSheet = RenderableHolder.blackLeft;
		}else{
			this.rightSheet = RenderableHolder.whiteRight;
			this.leftSheet = RenderableHolder.whiteLeft;
		}

		yellowRightSheet = RenderableHolder.yellowRight;
		yellowLeftSheet = RenderableHolder.yellowLeft;
		double frameWidth = this.rightSheet.getWidth() / 17;
		double frameHeight = this.rightSheet.getHeight();
		
		for(int i = 0 ; i < 17 ; i++){
			WritableImage cropped1 = new WritableImage(rightSheet.getPixelReader(),(int)(i * frameWidth), 0,(int)frameWidth, (int)frameHeight);
			normRight[i] = cropped1;
			WritableImage cropped2 = new WritableImage(leftSheet.getPixelReader(),(int)(i * frameWidth), 0,(int)frameWidth, (int)frameHeight);
			normLeft[i] = cropped2;
			WritableImage cropped3 = new WritableImage(yellowRightSheet.getPixelReader(),(int)(i * frameWidth), 0,(int)frameWidth, (int)frameHeight);
			yellowRight[i] = cropped3;
			WritableImage cropped4 = new WritableImage(yellowLeftSheet.getPixelReader(),(int)(i * frameWidth), 0,(int)frameWidth, (int)frameHeight);
			yellowLeft[i] = cropped4;

		}
		
		
		this.frameDelay = 1;
		this.currentFrame = 0;
		this.frameDelayCount = 0;
		this.x = 0;
		this.y = 0;
		super.runTime = 6;
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
		
		if(prevStates[1]){ 
			if(!this.checkSameStates()){
				currentFrame = 0;
			}
			currentFrame += 1;
			if(currentFrame == runTime){ 
				currentFrame = 0; 
			}
		}
		else if(prevStates[2]){
			currentFrame += 1;
			if(currentFrame == stunTime){
				currentFrame = 0;
			}
		}
		
		
		else if(prevStates[3]){
			currentFrame += 1;
			if(currentFrame == slashTime){
				currentFrame = 0;
			}
		}
		
		else if(prevStates[4]){
			currentFrame += 1;
			if(currentFrame >= 2){
				currentFrame = 2;
			}
		}
		
	}



	@Override
	public void draw(GraphicsContext gc) {
		if (this.isImmune){
			this.Right = this.yellowRight;
			this.Left = this.yellowLeft;
		}else if (!this.isImmune){
			this.Right = this.normRight;
			this.Left = this.normLeft;
		}
		if (states[0]){
			if(this.directionX == this.DIRECTION_RIGHT){
				gc.drawImage(Right[0],this.x - 214.1, this.y - 149);
			}else{
				gc.drawImage(Left[0],this.x - 214.1, this.y - 149);
			}
		}else if(states[1]){
			if(this.directionX == this.DIRECTION_RIGHT){
				gc.drawImage(Right[this.currentFrame + 2],this.x - 214.1, this.y - 149);
			}else{
				gc.drawImage(Left[this.currentFrame + 2],this.x - 214.1, this.y - 149);
			}
		}else if(states[2]){
			gc.setFill(Color.BLUE);
			gc.fillRect(x, frameDelay, width, height);
		}else if(states[3]){
			if (this.directionX == this.DIRECTION_RIGHT){
				gc.drawImage(Right[this.currentFrame + 8] , this.x - 214.1, this.y - 149);
			}else{
				gc.drawImage(Left[this.currentFrame +8], this.x - 214.1, this.y - 149);
			}
		}else if(states[4]){
			if (this.directionX == this.DIRECTION_RIGHT){
				gc.drawImage(Right[this.currentFrame + 13], this.x - 214.1, this.y - 149);
			}else{
				gc.drawImage(Left[this.currentFrame + 13], this.x - 214.1, this.y - 149);
			}
		}else if(states[5]){
			if (this.directionX == this.DIRECTION_RIGHT){
				gc.drawImage(Right[16], this.x - 214.1, this.y - 149);
			}else{
				gc.drawImage(Left[16], this.x - 214.1, this.y - 149);
			}
		}
		 
//		if (states[0]){
//			gc.setFill(Color.RED);
//			gc.fillRect(x, frameDelay, width, height);
//			for(Entity e : GameLogic.getGameObjectContainer()){
//				if(e instanceof Slasher){
//					if(this.equals((Blinker)e)){
//						gc.setFill(Color.BLACK);
//						gc.fillText(Boolean.toString(isBlack), x, y+40);
//					}
//				}
//			}
//		}else if(states[1]){
//			gc.setFill(Color.GREEN);
//			gc.fillRect(x, frameDelay, width, height);
//		}else if(states[2]){
//			gc.setFill(Color.BLUE);
//			gc.fillRect(x, frameDelay, width, height);
//		}else if(states[3]){
//			gc.setFill(Color.PINK);
//			gc.fillRect(x, frameDelay, width, height);
//		}else if(states[4]){
//			gc.setFill(Color.ORANGE);
//			gc.fillRect(x, frameDelay, width, height);
//		}else if(states[5]){
//			gc.setFill(Color.CYAN);
//			gc.fillRect(x, frameDelay, width, height);
//		}
		
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
