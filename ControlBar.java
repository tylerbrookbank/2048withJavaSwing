/*Class that places all four control buttons into a single component*/
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.GroupLayout;

public class ControlBar extends JPanel{
	
	public JButton upButton, downButton, leftButton, rightButton;//public facing buttons
	
	public ControlBar() {
		createBar();
	}
	
	private void createBar() {
		
		upButton = new JButton("UP");
		downButton = new JButton("DOWN");
		rightButton = new JButton("RIGHT");
		leftButton = new JButton("LEFT");
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		vGroup.addGroup(layout.createParallelGroup().addComponent(upButton).addComponent(downButton).addComponent(leftButton).addComponent(rightButton));
		hGroup.addGroup(layout.createParallelGroup().addComponent(upButton));
		hGroup.addGroup(layout.createParallelGroup().addComponent(downButton));
		hGroup.addGroup(layout.createParallelGroup().addComponent(leftButton));
		hGroup.addGroup(layout.createParallelGroup().addComponent(rightButton));
		
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}
	
}
