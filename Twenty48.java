/*main driver class for game*/

import javax.swing.JFrame;
import javax.swing.GroupLayout;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.EventQueue;

public class Twenty48 extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8506717210030744211L;
	private Board gameBoard;
	
	public Twenty48() {//constrctor
		
		createGame();
		
	}
	
	private void createGame() {//constructor helper
		
		/*initialize the game components*/
		gameBoard = new Board();
		
		/*place the board and buttons on the game frame*/
		placeGameComponents();
		//setUpButtons();
		
		/*size the game board and set up location and close operation*/
		setSize(550,600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addKeyListener(new KeyStrokeListener());
	}
	
	private void placeGameComponents() {//place the components into the frame
		
		Container pane = getContentPane();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		hGroup.addGroup(layout.createParallelGroup().addComponent(gameBoard));
		vGroup.addGroup(layout.createParallelGroup().addComponent(gameBoard));
		
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}
	
	public boolean checkWin() {
		return gameBoard.checkWin();
	}
	
	public boolean checkLost() {
		return gameBoard.checklost();
	}
	
	/*key listener for buttons*/
	private class KeyStrokeListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) gameBoard.gameInput("UP");
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) gameBoard.gameInput("LEFT");
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) gameBoard.gameInput("RIGHT");
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) gameBoard.gameInput("DOWN");
			else if(e.getKeyCode() == KeyEvent.VK_ENTER) gameBoard.gameInput("ENTER");
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(String args[]) {//start game
		EventQueue.invokeLater(new Runnable() {//add game to event queue and run
			public void run() {
				Twenty48 game = new Twenty48();
				game.setVisible(true);
			}
		});
	}
	
}
