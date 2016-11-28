import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class TopPipe {
	//global variables
	private Image topPipe;
	private int xLoc = 0, yLoc = 0;

	/**
	 * Default constructor
	 */
	public TopPipe(int initialWidth, int initialHeight) {
		topPipe = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/tube_top.png"));
		scaleTopPipe(initialWidth, initialHeight);
	}

	/**
	 * Method to scale the topPipe sprite into the desired dimensions
	 * @param width The desired width of the topPipe
	 * @param height The desired height of the topPipe
	 */
	public void scaleTopPipe(int width, int height) {
		topPipe = topPipe.getScaledInstance(width, height, Image.SCALE_SMOOTH);		
	}

	/**
	 * Getter method for the TopPipe object.
	 * @return Image
	 */
	public Image getPipe() {
		return topPipe;
	}

	/**
	 * Method to obtain the width of the TopPipe object
	 * @return int
	 */
	public int getWidth() {
		return topPipe.getWidth(null);
	}

	/**
	 * Method to obtain the height of the TopPipe object
	 * @return int
	 */
	public int getHeight() {
		return topPipe.getHeight(null);
	}

	/**
	 * Method to set the x location of the TopPipe object
	 * @param x
	 */
	public void setX(int x) {
		xLoc = x;
	}

	/**
	 * Method to get the x location of the TopPipe object
	 * @return int
	 */
	public int getX() {
		return xLoc;
	}

	/**
	 * Method to set the y location of the TopPipe object
	 * @param y
	 */
	public void setY(int y) {
		yLoc = y;
	}

	/**
	 * Method to get the y location of the TopPipe object
	 * @return int
	 */
	public int getY() {
		return yLoc;
	}

	/**
	 * Method used to acquire a Rectangle that outlines the TopPipe's image
	 * @return Rectangle outlining the TopPipe's position on screen
	 */
	public Rectangle getRectangle() {
		return (new Rectangle(xLoc, yLoc, topPipe.getWidth(null), topPipe.getHeight(null)));
	}
	
	/**
	 * Method to acquire a BufferedImage that represents the TopPipe's image object
	 * @return TopPipe's BufferedImage object
	 */
	public BufferedImage getBI() {
		BufferedImage bi = new BufferedImage(topPipe.getWidth(null), topPipe.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(topPipe, 0, 0, null);
		g.dispose();
		return bi;
	}
}