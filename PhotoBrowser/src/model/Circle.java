package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Circle implements Drawable {
	private int x, y;
	private int width, height;
	private Color color;
	private Color selectedColor = Color.yellow;
	private boolean selected = false;
	private boolean grabbed = false;
	private boolean permanent;

	public Circle(int x, int y, int width, int height, Color c) {
		this.x = Math.min(x, width);
		this.y = Math.min(y, height);
		this.width = Math.max(x, width);
		this.permanent = true;
		this.height = Math.max(y, height);
		this.color = c;
	}
	
	public Circle(int x, int y, int width, int height, Color c, boolean permanent) {
		this.x = Math.min(x, width);
		this.y = Math.min(y, height);
		this.width = Math.max(x, width);
		this.permanent = false;
		this.height = Math.max(y, height);
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
		g.fillOval(x, y, width - this.x, height - this.y);
		// g.drawRect(x, y, width, height);
		//draws the selected "version" of the oval
		if (selected) {
			Stroke oldStroke = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.setColor(selectedColor);
			g.drawOval(x, y, width - this.x, height - this.y);
			g.setStroke(oldStroke);
		}
	}

	@Override
	public boolean insideHitbox(int x, int y) {
		double a = Math.max(width - this.x, height - this.y)/2;
		double b = Math.min(width - this.x, height - this.y)/2;
		double centerX = this.x + (this.width - this.x) / 2;
		double centerY = this.y + (this.height - this.y) / 2;
		// formula from
		// https://www.geeksforgeeks.org/check-if-a-point-is-inside-outside-or-on-the-ellipse/
		double eq1 = ((x - centerX) * (x - centerX)) / (a * a);
		double eq2 = ((y - centerY) * (y - centerY)) / (b * b);
		double finaleq = eq1 + eq2;

		System.out.println("FORMULA RESULT = " + finaleq);
		return finaleq < 1;
	}

	@Override
	public void setSelected(boolean b) {
		this.selected = b;

	}

	@Override
	public boolean isSelected() {
		return selected;
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
