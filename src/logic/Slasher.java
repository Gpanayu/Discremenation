package logic;

import java.util.Arrays;

import input.InputUtility;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import sharedObject.RenderableHolder;

public abstract class Slasher extends Entity{
	protected int directionX;
	protected int directionY;
	protected int speedX;
	protected int speedY;
	protected int accelerationX;
	protected double accelerationY;
	protected boolean[] states;
	protected boolean[] prevStates;
	
	protected int counter;
	protected int runTime;
	protected int immuneCounter;
	protected int slashTime;
	protected int stunTime;
	protected int immuneTime;
	protected boolean isImmune;
	
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_UP = -1;
	public static final int DIRECTION_DOWN = 1;
	public static final int NOT_JUMP = 0;
	public static final int INITIAL_SPEED_X = 1;
	public static final int INITIAL_SPEED_Y = 35;	
	protected boolean canMove;
	
	public Slasher(double x, double y, int direction){
		super(x, y);
		super.z = 5;
		this.directionX = direction;
		this.directionY = NOT_JUMP;
		this.speedX = INITIAL_SPEED_X;
		this.speedY = 0;
		this.accelerationX = 2;
		this.accelerationY = 1.5;
		states = new boolean[10];
		states[0] = true;
		for(int i = 1; i < states.length ; i++){
			states[i] = false;
		}
		this.canMove = true;
		this.isImmune = false;
		prevStates = new boolean[10];
		prevStates[0] = true;
		for(int i = 1; i < prevStates.length ; i++){
			prevStates[i] = false;
		}
		counter = 0;
		immuneCounter = 0;
	}
	
	protected boolean checkSameStates(){
		for(int i = 0 ; i < states.length ; i++){
			if (states[i] != prevStates[i]){
				return false;
			}
		}
		return true;
		
	}
	
	
	protected void slash(Slasher other){
		clearStates();
		states[3] = true;
	
		if(canSlash(other) && !other.states[6]){
			if(!other.isImmune){
				if(other.equals(GameLogic.getPlayer1())){
					GameLogic.getHPPlayer1().decreaseHP(50);
					GameLogic.getGaugePlayer1().increaseGauge(1);
					RenderableHolder.getInstance().getSound("hit_sound").play();

				}
				else {
					GameLogic.getHPPlayer2().decreaseHP(50);
					GameLogic.getGaugePlayer2().increaseGauge(1);
					RenderableHolder.getInstance().getSound("hit_sound").play();
				}
				other.stun();
			}
		}
		this.canMove = false;
	}
	
	protected boolean canSlash(Slasher other){
		if(!this.states[5] && !this.states[4] && !this.states[2]){
			if(this.directionX == Slasher.DIRECTION_RIGHT){
				if(((this.x + ConfigurableOption.DAMAGE_X <= other.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH
						&& other.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH <= this.x + ConfigurableOption.DAMAGE_X+ ConfigurableOption.DAMAGE_WIDTH) 
						|| (this.x + ConfigurableOption.DAMAGE_X <= other.x + ConfigurableOption.HIT_X
								&& other.x + ConfigurableOption.HIT_X <= this.x + ConfigurableOption.DAMAGE_X+ ConfigurableOption.DAMAGE_WIDTH))
						&& ((this.y + ConfigurableOption.DAMAGE_Y <= other.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT
								&& other.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT <= this.y + ConfigurableOption.DAMAGE_Y+ ConfigurableOption.DAMAGE_HEIGHT) 
								|| (this.y + ConfigurableOption.DAMAGE_Y <= other.y + ConfigurableOption.HIT_Y
										&& other.y + ConfigurableOption.HIT_Y <= this.y + ConfigurableOption.DAMAGE_Y+ ConfigurableOption.DAMAGE_HEIGHT))){
				
					return true;
				}
				return false;
			}
			else{
				if(((this.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH - ConfigurableOption.DAMAGE_WIDTH <= other.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH
						&& other.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH <= this.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH) 
						|| (this.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH - ConfigurableOption.DAMAGE_WIDTH <= other.x + ConfigurableOption.HIT_X
								&& other.x + ConfigurableOption.HIT_X <= this.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH))
						&& ((this.y + ConfigurableOption.DAMAGE_Y <= other.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT
								&& other.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT <= this.y + ConfigurableOption.DAMAGE_Y+ ConfigurableOption.DAMAGE_HEIGHT) 
								|| (this.y + ConfigurableOption.DAMAGE_Y <= other.y + ConfigurableOption.HIT_Y
										&& other.y + ConfigurableOption.HIT_Y <= this.y + ConfigurableOption.DAMAGE_Y+ ConfigurableOption.DAMAGE_HEIGHT))){
					return true;
				}
				return false;
				
			}
		}
		else{
			return false;
		}
	}
	
	protected void jump(){
		if(prevStates[0] || prevStates[1]){
			speedY = INITIAL_SPEED_Y;
			directionY = DIRECTION_UP;
			y += speedY * directionY;
			clearStates();
			states[4] = true;
			this.canMove = false;
		}
		else{
			x += speedX * directionX;
			y += speedY * directionY;
			speedY += directionY * accelerationY;
			if(speedY == 0){
				directionY = DIRECTION_DOWN;
				clearStates();
				states[4] = true;
				this.canMove = false;
			}
			if(y >= ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR){
				y = ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR;
				directionY = NOT_JUMP;
				speedY = 0;
				idle();
				this.canMove = true;
			}
			else if(y < 0){
				y = 0;
				clearStates();
				states[4] = true;
				this.canMove = false;
			}
			if(x >= ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH){
				x = ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH;
			}
			else if(x <= 0){
				x = 0;
			}
		}
	}
	
	protected void run(){
		if(!prevStates[1]){
			speedX = INITIAL_SPEED_X;
		}
		x += speedX * directionX;
		speedX += accelerationX;
		clearStates();
		states[1] = true;
		if(x >= ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH){
			x = ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH;
			idle();
		}
		else if(x <= 0){
			x = 0;
			idle();
		}
	}
	
	protected void stun(){
		if(!prevStates[2]){
			for(Entity e : GameLogic.getGameObjectContainer()){
				if(e instanceof Slasher && !e.equals(this)){
					Blinker b = (Blinker)e;
					this.directionX = -b.directionX;
					speedX = 20;
					if(prevStates[4]){
						speedY = 20;//discuss later
						speedX = 10;
						directionY = DIRECTION_UP;
					}
					clearStates();
					states[2] = true;
				}
			}
			isImmune = true;
		}
		x += speedX * (-directionX);
		y += speedY * directionY;
		speedY += directionY * accelerationY;
		if(speedY == 0 && directionY != NOT_JUMP){
			System.out.println("in1");
			directionY = DIRECTION_DOWN;
			clearStates();
			states[2] = true;
			this.canMove = false;
		}
		if(y >= ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR && directionY != NOT_JUMP){
			System.out.println("in2");
			y = ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR;
			directionY = NOT_JUMP;
			speedY = 0;
			idle();
			counter = 0;
			this.canMove = true;
			if(this.equals(GameLogic.getPlayer1())){
				System.out.println("in6");
				if(GameLogic.getHPPlayer1().getHPValue() <= 0){
					System.out.println("in7");
					this.setIsDead();
				}
			}
			else{
				System.out.println("in8");
				if(GameLogic.getHPPlayer2().getHPValue() <= 0){
					System.out.println("in9");
					this.setIsDead();
				}
			}
		}
		else if(y < 0){
			System.out.println("in3");
			y = 0;
			clearStates();
			states[2] = true;
			this.canMove = false;
		}
		if(x >= ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH){
			System.out.println("in4");
//			if(y >= ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR && directionY != NOT_JUMP){
//				System.out.println("in5");
//				y = ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR;
//				directionY = NOT_JUMP;
//				speedY = 0;
//				idle();
//				counter = 0;
//				this.canMove = true;
//			}
			if(directionY != NOT_JUMP){
				clearStates();
				states[2] = true;
			}
			x = ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH;
		}
		else if(x <= 0){
			System.out.println("in10");
			if(directionY != NOT_JUMP){
				System.out.println("in11");
				clearStates();
				states[2] = true;
			}
			x = 0;
		}
	}
	
	protected void idle(){
		speedX = 0;
		speedY = 0;
		y = ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR;
		clearStates();
		states[0] = true;
		this.canMove = true;
	}
	

	public int getDirectionX() {
		return directionX;
	}

	protected void setDirectionX(int directionX) {
		this.directionX = directionX;
	}

	public int getDirectionY() {
		return directionY;
	}

	protected void setDirectionY(int directionY) {
		this.directionY = directionY;
	}

	public int getSpeedX() {
		return speedX;
	}

	protected void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getAccelerationX() {
		return accelerationX;
	}

	protected void setAccelerationX(int accelerationX) {
		this.accelerationX = accelerationX;
	}

	public double getAccelerationY() {
		return accelerationY;
	}

	protected void setAccelerationY(int accelerationY) {
		this.accelerationY = accelerationY;
	}

	protected void update(){
		if(this instanceof Blinker){
			Blinker b = (Blinker)(this);
			b.updateAnimation();
			if(states[2]){
				counter += 1;
				if(counter >= stunTime && directionY == NOT_JUMP){
					if(b.equals(GameLogic.getPlayer1())){
						if(GameLogic.getHPPlayer1().getHPValue() <= 0){
							b.setIsDead();
						}
						else{
							idle();
							counter = 0;
						}
					}
					else{
						if(GameLogic.getHPPlayer2().getHPValue() <= 0){
							b.setIsDead();
						}
						else{
							idle();
							counter = 0;
						}
					}
				}
				else{
					stun();
				}
			}
			else if(prevStates[3]){
				counter += 1;
				clearStates();
				states[3] = true;
				if(counter >= slashTime){
					idle();
					counter = 0;
				}
			}
			if(isImmune){
				immuneCounter += 1;
				if(immuneCounter >= immuneTime){
					isImmune = false;
					immuneCounter = 0;
				}
			}
			if(((InputUtility.getKeyPressed(KeyCode.W) && !b.isBlack()) || (InputUtility.getKeyPressed(KeyCode.UP) && b.isBlack())) && b.canMove || b.prevStates[4]){
				b.jump();
			}
			else if(((InputUtility.getKeyPressed(KeyCode.A) && !b.isBlack()) || (InputUtility.getKeyPressed(KeyCode.LEFT) && b.isBlack())) && b.canMove){
				if(directionX != Slasher.DIRECTION_LEFT){
					directionX = Slasher.DIRECTION_LEFT;
					idle();
				}
				else{
					run();
					counter = 0;
				}
			}
			else if(((InputUtility.getKeyPressed(KeyCode.D) && !b.isBlack()) || (InputUtility.getKeyPressed(KeyCode.RIGHT) && b.isBlack())) && b.canMove){
				if(directionX != Slasher.DIRECTION_RIGHT){
					directionX = Slasher.DIRECTION_RIGHT;
					idle();
				}
				else{
					run();
					counter = 0;
				}
			}
			else if(InputUtility.getKeyTriggered(KeyCode.SPACE) && !b.isBlack() && b.canMove || (prevStates[3] && !b.isBlack())){
				b.slash(GameLogic.getPlayer2());
				RenderableHolder.getInstance().getSound("slash_sound").play();
			}
			else if(InputUtility.getKeyTriggered(KeyCode.ENTER) && b.isBlack() && b.canMove || (prevStates[3] && b.isBlack())){
				b.slash(GameLogic.getPlayer1());
				RenderableHolder.getInstance().getSound("slash_sound").play();

			}
			else if(((InputUtility.getKeyTriggered(KeyCode.ALT) && !b.isBlack()) || (InputUtility.getKeyTriggered(KeyCode.BACK_SLASH) && b.isBlack())) && b.canMove){
				if(this.equals(GameLogic.getPlayer1())){
					if(GameLogic.getGaugePlayer1().getGaugeValue() >= 250){
						x += directionX * ConfigurableOption.SCREEN_WIDTH;
						GameLogic.getGaugePlayer1().decreaseGuage(250);
						RenderableHolder.getInstance().getSound("blink_sound").play();
					}
				}
				else{
					if(GameLogic.getGaugePlayer2().getGaugeValue() >= 250){
						x += directionX * ConfigurableOption.SCREEN_WIDTH;
						GameLogic.getGaugePlayer2().decreaseGuage(250);
						RenderableHolder.getInstance().getSound("blink_sound").play();
					}
				}
				if(x >= ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH){
					x = ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH;
					idle();
				}
				else if(x <= 0){
					x = 0;
					idle();
				}
			}
			else if(!prevStates[5] && !states[2]){
				idle();
			}
		}
		prevStates = states;
	}
	
	protected void setIsDead(){
		clearStates();
		this.states[5] = true;
		canMove = false;
		speedX = 0;
		speedY = 0;
		for(Entity e : GameLogic.getGameObjectContainer()){
			if(e instanceof Slasher && !e.equals(this)){
				if(e.equals(GameLogic.getPlayer1())){
					InputUtility.setKeyPressed(KeyCode.SPACE, false);
				}
				else{
					InputUtility.setKeyPressed(KeyCode.ENTER, false);
				}
			}
		}
	}
	
	protected boolean getIsDead(){
		return states[5];
	}

	
	public void clearStates(){
		for(int i = 0 ; i <= 5 ; i++){
			states[i] = false;
		}
	}
	
	protected abstract void useSkill();

}
