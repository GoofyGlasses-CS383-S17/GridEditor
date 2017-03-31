package gridEditor.common;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NodeActionListener implements MouseListener{
	private int row;
	private int col;
	
	public NodeActionListener(int r, int c){
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
