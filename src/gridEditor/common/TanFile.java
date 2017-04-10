package gridEditor.common;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TanFile {

	public static ArrayList<Frame> readFile(File filename) {
		String line;
		String[] tempArray;
		Frame tempFrame;
		Node tempNode;
		Node[][] tempNodeArr;
		Color tempColor;
		ArrayList<Frame> grid = new ArrayList<Frame>();
 
		try 
		{
			// Open File
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			// Read by lines (storing first 4 lines to variable for future use)
			String version = reader.readLine();
			String audio = reader.readLine();
			String currentColor = reader.readLine();
			String previousColors = reader.readLine();
			
			// line containing: numberOfGrids gridRowCount gridColumnCount
			line = reader.readLine();
			tempArray = line.split(" ");
			int gridCount = Integer.parseInt(tempArray[0]);
			int rows = Integer.parseInt(tempArray[1]);
			int columns = Integer.parseInt(tempArray[2]);
			
			
			
			// read in each grid, save startTime and nodes to a frame to the ArrayList
			for(int i=0; i<gridCount; i++) {
				line = reader.readLine();
				tempFrame = new Frame();
				tempFrame.setStartingTime(Integer.parseInt(line));
				tempNodeArr = new Node[rows][columns];
				for(int j=0; j<rows; j++){
					line = reader.readLine();
					tempArray = line.split(" ");
					// adding nodes by row
					for(int k=0; k<columns; k++) {
						//retrieve color value
						tempColor = new Color(Integer.parseInt(tempArray[k*3]), Integer.parseInt(tempArray[(k*3)+1]), Integer.parseInt(tempArray[(k*3)+2]));
						//assign color to associated node
						tempNode = new Node();
						tempNode.setColor(tempColor);
						tempNodeArr[j][k] = tempNode;
					}
				}
				// add node to the Frame
				tempFrame.setNodeGrid(tempNodeArr);
				//add each Frame to the ArrayList<Frame> that was passed in
				grid.add(tempFrame);
			}
		
			// Close File
			reader.close();
			
			
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
		}
		
		return grid;

	}
	
	public static void writeFile(File filename, ArrayList<Frame> grid) {
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
		writer.write("0.4 //version number\n");
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
