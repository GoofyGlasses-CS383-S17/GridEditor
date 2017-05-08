//Node class
//Initially created 03/27/2017

package gridEditor.common;

import java.awt.Color;

public class Node {
	protected Color color;
	public Node(){
		this.setColor(Color.WHITE);
    // Set to 255, so when creating a new frame, the rest of Nodes are white,
		// and not Black (if user doesn't edit all nodes themselves)
  }
	public Node(int red, int green, int blue){
		color=new Color(red, green, blue);
	}
	@Override
	public Node clone(){
		Node clonedNode = new Node();
		Color clonedColor = new Color(0,0,0);
		clonedColor = getColor();
		clonedNode.setColor(clonedColor);
		return clonedNode;
	}
	public boolean equals(Node c) {
		if(getColor().getRed() == c.getColor().getRed()) {
			if(getColor().getBlue() == c.getColor().getBlue()) {
				if(getColor().getGreen() == c.getColor().getGreen()) {
					return true;
				}
			}
		}
		return false;
	}
	public void setColor(Color color){
		this.color=color;
	}
	public Color getColor(){
		return color;
	}
}
