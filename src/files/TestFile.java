package files;

import java.util.ArrayList;
import java.awt.Color;

import gridEditor.common.Frame;
import gridEditor.common.Node;

public class TestFile {

	public static void main(String[] args) {
		// write file test
		ArrayList<Frame> testList = new ArrayList<>();
		Frame test;
		
		Color tempColor = new Color(0, 0, 255);
		Color tempColor2 = new Color(255, 0, 0);
		Color tempColor3 = new Color(0, 255, 0);
		Color tempColor4 = new Color(255, 255, 255);
		
		Node tempNode = new Node();
		Node tempNode2 = new Node();
		Node tempNode3 = new Node();
		Node tempNode4 = new Node();
		tempNode.setColor(tempColor);
		tempNode2.setColor(tempColor2);
		tempNode3.setColor(tempColor3);
		tempNode4.setColor(tempColor4);
		
		Node[][] multiNode = new Node[2][2];
		multiNode[0][0] = tempNode;
		multiNode[0][1] = tempNode2;
		multiNode[1][0] = tempNode3;
		multiNode[1][1] = tempNode4;
		
		for(int z=0; z<2; z++) {
			test = new Frame();
			test.setDuration(z);
			test.setNodeGrid(multiNode);
			testList.add(test);
		}
		
		WriteFile.writeFile("test.tan", testList);
		
		// read file test
		ArrayList<Frame> tempGrid;
		tempGrid = new ArrayList<Frame>();
		ReadFile.readFile("test.tan", tempGrid);
		
		
		System.out.println("The End");
		

	}

}
