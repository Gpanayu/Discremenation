package logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import sharedObject.RenderableHolder;

public class Blinker extends Slasher{
	private int frameDelay = 1;
	private int frameDelayCount;
	private boolean isBlack;
	private boolean visible = false, playing = false;
	private static double frameWidth;
	private static double frameHeight;
	private int currentRunFrame, currentSlashFrame, currentJumpFrame;

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
		frameWidth = this.rightSheet.getWidth() / 17;
		frameHeight = this.rightSheet.getHeight();
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
  		this.frameDelayCount = 0;
  		super.runTime = 6;
  		super.slashTime = 5;
  		super.stunTime = 10;
  		super.immuneTime = 35;
		
	}
	
	public boolean isBlack() {
		return isBlack;
	}
	
	public void updateAnimation() {
		if(prevStates[1]){ 
			if(!this.checkSameStates()){
				   currentRunFrame = 0;
			  }
			  if(this.frameDelayCount < this.frameDelay){
				  this.frameDelayCount ++;
				  return;
			  }
			  else{
				  currentRunFrame += 1;
				  this.frameDelayCount = 0;
				  if(currentRunFrame == runTime){ 
					  currentRunFrame = 0; 
				  }
			}
		}		
		else if(prevStates[3]){
			currentSlashFrame += 1;
			if(currentSlashFrame == slashTime){
				currentSlashFrame = 0;
			}
		}
		else if(states[4]){
			if(!this.checkSameStates()){	
				   currentJumpFrame = 0;
				   frameDelayCount = 0;
			  }
			  if (frameDelayCount < frameDelay){
				  frameDelayCount ++;
				  return;
			  }
			  else{
				  currentJumpFrame ++;
				  if(currentJumpFrame >= 2){
				  	currentJumpFrame = 2;
				  }	
			  }	  	
		}
		if(!states[4]){
			  this.currentJumpFrame = 0;
		  }
	}


	public static double getFrameWidth() {
		return frameWidth;
	}

	public static double getFrameHeight() {
		return frameHeight;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (this.isImmune){
			this.Right = this.yellowRight;
			this.Left = this.yellowLeft;
		}
		else if (!this.isImmune){
				this.Right = this.normRight;
				this.Left = this.normLeft;
		}
		if (states[0]){
			if(this.directionX == Slasher.DIRECTION_RIGHT){
				gc.drawImage(Right[0], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
			else{
				gc.drawImage(Left[0], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
		}
		else if(states[1]){
			if(this.directionX == Slasher.DIRECTION_RIGHT){
				gc.drawImage(Right[this.currentRunFrame + 2], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
			else{
				gc.drawImage(Left[this.currentRunFrame + 2], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
		}
		else if(states[2]){
			if (this.directionX == Slasher.DIRECTION_RIGHT){
				gc.drawImage(Right[1],  this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
			else{
				gc.drawImage(Left[1], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
		}
		else if(states[3]){
			if (this.directionX == Slasher.DIRECTION_RIGHT){
				gc.drawImage(Right[this.currentSlashFrame + 8] , this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
			else{
				gc.drawImage(Left[this.currentSlashFrame + 8], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
		}
		else if(states[4]){
			if (this.directionX == Slasher.DIRECTION_RIGHT){
				gc.drawImage(Right[this.currentJumpFrame + 13], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
			else{
				gc.drawImage(Left[this.currentJumpFrame + 13], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
		}
		else if(states[5]){
			if (this.directionX == Slasher.DIRECTION_RIGHT){
				gc.drawImage(Right[16], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}else{
				gc.drawImage(Left[16], this.x - ConfigurableOption.HIT_X, this.y - ConfigurableOption.HIT_Y);
			}
		}
	}

	@Override
	protected void useSkill() {
		// TODO Auto-generated method stub
		if(this.equals(GameLogic.getPlayer1())){
			if(GameLogic.getGaugePlayer1().getGaugeValue() >= 250){
				RenderableHolder.getInstance().getSound("blink_sound").play();
				x += directionX * ConfigurableOption.SCREEN_WIDTH;
				if(!checkXInBoundary()){
					setXInBoundary();
				}
				idle();
				GameLogic.getGaugePlayer1().decreaseGuage(250);
			}
		}
		else{
			if(GameLogic.getGaugePlayer2().getGaugeValue() >= 250){
				RenderableHolder.getInstance().getSound("blink_sound").play();
				x += directionX * ConfigurableOption.SCREEN_WIDTH;
				if(!checkXInBoundary()){
					setXInBoundary();
				}
				idle();
				GameLogic.getGaugePlayer2().decreaseGuage(250);
			}
		}
	}
}
