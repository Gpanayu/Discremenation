package logic;

public abstract class Slasher extends Entity{
	protected int height;
	protected int width;
	protected int directionX;
	protected int directionY;
	protected int speedX;
	protected int speedY;
	protected int accelerationX;
	protected int accelerationY;
	protected boolean isJump;
	
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
//		this.height = 
//		this.width = 
		//INITIAL_SPEED and accelerationX and accelerationY are just dummy data, we have to discuss soon.
		this.speedX = INITIAL_SPEED_X;
		this.speedY = 0;
		this.accelerationX = 5;
		this.accelerationY = 10;
		this.isJump = false;
	}
	
	protected void slash(){
//		if(canSlash()){
			
//		}
	}
	
	protected boolean canSlash(Slasher other){
//		if(direction == DIRECTION_RIGHT && ){
//			
//		}
//		else if(){
//			
//		}
//		We have to know the size of the pictures first.
		return false;
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

	
	protected void jump(){
		isJump = true;
		directionY = DIRECTION_UP;
		speedY = INITIAL_SPEED_Y;
	}
	
	protected void move(){
		if(isJump){
			x += speedX * directionX;
			if(speedY == 0){
				directionY = DIRECTION_DOWN;
			}
			if(y >= 800 - height - 30){
				y = 800 - height - 30;
				isJump = false;
				directionY = NOT_JUMP;
				speedY = 0;
			}
			else if(y < 0){
				y = 0;
			}
			y += speedY * directionY;
		}
		else{
			x += speedX * directionX;
			speedX += accelerationX;
			if(x >= 1500 - width){
				x = 1500 - width;
			}
			else if(x <= 0){
				x = 0;
			}
		}
	}
	
	protected abstract void useSkill();
}
