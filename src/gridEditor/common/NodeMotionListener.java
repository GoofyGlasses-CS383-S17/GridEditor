package gridEditor.common;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class NodeMotionListener implements MouseMotionListener{
	private int row;
	private int col;
	
	public NodeMotionListener(int r, int c){
		setRow(r);
		setCol(c);
	}
	
	private void setRow(int r){
		row = r;
	}
	private void setCol(int c){
		col = c;
	}
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
