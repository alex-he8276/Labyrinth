import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Daniel
public class HomeScreen extends JFrame implements ActionListener, KeyListener{

	private JPanel panel = new JPanel();

	private JLabel logo = new JLabel();

	public HomeScreen() {

		setBackground(new Color(57, 173, 189));
		setSize(1034, 798);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		addKeyListener(this);
		
		panel.setBounds(0, 0, 1024, 768);
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		add(panel);

		logo.setIcon(new ImageIcon(new ImageIcon("images/other/splash.png").getImage().getScaledInstance(1024, 768, 0)));
		logo.setBounds(0, 0, 1024, 768);
		panel.add(logo);

		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {

	}

	public void keyPressed(KeyEvent e) {
				
		if (e.getKeyCode() == 10) {
			
			this.setVisible(false);
			new Instructions();
		}

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		
	}
}