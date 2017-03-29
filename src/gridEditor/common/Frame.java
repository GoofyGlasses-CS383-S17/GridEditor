//Frame class
//Initially created 03/27/2017

package gridEditor.common;

public class Frame {
	//these variables should only be accessed via methods
	private Node[][] nodes;
	private int frameNum, startingTime, duration;
	public Frame(){
		
	}
	public Frame(int width, int height){
		nodes=new Node[width][height];
	}
	//copy constructor
	public Frame(Frame old){
		nodes=old.getNodeGrid();
		frameNum=old.getFrameNum()+1;
		duration=old.getDuration();
		startingTime=old.getStartingTime()+duration;
	}
	public Frame(Node[][] nodes){
		this.nodes=nodes;
	}
	public int getFrameNum(){
		return frameNum;
	}
	public void setFrameNum(int newVal){
		frameNum=newVal;
	}
	public int getWidth(){
		return nodes.length;
	}
	public int getHeight(){
		return nodes[0].length;
	}
	public int getStartingTime(){
		return startingTime;
	}
	public void setStartingTime(int newVal){
		startingTime=newVal;
	}
	public int getDuration(){
		return duration;
	}
	public void setDuration(int newVal){
		duration=newVal;
	}
	public Color getNodeColor(int x, int y){
		return nodes[x][y].getColor();
	}
	public void setNodeColor(int x, int y, Color color){
		nodes[x][y].setColor(color);
	}
	public void setNodeGrid(Node[][] nodes){
		this.nodes=nodes;
	}
	public Node[][] getNodeGrid(){
		return nodes;
	}
}
