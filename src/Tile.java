
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Tile extends JLabel {

	private String name;
	private String shape;
	private int orientation;
	private boolean movable;
	private int column;
	private int row;
	private int owner;
	String path = "0000"; // 4 digit LURD

	private Random rand = new Random();

	JLabel image = new JLabel();
	JLabel mark = new JLabel();

	public Tile(String name, String shape, int orientation, boolean movable, int column, int row) {

		super();
		this.name = name;
		this.shape = shape;
		this.orientation = orientation;
		this.movable = movable;
		this.column = column;
		this.row = row;

		setOpaque(true);
		setVisible(true);
		setLayout(null);

		if (orientation == -1)
			this.orientation = rand.nextInt(4);

		setIcon(new ImageIcon("images/tiles/" + shape + this.orientation + ".PNG"));

		ImageIcon treasure = new ImageIcon("images/treasures/" + name + ".PNG");
		image.setIcon(treasure);
		image.setBounds(15, 10, treasure.getIconWidth(), treasure.getIconHeight());
		add(image);
		
		ImageIcon marker = new ImageIcon("images/other/mark.PNG");
		mark.setIcon(marker);
		mark.setBounds(0, 0, marker.getIconWidth(), marker.getIconHeight());
		mark.setVisible(false);
		add(mark);

		setPath();

	}

	public void setPath() {

		if (shape.equals("L")) {

			if (orientation == 0)
				path = "0110";
			else if (orientation == 1)
				path = "0011";
			else if (orientation == 2)
				path = "1001";
			else
				path = "1100";

		} else if (shape.equals("T")) {

			if (orientation == 0)
				path = "1011";
			else if (orientation == 1)
				path = "1101";
			else if (orientation == 2)
				path = "1110";
			else
				path = "0111";

		} else {

			if (orientation == 0 || orientation == 2)
				path = "0101";
			else if (orientation == 1 || orientation == 3)
				path = "1010";

		}

	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		if (orientation == -1)
			this.orientation = rand.nextInt(4);
		else
			this.orientation = orientation;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public JLabel getImage() {
		return image;
	}

	public void setImage(JLabel image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Tile [name=" + name + ", shape=" + shape + ", orientation=" + orientation + ", movable=" + movable
				+ ", column=" + column + ", row=" + row + ", owner=" + owner + ", path=" + path + " ]";
	}

}
