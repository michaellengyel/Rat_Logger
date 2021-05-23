package logger.main;

import java.util.Formatter;

public class FileWriter {
	
	private Formatter formatter;
	
	public FileWriter(String filename) {
		try {
			formatter = new Formatter(filename);
		} catch (Exception e) {
			System.out.println("Could not make output file.");
			System.exit(0);
		}
	}
	
	public void writeCoords(int dataX, int dataY) {
		formatter.format("%d%c%d%c", dataX, ';', dataY, ';');
		formatter.flush();
	}
	
	public void writeAim(double aim) {
		formatter.format("%f%n", aim, "\n");
		formatter.flush();
	}
	
	public void writeClick(int dataX, int dataY) {
		formatter.format("%n","\n");
		//formatter.format("%s%d%c%d%n", "Click: ", dataX, ';', dataY, "\n");
		formatter.flush();
	}
	
	public void writeTargetCoords(int dataX, int dataY) {
		formatter.format("%s%d%c%d%n", "Target: ", dataX, ';', dataY, "\n");
		formatter.flush();
	}
	
	public void closeFile() {
		formatter.close();
	}

}
