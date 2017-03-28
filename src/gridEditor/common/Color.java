//Color class
//Initially created 03/27/2017

package gridEditor.common;

public class Color {
	protected int red, green, blue;
	
	//this constructor will probably never be used
	public Color(){
		this(0, 0, 0);
	}
	public Color(int red, int green, int blue){
		this.red=red;
		this.blue=blue;
		this.green=green;
	}
	public void setColor(int red, int green, int blue){
		this.red=red;
		this.blue=blue;
		this.green=green;
	}
}
