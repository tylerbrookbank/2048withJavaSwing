/*main driver class for game*/

import javax.swing.JFrame;
import javax.swing.GroupLayout;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;

public class Twenty48 extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8506717210030744211L;
	private Board gameBoard;
	private ControlBar gameControl;
	
	public Twenty48() {//constrctor
		
		createGame();
		
	}
	
	private void createGame() {//constructor helper
		
		/*initialize the game components*/
		gameBoard = new Board();
		gameControl = new ControlBar();
		
		/*place the board and buttons on the game frame*/
		placeGameComponents();
		setUpButtons();
		
		/*size the game board and set up location and close operation*/
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	private void placeGameComponents() {//place the components into the frame
		
		Container pane = getContentPane();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		hGroup.addGroup(layout.createParallelGroup().addComponent(gameBoard).addComponent(gameControl));
		vGroup.addGroup(layout.createParallelGroup().addComponent(gameBoard));
		vGroup.addGroup(layout.createParallelGroup().addComponent(gameControl));
		
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}
	
	private void setUpButtons() {
		
		//set action commands
		gameControl.upButton.setActionCommand("UP");
		gameControl.downButton.setActionCommand("DOWN");
		gameControl.leftButton.setActionCommand("LEFT");
		gameControl.rightButton.setActionCommand("RIGHT");
		
		//add event listeners
		gameControl.upButton.addActionListener(new ButtonActionListener());
		gameControl.downButton.addActionListener(new ButtonActionListener());
		gameControl.leftButton.addActionListener(new ButtonActionListener());
		gameControl.rightButton.addActionListener(new ButtonActionListener());
		
		//add key listeners
		gameControl.upButton.addKeyListener(new KeyStrokeListener());
		gameControl.downButton.addKeyListener(new KeyStrokeListener());
		gameControl.leftButton.addKeyListener(new KeyStrokeListener());
		gameControl.rightButton.addKeyListener(new KeyStrokeListener());
	}
	
	/*action listener for buttons*/
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gameBoard.move(e.getActionCommand());
		}
		
	}
	
	/*key listener for buttons*/
	private class KeyStrokeListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) gameBoard.move("UP");
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) gameBoard.move("LEFT");
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) gameBoard.move("RIGHT");
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) gameBoard.move("DOWN");
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
