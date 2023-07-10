package model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.PhotoComponent;
import view.PhotoBrowserApp;

public class PhotoComponentModel {
	private String path;
	boolean isFlipped;
	private PhotoComponent pc;
	public int firstDrawingPointX;
	public int firstDrawingPointY;
	public int oldX, oldY;

	private ArrayList<ChangeListener> changeListeners = new ArrayList<>();
	private ArrayList<ActionListener> actionListeners = new ArrayList<>();

	private ArrayList<Drawable> drawables = new ArrayList<>();

	/* Constructor */
	public PhotoComponentModel(String p, PhotoComponent pc) {
		this.path = p;
		this.isFlipped = false;
		this.pc = pc;
	}

	/* Getter returning the path of the photo */
	public void setPath(String path) {
		if (path != this.path) {
			this.path = path;
			notifyChangeListeners();
		}
	}

	/* Method checking if any drawable is selected */
	public boolean checkSelected() {
		for (Drawable d : drawables)
			if (d.isSelected())
				return true;
		return false;
	}
	
	/* Method returning the selected drawable (only implemented for one, in case of multi selection need to rework the method) */
	public Drawable getSelected() {
		for(Drawable d: drawables)
			if(d.isSelected()) return d;
		return null;
	}

	/* Method checking if any drawable is grabbed */
	public boolean checkGrabbed() {
		for (Drawable d : drawables)
			if (d.isGrabbed())
				return true;
		return false;
	}

	/* Method returning the drawable which the user clicked on */
	public Drawable isInShape(int x, int y) {
		for(int i = drawables.size()- 1; i >= 0; i--) {
			if(drawables.get(i).insideHitbox(x, y)) return drawables.get(i);
		}
		return null;
	}


	/* Method returning wether the photocomponent is flipped or not */
	public boolean isFlipped() {
		return this.isFlipped;
	}

	/* Method returning the path to the photo */
	public String getPath() {
		return this.path;
	}

	public ArrayList<Drawable> getDrawables() {
		return this.drawables;
	}

	/* Method making the photocomponent "flip" */
	public void flip(boolean b) {
		if (this.isFlipped != b) {
			if (b == false)
				fire();
			this.isFlipped = b;
			notifyChangeListeners();
		}
	}

	/* Method that instantiates the start of drawing variables */
	public void startDrawing(int x, int y) {
		this.firstDrawingPointX = x;
		this.firstDrawingPointY = y;
	}

	/* Method adding a drawable into the Drawables arraylist */
	public void addDrawable(Drawable drawable) {
		this.drawables.add(drawable);
	}

	/* Method clearing all the empty textZones left behind after clicks */
	public void clearEmptyTextZones() {
		int i = 0;
		int toRemove = -1;
		for (Drawable clearSelect : drawables) {
			// removes all empty text zones that are created when just clicking
			if (clearSelect instanceof TextObj && !((TextObj) (clearSelect)).getHasText())
				toRemove = i;
			i++;
		}
		// System.out.println("CLEARED");
		if (toRemove != -1)
			drawables.remove(toRemove);
	}

	/*
	 * Method clearing all the empty lines (lines who have no points added to them)
	 */
	public void clearEmptyLines() {
		int i = 0;
		int toRemove = -1;
		for (Drawable clearSelect : drawables) {
			// removes all empty text zones that are created when just clicking
			if (clearSelect instanceof StrokeObj && !((StrokeObj) (clearSelect)).getHasPoints())
				toRemove = i;
			i++;
		}
		System.out.println("CLEARED");
		if (toRemove != -1)
			drawables.remove(toRemove);
	}

	/* Method removing the selected status from all the drawables */
	public void clearSelections() {
		for (Drawable clearSelect : drawables) {
			clearSelect.setSelected(false);
		}
	}

	/*
	 * Method notifying the changelisteners and firing the actionlisteners when a
	 * pencil drawing is performed
	 */
	public void drawing() {
		fire();
		notifyChangeListeners();
	}

	/* Method creating a new line (only has one point == empty) */
	public void drawingBeginLine(int x, int y) {
		PhotoBrowserApp pba = this.pc.getApp();
		drawables.add(new StrokeObj(x, y, (int) pba.pencilSize.getValue(), pba.colorPick.getBackground()));
	}

	/* Method adding points to the currently drawn line */
	public void drawLine(int x, int y) {
		((StrokeObj) drawables.get(drawables.size() - 1)).addPoint(x, y);
	}

	/* Method making a drawable enter the "grabbed" status */
	public void grabDrawable(Drawable drawGrab, int oldX, int oldY) {
		// used to calculate the translation the drawable is going to get thit by
		this.oldX = oldX;
		this.oldY = oldY;
		//I didnt add drawGrab as part of the drawable class because i was not planning on grabbing lines
		if (drawGrab instanceof Rectangle)
			((Rectangle) drawGrab).grab(true);
		if (drawGrab instanceof Circle)
			((Circle) drawGrab).grab(true);
		if (drawGrab instanceof TextObj)
			((TextObj) drawGrab).grab(true);
		if(drawGrab instanceof StrokeObj)
			((StrokeObj) drawGrab).grab(true);

	}
	
	/* Method making the dragging of a drawable "visible" during the drag */
	public void dragGrabbedDrawable(int x, int y) {
		Drawable d = getSelected();
		int movedX = x - this.oldX;
		int movedY = y - this.oldY;
		if(d.isGrabbed()){
			d.move(movedX, movedY);	
			this.oldX = x;
			this.oldY = y;
		}
	
	}
	
	/* Method making a drawable lose its "grabbed" status */
	public void releaseDrawable(Drawable grabbed, int pos, int x, int y) {
		//calculating the translation
		int movedX = x - this.oldX;
		int movedY = y - this.oldY;
		// putting the moved drawable onto its new position
		grabbed.move(movedX, movedY);
		grabbed.grab(false);
			
	}

	/* Method used to draw a rectangle with mouseDragged */
	public void drawingRectangle(int x, int y) {
		// removing the last temporary rectangle added (the one drawn to simulate an animation)
		boolean toRemove = false;
		PhotoBrowserApp pba = this.pc.getApp();
		if (drawables.size() > 0 && drawables.get(drawables.size() - 1) instanceof Rectangle
				&& !((Rectangle) drawables.get(drawables.size() - 1)).isPermanent())
			toRemove = true;

		if (toRemove)
			drawables.remove(drawables.size() - 1);
		
		// adding the next temporary rectangle to keep the drawing simulation flowing
		Rectangle r = new Rectangle(this.firstDrawingPointX, this.firstDrawingPointY, x, y,
				pba.colorPickGeo.getBackground(), false);
		drawables.add(r);
	}

	/* Method used to draw a circle with mouseDragged */
	public void drawingCircle(int x, int y) {
		// removing the last temporary circle added (the one drawn to simulate an animation)
		boolean toRemove = false;
		PhotoBrowserApp pba = this.pc.getApp();
		if (drawables.size() > 0 && drawables.get(drawables.size() - 1) instanceof Circle
				&& !((Circle) drawables.get(drawables.size() - 1)).isPermanent())
			toRemove = true;

		if (toRemove)
			drawables.remove(drawables.size() - 1);
		
		// adding the next temporary circle to keep the drawing simulation flowing
		Circle c = new Circle(this.firstDrawingPointX, this.firstDrawingPointY, x, y, pba.colorPickGeo.getBackground(),
				false);
		drawables.add(c);
	}

	/* Method signaling that the mouse has been released (used to know when a rectangle or circle drawing has ended) */
	public void releasedMouse(int x, int y, int width, int height) {
		PhotoBrowserApp pba = this.pc.getApp();
		// adding rectangle
		if (pba.rectangle.isSelected()) {
			drawables.remove(drawables.size() - 1);// remove the last "temporary" drawing present in the arraylist
			//adding the PERMANENT rectangle (the one drawn where the user let go of the mouse button)
			drawables.add(new Rectangle(x, y, width, height, pba.colorPickGeo.getBackground()));
			// resetting the form buttons
			pba.rectangle.setSelected(false);
			pba.circle.setEnabled(true);
		}

		// adding circle
		if (pba.circle.isSelected()) {
			drawables.remove(drawables.size() - 1);// remove the last "temporary" drawing present in the arraylist
			//adding the PERMANENT circle (the one drawn where the user let go of the mouse button)
			drawables.add(new Circle(x, y, width, height, pba.colorPickGeo.getBackground()));
			// resetting the form buttons
			pba.circle.setSelected(false);
			pba.rectangle.setEnabled(true);
		}

	}

	// Listener Management
	public void addChangeListener(ChangeListener listener) {
		changeListeners.add(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		changeListeners.remove(listener);
	}

	public void addActionListener(ActionListener listener) {
		actionListeners.add(listener);
	}

	public void removeActionListener(ActionListener listener) {
		actionListeners.remove(listener);
	}

	private void notifyChangeListeners() {
		for (ChangeListener listener : changeListeners) {
			listener.stateChanged(new ChangeEvent(this));
		}
	}

	private void fire() {
		for (ActionListener listener : actionListeners) {
			listener.actionPerformed(new ActionEvent(this, (int) System.currentTimeMillis(), "fire"));
		}
	}
}
