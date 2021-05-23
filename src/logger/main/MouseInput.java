package logger.main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener{
	
	private Handler handler;
	public static FileWriter fileWriter;
	
	private double absCenterToclickX;
	private double absCenterToclickY;
	private double absCenterToClick;
	
	
	public MouseInput(Handler handler) {
		this.handler = handler;
		fileWriter = new FileWriter("data.txt");
	}

	public void mouseClicked(MouseEvent e) {
		
	}


	public void mouseEntered(MouseEvent e) {
	
	}


	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
		int xTarget = handler.target.getX();
		int yTarget = handler.target.getY();
		
		int xCurser = e.getPoint().x;
		int yCurser = e.getPoint().y;
		
		boolean withinX = Math.abs(xTarget - xCurser) < handler.target.getRadius();
		boolean withinY = Math.abs(yTarget - yCurser) < handler.target.getRadius();
		
		if(withinX && withinY) {
			handler.target.state = State.dead;
			fileWriter.writeClick(xCurser, yCurser);
			//fileWriter.writeTargetCoords(xTarget, yTarget);
			//System.out.println("Hit");
		}
		
		/*
		if(handler.targets.size() > 0) {
			for(int i = 0; i < handler.targets.size(); i++) {
				//mouseWithinRad = (handler.targets.get(i).getX() <= e.getPoint().getX()) && (handler.targets.get(i).getX2() >= e.getPoint().getX());
				absCenterToclickX = Math.abs((double)handler.targets.get(i).getX() - (double)(e.getPoint().getX()));
				absCenterToclickY = Math.abs((double)handler.targets.get(i).getY() - (double)(e.getPoint().getY()));
				absCenterToClick = Math.sqrt(Math.pow(absCenterToclickX, 2)+ Math.pow(absCenterToclickY, 2));
				if(absCenterToClick <= handler.targets.get(i).getRadius()) {
					handler.targets.get(i).state = State.dead;
					fileWriter.writeClick();
					fileWriter.writeAim(absCenterToClick);
					fileWriter.writeTargetCoords(handler.targets.get(i).getX(), handler.targets.get(i).getY());
					System.out.println("Hit");
				}
			}
		}
		*/
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("" + e.getX() +"	"+ e.getY() + "");
		fileWriter.writeCoords(e.getX(), e.getY());
	}

}
