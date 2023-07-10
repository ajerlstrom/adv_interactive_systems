package model;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Drawable {
	public void draw(Graphics2D g);
	public boolean insideHitbox(int x, int y);
	
	public void setSelected(boolean b);
	public boolean isSelected();
	public Color getColor();
	public void setColor(Color c);
	public boolean isGrabbed();
	public void grab(boolean b);
	public void move(int x, int y);
	
	}
