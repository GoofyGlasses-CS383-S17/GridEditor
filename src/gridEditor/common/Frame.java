//Frame class
//Initially created 03/27/2017

package gridEditor.common;

import java.awt.Color;

public class Frame {
	//these variables should only be accessed via methods
	private Node[][] nodes;
	private int startingTime, duration;
	public Frame(){
		
	}
	public Frame(int rows, int cols){
		nodes=new Node[rows][cols];
		for(int x=0;x<rows;x++){
			for(int y=0;y<cols;y++){
				nodes[x][y]=new Node();
			}
		}
	}
	//copy constructor
	public Frame(Frame old){
		nodes=old.getNodeGrid();
		duration=old.getDuration();
		startingTime=old.getStartingTime()+duration;
	}
	public Frame(Node[][] nodes){
		this.nodes=nodes;
	}
	public int getWidth(){
		return nodes[0].length;
	}
	public int getHeight(){
		return nodes.length;
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
	public Color getNodeColor(int row, int col){
		return nodes[row][col].getColor();
	}
	public void setNodeColor(int row, int col, Color color){
		nodes[row][col].setColor(color);
	}
	public void setNodeGrid(Node[][] nodes){
		this.nodes=nodes;
	}
	public Node[][] getNodeGrid(){
		return nodes;
	}
}
