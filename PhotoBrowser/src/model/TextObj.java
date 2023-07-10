package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

import view.PhotoComponentView;

public class TextObj implements Drawable {
	private int startX, startY;
	private boolean hasText = false;
	private ArrayList<Character> c;
	private ArrayList<ArrayList<Character>> textZones = new ArrayList<>();
	private ArrayList<Integer> whiteSpace = new ArrayList<>();
	private int imgHeight, imgWidth;
	private Color col;
	private Color selectedColor = Color.yellow;
	private boolean selected = false;
	private boolean grabbed = false;

	private int textZoneHeight;
	private int textZoneWidth;
	private int hitboxY;// the Y for the hitbox is different since charactes are drawn "above" the line
						// theyre on

	/* Constructor */
	public TextObj(int x, int y, int width, int height, Color color) {
		this.startX = x;
		this.startY = y;
		this.c = new ArrayList<>();
		this.imgHeight = height;
		this.imgWidth = width;
		this.col = color;

	}

	public void add(char ch) {
		this.c.add(ch);
		if (Character.isWhitespace(ch))
			whiteSpace.add(c.size() - 1);
		this.hasText = true;
	}

	public ArrayList<Character> getCurrLine() {
		return c;
	}

	public ArrayList<ArrayList<Character>> getZones() {
		return textZones;
	}

	public boolean getHasText() {
		return this.hasText;
	}

	@Override
	public void grab(boolean b) {
		this.grabbed = b;
	}

	@Override
	public void move(int x, int y) {
		this.startX += x;
		this.startY += y;
	}
	/* Method splitting the text into small groups in case of border "collision" */
	private void splitTextZones(int sizeString) {
		if (startX + sizeString > imgWidth) {
			int spaceLocation;
			// if there are spaces available in the written txt
			if (whiteSpace.size() == 0)
				spaceLocation = c.size() - 1;
			else
				spaceLocation = whiteSpace.get(whiteSpace.size() - 1);

			ArrayList<Character> temp = new ArrayList<>();
			for (int i = 0; i < spaceLocation; i++) {
				temp.add(c.get(i));// add all the characters up to the break point
			}

			textZones.add(temp);// adds it to the "old lines"
			ArrayList<Character> temp2 = new ArrayList<>();
			for (int i = spaceLocation; i < c.size(); i++) {
				temp2.add((c.get(i)));
			}
			this.c = temp2;// this is the current "new line"
			whiteSpace = new ArrayList<>();// we clear the white spaces, since we are on a new line
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(this.col);
		int lineJump = 0;// the amount of space to jump between each line
		
		if (textZones.size() != 0)// if there are any old lines to draw
			lineJump = drawOldLines(g, lineJump);

		// drawing of the "current line"
		StringBuilder sb = new StringBuilder(this.c.size());
		for (char ch : this.c) {
			sb.append(ch);
		}

		// if for some reason the current line is outside of the photocomponent borders
		// it isnt drawn
		if (lineJump + startY < imgHeight) {
			g.drawString(sb.toString(), startX, startY + lineJump);
		}

		int stringHeight = g.getFontMetrics().getMaxAscent() + g.getFontMetrics().getMaxDescent();
		int stringWidth = g.getFontMetrics().stringWidth(sb.toString());

		this.textZoneHeight = lineJump + stringHeight;
		if (stringWidth > this.textZoneWidth)
			this.textZoneWidth = stringWidth;
		this.hitboxY = startY - stringHeight;

		//draws the selected "version" of the oval
		if (selected) {
			Stroke oldStroke = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.setColor(selectedColor);
			g.drawRect(startX, hitboxY, textZoneWidth, textZoneHeight);
			g.setStroke(oldStroke);
		}

		splitTextZones(stringWidth);
	}
	
	public int drawOldLines(Graphics2D g, int lineJump) {
		StringBuilder sb2;

		// turning the arraylist of "old lines" into a string
		for (ArrayList<Character> alc : textZones) {
			sb2 = new StringBuilder(alc.size());
			for (char ch : alc) {
				sb2.append(ch);
			}
			// if the lines dont go outside the borders vertically
			if (lineJump + startY < imgHeight) {
				g.drawString(sb2.toString(), startX, startY + lineJump);// they are drawn
				//we increment the linejump to take care of the jumping to the next line
				lineJump += g.getFontMetrics().getMaxAscent() + g.getFontMetrics().getMaxDescent();																							
			}
		}
		return lineJump;
	}

	@Override
	public boolean insideHitbox(int x, int y) {
		return (x > this.startX && x < this.startX + this.textZoneWidth && y > this.hitboxY
				&& y < this.startY + this.textZoneHeight);
	}

	@Override
	public void setSelected(boolean b) {
		this.selected = b;

	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public Color getColor() {
		return this.col;
	}

	@Override
	public void setColor(Color c) {
		this.col = c;

	}

	@Override
	public boolean isGrabbed() {
		return this.grabbed;
	}

}
