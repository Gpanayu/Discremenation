package logic;

import javafx.scene.canvas.GraphicsContext;

public class Blinker extends Slasher{
	
	public Blinker(double x, double y, int direction) {
		super(x, y, direction);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
//		if(this.isRun){
//			SlasherAnimation.run(this, x, y);
//		}else if(this.isJump){
//			SlasherAnimation.jump(this,x,y);
//		}else if(this.isSlash){
//			SlasherAnimation.slash(this,x,y);
//		}else if(this.isStun){
//			SlasherAnimation.stun(this,x,y);
//		}else if(this.isDead){
//				
//		}
	}

	@Override
	protected void useSkill() {
		// TODO Auto-generated method stub
		
	}
}
