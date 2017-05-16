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
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import java.util.Random;

public class Board extends JPanel{
	
	private static final int LEFT = 0;//possibleMoves left location
	private static final int RIGHT = 1;//possibleMoves right location
	private static final int UP = 2;//possibleMoves up location
	private static final int DOWN = 3;//possibleMoves down location
	
	private static final int WINNER = 2048;//value for winning condition
	
	private static final long serialVersionUID = -5743761189268119230L;//included by eclipse
	
	private Cell[] cells;//holds game cells
	private boolean win;//did you win?
	private boolean lost;//did you lose?
	private boolean playable;//varible to check if game is in playable state
	private int freeSpace;//number of free cells
	private Random rand;//random number generator
	private boolean[] possibleMoves;//hold values for possible moves o board
	private int score;//holds players score (sum of the cell values created through combination)
	private JLabel scoreLabel;//display score
	private JLabel winLabel;//display if won the game
	private JLabel loseLabel;//display if lost the game
	private JLabel startLabel;//display to start or restart
	
	/*constructor*/
	public Board() {
		createBoard();
	}
	
	private void createBoard() {
		
		rand = new Random(); //used to generate starting indexes
		cells = new Cell[16];//Initialize cell array
		win = false;//game just started, you didn't win yet!
		lost = false;//game just started, you didn't lose yet!
		score = 0;//game just started, zero score!
		freeSpace = 16;//holds current free cells on board
		possibleMoves = new boolean[4];
		
		/*create game labels*/
		scoreLabel = new JLabel("Score: 0");
		winLabel = new JLabel("YOU WIN!");
		loseLabel = new JLabel("You lose.");
		startLabel = new JLabel("Press Enter!");
		
		/*make win and loss labels invisible*/
		winLabel.setVisible(false);
		loseLabel.setVisible(false);
		
		/*create cells in game start state*/
		for(int i=0;i<16;i++) cells[i] = new Cell(-1);
		
		//place cells on board
		placeCells();
	}
	
	/*Method to place the cells in a 4x4 grid*/
	private void placeCells() {
		/*create layout and set board layout to it*/
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		/*create gaps between cells*/
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		/*create groups for horizontal and vertical groups*/
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		/*layout the cells in the horizontal group*/
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[0]).addComponent(cells[4]).addComponent(cells[8]).addComponent(cells[12]).addComponent(scoreLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[1]).addComponent(cells[5]).addComponent(cells[9]).addComponent(cells[13]).addComponent(startLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[2]).addComponent(cells[6]).addComponent(cells[10]).addComponent(cells[14]).addComponent(winLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(cells[3]).addComponent(cells[7]).addComponent(cells[11]).addComponent(cells[15]).addComponent(loseLabel));
		
		/*layout the cells in the vertical group*/
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[0]).addComponent(cells[1]).addComponent(cells[2]).addComponent(cells[3]));
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[4]).addComponent(cells[5]).addComponent(cells[6]).addComponent(cells[7]));
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[8]).addComponent(cells[9]).addComponent(cells[10]).addComponent(cells[11]));
		vGroup.addGroup(layout.createParallelGroup().addComponent(cells[12]).addComponent(cells[13]).addComponent(cells[14]).addComponent(cells[15]));
		vGroup.addGroup(layout.createParallelGroup().addComponent(scoreLabel).addComponent(startLabel).addComponent(winLabel).addComponent(loseLabel));
		
		//add the h and v groups to the layout
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}
	
	/*check to see if player lost*/
	public boolean checklost() {
		return lost;
	}
	
	/*check to see if player won*/
	public boolean checkWin() {
		return win;
	}
	
	/*get player score*/
	public int getScore() {
		return score;
	}
	
	/*method called to start the game, sets up the board starting point*/
	private void startGame() {
		
		int a, b;//used to get the first two cell indexes
		
		a = rand.nextInt(16);
		while((b=rand.nextInt(16))==a);//loop until b != a
		
		for( int i=0; i < 16; i++) {//loop over all cells setting each cell starting point
			
			if(i==a||i==b) {//set the first two cells either 2 or 4 (4 happens 10% of the time)
				if(rand.nextInt(10)==9) cells[i].setValue(4);
				else cells[i].setValue(2);
			} else cells[i].setValue(0);//default for the rest is 0
			
		}
		
		freeSpace = 16-2;//update freeSpace of board
		
		/*turn off all labels besides score*/
		startLabel.setVisible(false);
		winLabel.setVisible(false);
		loseLabel.setVisible(false);
		
		/*set varibles back to default*/
		win = lost = false;
		score = 0;
		updateScore();
		
		/*check moves for first move*/
		checkMoves();
	}
	
	/*called if game Won, sets board up to winning state*/
	private void gameWon() {
		
		playable = false;//turn off game playable, inputs wont work while in this state
		winLabel.setVisible(true);//turn on win label
		startLabel.setVisible(true);//turn on start label to let player know they can restart the game
		
		for( int i=0; i<16; i++) {
			if(cells[i].getValue()==WINNER) cells[i].setValue(-4);//if current cell is the winning cell, color it accordingly
			else cells[i].setValue(-3);//else color it to default winning state color
		}
		
	}
	
	/*called if game lost, sets up lost state*/
	private void gameLost() {
		playable = false;//turn off game
		
		loseLabel.setVisible(true);//turn on lost label
		startLabel.setVisible(true);//turn on restart label
		
		for(int i=0; i< 16; i++) cells[i].setValue(-2);//set up lost look
	}
	
	/*handles user input*/
	public void gameInput(String input) {
		
		if(!playable && input.equals("ENTER")) {//start game if not yet playable and player pressed enter
			playable = true;
			startGame();
		} else if(playable && !input.equals("ENTER")) move(input);//if game is playable and player pressed one of the arrow keys
		
	}
	
	/*method to perform a move on the board
	 * given the direction as input determine the correct move*/
	private void move(String direction) {
		switch(direction) {
		case "UP":
			//System.out.println("UP");
			if(possibleMoves[UP]) { 
				moveUp();
				addCell();
			}
			else System.out.println("Cant move that way.");//debug line
			break;
		case "DOWN":
			//System.out.println("DOWN");
			if(possibleMoves[DOWN]){
				moveDown();
				addCell();
			}
			else System.out.println("Cant move that way.");//debug line
			break;
		case "LEFT":
			//System.out.println("LEFT");
			if(possibleMoves[LEFT]) {
				moveLeft();
				addCell();
			} else System.out.println("Cant move that way.");//debug line
			break;
		case "RIGHT":
			//System.out.println("RIGHT");
			if(possibleMoves[RIGHT]) {
				moveRight();
				addCell();
			} else System.out.println("Cant move that way.");//debug line
			break;
		}
		
		checkMoves();
		updateScore();
		for(int i=0; i<16; i++) cells[i].resetCanCombine();//make cells combinable again
		if(win) gameWon();
		if(lost) gameLost();
	}
	
	/*update score on board*/
	private void updateScore() {
		scoreLabel.setText("Score: "+score);
	}
	
	/*add new cell to the board*/
	private void addCell() {
		
		int index;//index of the new cell
		
		/*add a cell if there is space*/
		if(freeSpace > 0) {
			
			while ( (cells[index = rand.nextInt(16)].getValue()) != 0 );//loop until index is empty
			
			/*adding a new cell of the board, new cells should be 2 90% of the time, 4 10% of the time*/
			if(rand.nextInt(10)==9) cells[index].setValue(4);
			else cells[index].setValue(2);
			
			freeSpace--;//update free space

		}
		
	}
	
	/*move functions, they are fairly similar so only one is fully commented...*/
	private void moveRight() {
		
		int upperBound;//upperbound for second loop
		
		for( int i=15; i>-1; i-- ) {
			if(cells[i].getValue()!=0)
			{
				int index = i; //varible to save location
				
				//this upper bound function is silly because javas mod operation is incorrect on negative numbers
				//i.e. -15 mod 4 = 1, however in java -15 % 4 = -3, so i offset it by 4 and mod again to make 4 = 0.... 
				upperBound =  ((((i + 1)  * -1)  % 4) + 4) % 4;
				
				for( int j=0; j<upperBound ; j++) {//loop for possible moves (3 max moves per block)
					
					int k = i + (1 + j);//calculate move
					
					if(cells[k].getValue() == 0) {//check to see if spot is empty
						cells[k].setValue( cells[index].getValue() );//if so move
						cells[index].resetCell();//reset old cell
						index = k;//save location
					}
					else if(cells[k].getValue() == cells[index].getValue() && cells[k].canCombine() && cells[index].canCombine()){//check if next spot can combine
						cells[k].setValue(cells[k].getValue(),cells[index].getValue());//if so do so
						
						/*check if won*/
						if(cells[k].getValue() == WINNER) win = true;
						score += cells[k].getValue();
						
						cells[index].resetCell();//reset old cell
						freeSpace++;
						index = k;//save location
					} else break;
					
				}
			}
		}
		
	}
	
	private void moveUp() {
		
		for( int i=0; i<16; i++) {
			if( cells[i].getValue() != 0 ) {
				int index = i;
				for( int j=1; j<4; j++ ) {
					int k = i - (4*j);
					if(k>-1) {
						if(cells[k].getValue()==0) {
							cells[k].setValue(cells[index].getValue());
							cells[index].resetCell();
							index = k;
						}
						else if(cells[k].getValue() == cells[index].getValue() && cells[k].canCombine() && cells[index].canCombine()) {
							cells[k].setValue(cells[k].getValue(),cells[index].getValue());//if so do so
							
							/*check if won*/
							if(cells[k].getValue() == WINNER) win = true;
							score += cells[k].getValue();
							
							cells[index].resetCell();
							freeSpace++;
							index = k;
						} else break;
						
					}
				}
			}
		}
		
	}
	
	private void moveDown() {
		
		for( int i=15; i>-1; i--) {
			if( cells[i].getValue() != 0 ) {
				int index = i;
				for( int j=1; j<4; j++ ) {
					int k = i + (4*j);
					if(k<16) {
						if(cells[k].getValue()==0) {
							cells[k].setValue(cells[index].getValue());
							cells[index].resetCell();
							index = k;
						}
						else if(cells[k].getValue() == cells[index].getValue() && cells[k].canCombine() && cells[index].canCombine()) {
							cells[k].setValue(cells[k].getValue(),cells[index].getValue());//if so do so
							
							/*check if won*/
							if(cells[k].getValue() == WINNER) win = true;
							score += cells[k].getValue();
							
							cells[index].resetCell();
							freeSpace++;
							index = k;
						} else break;
						
					}
				}
			}
		}
		
	}
	
	private void moveLeft() {
		
		for( int i=0; i<16; i++ ) {
			if(cells[i].getValue()!=0)
			{
				int index = i; //varible to save location
				for( int j=0; j<i%4 ; j++) {//loop for possible moves (3 max moves per block)
					int k = i - (1 + j);//calculate move
					
					if(cells[k].getValue() == 0) {//check to see if spot is empty
						cells[k].setValue( cells[index].getValue() );//if so move
						cells[index].resetCell();//reset old cell
						index = k;//save location
					}
					else if(cells[k].getValue() == cells[index].getValue() && cells[k].canCombine() && cells[index].canCombine()){//check if next spot can combine
						cells[k].setValue(cells[k].getValue(),cells[index].getValue());//if so do so
						
						/*check if won*/
						if(cells[k].getValue() == WINNER) win = true;
						score += cells[k].getValue();
						
						cells[index].resetCell();//reset old cell
						freeSpace++;
						index = k;//save location
					} else break;
					
				}
			}
		}
		
	}
	
	/*checks if all/which four moves are possible*/
	private void checkMoves() {
		
		/*reset possible moves*/
		for(int i=0;i<4;i++) {
			possibleMoves[i] = false;
		}
		
		/*check each cell if it can move, if all moves are possible then break - no need to check all*/
		for(int i=0; i<16; i++) {
			
			/*mark if move is possible in each direction*/
			if(cells[i].getValue() > 1) {
				if( i%4 > 0)
					if(cells[i-1].getValue() == 0 || cells[i].getValue() == cells[i-1].getValue()) possibleMoves[LEFT] = true;
				if(i-4 > -1)
					if(cells[i-4].getValue() == 0 || cells[i].getValue() == cells[i-4].getValue()) possibleMoves[UP] = true;
				if(i%4 < 3)
					if(cells[i+1].getValue() == 0 || cells[i].getValue() == cells[i+1].getValue()) possibleMoves[RIGHT] = true;
				if(i + 4 < 16)
					if(cells[i+4].getValue()==0 || cells[i].getValue() == cells[i+4].getValue()) possibleMoves[DOWN] = true;
				
				if(possibleMoves[LEFT] && possibleMoves[RIGHT] && possibleMoves[UP] && possibleMoves[DOWN]) break;
			}
		}
		
		/*check if lost*/
		if(!possibleMoves[LEFT] && !possibleMoves[RIGHT] && !possibleMoves[UP] && !possibleMoves[DOWN]) lost = true;
		
	}
	
}
