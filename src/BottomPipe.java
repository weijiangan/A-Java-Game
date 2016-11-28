import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class BottomPipe {
	//global variables
	private Image bottomPipe;
	private int xLoc = 0, yLoc = 0;
	
	/**
	 * Default constructor
	 */
	public BottomPipe(int initialWidth, int initialHeight) {
		bottomPipe = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/tube_bottom.png"));
		scaleBottomPipe(initialWidth, initialHeight);
	}
	
	/**
	 * Method to scale the BottomPipe sprite into the desired dimensions
	 * @param width The desired width of the BottomPipe
	 * @param height The desired height of the BottomPipe
	 */
	public void scaleBottomPipe(int width, int height) {
		bottomPipe = bottomPipe.getScaledInstance(width, height, Image.SCALE_SMOOTH);		
	}
	
	/**
	 * Getter method for the BottomPipe object.
	 * @return Image
	 */
	public Image getPipe() {
		return bottomPipe;
	}
	
	/**
	 * Method to obtain the width of the BottomPipe object
	 * @return int
	 */
	public int getWidth() {
		return bottomPipe.getWidth(null);
	}
	
	/**
	 * Method to obtain the height of the BottomPipe object
	 * @return int
	 */
	public int getHeight() {
		return bottomPipe.getHeight(null);
	}
	
	/**
	 * Method to set the x location of the BottomPipe object
	 * @param x
	 */
	public void setX(int x) {
		xLoc = x;
	}
	
	/**
	 * Method to get the x location of the BottomPipe object
	 * @return int
	 */
	public int getX() {
		return xLoc;
	}
	
	/**
	 * Method to set the y location of the BottomPipe object
	 * @param y
	 */
	public void setY(int y) {
		yLoc = y;
	}
	
	/**
	 * Method to get the y location of the BottomPipe object
	 * @return int
	 */
	public int getY() {
		return yLoc;
	}
	
	/**
	 * Method used to acquire a Rectangle that outlines the BottomPipe's image
	 * @return Rectangle outlining the BottomPipe's position on screen
	 */
	public Rectangle getRectangle() {
		return (new Rectangle(xLoc, yLoc, bottomPipe.getWidth(null), bottomPipe.getHeight(null)));
	}
	
	/**
	 * Method to acquire a BufferedImage that represents the TopPipe's image object
	 * @return TopPipe's BufferedImage object
	 */
	public BufferedImage getBI() {
		BufferedImage bi = new BufferedImage(bottomPipe.getWidth(null), bottomPipe.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(bottomPipe, 0, 0, null);
		g.dispose();
		return bi;
	}
}