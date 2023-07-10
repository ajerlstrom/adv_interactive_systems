package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;

public class StrokeObj implements Drawable{
	private Color fillColor;
	private Color selectColor = Color.yellow;
	private int x, y, width;
	private ArrayList<Point> linePoints;
	private boolean hasPoints;
	private boolean isSelected = false;
	private boolean grabbed = false;
	public StrokeObj(int x, int y, int width, Color c) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.fillColor = c;
		this.linePoints = new ArrayList<>();
		this.hasPoints = false;
		linePoints.add(new Point(x, y));
	}
	

	public void addPoint(int x, int y) {
		this.linePoints.add(new Point(x, y));
		this.hasPoints = true;
	}
	
	public boolean getHasPoints() {
		return this.hasPoints;
	}
	
	@Override
	public void grab(boolean b) {
		this.grabbed = b;
	}
	
	@Override
	public void move(int x, int y) {
		for(Point p : linePoints) {
			p.x += x;
			p.y += y;
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(linePoints.size() == 0) return;
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(width));
		
		//sets the color for the selected "version" of the oval
		if(this.isSelected) g.setColor(selectColor);
		else g.setColor(fillColor);
		int currX, currY;
		int oldX = linePoints.get(0).x;
		int oldY = linePoints.get(0).y;
		//drawing the lines
		for(int i = 1; i < linePoints.size(); i++) {
			currX = linePoints.get(i).x;
			currY = linePoints.get(i).y;
			g.drawLine(oldX, oldY, currX, currY);
			oldX = currX;
			oldY = currY;
		}
		g.setStroke(oldStroke);
		
	}
	
	@Override
	public boolean insideHitbox(int x, int y) {
		
		double distAC, distCB, distAB;
		Point prevPoint = new Point();
		for(Point p : linePoints) {
			if(prevPoint == null) prevPoint = p;
			else {
				distAB = calcDistance((double) prevPoint.x, (double) prevPoint.y,(double) p.x,(double) p.y);
				distAC = calcDistance((double) prevPoint.x, (double) prevPoint.y, (double) x, (double) y);
				distCB = calcDistance((double) x, (double) y,(double) p.x,(double) p.y);
				//double test = distAC + distCB;
				//System.out.println("DIST AB :" + distAB + " SUMM = " + test);
				//if true, the point is between the 2 points that are currently tested
				if(distAC + distCB <= distAB + 9 && distAC + distCB >= distAB) return true;
			}
			prevPoint = p;
		}
		return false;
	
	}
	
	/* Method returning the distance between 2 points */
	public double calcDistance(double ax, double ay, double bx, double by) {
		double formula = (ax - bx) * (ax - bx) + (ay - by) * (ay - by);
		return Math.sqrt(formula);
	}


	@Override
	public void setSelected(boolean b) {
		this.isSelected = b;
		
	}


	@Override
	public boolean isSelected() {
		return this.isSelected;
	}


	@Override
	public Color getColor() {
		return this.fillColor;
	}


	@Override
	public void setColor(Color c) {
		this.fillColor = c;
		
	}


	@Override
	public boolean isGrabbed() {
		return this.grabbed;
	}

}
