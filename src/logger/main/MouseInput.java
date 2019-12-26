package logger.main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener{
	
	private Handler handler;
	private static FileWriter fileWriter;
	
	private double absCenterToclickX;
	private double absCenterToclickY;
	private double absCenterToClick;
	
	private Boolean mouseWithinRad;
	
	private String temp;
	
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
		for(int i = 0; i < handler.targets.size(); i++) {
			//mouseWithinRad = (handler.targets.get(i).getX() <= e.getPoint().getX()) && (handler.targets.get(i).getX2() >= e.getPoint().getX());
			absCenterToclickX = Math.abs((double)handler.targets.get(i).getX() - (double)(e.getPoint().getX()));
			absCenterToclickY = Math.abs((double)handler.targets.get(i).getY() - (double)(e.getPoint().getY()));
			absCenterToClick = Math.sqrt(Math.pow(absCenterToclickX, 2)+ Math.pow(absCenterToclickY, 2));
			if(absCenterToClick <= handler.targets.get(i).getRadius()) {
				handler.targets.get(i).state = State.destructing;
				fileWriter.writeClick();
				fileWriter.writeAim(absCenterToClick);
				//System.out.println("Hit");
			}
		}
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
