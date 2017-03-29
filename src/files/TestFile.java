package files;

import java.util.ArrayList;

import gridEditor.common.Color;
import gridEditor.common.Frame;
import gridEditor.common.Node;

public class TestFile {

	public static void main(String[] args) {
		// write file test
		ArrayList<Frame> testList = new ArrayList<>();
		Frame test;
		
		Color tempColor = new Color();
		Color tempColor2 = new Color();
		Color tempColor3 = new Color();
		Color tempColor4 = new Color();
		tempColor.setColor(0, 0, 255);
		tempColor2.setColor(255, 0, 0);
		tempColor3.setColor(0, 255, 0);
		tempColor4.setColor(255, 255, 255);
		
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
		
		WriteFile.writeFile("test.tang", testList);
		
		// read file test
		ArrayList<Frame> tempGrid;
		tempGrid = new ArrayList<Frame>();
		ReadFile.readFile("test.tang", tempGrid);
		
		
		System.out.println("The End");
		

	}

}
