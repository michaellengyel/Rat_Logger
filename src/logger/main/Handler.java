package logger.main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class Handler {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private Random r = new Random();
	Target target = new Target(r.nextInt(WIDTH), r.nextInt(HEIGHT), 30, State.alive);

	double currentPosX;
	double currentPosY;
	
	public void tick() {

		if(target.state == State.dead) {
			target.x = r.nextInt(WIDTH);
			target.y = r.nextInt(HEIGHT);
			target.state = State.alive;
		}
		
	}
	
	public void render(Graphics g) {
		target.render(g);
	}
	
}
