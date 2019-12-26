package logger.main;

import java.awt.Graphics;

public abstract class Entity {

	protected int x, y;
	protected State state;
	
	public Entity(int x, int y, State state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}

}
