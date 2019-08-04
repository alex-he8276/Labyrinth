import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Player extends JLabel {

	private String playerName;
	private int playerID;
	private int cardsLeft;

	Card[] hand = new Card[5];
	Token token = new Token();

	public Player(String playerName, int playerID) {

		setPlayerName(playerName);
		setPlayerID(playerID);
		token.setIcon(new ImageIcon("images/other/player" + playerID + ".png"));

		token.setPlayerID(playerID);
		if (playerID == 1) {
			token.setX(6);
			token.setY(6);
		} else if (playerID == 2) {
			token.setX(0);
			token.setY(6);
		} else if (playerID == 3) {
			token.setX(6);
			token.setY(0);
		} else {
			token.setX(0);
			token.setY(0);
		}

		setCardsLeft(5);

	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public Card[] getHand() {
		return hand;
	}

	public void setHand(Card[] hand) {
		this.hand = hand;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public int getCardsLeft() {
		return cardsLeft;
	}

	public void setCardsLeft(int cardsLeft) {
		this.cardsLeft = cardsLeft;
	}
	
	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", playerID=" + playerID + ", cardsLeft=" + cardsLeft + ", hand="
				+ Arrays.toString(hand) + ", token=" + token + "]";
	}

}