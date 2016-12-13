package logic;

import input.InputUtility;
import javafx.scene.input.KeyCode;
import lib.ConfigurableOption;

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
	public static final int INITIAL_SPEED_X = 2;
	public static final int INITIAL_SPEED_Y = 30;	
	protected boolean canMove;
	
	public Slasher(double x, double y, int direction){
		super(x, y);
		super.z = 5;
		this.directionX = direction;
		this.directionY = NOT_JUMP;
		this.speedX = INITIAL_SPEED_X;
		this.speedY = 0;
		this.accelerationX = 3;
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
				}
				else {
					GameLogic.getHPPlayer2().decreaseHP(50);
					GameLogic.getGaugePlayer2().increaseGauge(1);
				}
				other.stun();
			}
		}
		this.canMove = false;
	}
	
	protected boolean canSlash(Slasher other){
		if(!this.states[5] && !this.states[4] && !this.states[2]){
			double rightDirectionLeftDamageIndent = ConfigurableOption.DAMAGE_X;
			double rightDirectionRightDamageIndent = ConfigurableOption.DAMAGE_X + ConfigurableOption.DAMAGE_WIDTH;
			double leftDirectionLeftDamageIndent = ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH - ConfigurableOption.DAMAGE_WIDTH;
			double leftDirectionRightDamageIndent = ConfigurableOption.DAMAGE_X - ConfigurableOption.HIT_WIDTH;
			double leftHitIndent = ConfigurableOption.HIT_X;
			double rightHitIndent = ConfigurableOption.HIT_X + ConfigurableOption.HIT_WIDTH;
			double topDamageIndent = ConfigurableOption.DAMAGE_Y;
			double bottomDamageIndent = ConfigurableOption.DAMAGE_Y + ConfigurableOption.DAMAGE_HEIGHT;
			double topHitIndent = ConfigurableOption.HIT_Y;
			double bottomHitIndent = ConfigurableOption.HIT_Y + ConfigurableOption.HIT_HEIGHT;
			
			if(this.directionX == Slasher.DIRECTION_RIGHT){
				if(((this.x + rightDirectionLeftDamageIndent <= other.x + rightHitIndent && other.x + rightHitIndent <= this.x + rightDirectionRightDamageIndent) 
					|| (this.x + rightDirectionLeftDamageIndent <= other.x + leftHitIndent && other.x + leftHitIndent <= this.x + rightDirectionRightDamageIndent))
					&& ((this.y + topDamageIndent <= other.y + bottomHitIndent && other.y + bottomHitIndent <= this.y + bottomDamageIndent) 
					|| (this.y + topDamageIndent <= other.y + topHitIndent && other.y + topHitIndent <= this.y + bottomDamageIndent))){
				
					return true;
				}
				return false;
			}
			else{
				if(((this.x + leftDirectionLeftDamageIndent <= other.x + rightHitIndent	&& other.x + rightHitIndent <= this.x + leftDirectionRightDamageIndent) 
					|| (this.x + leftDirectionLeftDamageIndent <= other.x + leftHitIndent && other.x + leftHitIndent <= this.x + leftDirectionRightDamageIndent))
					&& ((this.y + topDamageIndent <= other.y + bottomHitIndent && other.y + bottomHitIndent <= this.y + bottomDamageIndent) 
					|| (this.y + topDamageIndent <= other.y + topHitIndent && other.y + topHitIndent <= this.y + bottomDamageIndent))){
					
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
			
			if(!checkYInBoundary()){
				setYInBoundary();
				canMove = false;
				clearStates();
				states[4] = true;
				this.canMove = false;
				if(y >= ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR){
					directionY = NOT_JUMP;
					speedY = 0;
					idle();
					canMove = true;
					
				}
			}
			if(!checkXInBoundary()){
				setXInBoundary();
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
		if(!checkXInBoundary()){
			setXInBoundary();
			idle();
		}
	}
	
	protected void stun(){
		if(!prevStates[2]){
			speedX = 5;
			if(this.equals(GameLogic.getPlayer1())){
				this.directionX = -GameLogic.getPlayer2().directionX;
			}
			else{
				this.directionX = -GameLogic.getPlayer1().directionX;
			}
			if(prevStates[4]){
				speedY = 20;
				speedX = 10;
				directionY = DIRECTION_UP;
			}
			clearStates();
			states[2] = true;
			canMove = false;
			isImmune = true;
		}
		x += speedX * (-directionX);
		y += speedY * directionY;
		speedY += directionY * accelerationY;
		if(speedY == 0 && directionY != NOT_JUMP){
			directionY = DIRECTION_DOWN;
			clearStates();
			states[2] = true;
			this.canMove = false;
		}
		if(!checkYInBoundary()){
			setYInBoundary();
			clearStates();
			states[2] = true;
			this.canMove = false;
			if(y >= ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR && directionY != NOT_JUMP){
				directionY = NOT_JUMP;
				speedY = 0;
				idle();
				counter = 0;
				canMove = true;
				controlIsDead();
			}
			
		}
		if(!checkXInBoundary()){
			if(directionY != NOT_JUMP){
				clearStates();
				states[2] = true;
			}
			setXInBoundary();
		}

	}
	
	public void controlIsDead(){
		if(this.equals(GameLogic.getPlayer1())){
			if(GameLogic.getHPPlayer1().getHPValue() <= 0){
				this.setIsDead();
			}
		}
		else{
			if(GameLogic.getHPPlayer2().getHPValue() <= 0){
				this.setIsDead();
			}
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
	
	public boolean checkXInBoundary(){
		if(x <= 0 || x >= ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH){
			return false;
		}
		return true;
	}

	public void setXInBoundary(){
		if(x <= 0){
			x = 0;
		}
		else {
			x = ConfigurableOption.SCREEN_WIDTH - ConfigurableOption.HIT_WIDTH;
		}
	}
	
	public boolean checkYInBoundary(){
		if(y <= 0 || y >= ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR){
			return false;
		}
		return true;
	}
	
	public void setYInBoundary(){
		if(y <= 0){
			y = 0;
		}
		else {
			y = ConfigurableOption.SCREEN_HEIGHT - ConfigurableOption.FLOOR;
		}
	}

	protected void update(){
		if(this instanceof Blinker){
			Blinker b = (Blinker)(this);
			b.updateAnimation();
			if(states[2]){
				counter += 1;
				if(counter >= stunTime && directionY == NOT_JUMP){
					idle();
					counter = 0;
					controlIsDead();
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
			inputAndLoopControl();
		}
		prevStates = states;
	}
	
	private void inputAndLoopControl(){
		if(((InputUtility.getKeyPressed(KeyCode.W) && this.equals(GameLogic.getPlayer1())) || (InputUtility.getKeyPressed(KeyCode.UP) && this.equals(GameLogic.getPlayer2()))) && canMove || prevStates[4]){
			jump();
		}
		else if(((InputUtility.getKeyPressed(KeyCode.A) && this.equals(GameLogic.getPlayer1())) || (InputUtility.getKeyPressed(KeyCode.LEFT) && this.equals(GameLogic.getPlayer2()))) && canMove){
			if(directionX != Slasher.DIRECTION_LEFT){
				directionX = Slasher.DIRECTION_LEFT;
				idle();
			}
			else{
				run();
				counter = 0;
			}
		}
		else if(((InputUtility.getKeyPressed(KeyCode.D) && this.equals(GameLogic.getPlayer1())) || (InputUtility.getKeyPressed(KeyCode.RIGHT) && this.equals(GameLogic.getPlayer2()))) && canMove){
			if(directionX != Slasher.DIRECTION_RIGHT){
				directionX = Slasher.DIRECTION_RIGHT;
				idle();
			}
			else{
				run();
				counter = 0;
			}
		}
		else if(InputUtility.getKeyTriggered(KeyCode.SPACE) && this.equals(GameLogic.getPlayer1()) && canMove || (prevStates[3] && this.equals(GameLogic.getPlayer1()))){
			slash(GameLogic.getPlayer2());
		}
		else if(InputUtility.getKeyTriggered(KeyCode.ENTER) && this.equals(GameLogic.getPlayer2()) && canMove || (prevStates[3] && this.equals(GameLogic.getPlayer2()))){
			slash(GameLogic.getPlayer1());
		}
		else if(((InputUtility.getKeyTriggered(KeyCode.ALT) && this.equals(GameLogic.getPlayer1())) || (InputUtility.getKeyTriggered(KeyCode.BACK_SLASH) && this.equals(GameLogic.getPlayer2()))) && canMove){
			useSkill();
		}
		else if(!prevStates[5] && !states[2]){
			idle();
		}
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
