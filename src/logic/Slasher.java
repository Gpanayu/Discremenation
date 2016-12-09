package logic;

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
	protected int lives;
	protected boolean[] states;
	protected boolean[] prevStates;
	
	
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_UP = -1;
	public static final int DIRECTION_DOWN = 1;
	public static final int NOT_JUMP = 0;
	public static final int INITIAL_SPEED_X = 15;
	public static final int INITIAL_SPEED_Y = 15;
	
	public Slasher(double x, double y, int direction){
		super(x, y);
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
		states[1] = true;
		for(int i = 2; i < states.length ; i++){
			states[i] = false;
		}
		prevStates = new boolean[10];
		prevStates[0] = true;
		prevStates[1] = true;
		for(int i = 2; i < prevStates.length ; i++){
			prevStates[i] = false;
		}
		
		
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
		if(canSlash(other)){
			clearStates();
			states[3] = true;
			if(!other.states[6]){
				other.lives -= 1;
				other.stun();
				other.states[6] = true;
			}
		}
	}
	
	protected boolean canSlash(Slasher other){
		if(!this.states[5] && !this.states[4] && !this.states[2]){
			//check the direction, too.
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
			return false;
		}
	}
	
	protected void jump(){
		x += speedX * directionX;
		if(speedY == 0){
			directionY = DIRECTION_DOWN;
			clearStates();
			states[4] = true;
		}
		if(y >= ConfigurableOption.SCREEN_HEIGHT - height - ConfigurableOption.FLOOR){
			y = ConfigurableOption.SCREEN_HEIGHT - height - ConfigurableOption.FLOOR;
			directionY = NOT_JUMP;
			speedY = 0;
			idle();
		}
		else if(y < 0){
			y = 0;
			clearStates();
			states[4] = true;
		}
		y += speedY * directionY;
		directionY = DIRECTION_UP;
		speedY = INITIAL_SPEED_Y;
	}
	
	protected void run(){
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
		//enter stuff here
		clearStates();
		states[2] = true;
	}
	
	protected void idle(){
		//enter stuff here
		clearStates();
		states[0] = true;
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
	
	public int getLives() {
		return lives;
	}

	protected void setLives(int lives) {
		this.lives = lives;
	}

	
	protected void setIsDead(){
		clearStates();
		this.states[5] = true;
	}
	
	protected boolean getIsDead(){
		return states[5];
	}

	
	public void clearStates(){
		for(int i = 0 ; i <= states.length ; i++){
			states[i] = false;
		}
	}
	
	protected abstract void useSkill();

}
