package logger.main;

import java.awt.Graphics;
import java.util.Random;

public class Handler {
	
	private int WIDTH = 500;
	private int HEIGHT = 500;
	private int RADIUS = 30;
	
	private Random r = new Random();
	Target target = new Target(WIDTH/2, HEIGHT/2, RADIUS, State.alive);

	double currentPosX;
	double currentPosY;
	
	public void tick() {

		if(target.state == State.dead) {
			target.x = r.nextInt(WIDTH - RADIUS);
			target.y = r.nextInt(HEIGHT - RADIUS);
			target.state = State.alive;
		}
		
	}
	
	public void render(Graphics g) {
		target.render(g);
	}
	
}
