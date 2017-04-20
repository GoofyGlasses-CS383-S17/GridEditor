package gridEditor.common;

import javax.swing.JPopupMenu;

public class FramePopupMenu extends JPopupMenu {
	private int frameNum;
	public FramePopupMenu(int frameNum){
		super();
		this.frameNum=frameNum;
	}
	public int getFrameNumber(){
		return frameNum;
	}
	public void setFrameNumber(int frameNum){
		this.frameNum=frameNum;
	}
}
