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
		//formatter.format("%d%c%d%n", dataX, '\t', dataY, '\n');
		formatter.format("%d%c%d%n", dataX, '\t', dataY, '\n');
		formatter.flush();
	}
	
	public void writeAim(double aim) {
		formatter.format("%f%n", aim, "\n");
		formatter.flush();
	}
	
	public void writeClick() {
		formatter.format("%s%n", "CLICK", "\n");
		formatter.flush();
	}
	
	public void closeFile() {
		formatter.close();
	}

}
