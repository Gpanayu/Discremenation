package logic;

import input.InputUtility;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;

public abstract class Slasher extends Entity{
	protected int height;
	protected int width;
	protected int directionX;
	protected int directionY;
	protected int speedX;
	protected int speedY;
	protected int accelerationX;
	protected int accelerationY;
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
	public static final int INITIAL_SPEED_X = 15;
	public static final int INITIAL_SPEED_Y = 15;	
	protected boolean canMove;
	
	public Slasher(double x, double y, int direction){
		super(x, y);
		super.z = 5;
		this.directionX = direction;
		this.directionY = NOT_JUMP;
		this.height = ConfigurableOption.SLASHER_HEIGHT;
		this.width = ConfigurableOption.SLASHER_WIDTH;
		//INITIAL_SPEED and accelerationX and accelerationY are just dummy data, we have to discuss soon.
		this.speedX = INITIAL_SPEED_X;
		this.speedY = 0;
		this.accelerationX = 5;
		this.accelerationY = 10;
		states = new boolean[10];
		states[0] = true;
		for(int i = 2; i < states.length ; i++){
			states[i] = false;
		}
		this.canMove = true;
		this.isImmune = false;
		prevStates = new boolean[10];
		prevStates[0] = true;
		for(int i = 2; i < prevStates.length ; i++){
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
					GameLogic.getHPPlayer1().decreaseHP(1);
					GameLogic.getGaugePlayer1().increaseGauge(1);
				}
				else{
					GameLogic.getHPPlayer2().decreaseHP(1);
					GameLogic.getGaugePlayer2().increaseGauge(1);
				}
				other.stun();
			}
		}
		this.canMove = false; //change
	}
	
	protected boolean canSlash(Slasher other){
		if(!this.states[5] && !this.states[4] && !this.states[2]){
			if(this.directionX == Slasher.DIRECTION_RIGHT){
				if(((this.x + ConfigurableOption.HIT_X < other.x + ConfigurableOption.DAMAGE_X &&
						this.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH < other.x + ConfigurableOption.DAMAGE_X + ConfigurableOption.DAMAGE_WIDTH) ||
					(other.x + ConfigurableOption.DAMAGE_X < this.x + ConfigurableOption.HIT_X &&
						this.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH < other.x + ConfigurableOption.DAMAGE_X + ConfigurableOption.DAMAGE_WIDTH) ||
					(this.x + ConfigurableOption.DAMAGE_X < other.x + ConfigurableOption.HIT_X &&
						other.x + ConfigurableOption.DAMAGE_X + ConfigurableOption.DAMAGE_WIDTH < this.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH))&&
					((this.y + ConfigurableOption.HIT_Y < other.y + ConfigurableOption.DAMAGE_Y &&
						this.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT < other.y +ConfigurableOption.DAMAGE_Y + ConfigurableOption.DAMAGE_HEIGHT) || 
						(other.y + ConfigurableOption.DAMAGE_Y < this.y + ConfigurableOption.HIT_Y &&
						this.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT < other.y + ConfigurableOption.DAMAGE_Y + ConfigurableOption.DAMAGE_HEIGHT) ||
					(this.y + ConfigurableOption.DAMAGE_Y < other.y + ConfigurableOption.HIT_Y &&
						other.y + ConfigurableOption.DAMAGE_Y + ConfigurableOption.DAMAGE_HEIGHT < this.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT))){
				
					return true;
				}
				return false;
			}
			else{
				if(((this.x + ConfigurableOption.HIT_X < other.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH - ConfigurableOption.DAMAGE_WIDTH &&
						this.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH < other.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH) ||
					(other.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH - ConfigurableOption.DAMAGE_WIDTH < this.x + ConfigurableOption.HIT_X &&
						this.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH < other.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH) ||
					(this.x + ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH - ConfigurableOption.DAMAGE_WIDTH < other.x + ConfigurableOption.HIT_X &&
						other.x + ConfigurableOption.DAMAGE_X + ConfigurableOption.HIT_WIDTH < this.x + ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH))&&
					((this.y + ConfigurableOption.HIT_Y < other.y + ConfigurableOption.DAMAGE_Y &&
						this.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT < other.y +ConfigurableOption.DAMAGE_Y + ConfigurableOption.DAMAGE_HEIGHT) || 
						(other.y + ConfigurableOption.DAMAGE_Y < this.y + ConfigurableOption.HIT_Y &&
						this.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT < other.y + ConfigurableOption.DAMAGE_Y + ConfigurableOption.DAMAGE_HEIGHT) ||
					(this.y + ConfigurableOption.DAMAGE_Y < other.y + ConfigurableOption.HIT_Y &&
						other.y + ConfigurableOption.DAMAGE_Y + ConfigurableOption.DAMAGE_HEIGHT < this.y + ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT))){
				
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
		if(prevStates[1]){
			x += speedX * directionX;
			speedY = speedX;
			directionY = DIRECTION_UP;
			y += speedY * directionY;
			clearStates();
			states[4] = true;
			this.canMove = false;
		}
		else if(prevStates[0]){
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
			speedY += directionY;
			if(speedY == 0){
				directionY = DIRECTION_DOWN;
				clearStates();
				states[4] = true;
				this.canMove = false;
			}
			if(y >= ConfigurableOption.SCREEN_HEIGHT - height - ConfigurableOption.FLOOR){
				y = ConfigurableOption.SCREEN_HEIGHT - height - ConfigurableOption.FLOOR;
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
			if(x >= ConfigurableOption.SCREEN_WIDTH - width){
				x = ConfigurableOption.SCREEN_WIDTH - width;
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
		if(x >= ConfigurableOption.SCREEN_WIDTH - width){
			x = ConfigurableOption.SCREEN_WIDTH - width;
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
					speedX = INITIAL_SPEED_X;
				}
			}
			isImmune = true;
		}
		x += speedX * (-directionX);
		if(x >= ConfigurableOption.SCREEN_WIDTH - width){
			x = ConfigurableOption.SCREEN_WIDTH - width;
		}
		else if(x <= 0){
			x = 0;
		}
		clearStates();
		states[2] = true;
		this.canMove = false; // change
	}
	
	protected void idle(){
		speedX = 0;
		speedY = 0;
		y = ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR - height;
		clearStates();
		states[0] = true;
		this.canMove = true;
	}
	
	public int getHeight() {
		return height;
	}


	public int getWidth() {
		return width;
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

	public int getAccelerationY() {
		return accelerationY;
	}

	protected void setAccelerationY(int accelerationY) {
		this.accelerationY = accelerationY;
	}

	protected void update(){
		if(this instanceof Blinker){
			Blinker b = (Blinker)(this);
//			b.updateAnimation();
			if(states[2]){
				counter += 1;
				if(counter == stunTime){
					idle();
					counter = 0;
				}
				else{
					stun();
				}
			}
			else if(prevStates[3]){
				counter += 1;
				clearStates();
				states[3] = true;
				if(counter == slashTime){
					idle();
					counter = 0;
				}
			}
			if(isImmune){
				immuneCounter += 1;
				if(immuneCounter == immuneTime){
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
			else if(InputUtility.getKeyPressed(KeyCode.SPACE) && !b.isBlack() && b.canMove || prevStates[3]){
				b.slash(GameLogic.getPlayer2());
			}
			else if(InputUtility.getKeyPressed(KeyCode.ENTER) && b.isBlack() && b.canMove || prevStates[3]){
				b.slash(GameLogic.getPlayer1());
			}
			else if(((InputUtility.getKeyPressed(KeyCode.ALT) && !b.isBlack()) || (InputUtility.getKeyPressed(KeyCode.BACK_SLASH) && b.isBlack())) && b.canMove){
//				use special power
			}
			else{
				idle();
			}
		}
		prevStates = states;
	}
	
	protected void setIsDead(){
		clearStates();
		this.states[5] = true;
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
