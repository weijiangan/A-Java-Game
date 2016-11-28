import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Bird {
	//global variables
	private Image flappyBird;
	private int xLoc = 0, yLoc = 0;
	
	/**
	 * Default constructor
	 */
	public Bird(int initialWidth, int initialHeight) {
		flappyBird = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/blue_bird.png"));
		scaleBird(initialWidth, initialHeight);
	}
	
	/**
	 * Method to scale the bird sprite into the desired dimensions
	 * @param width The desired width of the flappy bird
	 * @param height The desired height of the flappy bird
	 */
	public void scaleBird(int width, int height) {
		flappyBird = flappyBird.getScaledInstance(width, height, Image.SCALE_SMOOTH);		
	}
	
	/**
	 * Getter method for the flappyBird object.
	 * @return Image
	 */
	public Image getBird() {
		return flappyBird;
	}
	
	/**
	 * Method to obtain the width of the Bird object
	 * @return int
	 */
	public int getWidth() {
		try {
			return flappyBird.getWidth(null);
		}
		catch(Exception e) {
			return -1;
		}
	}
	
	/**
	 * Method to obtain the height of the Bird object
	 * @return int
	 */
	public int getHeight() {
		try {
			return flappyBird.getHeight(null);
		}
		catch(Exception e) {
			return -1;
		}
	}
	
	/**
	 * Method to set the x location of the Bird object
	 * @param x
	 */
	public void setX(int x) {
		xLoc = x;
	}
	
	/**
	 * Method to get the x location of the Bird object
	 * @return int
	 */
	public int getX() {
		return xLoc;
	}
	
	/**
	 * Method to set the y location of the Bird object
	 * @param y
	 */
	public void setY(int y) {
		yLoc = y;
	}
	
	/**
	 * Method to get the y location of the Bird object
	 * @return int
	 */
	public int getY() {
		return yLoc;
	}
	
	/**
	 * Method used to acquire a Rectangle that outlines the Bird's image
	 * @return Rectangle outlining the bird's position on screen
	 */
	public Rectangle getRectangle() {
		return (new Rectangle(xLoc, yLoc, flappyBird.getWidth(null), flappyBird.getHeight(null)));
	}
	
	/**
	 * Method to acquire a BufferedImage that represents the Bird's image object
	 * @return Bird's BufferedImage object
	 */
	public BufferedImage getBI() {
		BufferedImage bi = new BufferedImage(flappyBird.getWidth(null), flappyBird.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(flappyBird, 0, 0, null);
		g.dispose();
		return bi;
	}
}