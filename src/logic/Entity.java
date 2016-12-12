package logic;

import sharedObject.IRenderable;

public abstract class Entity implements IRenderable{

	protected double x,y;
	protected int z;
	protected boolean visible,destroyed;
	
	protected Entity(double x, double y){
		this.x = x;
		this.y = y;
		visible = true;
		destroyed = false;
	}
	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}
	
	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return destroyed;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}
}
