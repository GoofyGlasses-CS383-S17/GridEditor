package files;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import gridEditor.common.Color;
import gridEditor.common.Frame;
import gridEditor.common.Node;

public class WriteFile {
	// use array list of the Frame objects
	
	public static void writeFile(String filename, ArrayList<Frame> grid) {
		int gridCount = grid.size();
		Frame oneFrame;
		Node[][] oneNode;
		Color tempColor;
		
		
		//retrieving rows and columns from first Frame in the ArrayList
		oneFrame = new Frame();
		oneFrame = grid.get(0);
		int rows = oneFrame.getHeight();
		int columns = oneFrame.getWidth();
		oneNode = new Node[rows][columns];

		// write to file
		try {
		// open file
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		

		//line 1, 2, 3, 4 fillers for first sprint
		writer.write("0.0 //version number\n");
		writer.write("noAudioFile\n");
		writer.write("0 0 255\n");
		writer.write("0 0 0 0 0 0 0 0 0 0\n");
		
		
		//line 5 number of grids, grid dimensions
		writer.write(Integer.toString(gridCount));
		writer.write(" ");
		writer.write(Integer.toString(rows));
		writer.write(" ");
		writer.write(Integer.toString(columns));
		writer.newLine();
		
		//writing startTime of Frame and Frame Node Color values
		for(int i = 0; i<gridCount; i++) {
			//breaking down Frame to get Color values
			oneFrame = grid.get(i);
			oneNode = oneFrame.getNodeGrid();
			//line for start time
			writer.write(Integer.toString(oneFrame.getDuration()));
			writer.newLine();
			//loop for writing Frame
			for(int j = 0; j<rows; j++) {
				for(int k = 0; k < columns; k++) {
					tempColor = oneNode[j][k].getColor();
					writer.write(Integer.toString(tempColor.getRed()));
					writer.write(" ");
					writer.write(Integer.toString(tempColor.getGreen()));
					writer.write(" ");
					writer.write(Integer.toString(tempColor.getBlue()));
					writer.write(" ");
				}
				writer.newLine();
			}	
		}
		//close file
		writer.close();
		}
		catch (IOException e) {
			System.err.format("Exception occurred trying to write to '%s'.", filename);
			e.printStackTrace();
		}
	}

}

