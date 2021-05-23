package logger.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

//https://www.youtube.com/watch?v=1gir2R7G9ws

public class Game extends Canvas implements Runnable{
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private Thread thread;
	private Boolean running = false;
	
	private Handler handler;
	private Random r;

	public Game() {
		handler = new Handler();
		r = new Random();

		//this.addKeyListener(new KeyInput());
		this.addMouseListener(new MouseInput(handler));
		this.addMouseMotionListener(new MouseInput(handler));
		
		new Window(WIDTH, HEIGHT,"RatLogger", this);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		}catch (Exception e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				render();
				frames++;
			}
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game();
	}

}
