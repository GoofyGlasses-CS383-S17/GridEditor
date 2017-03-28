package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class ReadFile {
	
	public static void main(String filename) {
		String line;
		String[] tempArray;
		Integer[][] grid = null;
		int startTime, totalCount;
 
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
			grid = new Integer[rows][columns*3];
			
			// read in each grid, save startTime and grid of nodes to an array
			for(int i=0; i<gridCount; i++) {
				line = reader.readLine();
				startTime = Integer.parseInt(line);
				for(int j=0; j<rows; j++){
					line = reader.readLine();
					tempArray = line.split(" ");
					// columns*3=grid columns
					totalCount = columns * 3;
					// saving values to grid, note rows is at the value of j
					for(int k=0; k<totalCount; k++) {
						grid[j][k] = Integer.parseInt(tempArray[k]);
					}
				}
				//this is where you will do something with each grid
				//TESTING for now
				System.out.printf("\n");
				System.out.println(Arrays.deepToString(grid));
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
