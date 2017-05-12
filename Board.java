/*This class is for the game board
 * Holds a 4x4, 16 cell, grid of cells
 * Each cell holds a value of a power of 2 up to 2048
 * 
 * When two cells of the same value move against one another, their values are added and
 * combine into one cell
 * 
 * If there is no possible moves, i.e. all cells contain a value > 0 and no adjacent cells
 * are of the same value, the game ends
 * 
 * If there ever exists a cell containing the value 2048, the player wins and the game ends*/

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import java.util.Random;

public class Board extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5743761189268119230L;
	private Cell[] cells;//holds game cells
	private boolean win;//did you win?
	private boolean lost;//did you lose?
	private int freeSpace;//number of free cells
	private Random rand;
	
	public Board() {
		createBoard();
	}
	
	private void createBoard() {
		
		int a, b;//indexes for starting cells (two cells will start at 2)
		rand = new Random(); //used to generate starting indexes
		cells = new Cell[16];//Initialize cell array
		win = false;//game just started, you didn't win yet!
		lost = false;//game just started, you didn't lose yet!
		
		/*create cells*/ 
		a = rand.nextInt(16);//generate random index for a
		while( (b=rand.nextInt(16)) == a);//generate index for b, loop until a and b are different
		
		freeSpace = 16 - 2;//set free space to full board minus index a & b
		
		for(int i=0;i<16;i++) {//construct the cells
			if(i==a || i==b) cells[i] = new Cell(2);//start cell at 2
			else cells[i] = new Cell();
		}
		
		//place cells on board
		placeCells();
	}
	
	/*Method to place the cells in a 4x4 grid*/
	private void placeCells() {
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		/*create the horizontal group for the board*/
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[0]).addComponent(cells[4]).addComponent(cells[8]).addComponent(cells[12]));
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[1]).addComponent(cells[5]).addComponent(cells[9]).addComponent(cells[13]));
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[2]).addComponent(cells[6]).addComponent(cells[10]).addComponent(cells[14]));
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[3]).addComponent(cells[7]).addComponent(cells[11]).addComponent(cells[15]));
		
		/*create the vertical group for the board*/
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[0]).addComponent(cells[1]).addComponent(cells[2]).addComponent(cells[3]));
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[4]).addComponent(cells[5]).addComponent(cells[6]).addComponent(cells[7]));
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[8]).addComponent(cells[9]).addComponent(cells[10]).addComponent(cells[11]));
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[12]).addComponent(cells[13]).addComponent(cells[14]).addComponent(cells[15]));
		
		//add the h and v groups to the layout
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}
	
	/*check to see if we lost*/
	public boolean checklost() {
		return lost;
	}
	
	/*check to see if we won*/
	public boolean checkWin() {
		return win;
	}
	
	/*method to perform a move on the board
	 * given the direction as input determine the correct move*/
	public void move(String direction) {
		switch(direction) {
		case "UP":
			//System.out.println("UP");
			moveUp();
			break;
		case "DOWN":
			//System.out.println("DOWN");
			moveDown();
			break;
		case "LEFT":
			//System.out.println("LEFT");
			moveLeft();
			break;
		case "RIGHT":
			//System.out.println("RIGHT");
			moveRight();
			break;
		}
		addCell();
	}
	
	private void addCell() {
		
		int index;
		
		if(freeSpace > 0) {
			
			while ( (cells[index = rand.nextInt(16)].getValue()) != 0 );
			
			cells[index].setValue(2);
			
			freeSpace--;
			
			System.out.println("Cell added at "+index+"\t\tfree space: "+freeSpace);
		} else System.out.println("Full");
		
	}
	
	private void moveUp() {
		
		boolean combineCheck=false;
		
		for( int i=0; i<16; i++) {
			if( cells[i].getValue() != 0 ) {
				int index = i;
				if(i%4==0) combineCheck = false;
				for( int j=1; j<4; j++ ) {
					int k = i - (4*j);
					if(k>-1) {
						if(cells[k].getValue()==0) {
							cells[k].setValue(cells[index].getValue());
							cells[index].resetCell();
							index = k;
						}
						else if(cells[k].getValue() == cells[index].getValue() && !combineCheck) {
							cells[k].setValue(cells[index].getValue()*2);
							cells[index].resetCell();
							freeSpace++;
							index = k;
							combineCheck = true;
						} else break;
						
					}
				}
			}
		}
		
	}
	
	private void moveDown() {
		
		boolean combineCheck=false;
		
		for( int i=15; i>-1; i--) {
			if( cells[i].getValue() != 0 ) {
				int index = i;
				if(i%4==3) combineCheck = false;
				for( int j=1; j<4; j++ ) {
					int k = i + (4*j);
					if(k<16) {
						if(cells[k].getValue()==0) {
							cells[k].setValue(cells[index].getValue());
							cells[index].resetCell();
							index = k;
						}
						else if(cells[k].getValue() == cells[index].getValue() && !combineCheck) {
							cells[k].setValue(cells[index].getValue()*2);
							cells[index].resetCell();
							freeSpace++;
							index = k;
							combineCheck = true;
						} else break;
						
					}
				}
			}
		}
		
	}
	
	private void moveRight() {
		
		boolean combineCheck=false;
		int upperBound;
		
		//System.out.println("****RIGHT DEBUG****");
		
		for( int i=15; i>-1; i-- ) {
			if(cells[i].getValue()!=0)
			{
				int index = i; //varible to save location
				if(i%4==3) combineCheck = false;
				//this upper bound function is silly because javas mod operation is incorrect on negative numbers
				//i.e. -15 mod 4 = 1, however in java -15 % 4 = -3, so i offset it by 4 and mod again to make 4 = 0.... 
				upperBound =  ((((i + 1)  * -1)  % 4) + 4) % 4;
				
				for( int j=0; j<upperBound ; j++) {//loop for possible moves (3 max moves per block)
					
					int k = i + (1 + j);//calculate move
					
					//System.out.println("i: "+i+"\t\tj: "+j+"\t\tupperBound: "+upperBound);
					
					if(cells[k].getValue() == 0) {//check to see if spot is empty
						cells[k].setValue( cells[index].getValue() );//if so move
						cells[index].resetCell();//reset old cell
						index = k;//save location
					}
					else if(cells[k].getValue() == cells[index].getValue() && !combineCheck){//check if next spot can combine
						cells[k].setValue(cells[index].getValue()*2);//if so do so
						cells[index].resetCell();//reset old cell
						freeSpace++;
						index = k;//save location
						combineCheck = true;
					} else break;
					
				}
			}
		}
		
		//System.out.println("****RIGHT DEBUG****");
		
	}
	
	private void moveLeft() {
		
		boolean combineCheck=false;
		
		for( int i=0; i<16; i++ ) {
			if(cells[i].getValue()!=0)
			{
				int index = i; //varible to save location
				if(i%4==0) combineCheck = false;//set combinecheck back to false (only one space can combine per row)
				for( int j=0; j<i%4 ; j++) {//loop for possible moves (3 max moves per block)
					int k = i - (1 + j);//calculate move
					
					if(cells[k].getValue() == 0) {//check to see if spot is empty
						cells[k].setValue( cells[index].getValue() );//if so move
						cells[index].resetCell();//reset old cell
						index = k;//save location
					}
					else if(cells[k].getValue() == cells[index].getValue() && !combineCheck){//check if next spot can combine
						cells[k].setValue(cells[index].getValue()*2);//if so do so
						cells[index].resetCell();//reset old cell
						freeSpace++;
						index = k;//save location
						combineCheck = true;
					} else break;
					
				}
			}
		}
		
	}
	
}
