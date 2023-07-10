package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Rectangle implements Drawable {
	private int x, y;
	private int width, height;
	private Color color;
	private Color selectedColor = Color.yellow;
	private boolean permanent; // determines if its a permanent figure or just a temporary one (visible lines
								// while drawing
	public boolean selected;
	private boolean grabbed = false;

	public Rectangle(int x, int y, int width, int height, Color c) {
		// this takes care of the creation in case the user "swipes" in the wrong
		// direction
		this.x = Math.min(x, width);
		this.y = Math.min(y, height);
		this.width = Math.max(x, width);
		this.height = Math.max(y, height);
		this.permanent = true;
		this.color = c;
		this.selected = false;
	}

	public Rectangle(int x, int y, int width, int height, Color c, boolean permanent) {
		// this takes care of the creation in case the user "swipes" in the wrong
		// direction
		this.x = Math.min(x, width);
		this.y = Math.min(y, height);
		this.width = Math.max(x, width);
		this.height = Math.max(y, height);
		this.permanent = permanent;
		this.color = c;
	}
	public boolean isPermanent() {
		return this.permanent;
	}
	
	@Override
	public void grab(boolean b) {
		this.grabbed = b;
	}
	@Override
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
		this.width += x;
		this.height += y;
	}
	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(color);
		g.fillRect(x, y, width - x, height - y);
		//draws the selected "version" of the oval
		if(selected) {
			Stroke oldStroke = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.setColor(selectedColor);
			g.drawRect(x, y, width - x, height - y);
			g.setStroke(oldStroke);
		}
		

	}

	@Override
	public boolean insideHitbox(int ex, int ey) {
		return (ex > this.x && ex < this.width  && ey > this.y && ey < this.height);
	}

	@Override
	public void setSelected(boolean b) {
		this.selected = b;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return this.selected;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setColor(Color c) {
		this.color = c;
		
	}

	@Override
	public boolean isGrabbed() {
		return this.grabbed;
	}


}
