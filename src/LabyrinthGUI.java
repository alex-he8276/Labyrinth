import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class LabyrinthGUI extends JFrame implements ActionListener, KeyListener {

	// has the hand and free tile
	// has the board
	// spreading tiles - Alex
	// handing out the cards - Austin
	// sliding in the tiles - Daniel

	static Board board = new Board();
	JLabel background = new JLabel();
	static JButton[] insertButtons = new JButton[12];

	JLabel turnLabel = new JLabel();
	JButton turnButton = new JButton();

	static ActionListener play = new ActionListener() {

		public void actionPerformed(ActionEvent evt) {

			playSounds(1);

		}

	};

	static Timer musicTimer = new Timer(56000, play);

	private static Card[] deck = new Card[24];
	private static boolean[] deckCheck = new boolean[24];
	public static Player[] players = new Player[5]; // 5th player not used
	public static boolean insert = false;
	private static int turn = 1;
	private boolean needMove = false;

	public LabyrinthGUI() {

		// 1. Setup the GUI
		setSize(1318, 805);
		setTitle("Labyrinth");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		setFocusable(true);

		for (int count = 0; count < 12; count++) {

			insertButtons[count] = new JButton();

			insertButtons[count].setOpaque(false);
			insertButtons[count].setContentAreaFilled(false);
			insertButtons[count].setBorderPainted(false);
			insertButtons[count].addActionListener(this);

			if (count >= 0 && count < 3)
				insertButtons[count].setBounds(195 + count * 160, 50, 50, 40);
			else if (count >= 3 && count < 6)
				insertButtons[count].setBounds(685, 205 + (count - 3) * 163, 40, 50);

			else if (count >= 6 && count < 9)
				insertButtons[count].setBounds(199 + (count - 6) * 160, 689, 50, 40);

			else if (count >= 9 && count < 12)
				insertButtons[count].setBounds(42, 205 + (count - 9) * 163, 40, 50);

			add(insertButtons[count]);

		}

		turnButton.setBounds(950, 130, 200, 50);
		turnButton.setIcon(new ImageIcon("images/other/done.png"));
		turnButton.addActionListener(this);
		background.add(turnButton);

		turnLabel.setIcon(new ImageIcon(
				new ImageIcon("images/other/border.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)));
		turnLabel.setBounds(755, 105 + (120 * turn), 80, 82);
		background.add(turnLabel);

		background.setBounds(0, 0, 1318, 768);
		background.setIcon(new ImageIcon("images/other/background.png"));
		add(background);

		// 2. Listen for events on the board and add the board to the GUI
		background.add(board);
		addKeyListener(board);

		makeDeck();

		// 3. Make GUI visible
		setVisible(true);

	}

	private void makeDeck() {

		int deckCounter = 0;

		for (int y = 0; y < board.getTiles().length; y++) {

			for (int x = 0; x < board.getTiles().length; x++) {

				if ((board.getTiles()[x][y].getName().contains("Start")
						|| board.getTiles()[x][y].getName().equals("none")) == false) {

					deck[deckCounter] = new Card(board.getTiles()[x][y].getName(), -1);
					background.add(deck[deckCounter]);

					// System.out.println(deck[deckCounter] + " " +
					// deckCounter);

					deckCounter++;

				}

			}

		}

		int index;
		int cardCounter;
		Random rand = new Random();

		for (int count = 1; count <= 4; count++) {

			cardCounter = 0;
			players[count] = new Player("Player " + count, count);
			// System.out.println(players[count]);

			do {

				do {

					// used to randomize card order
					index = rand.nextInt(24);

					// If card has not been used, add to player's hand
					if (deckCheck[index] == false) {

						// add card from deck to player's hand
						players[count].getHand()[cardCounter] = (deck[index]);
						players[count].getHand()[cardCounter].setOwner(count);
						players[count].setIcon(new ImageIcon("images/other/player" + count + ".png"));
						players[count].setBounds(760, 105 + (120 * count), 80, 82);
						players[count].getHand()[cardCounter].setBounds(850 + (90 * cardCounter), 100 + (120 * count),
								60, 82);
						background.add(players[count]);

					}

					// 1.1.1.2 Repeat if the tile has been used
				} while (deckCheck[index] == true);

				cardCounter++;
				deckCheck[index] = true;

			} while (cardCounter < 5);

			// make true; card cannot be chosen again
			// System.out.println(players[count]);

			for (int y = 0; y < board.getTiles().length; y++) {

				for (int x = 0; x < board.getTiles().length; x++) {

					for (Card current : players[count].getHand()) {

						if (board.getTiles()[x][y].getName() == current.getTreasure()) {

							board.getTiles()[x][y].setOwner(count);

						}
					}
				}

			}

		}

		board.startGame();

	}

	// action performed for click left click rotate left .....
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == insertButtons[0]) {
			board.move(board.getExtraTileIn(), 1, 0);
			insert = true;
		} else if (e.getSource() == insertButtons[1]) {
			board.move(board.getExtraTileIn(), 3, 0);
			insert = true;
		} else if (e.getSource() == insertButtons[2]) {
			board.move(board.getExtraTileIn(), 5, 0);
			insert = true;
		} else if (e.getSource() == insertButtons[3]) {
			board.move(board.getExtraTileIn(), 1, 1);
			insert = true;
		} else if (e.getSource() == insertButtons[4]) {
			board.move(board.getExtraTileIn(), 3, 1);
			insert = true;
		} else if (e.getSource() == insertButtons[5]) {
			board.move(board.getExtraTileIn(), 5, 1);
			insert = true;
		} else if (e.getSource() == insertButtons[6]) {
			board.move(board.getExtraTileIn(), 1, 2);
			insert = true;
		} else if (e.getSource() == insertButtons[7]) {
			board.move(board.getExtraTileIn(), 3, 2);
			insert = true;
		} else if (e.getSource() == insertButtons[8]) {
			board.move(board.getExtraTileIn(), 5, 2);
			insert = true;
		} else if (e.getSource() == insertButtons[9]) {
			board.move(board.getExtraTileIn(), 1, 3);
			insert = true;
		} else if (e.getSource() == insertButtons[10]) {
			board.move(board.getExtraTileIn(), 3, 3);
			insert = true;
		} else if (e.getSource() == insertButtons[11]) {
			board.move(board.getExtraTileIn(), 5, 3);
			insert = true;
		} else if (e.getSource() == turnButton) {

			checkCollect();

			needMove = false;

			removeMark();

			board.repaint();

			if (turn == 4)
				turn = 1;
			else
				turn++;

			setTurn();

			insert = false;

			for (JButton current : insertButtons)
				current.setEnabled(true);

			if (turn == 4) {

				board.decide();

			}

		}

		if (insert == true) {

			needMove = true;

			playSounds(2);

			for (JButton current : insertButtons)
				current.setEnabled(false);

			// board.printTiles();

			int x = players[turn].getToken().getX();
			int y = players[turn].getToken().getY();

			System.out.println("x:" + x + "   y:" + y);

			board.traverse(turn);
			board.repaint();

		}

	}

	private void checkCollect() {

		int x = players[turn].getToken().getX();
		int y = players[turn].getToken().getY();

		if (board.getTiles()[x][y].getOwner() == turn) {

			if (players[turn].getCardsLeft() == 0)
				System.out.println("Winner: Player " + turn);

			else {

				players[turn].setCardsLeft(players[turn].getCardsLeft() - 1);
				board.getTiles()[x][y].getImage().setVisible(false);

				System.out.println(players[turn].toString());
				// System.out.println("HERE");

				for (Card current : players[turn].getHand()) {

					if (current.getTreasure().equals(board.getTiles()[x][y].getName())) {

						current.setVisible(false);
					}

				}

			}

		}

	}

	private void removeMark() {

//		// left
//		if (players[turn].getToken().getX() - 1 >= 0) {
//
//			board.getTiles()[players[turn].getToken().getX() - 1][players[turn].getToken().getY()]
//					.remove(board.mark[0]);
//		}
//
//		// up
//		if (players[turn].getToken().getY() - 1 >= 0) {
//
//			board.getTiles()[players[turn].getToken().getX()][players[turn].getToken().getY() - 1]
//					.remove(board.mark[1]);
//
//		}
//
//		// right
//		if (players[turn].getToken().getX() + 1 <= 6) {
//			board.getTiles()[players[turn].getToken().getX() + 1][players[turn].getToken().getY()]
//					.remove(board.mark[2]);
//
//		}
//
//		// down
//		if (players[turn].getToken().getY() + 1 <= 6) {
//
//			board.getTiles()[players[turn].getToken().getX()][players[turn].getToken().getY() + 1]
//					.remove(board.mark[3]);
//			
//		}

		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				board.getTiles()[i][j].mark.setVisible(false);

			}

		}

	}

	private void setTurn() {

		turnLabel.setBounds(755, 105 + (120 * turn), 80, 82);

	}

	public static int getTurn() {
		return turn;
	}

	public static void playSounds(int selection) {

		// Holds the various files.
		File fileA = new File("sounds/ambient.wav");
		File fileB = new File("sounds/sten1.wav");

		// 1. If the selection is 1
		if (selection == 1) {

			// 1.1. Play the background sound
			try {
				Clip background = AudioSystem.getClip();
				background.open(AudioSystem.getAudioInputStream(fileA));
				background.start();
			} catch (Exception e) {
				System.out.println("Error1");
			}

			// 2. If the selection is 2
		} else if (selection == 2) {

			// 2.1. Play the insert sound
			try {
				Clip insert = AudioSystem.getClip();
				insert.open(AudioSystem.getAudioInputStream(fileB));
				insert.start();
			} catch (Exception e) {
				System.out.println("Error2");
			}

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (needMove == true) {

			boolean moved = false;

			int oldX = 0;
			int oldY = 0;

			int newX = 0;
			int newY = 0;

			// left
			if (e.getKeyCode() == 37) {

				newX = players[turn].getToken().getX() - 1;
				oldX = players[turn].getToken().getX();
				oldY = players[turn].getToken().getY();
				newY = oldY;

				if (newX >= 0) {

					if (board.getTiles()[newX][newY].getPath().charAt(2) == ('1')
							&& board.getTiles()[oldX][oldY].getPath().charAt(0) == ('1')) {

//						board.getTiles()[newX][newY].remove(board.mark[0]);

						moved = true;

					}

				}

				// up
			} else if (e.getKeyCode() == 38) {

				oldY = players[turn].getToken().getY();
				newY = players[turn].getToken().getY() - 1;

				newX = players[turn].getToken().getX();
				oldX = players[turn].getToken().getX();

				if (newY >= 0) {

					if (board.getTiles()[newX][newY].getPath().charAt(3) == ('1')
							&& board.getTiles()[oldX][oldY].getPath().charAt(1) == ('1')) {

//						board.getTiles()[newX][newY].remove(board.mark[1]);

						moved = true;

					}

				}

				// right
			} else if (e.getKeyCode() == 39) {

				oldY = players[turn].getToken().getY();
				newY = players[turn].getToken().getY();

				newX = players[turn].getToken().getX() + 1;
				oldX = players[turn].getToken().getX();

				if (newX <= 6) {

					if (board.getTiles()[newX][newY].getPath().charAt(0) == ('1')
							&& board.getTiles()[oldX][oldY].getPath().charAt(2) == ('1')) {

//						board.getTiles()[newX][newY].remove(board.mark[2]);

						moved = true;

					}

				}

				// down
			} else if (e.getKeyCode() == 40) {

				oldY = players[turn].getToken().getY();
				newY = players[turn].getToken().getY() + 1;

				newX = players[turn].getToken().getX();
				oldX = players[turn].getToken().getX();

				if (newY <= 6) {

					if (board.getTiles()[newX][newY].getPath().charAt(1) == ('1')
							&& board.getTiles()[oldX][oldY].getPath().charAt(3) == ('1')) {

//						board.getTiles()[newX][newY].remove(board.mark[3]);

						moved = true;

					}

				}

			}

			if (moved == true) {

//				removeMark();

				board.getTiles()[newX][newY].add(players[turn].getToken());

				players[turn].getToken().setX(newX);
				players[turn].getToken().setY(newY);

				repaint();

				board.traverse(turn);

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}

}
