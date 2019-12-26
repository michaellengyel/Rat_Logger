package logger.main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class Handler {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private int counter = 0;
	private int cycles = 30;
	
	LinkedList<Target> targets = new LinkedList<Target>();
	private Random r = new Random();

	double currentPosX;
	double currentPosY;
	
	public void tick() {

		if(counter >= cycles) {
			addEntity(new Target(r.nextInt(WIDTH), r.nextInt(HEIGHT), 1, State.growing));
			counter = 0;
		} else {
			counter++;
		}
		
		for(int i = 0; i < targets.size(); i++) {
			targets.get(i).tick();
			
			// Remove a target if it expires (TODO: Remove a life)
			if(targets.get(i).state == State.dead) {
				removeEntity(targets.get(i));
			}	
			
			// Remove a target if it is clicked on

			if(false) {
				removeEntity(targets.get(i));
			}

		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < targets.size(); i++) {
			Target tempTarget = targets.get(i);
			tempTarget.render(g);
		}
	}
	
	public void addEntity(Target target) {
		this.targets.add(target);
	}

	public void removeEntity(Target target) {
		this.targets.remove(target);
	}
	
	//This is to give the MouseMovementListener information about location of the targets
	public LinkedList<Target> getTargets() {
		return targets;
	}
}
