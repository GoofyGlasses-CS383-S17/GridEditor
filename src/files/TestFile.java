package files;

import gridEditor.common.Color;
import gridEditor.common.Frame;
import gridEditor.common.Node;

public class TestFile {

	public static void main(String[] args) {
		// write file test
		
		Frame test = new Frame();
		
		Color tempColor = new Color();
		tempColor.setColor(0, 0, 255);
		Color tempColor2 = new Color();
		tempColor2.setColor(255, 0, 0);
		
		Node tempNode = new Node();
		Node tempNode2 = new Node();
		tempNode.setColor(tempColor);
		tempNode2.setColor(tempColor2);
		
		Node[][] multiNode = new Node[2][2];
		multiNode[0][0] = tempNode;
		multiNode[0][1] = tempNode2;
		multiNode[1][0] = tempNode;
		multiNode[1][1] = tempNode2;
		
		test.setDuration(0);
		test.setNodeGrid(multiNode);
		
		WriteFile.writeFile("test.txt", test);
		
		System.out.println("The End");
		

	}

}
