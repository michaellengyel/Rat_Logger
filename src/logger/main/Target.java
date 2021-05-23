package logger.main;

import java.awt.Color;
import java.awt.Graphics;

public class Target extends Entity{
	
	private int xAdjusted;
	private int yAdjusted;
	
	private int radius;

	public Target(int x, int y, int radius, State state) {
		super(x, y, state);
		this.radius = radius;
		this.state = State.alive;
		xAdjusted = x-radius;
		yAdjusted = y-radius;
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		
		g.setColor(Color.black);
		g.drawString(Integer.toString(30), 5, 15);
		
		if(state == State.alive) {
			g.drawLine(x + radius, y, x - radius, y);
			g.drawLine(x, y + radius, x, y - radius);
			g.drawRect(x - radius/2, y - radius/2, radius, radius);
		}
		
		else if(state == State.dead) {
			g.setColor(new Color(255, 0, 0));
			g.fillRect(x - radius/2, y - radius/2, radius, radius);
		}
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRadius() {
		return radius/2;
	}
}
