import javax.swing.*;

@SuppressWarnings("serial")
public class Card extends JLabel {

	private String treasure;
	private int owner;

	public Card(String treasure, int owner) {

		setTreasure(treasure);
		setOwner(owner);
		
		setOpaque(true);
		setVisible(true);

		setIcon(new ImageIcon("images/other/card.PNG"));

		JLabel image = new JLabel();
		ImageIcon treasureImage = new ImageIcon("images/treasures/" + treasure + ".PNG");
		image.setIcon(treasureImage);
		image.setBounds(5, 15, treasureImage.getIconWidth(), treasureImage.getIconHeight());
		add(image);

	}

	public String getTreasure() {
		return treasure;
	}

	public void setTreasure(String treasure) {
		this.treasure = treasure;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Card [treasure=" + treasure + ", owner=" + owner + "]";
	}

}
