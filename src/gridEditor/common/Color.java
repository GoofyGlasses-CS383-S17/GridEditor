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
	public int getRed(){
		return this.red;
	}
	public int getGreen(){
		return this.green;
	}
	public int getBlue(){
		return this.blue;
	}
	public void setRed(int r){
		this.red = r;
	}
	public void setGreen(int g){
		this.green = g;
	}
	public void setBlue(int b){
		this.blue = b;
	}
}
