package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import controller.PhotoComponent;
import model.Circle;
import model.Drawable;
import model.PhotoComponentModel;
import model.Rectangle;
import model.StrokeObj;
import model.TextObj;

public class PhotoComponentView {
	public int imgWidth = 200;
	public int imgHeight = 200;

	/* Constructor */
	public PhotoComponentView(PhotoComponent controller) {
		installListeners(controller);
	}

	public void paint(Graphics2D g, JComponent c) throws IOException {

		Color bgColor = Color.lightGray;

		// Image dimensions
		BufferedImage img = ImageIO.read(new File(((PhotoComponent) (c)).getModel().getPath()));
		int imgHeight = img.getHeight();
		int imgWidth = img.getWidth();
		this.imgHeight = imgHeight;
		this.imgWidth = imgWidth;

		// App dimensions
		Dimension dim = ((PhotoComponent) (c)).getApp().getSize();
		int width = dim.width;
		int height = dim.height;

		// to determine what the dimensions of the background should be
		int biggestWidth, biggestHeight;
		biggestWidth = Math.max(width, imgWidth);
		biggestHeight = Math.max(height, imgHeight);

		g.setColor(bgColor);
		g.fillRect(0, 0, biggestWidth, biggestHeight);
		g.drawImage(img, 0, 0, null);
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		// THE PART WHERE THE ASSIGNMENT ISN'T SUPER CLEAR IS HERE: SHOULD I SEE ANNOTATIONS AT ALL TIME OR ONLY WHEN I 
		//DOUBLE CLICK? OR DOES THE DOUBLE CLICK SIMPLY ENABLE THE WRITING OF ANNOTATIONS: IF YES REMOVE THE FOLLOWING LINE, ELSE ADD IT
		//depending if the assignment is asking us to only have visible annotations when double clicking or not you can remove this line
		
		if(!((PhotoComponent) (c)).getModel().isFlipped()) return;
		
		// draws all the drawable onto the photocomponent
		Color col = Color.black;
		for (Drawable drawable : ((PhotoComponent) (c)).getModel().getDrawables()) {
			//getting the colorPicker color for the selected type of drawable
			if (drawable.isSelected()) {
				if (drawable instanceof Rectangle || drawable instanceof Circle) 
					col = ((PhotoComponent) (c)).getApp().colorPickGeo.getBackground();
					
				if (drawable instanceof TextObj) 
					col = ((PhotoComponent) (c)).getApp().colorPickText.getBackground();
				
				if (drawable instanceof StrokeObj) 
					col = ((PhotoComponent) (c)).getApp().colorPick.getBackground();
					
				drawable.setColor(col);	
			}
			//System.out.println(((PhotoComponent) (c)).getModel().getDrawables().size());
			drawable.draw((Graphics2D) g);
		}
	}

	// determines if x and y is in the photocomponent "hitbox", used for the pencil
	// drawing
	public boolean isInWindow(int x, int y) {
		return (x > 0 && x < this.imgWidth && y > 0 && y < this.imgHeight);
	}

	/* Installing all the listeners for the photocomponent */
	private void installListeners(PhotoComponent c) {
		c.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				c.keyTyped(e);
			}
		});

		c.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				c.mouseClicked(e);
					c.repaint();
					c.click();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				c.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				c.mouseReleased(e);
			}
		});

		c.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				c.mouseDragged(e);
			
			}
		});
	}



	/* Method returning the preferred size of the photocomponent */
	public Dimension getPreferredSize() {
		return new Dimension(this.imgWidth, this.imgHeight);
	}

	/* Getter for the image width */
	public int getCWidth() {
		return this.imgWidth;
	}

	/* Getter for the image height */
	public int getCHeight() {
		return this.imgHeight;
	}
}
