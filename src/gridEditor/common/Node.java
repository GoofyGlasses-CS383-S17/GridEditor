//Node class
//Initially created 03/27/2017

package gridEditor.common;

import java.awt.Color;

public class Node {
	protected Color color;
	public Node(){
		this.setColor(Color.WHITE);
	}
	public Node(int red, int green, int blue){
		color=new Color(red, green, blue);
	}
	public void setColor(Color color){
		this.color=color;
	}
	public Color getColor(){
		return color;
	}
}
