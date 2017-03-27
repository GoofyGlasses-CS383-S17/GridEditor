package gridEditor.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NodeActionListener implements ActionListener{
	private int row;
	private int col;
	
	public NodeActionListener(int r, int c){
		setRow(r);
		setCol(c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
	

}
