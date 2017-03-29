//Node class
//Initially created 03/27/2017

package gridEditor.common;

public class Node {
	protected Color color;
	public Node(){
		this(0, 0, 0);
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