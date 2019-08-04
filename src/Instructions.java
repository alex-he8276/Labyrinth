
/**
 * This class creates a Main Menu GUI that extends the JFrame class.  It has an instructionsPanel 
 * (JPanel) and includes a constructor method that sets up the frame and adds a key listener to the 
 * board. 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Instructions extends JFrame implements ActionListener {

	JLabel logo = new JLabel();

	JLabel titleLabel = new JLabel("          Labyrinth  ");

	JLabel title2Label = new JLabel("         Instructions");

	JButton nextFrameButton = new JButton();

	JTextArea instructionsTextArea = new JTextArea(
			" 4 Players enter the labyrinth! Each player begins their turn by sliding a tile onto the maze. "
			+ "You can right click to rotate the tile clockwise or left click to rotate the tile counter-clockwise. "
			+ "Your goal is to make a path to collect your treasures. Next, move your token using the arrow keys "
			+ "towards your treasures. The first one to collect all 5 of their treasures wins!");

	public Instructions() {

		panelSetup();
		frameSetup();

	}

	private void frameSetup() {

		getContentPane().setBackground(Color.BLACK);
		setSize(1034, 798);
		setLayout(null);
		setTitle("Instructions");
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		setVisible(true);

		logo.setIcon(
				new ImageIcon(new ImageIcon("images/other/winner.png").getImage().getScaledInstance(1024, 768, 0)));
		logo.setBounds(0, 0, 1024, 768);
		add(logo);

	}

	private void panelSetup() {

		Font font = new Font("Constantia", Font.ITALIC, 90);
		Font font2 = new Font("Comic Sans MS", Font.BOLD, 46);
		Font font3 = new Font("Comic Sans MS", Font.BOLD, 28);

		titleLabel.setBounds(110, 40, 710, 85);
		titleLabel.setFont(font);
		titleLabel.setOpaque(false);
		titleLabel.setForeground(Color.WHITE);
		add(titleLabel);

		title2Label.setFont(font2);
		title2Label.setBounds(170, 155, 590, 95);
		title2Label.setOpaque(false);
		title2Label.setForeground(Color.WHITE);
		add(title2Label);

		instructionsTextArea.setBounds(170, 230, 620, 367);
		instructionsTextArea.setFont(font3);
		instructionsTextArea.setOpaque(false);
		instructionsTextArea.setEditable(false);
		instructionsTextArea.setWrapStyleWord(true);
		instructionsTextArea.setLineWrap(true);
		instructionsTextArea.setForeground(Color.WHITE);
		add(instructionsTextArea);

		nextFrameButton.addActionListener(this);
		nextFrameButton.setFont(font2);
		nextFrameButton.setText("Enter");
		nextFrameButton.setBounds(425, 620, 160, 75);
		nextFrameButton.setBorder(new LineBorder(Color.BLACK, 4));
		add(nextFrameButton);

	}

	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == nextFrameButton) {

			setVisible(false);
			new LabyrinthGUI();

		}
	}
}
