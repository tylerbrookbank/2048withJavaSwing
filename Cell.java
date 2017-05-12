/*
 * This cell class holds information about a single cell on the board.
 * 
 * Tyler Brookbank
 * */

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;


public class Cell extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -604701939078884883L;
	private int value;//holds value of the cell
	private JLabel label;//holds the label on the cell
	
	public Cell() {//default constructor
		
		value = 0;//set default value to 0 (this corresponds to a blank cell)
		label = new JLabel("");//the default label is empty
		setColor(0);//set default color
		add(label);
		
	}
	
	public Cell(int num) {//constructor given a value
		
		label = new JLabel("");
		setValue(num);
		add(label);
		
	}
	
	/*This method is called to reset the cell back to defaults*/
	public void resetCell() {
		value = 0;
		setColor(value);
		label.setText("");
	}
	
	public int getValue() {
		return value;
	}
	
	/*Sets the value of the cell to the passed value
	 * Also checks whether the value passed is a possible
	 * value. Possible values are powers of 2 up to 2048
	 * 
	 * returns 0 on success and 1 on fail*/
	public int setValue(int num) {
		
		value = num;
		
		if( setColor(value)==1 ) {//set color and check if value is a valid number
			System.out.println(value+"is not a valid value for a cell.");
			return 1;
		}
		
		if(value!=0) label.setText(""+value);//set Text to the value
		else label.setText("");
		
		return 0;
	}
	
	/*Sets color of the cell to the color corresponding to value of the cell
	 * returns 0 on success and 1 on fail*/
	private int setColor(int num) {
		switch(num){
		case 0://default color
			setBackground(new Color(232, 217, 243));
			break;
		case 2://2 cell color
			setBackground(new Color(207, 176, 230));
			break;
		case 4://4cell color
			setBackground(new Color(193, 153, 223));
			break;
		case 8://8 cell color
			setBackground(new Color(174, 121, 213));
			break;
		case 16://16 cell color
			setBackground(new Color(152, 86, 202));
			break;
		case 32://32 cell color
			setBackground(new Color(137, 62, 194));
			break;
		case 64://64 cell color
			setBackground(new Color(76, 94, 184));
			break;
		case 128://128 cell color
			setBackground(new Color(99, 115, 193));
			break;
		case 256://256 cell color
			setBackground(new Color(115, 129, 199));
			break;
		case 512://512 cell color
			setBackground(new Color(138, 150, 208));
			break;
		case 1080://1080 cell color
			setBackground(new Color(172, 180, 222));
			break;
		case 2048://2048 cell color
			setBackground(new Color(104, 64, 112));
			break;
		default://wrong value was passed to method
			return 1;
		}
		
		return 0;
	}
}
