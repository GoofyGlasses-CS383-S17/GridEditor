package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import gridEditor.common.Color;
import gridEditor.common.Frame;
import gridEditor.common.Node;

public class ReadFile {
	
	public static void readFile(String filename, ArrayList<Frame> grid) {
		String line;
		String[] tempArray;
		Frame tempFrame;
		Node tempNode;
		Node[][] tempNodeArr;
		Color tempColor;
 
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
			
			
			
			// read in each grid, save startTime and grid of nodes to an array
			for(int i=0; i<gridCount; i++) {
				line = reader.readLine();
				tempFrame = new Frame();
				tempFrame.setStartingTime(Integer.parseInt(line));
				for(int j=0; j<rows; j++){
					line = reader.readLine();
					tempArray = line.split(" ");
					// saving values to grid, note rows is at the value of j
					tempNodeArr = new Node[rows][columns];
					for(int k=0; k<columns; k++) {
						//retrieve color value
						tempColor = new Color();
						tempColor.setRed(Integer.parseInt(tempArray[k]));
						tempColor.setGreen(Integer.parseInt(tempArray[(k*3)+1]));
						tempColor.setBlue(Integer.parseInt(tempArray[(k*3)+2]));
						//assign color to associated node
						tempNode = new Node();
						tempNode.setColor(tempColor);
						tempNodeArr[j][k] = tempNode;
					}
					tempFrame.setFrameNum(i);
					tempFrame.setNodeGrid(tempNodeArr);
				}
				//add each Frame to the ArrayList<Frame> that was passed in
				grid.add(tempFrame);
				//TESTING for now
				System.out.printf("\n");
				System.out.printf("\n");
			}
		
			// prints for TESTING line was read
		    System.out.printf("%s %s %s %s\n", version, audio, currentColor, previousColors);
		    System.out.printf("grid count: %d\n", gridCount);
		    System.out.printf("size of grids: %dx%d\n", rows, columns);

			// Close File
			reader.close();
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
		}

	}

}
