
public class AI extends Player {

	int winningPlayer;

	public AI(String playerName, int playerID) {

		super(playerName, playerID);

	}

	public void decide() {

		winningPlayer = 1;

		for (int count = 1; count <= 4; count++) {

			if (LabyrinthGUI.players[count].getCardsLeft() < LabyrinthGUI.players[winningPlayer].getCardsLeft())
				winningPlayer = count;

		}

		// Play offensively if tied or currently winning
		if (super.getCardsLeft() >= LabyrinthGUI.players[winningPlayer].getCardsLeft())
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

					if (LabyrinthGUI.getBoard().getTiles()[i][j].getName().equals(super.getHand()[k].getTreasure()))
						cardLocations[i][j] = true;

				}

			}

		}

		int treasureX = 6;
		int treasureY = 6;
		boolean directPath = false;

		// 3. Check for direct path
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				if (cardLocations[i][j] == true && possibleTiles[i][j] == true) {

					treasureX = i;
					treasureY = j;
					directPath = true;

				}

			}

		}

		// perhaps use x and y
		// perhaps check which row/column is being used least
		// 4. If there is a direct path (work work if it crosses, need to find out the
		// path)
		if (directPath == true) {

			// 4.1 Compare current location and treasure location
			if (treasureX > 1 && super.getToken().getX() > 1) {

				// insert at top or bot 1
			} else if (treasureX < 5 && super.getToken().getX() < 5) {

				// insert at top or bot 5
			} else {

				// insert at top or bot 1
			}

		}

		// 5. Click direct path
		// (create clicking mechanism) (just an if it has visible mark lol)

		int distanceX = 6;
		int distanceY = 6;

		// 6. If no direct path
		if (directPath == false) {

			// look for closest treasure;

			for (int i = 0; i < 7; i++) {

				for (int j = 0; j < 7; j++) {

					if (cardLocations[i][j] == true) {

						if (Math.abs(super.getToken().getX() - i) + Math.abs(super.getToken().getY() - j) < distanceX
								+ distanceY) {
							
							distanceX = Math.abs(super.getToken().getX() - i);
							distanceY = Math.abs(super.getToken().getY() - j);

							treasureX = i;
							treasureY = j;
							
						}

					}

				}

			}
		
			//insert???
			//now i have coordinates for the closest treasure, now look for the spot closet to that

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