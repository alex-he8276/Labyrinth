import java.awt.event.MouseEvent;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel implements KeyListener {

	private static Tile[][] tiles = new Tile[7][7]; // 2D-Tile array for the
													// board
	private static Tile[] randomTiles = new Tile[34]; // Tile array for the
														// random tiles
	private static boolean[] usedTiles = new boolean[34]; // Boolean array to
															// track used tiles
	private Tile extraTileIn; // Tile object for the extra tile
	private static Tile extraTileOut;

	private static int first = 0; // Used so labels are only added once

	private static Random rand = new Random(); // Random object used to
												// randomize orientation and
												// placement of tiles

	char[][] maze = new char[7][7];

	private int move = 0;

	static final int LEFT = 0;
	static final int UP = 1;
	static final int RIGHT = 2;
	static final int DOWN = 3;

	private int winningPlayer;

	JLabel[] mark = new JLabel[4];

	/*
	 * Alex He A constructor method that sets up the properties of the JPanel. It
	 * reads in the full tile list and sets up the 2-D tile array for the unmovable
	 * tiles. It adds the movable tiles to the random tiles array.
	 */
	public Board() {

		setBounds(100, 115, 800, 560);
		setOpaque(false);
		setLayout(null);
		addKeyListener(this);
		setFocusable(true);

		for (int count = 0; count < mark.length; count++) {

			mark[count] = new JLabel();
			mark[count].setIcon(new ImageIcon(
					new ImageIcon("images/other/border.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
			mark[count].setBounds(5, 5, 60, 60);

		}

		// 1. Read the treasures list to make the tiles
		try {

			// Program reads the csv file and make the delimiter
			Scanner input = new Scanner(new File("treasures.csv"));
			input.useDelimiter(",");
			input.nextLine();

			int unmovableCounter = 0; // Keeps count of the number of the
										// unmovable tiles
			int movableCounter = 0; // Keeps count of movable tiles
			String treasure;
			String type;
			int orientation;
			boolean movable;
			int x;
			int y;

			// 1.1 Reads the information from the downloaded csv file
			while (input.hasNextLine()) {

				treasure = input.next().replaceAll("\r", "").replaceAll("\n", "");
				type = input.next();
				orientation = input.nextInt();
				movable = input.nextBoolean();
				x = input.nextInt();
				y = input.nextInt();

				// 1.1.1 Add the 16 unmovable tiles directly to the tiles array.
				if (unmovableCounter < 16) {
					tiles[x][y] = new Tile(treasure, type, orientation, movable, x, y);
					unmovableCounter++;
					// System.out.println(tiles[x][y]);
				}

				// 1.1.2 Add the other 12 tiles with treasures to an array that
				// needs to be
				// random
				else if (movableCounter < 12) {
					randomTiles[movableCounter] = new Tile(treasure, type, orientation, movable, x, y);
					movableCounter++;
				}

			}

			// 1.2 Close the file
			input.close();

			// 2. Catch the possible error
		} catch (FileNotFoundException error) {

			// 2.1 If the file does not exist, send error message
			System.out.println("Sorry wrong file - please check the name");

		}

		// 3. Add the 13 "I" tiles to the random array and randomize orientation
		for (int count = 0; count < 13; count++) {

			int orientation = rand.nextInt(4);
			randomTiles[count + 12] = new Tile("none", "I", orientation, true, -1, -1);

		}

		// 4. Add the 9 L" tiles to the random array and randomize orientation
		for (int count = 0; count < 9; count++) {

			int orientation = rand.nextInt(4);
			randomTiles[count + 12 + 13] = new Tile("none", "L", orientation, true, -1, -1);

		}

		// 5. Call the generate tile array
		generateTileArray();
		generateTileBoard();

		// System.out.println("extratile: " + getExtraTileIn());

		allowMouse();

	}

	/*
	 * Alex He A method that generates the tile array in proper format. The
	 * individual tiles are inserted into the array.
	 */
	private void generateTileArray() {

		// Index used to keep track of all the used tiles (tiles can only be
		// used once)
		int index;

		// 1. Cycle through all the rows
		for (int y = 0; y < tiles.length; y++) {

			// 1.1. For each row, cycle through all the columns
			for (int x = 0; x < tiles.length; x++) {

				// 1.1.1 Generate a random tile if it is not a preset tile
				// Preset tiles exist on even rows and columns
				if ((y % 2 == 0 && x % 2 == 0) == false) {

					// 1.1.1.1 Generate a random tile that has not been used
					do {

						index = rand.nextInt(34);

						// 1.1.1.1.1 If tile has not been used, add to the board
						if (usedTiles[index] == false) {
							randomTiles[index].setColumn(x);
							randomTiles[index].setRow(y);
							tiles[x][y] = randomTiles[index];

						}

						// 1.1.1.2 Repeat if the tile has been used
					} while (usedTiles[index] == true);

					// 1.1.1.3 Track tile has been used
					usedTiles[index] = true;

				}

			}

		}

		// find unused tile- Daniel
		for (int index2 = 0; index2 <= 34; index2++) {

			if (usedTiles[index2] == false) {
				setExtraTileIn(randomTiles[index2]);
				extraTileIn.setBounds(700, 0, 80, 80);
				add(extraTileIn);
				break;
			}

		}

	}

	/*
	 * Alex He A method that generates the tiles onto the board properly. The
	 * individual tiles are added to the board during the first board generation and
	 * the first insertion. The location of the tiles are based on their x,y
	 * coordinate.
	 */
	private void generateTileBoard() {

		// 1. Cycle through all the rows
		for (int y = 0; y < tiles.length; y++) {

			// 1.1. For each row, cycle through all the columns
			for (int x = 0; x < tiles.length; x++) {

				// 1.1.1 Set bounds as a square and add the tile to the JPanel
				tiles[x][y].setBounds(80 * tiles[x][y].getColumn(), 80 * tiles[x][y].getRow(), 80, 80);

				// 1.1.1 Only add tiles for the first possible insertion
				if (first < 49) {

					add(tiles[x][y]);
					first++;
					// System.out.println(first);

				}

			}

		}

	}

	// move shifts tiles when player presses button - Daniel
	public void move(Tile extra, int index, int side) {
		// System.out.println("-----------------------------------------------------------");
		// 0 -> above, 1 -> from the right, 2 -> from below, 3 -> from the left

		if (side == 0) {

			// save last tile
			extraTileOut = tiles[index][6];

			for (int pos = 6; pos >= 1; pos--) {
				tiles[index][pos] = tiles[index][pos - 1];
				tiles[index][pos].setRow(tiles[index][pos].getRow() + 1);
			}

			extra.setRow(0);
			extra.setColumn(index);
			tiles[index][0] = extra;
			generateTileBoard();
			add(tiles[index][0]);
			setExtraTileIn(extraTileOut);

		} else if (side == 1) {

			// save last tile
			extraTileOut = tiles[0][index];

			for (int pos = 0; pos <= 5; pos++) {
				tiles[pos][index] = tiles[pos + 1][index];
				tiles[pos][index].setColumn(tiles[pos][index].getColumn() - 1);
			}

			extra.setRow(index);
			extra.setColumn(6);
			tiles[6][index] = extra;
			generateTileBoard();
			setExtraTileIn(extraTileOut);

		} else if (side == 2) {

			// save last tile
			extraTileOut = tiles[index][0];

			for (int pos = 0; pos <= 5; pos++) {
				tiles[index][pos] = tiles[index][pos + 1];
				tiles[index][pos].setRow(tiles[index][pos].getRow() - 1);
			}

			extra.setRow(6);
			extra.setColumn(index);
			tiles[index][6] = extra;
			generateTileBoard();
			setExtraTileIn(extraTileOut);

		} else if (side == 3) {

			// save last tile
			extraTileOut = tiles[6][index];

			for (int pos = 6; pos >= 1; pos--) {
				tiles[pos][index] = tiles[pos - 1][index];
				tiles[pos][index].setColumn(tiles[pos][index].getColumn() + 1);
			}

			extra.setRow(index);
			extra.setColumn(0);
			tiles[0][index] = extra;
			generateTileBoard();
			setExtraTileIn(extraTileOut);

		}

		// System.out.println("tilein" + extra);
		// System.out.println("tileout: " + getExtraTileIn());

		// playerFall(extraTileOut.getColumn(), extraTileOut.getRow());

		for (int num = 1; num < LabyrinthGUI.players.length; num++)

			if (side % 2 == 0) {

				if (playerFall(index, side, num)) {

					if (side == 0) {
						LabyrinthGUI.players[num].getToken().setY(0);
						tiles[LabyrinthGUI.players[num].getToken().getX()][0].add(LabyrinthGUI.players[num].getToken());
					} else {
						LabyrinthGUI.players[num].getToken().setY(6);
						tiles[LabyrinthGUI.players[num].getToken().getX()][6].add(LabyrinthGUI.players[num].getToken());
					}

				} else {

					// if in same column
					if (LabyrinthGUI.players[num].getToken().getX() == index) {

						if (side == 0)
							LabyrinthGUI.players[num].getToken().setY(LabyrinthGUI.players[num].getToken().getY() + 1);
						else
							LabyrinthGUI.players[num].getToken().setY(LabyrinthGUI.players[num].getToken().getY() - 1);

						System.out.println(LabyrinthGUI.players[num].getToken().getY());
					}

				}

			} else {

				if (playerFall(index, side, num)) {

					if (side == 1) {
						LabyrinthGUI.players[num].getToken().setX(6);
						tiles[6][LabyrinthGUI.players[num].getToken().getY()].add(LabyrinthGUI.players[num].getToken());
					} else {
						LabyrinthGUI.players[num].getToken().setX(0);
						tiles[0][LabyrinthGUI.players[num].getToken().getY()].add(LabyrinthGUI.players[num].getToken());
					}

				} else {

					// if in same row
					if (LabyrinthGUI.players[num].getToken().getY() == index) {

						if (side == 1)
							LabyrinthGUI.players[num].getToken().setX(LabyrinthGUI.players[num].getToken().getX() - 1);
						else
							LabyrinthGUI.players[num].getToken().setX(LabyrinthGUI.players[num].getToken().getX() + 1);

					}

				}

			}

		extraTileIn.setBounds(700, 0, 80, 80);

		allowMouse();

	}

	public boolean playerFall(int index, int side, int playerID) {

		if (side == 0) {

			if (LabyrinthGUI.players[playerID].getToken().getY() == 6
					&& (LabyrinthGUI.players[playerID].getToken().getX() == index))
				return true;

		} else if (side == 2) {

			if (LabyrinthGUI.players[playerID].getToken().getY() == 0
					&& (LabyrinthGUI.players[playerID].getToken().getX() == index))
				return true;

		} else if (side == 1) {

			if (LabyrinthGUI.players[playerID].getToken().getY() == index
					&& (LabyrinthGUI.players[playerID].getToken().getX() == 0))
				return true;

		} else if (side == 3) {

			if (LabyrinthGUI.players[playerID].getToken().getY() == index
					&& (LabyrinthGUI.players[playerID].getToken().getX() == 6))
				return true;

		}

		return false;

	}

	public void startGame() {

		tiles[6][6].add(LabyrinthGUI.players[1].getToken());
		tiles[0][6].add(LabyrinthGUI.players[2].getToken());
		// LabyrinthGUI.players[2].getToken().setX(1);
		// LabyrinthGUI.players[2].getToken().setY(1);
		tiles[6][0].add(LabyrinthGUI.players[3].getToken());
		tiles[0][0].add(LabyrinthGUI.players[4].getToken());

		// mazeTraversal(LabyrinthGUI.getTurn());

		// maze traversal
		// click = check
		// check if player own treasure if yes, remove treasure and flip card
		// down
		// if all cards are down winner (no more turns)

		// do {

		// } while (finished == false);

	}

	public void mazeTraversal(int playerID) {

		// left
		if (LabyrinthGUI.players[playerID].getToken().getX() - 1 >= 0) {

			if (tiles[LabyrinthGUI.players[playerID].getToken().getX() - 1][LabyrinthGUI.players[playerID].getToken()
					.getY()].getPath().charAt(2) == ('1')
					&& tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken()
							.getY()].getPath().charAt(0) == ('1')) {
				tiles[LabyrinthGUI.players[playerID].getToken().getX() - 1][LabyrinthGUI.players[playerID].getToken()
						.getY()].add(mark[0]);

			}

		}

		// up
		if (LabyrinthGUI.players[playerID].getToken().getY() - 1 >= 0) {

			if (tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken().getY()
					- 1].getPath().charAt(3) == ('1')
					&& tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken()
							.getY()].getPath().charAt(1) == ('1')) {
				tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken().getY()
						- 1].add(mark[1]);
			}
		}

		// right
		if (LabyrinthGUI.players[playerID].getToken().getX() + 1 <= 6) {

			if (tiles[LabyrinthGUI.players[playerID].getToken().getX() + 1][LabyrinthGUI.players[playerID].getToken()
					.getY()].getPath().charAt(0) == ('1')
					&& tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken()
							.getY()].getPath().charAt(2) == ('1')) {
				tiles[LabyrinthGUI.players[playerID].getToken().getX() + 1][LabyrinthGUI.players[playerID].getToken()
						.getY()].add(mark[2]);
			}

		}

		// down
		if (LabyrinthGUI.players[playerID].getToken().getY() + 1 <= 6) {

			if (tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken().getY()
					+ 1].getPath().charAt(1) == ('1')
					&& tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken()
							.getY()].getPath().charAt(3) == ('1')) {
				// down = true;
				tiles[LabyrinthGUI.players[playerID].getToken().getX()][LabyrinthGUI.players[playerID].getToken().getY()
						+ 1].add(mark[3]);

			}

		}

		repaint();

	}

	public void traverse(int playerID) {

		int startX = LabyrinthGUI.players[playerID].getToken().getX();
		int startY = LabyrinthGUI.players[playerID].getToken().getY();
		boolean result = AIMazeTraversal(playerID, startX, startY);

		// if (!result)
	}

	public boolean AIMazeTraversal(int playerID, int startX, int startY) {

		tiles[startX][startY].mark.setVisible(true);

		maze[startX][startY] = 'x';

		move++;

		for (int count = 0; count < 4; count++) {

			switch (count) {

			case LEFT:

				// if user can move left without hitting wall
				if (validMove(count, playerID, startX - 1, startY)) {
					if (AIMazeTraversal(playerID, startX - 1, startY))
						return true;
				}

				break;

			case UP: // move up

				// if user can move up without hitting wall
				if (validMove(count, playerID, startX, startY - 1)) {
					if (AIMazeTraversal(playerID, startX, startY - 1))
						return true;
				}

				break;

			case RIGHT: // move right

				// if user can move right without hitting wall

				if (validMove(count, playerID, startX + 1, startY)) {
					if (AIMazeTraversal(playerID, startX + 1, startY))
						return true;
				}

				break;

			case DOWN: // move down

				// if user can move down without hitting wall
				if (validMove(count, playerID, startX, startY + 1)) {
					if (AIMazeTraversal(playerID, startX, startY + 1))
						return true;
				}
			}

		}

//		maze[startX][startY] = '0';

		return false;

	}

	private boolean validMove(int direction, int playerID, int x, int y) {

		if ((x >= 0) && (x < 7) && (y >= 0) && (y < 7) && maze[x][y] != 'x') {

			if (direction == 0) {

				if (tiles[x][y].getPath().charAt(2) == ('1') && tiles[x + 1][y].getPath().charAt(0) == ('1')) {

					return true;

				}

			} else if (direction == 1) {

				if (tiles[x][y].getPath().charAt(3) == ('1') && tiles[x][y + 1].getPath().charAt(1) == ('1')) {

					return true;

				}

			} else if (direction == 2) {

				if (tiles[x][y].getPath().charAt(0) == ('1') && tiles[x - 1][y].getPath().charAt(2) == ('1')) {

					return true;
				}

			} else if (direction == 3) {

				if (tiles[x][y].getPath().charAt(1) == ('1') && tiles[x][y - 1].getPath().charAt(3) == ('1')) {

					return true;
				}

			}

		}

		return false;

	}

	private void allowMouse() {

		extraTileIn.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {

					rotateRight();

				}

				if (e.getButton() == MouseEvent.BUTTON3) {

					if (extraTileIn.getOrientation() == 3) {
						extraTileIn.setOrientation(0);
					} else {
						extraTileIn.setOrientation(extraTileIn.getOrientation() + 1);
					}

					extraTileIn.setIcon(new ImageIcon(
							"images/tiles/" + extraTileIn.getShape() + extraTileIn.getOrientation() + ".PNG"));

				}

				extraTileIn.setPath();
			}

		});

	}

	private void rotateRight() {

		if (extraTileIn.getOrientation() == 0 || extraTileIn.getOrientation() == -1) {
			extraTileIn.setOrientation(3);
		} else {
			extraTileIn.setOrientation(extraTileIn.getOrientation() - 1);
		}

		extraTileIn.setIcon(
				new ImageIcon("images/tiles/" + extraTileIn.getShape() + extraTileIn.getOrientation() + ".PNG"));

	}

	public void printTiles() {

		for (int y = 0; y < tiles.length; y++) {

			for (int x = 0; x < tiles.length; x++) {

				System.out.println(tiles[x][y]);
			}

		}
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public static void setTiles(Tile[][] tiles) {
		Board.tiles = tiles;
	}

	public Tile getExtraTileIn() {
		return extraTileIn;
	}

	public void setExtraTileIn(Tile extraTileIn) {
		this.extraTileIn = extraTileIn;
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	// AI CODE
	public void decide() {

		winningPlayer = 1;

		for (int count = 1; count <= 4; count++) {

			if (LabyrinthGUI.players[count].getCardsLeft() < LabyrinthGUI.players[winningPlayer].getCardsLeft())
				winningPlayer = count;

		}

		// Play offensively if tied or currently winning
		if (LabyrinthGUI.players[4].getCardsLeft() <= LabyrinthGUI.players[winningPlayer].getCardsLeft())
			playOffensively();
		else
			playDefensively();

	}

	private void playOffensively() {

		boolean[][] possibleTiles = new boolean[7][7];
		boolean[][] cardLocations = new boolean[7][7];

		// 1. After maze traversal, determine all possible tiles to travel to
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				if (LabyrinthGUI.getBoard().getTiles()[i][j].mark.isVisible())
					possibleTiles[i][j] = true;

			}

		}

		// 2. Determine the location of all cards
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				for (int k = 0; k < 5; k++) {

					if (LabyrinthGUI.getBoard().getTiles()[i][j].getName()
							.equals(LabyrinthGUI.players[4].getHand()[k].getTreasure()))
						cardLocations[i][j] = true;

				}

			}

		}

		int treasureX = 6;
		int treasureY = 6;
		int directTreasures = 0;
		boolean directPath = false;

		// 3. Check for direct path
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				if (cardLocations[i][j] == true && possibleTiles[i][j] == true) {

					directTreasures++;
					treasureX = i;
					treasureY = j;
					directPath = true;

				}

			}

		}

		// 4. If there is a direct path...
		if (directPath == true) {

//			// 4.1 Compare current location and treasure location
//			if (treasureX > 1 && LabyrinthGUI.players[4].getToken().getX() > 1) {
//
//				// insert at top or bot 1
//			} else if (treasureX < 5 && LabyrinthGUI.players[4].getToken().getX() < 5) {
//
//				// insert at top or bot 5
//			} else {
//
//				// insert at top or bot 1
//			}

			// 4.1 Try every combination of tile orientation
			for (int t = 0; t < 4; t++) {

				int directTreasures2 = 0;
				rotateRight();

				// 4.1.1. Try every combination of button orientation
				for (int b = 0; b < 12; b++) {

					LabyrinthGUI.insertButtons[b].doClick();
					
					// Check the number of direct treasures now
					for (int i = 0; i < 7; i++) {

						for (int j = 0; j < 7; j++) {

							if (cardLocations[i][j] == true && possibleTiles[i][j] == true)
								directTreasures2++;

						}

					}

					// If the shift was "bad", less direct paths, undo
					if (directTreasures2 < directTreasures) {

						undoShift(t, b);
						
						// If the shift was "good", go to the direct treasure
					} else {

						// Find direct treasure

						// Go to direct treasure
						// (create clicking mechanism) (just an if it has visible mark lol)

					}

				}

			}

		}

		int distanceX = 6;
		int distanceY = 6;

		// 6. If no direct path
		if (directPath == false) {

			// look for closest treasure;

			for (int i = 0; i < 7; i++) {

				for (int j = 0; j < 7; j++) {

					if (cardLocations[i][j] == true) {

						if (Math.abs(LabyrinthGUI.players[4].getToken().getX() - i)
								+ Math.abs(LabyrinthGUI.players[4].getToken().getY() - j) < distanceX + distanceY) {

							distanceX = Math.abs(LabyrinthGUI.players[4].getToken().getX() - i);
							distanceY = Math.abs(LabyrinthGUI.players[4].getToken().getY() - j);

							treasureX = i;
							treasureY = j;

						}

					}

				}

			}

			// insert???
			// now i have coordinates for the closest treasure, now look for the spot closet
			// to that

		}

	}

	private void undoShift(int t, int b) {

		if (b == 0) {
			LabyrinthGUI.insertButtons[8].doClick();
		} else if (b == 1) {
			LabyrinthGUI.insertButtons[7].doClick();
		} else if (b == 2) {
			LabyrinthGUI.insertButtons[6].doClick();
		} else if (b == 3) {
			LabyrinthGUI.insertButtons[11].doClick();
		} else if (b == 4) {
			LabyrinthGUI.insertButtons[10].doClick();
		} else if (b == 5) {
			LabyrinthGUI.insertButtons[9].doClick();
		} else if (b == 6) {
			LabyrinthGUI.insertButtons[2].doClick();
		} else if (b == 7) {
			LabyrinthGUI.insertButtons[1].doClick();
		} else if (b == 8) {
			LabyrinthGUI.insertButtons[0].doClick();
		} else if (b == 9) {
			LabyrinthGUI.insertButtons[5].doClick();
		} else if (b == 10) {
			LabyrinthGUI.insertButtons[4].doClick();
		} else if (b == 11) {
			LabyrinthGUI.insertButtons[3].doClick();
		} 
		
	}

	private void playDefensively() {

		// Look where winning player is
		int winningPlayerX = LabyrinthGUI.players[winningPlayer].getToken().getX();
		int winningPlayerY = LabyrinthGUI.players[winningPlayer].getToken().getY();

		// find his closest treasure
		// insert a tile to block him off to closest treasure

	}

}
