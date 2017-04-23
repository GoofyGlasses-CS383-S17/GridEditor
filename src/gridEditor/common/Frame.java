//Frame class
//Initially created 03/27/2017

package gridEditor.common;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

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
	public void adjustStartingTime(int toAdd){
		startingTime+=toAdd;
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
	public ImageIcon getFrameIcon(){
		Node[][] grid = this.getNodeGrid();
		BufferedImage frameImage = new BufferedImage(grid[0].length,grid.length,BufferedImage.TYPE_INT_RGB);
		for (int c = 0; c < grid[0].length; c++){
			for (int r = 0; r < grid.length; r++){
				frameImage.setRGB(c, r, grid[r][c].getColor().getRGB());			
			}
		}
		Image scaledImage = frameImage.getScaledInstance(grid[0].length * 10, grid.length * 10, Image.SCALE_FAST);
		return new ImageIcon(scaledImage);
	}
}
