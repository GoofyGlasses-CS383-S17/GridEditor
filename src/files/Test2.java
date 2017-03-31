package files;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.awt.Color;

import gridEditor.common.Frame;
import gridEditor.common.Node;

public class Test2 {

	public static void main(String[] args) {
		// write file test
		ArrayList<Frame> testList = new ArrayList<>();
		Frame test;
		
		
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Please enter x dimension: ");
		int x = scanner.nextInt();
		System.out.print("Please enter y dimension: ");
		int y = scanner.nextInt();
		
		System.out.print("The (x,y) pair entered is: (" + x + "," + y + ")");
		
		
		Node[][] color_matrix = new Node[x][y];
		
		for(int i = 0; i < x; i++)
		{	
			for(int j = 0; j < y; j++)
			{	
				color_matrix[i][j] = new Node();
				Random rand = new Random();
				Color tempColor = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
				
				color_matrix[i][j].setColor(tempColor);
			}
		}
		/*
		for(int i = 0; i < x; i++)
		{	
			for(int j = 0; j < y; j++)
			{	
				Random rand = new Random();
				int r = rand.nextInt(255);
				int g = rand.nextInt(255);
				int b = rand.nextInt(255);
				color_matrix[i][j].setColor(r, g, b);
			}
		}
		*/

		System.out.print("\n");
		/*
		for(int i = 0; i < x; i++)
		{	
			for(int j = 0; j < y; j++)
			{	
				int r = color_matrix[i][j].getRed();
				int g = color_matrix[i][j].getGreen();
				int b = color_matrix[i][j].getBlue();
				System.out.print("Red: " + r + " Green: " + g + " Blue: " + b);
				System.out.print("\n");
			}
			
		}
		*/
		/*
		Node[][] node_matrix = new Node[x][y];
		
		for(int i = 0; i < x; i++)
		{	
			for(int j = 0; j < y; j++)
			{	
				node_matrix[i][j] = new Node();
				node_matrix[i][j].setColor(color_matrix[i][j]);
			}
		}
		
		
		Node[][] multiNode = new Node[x][y];
		
		for(int i = 0; i < x; i++)
		{	
			for(int j = 0; j < y; j++)
			{	
				multiNode[i][j] = node_matrix[i][j];
			}
		}
		*/
		for(int z = 0; z < x*y; z++)
		{
			test = new Frame();
			test.setDuration(z);
			test.setNodeGrid(color_matrix);
			testList.add(test);
		}
		
		WriteFile.writeFile("test.tang", testList);
		
		// read file test
		ArrayList<Frame> tempGrid;
		tempGrid = new ArrayList<Frame>();
		ReadFile.readFile("test.tang", tempGrid);
		
		
		System.out.println("The End");
		
	}

}
