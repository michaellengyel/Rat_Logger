package logger.main;

import java.awt.Color;
import java.awt.Graphics;

public class Target extends Entity{
	
	private int xAdjusted;
	private int yAdjusted;
	
	private int radius;
	private final int MAXSIZE = 64;
	private final int MINSIZE = 0;
	
	private static int counter;
	
	//private Boolean growing;

	public Target(int x, int y, int radius, State state) {
		super(x, y, state);
		this.radius = radius;
		this.state = State.growing;
		xAdjusted = x-radius;
		yAdjusted = y-radius;
	}

	public void tick() {
		
		if(state == State.growing) {
			radius+=1;
			xAdjusted = x-radius/2;
			yAdjusted = y-radius/2;
			if(radius >= MAXSIZE) {
				state = State.shrinking;
			}
		}
		
		if(state == State.shrinking) {
			radius-=1;
			xAdjusted = x-radius/2;
			yAdjusted = y-radius/2;
			if(radius < MINSIZE) {
				state = state.dead;
			}
		}
		
		if(state == State.destructing) {
			radius-=5;
			xAdjusted = x-radius/2;
			yAdjusted = y-radius/2;
			if(radius < MINSIZE) {
				counter++;
				state = state.dead;
			}
		}
		
	}

	public void render(Graphics g) {
		//g.setColor(Color.green);
		//g.fillOval(xAdjusted, yAdjusted, radius, radius);
		
		g.setColor(Color.black);
		g.drawString(Integer.toString(counter), 5, 15);
		
		if(state == State.growing || state == State.shrinking) {
			g.setColor(new Color(50, 50, 50));
			g.fillOval(xAdjusted, yAdjusted, radius, radius);
			//g.setColor(new Color(220, 220, 220));
			//g.drawOval(xAdjusted+(3*radius/8), yAdjusted+(3*radius/8), radius/4, radius/4);
			//g.drawOval(xAdjusted+(radius/4), yAdjusted+(radius/4), radius/2, radius/2);
			//g.drawOval(xAdjusted+(radius/8), yAdjusted+(radius/8), 3*radius/4, 3*radius/4);
		}
		
		
		if(state == State.destructing) {
			g.setColor(new Color(255, 0, 0));
			g.fillOval(xAdjusted, yAdjusted, radius, radius);
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
