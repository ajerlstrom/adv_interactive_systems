package controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import model.Circle;
import model.Drawable;
import model.PhotoComponentModel;
import model.Rectangle;
import model.StrokeObj;
import model.TextObj;
import view.PhotoBrowserApp;
import view.PhotoComponentView;

public class PhotoComponent extends JComponent {
	// private boolean isFlipped;
	// private String path;

	private PhotoComponentModel pcm;
	private PhotoComponentView pcv;
	private PhotoBrowserApp pba;// this serves to retreive the size of the app

	/* Constructor */
	public PhotoComponent(String path, PhotoBrowserApp pba) {
		this.pcm = new PhotoComponentModel(path, this);
		this.pcv = new PhotoComponentView(this);
		this.pba = pba;
		this.pcm.addChangeListener(e -> repaint());

	}

	/* Getter for the model */
	public PhotoComponentModel getModel() {
		return this.pcm;
	}

	/* Getter for the app */
	public PhotoBrowserApp getApp() {
		return this.pba;
	}

	/* Method resetting the "drawing" buttons */
	public void resetPCButtons() {
		pba.circle.setEnabled(true);
		pba.circle.setSelected(false);
		pba.rectangle.setEnabled(true);
		pba.rectangle.setSelected(false);
	}

	/* EVENT HANDLING */
	public void keyTyped(KeyEvent e) {
		// if we're not in writing mode or buttons are not toggled
		if (!pcm.isFlipped() || pba.checkPCToggleButtons())
			return;
		// checking if a textZone is selected
		boolean textZoneSelected = false;
		ArrayList<Drawable> drawables = this.pcm.getDrawables();
		for (Drawable dr : drawables) {
			if (dr.isSelected() && dr instanceof TextObj) {
				((TextObj) (dr)).add(e.getKeyChar());
				textZoneSelected = true;
			}

		}
		// if no textZone is selected, get last item in drawables (which is a textZone,
		// otherwhise keytyped wouldn't get called
		if (!textZoneSelected)
			((TextObj) (drawables.get(drawables.size() - 1))).add(e.getKeyChar());

		this.pcm.drawing();
	}

	public void mouseClicked(MouseEvent e) {
		// toggling drawing annotations
		if (e.getClickCount() == 2) {
			doubleClick();
			// clear all empty drawables
			this.pcm.clearEmptyTextZones();
			this.pcm.clearEmptyLines();
			pba.statusBarMsg.setText("DRAWING TOGGLED TO: " + pcm.isFlipped());
		} else {

			// clear all empty drawables
			this.pcm.clearEmptyTextZones();
			this.pcm.clearEmptyLines();
			this.pcm.clearSelections();
			resetPCButtons();// by clicking on the photocomponent the buttons are reset to normal (just like
								// the selection)
			if (!this.pcm.isFlipped())
				return;

			// checking if a shape is getting selected
			if (this.pcm.isInShape(e.getX(), e.getY()) != null) {
				Drawable selected = this.pcm.isInShape(e.getX(), e.getY());
				selected.setSelected(true);
				// sets up the colorPicker buttons background color
				if (selected instanceof Rectangle || selected instanceof Circle)
					pba.colorPickGeo.setBackground(selected.getColor());
				if (selected instanceof TextObj)
					pba.colorPickText.setBackground(selected.getColor());
				if(selected instanceof StrokeObj)
					pba.colorPick.setBackground(selected.getColor());
			} else {
				// checking if click is in PhotoComponent borders
				if (!this.pcv.isInWindow(e.getX(), e.getY()))
					return;
				int x = e.getX();
				int y = e.getY();
				// create a new textZone
				TextObj typedText = new TextObj(x, y, this.pcv.getCWidth(), this.pcv.getCHeight(),
						pba.colorPickText.getBackground());
				this.pcm.addDrawable(typedText);

			}
		}

	}

	public void mousePressed(MouseEvent e) {
		if (!this.pcm.isFlipped())
			return;
		this.pcm.clearEmptyTextZones();
		this.pcm.clearEmptyLines();
		// rectangle or circle drawing
		if (pba.rectangle.isSelected() || pba.circle.isSelected()) {
			this.pcm.startDrawing(e.getX(), e.getY());
			pressed();
		}
		// checking if the user is trying to move a drawable
		Drawable dragged = null;
		ArrayList<Drawable> drawables = this.pcm.getDrawables();
		for (Drawable dr : drawables) {
			// if a drawable is selected, the user wants to move it
			if (dr.isSelected())
				dragged = dr;
		}
		// if an object is selected, and the user has pressed the mouse inside of said
		// object, it is moved
		if (dragged != null && dragged.insideHitbox(e.getX(), e.getY()))
			pressedGrab(dragged, e.getX(), e.getY());
		// otherwhise the user is trying to draw a line (pen)
		if (dragged == null) {
			pressedBeginLine(e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		// checking if the mouse wasnt released on the same spot as it was pressed on
		// (allows to differenciate from the mouse clicked)
		if (!this.pcm.isFlipped()
				|| (e.getX() == this.pcm.firstDrawingPointX && e.getY() == this.pcm.firstDrawingPointY))
			return;
		// if a rectangle or a circle is selected, simply release it, and "reset" the
		// toggle buttons
		if (pba.rectangle.isSelected() || pba.circle.isSelected())
			released(this.pcm.firstDrawingPointX, this.pcm.firstDrawingPointY, e.getX(), e.getY());

		// retrieving which drawable was grabbed
		Drawable grabbed = null;
		ArrayList<Drawable> drawables = this.pcm.getDrawables();
		int i = 0;
		for (Drawable dr : drawables) {
			if (dr.isGrabbed()) {
				grabbed = dr;
				break;
			}
			i++;
		}
		// causing the drawable to be "dropped" at the new location
		if (grabbed != null)
			releasedGrab(grabbed, i, e.getX(), e.getY());
	}

	public void mouseDragged(MouseEvent e) {

		if (!this.pcm.isFlipped()) //|| this.pcm.checkSelected() || this.pcm.checkGrabbed())
			return;

		int x = e.getX();
		int y = e.getY();
		// creating the displacement animation
		if (this.pcm.checkSelected() && this.pcm.checkGrabbed()) {
			this.pcm.dragGrabbedDrawable(x, y);
			repaint();
			return;
		}
		
		if(pcm.checkSelected()) return;
		// if no buttons are selected and the drag was inside the PhotoComponent window
		if (!pba.checkPCToggleButtons()) {
			if (this.pcv.isInWindow(x, y))
				mouseDragged(x, y);// the user is trying to draw (pen)
		}

		// creating the drawing "animation" of circles and rectangles
		if (pba.rectangle.isSelected())
			mouseDraggedRectangle(x, y);
		if (pba.circle.isSelected())
			mouseDraggedCircle(x, y);
	}

	/* Method telling the model that a doubleClick has been performed */
	public void doubleClick() {
		this.pcm.flip(!this.pcm.isFlipped());// telling the model in wich state the photocomponent is
	}

	/* Method telling the model that a click has been performed */
	public void click() {
		this.setFocusable(true);
		this.requestFocus();// getting focus to the component for the keylisteners

	}

	/* Method telling the model that a mouse press has been performed */
	public void pressed() {
		this.pcm.drawing();// telling the model in wich state the photocomponent is
	}

	/* Method beginning the drawing of lines */
	public void pressedBeginLine(int x, int y) {
		this.pcm.drawing();
		this.pcm.drawingBeginLine(x, y);
	}

	/* Method signaling that a user has "picked up" a drawable (not moved it yet) */
	public void pressedGrab(Drawable drawGrab, int oldX, int oldY) {
		this.pcm.drawing();
		this.pcm.grabDrawable(drawGrab, oldX, oldY);
	}

	/*
	 * Method that puts the grabbed object down at the new location it was dragged
	 * to
	 */
	public void releasedGrab(Drawable grabbed, int pos, int x, int y) {
		this.pcm.drawing();
		this.pcm.releaseDrawable(grabbed, pos, x, y);
		repaint();
	}

	/*
	 * Method telling the model that a mouse release has been performed while a
	 * toggle button was selected
	 */
	public void released(int x, int y, int width, int height) {
		this.pcm.drawing();// telling the model in wich state the photocomponent is
		this.pcm.releasedMouse(x, y, width, height);
		repaint();
	}

	/* Method telling the model that a mouse drag has been performed */
	public void mouseDragged(int x, int y) {
		this.pcm.drawing();// telling the model in wich state the photocomponent is
		this.pcm.drawLine(x, y);
	}

	/* This is a try to make the rectangle visible during the whole drawing */
	public void mouseDraggedRectangle(int x, int y) {
		this.pcm.drawing();// telling the model in wich state the photocomponent is
		this.pcm.drawingRectangle(x, y);
	}

	public void mouseDraggedCircle(int x, int y) {
		this.pcm.drawing();// telling the model in wich state the photocomponent is
		this.pcm.drawingCircle(x, y);
	}

	/* BOILER PLATE STUFF */
	public void addActionListener(ActionListener listener) {
		this.pcm.addActionListener(listener);
	}

	/* paints the photocomponent */
	public void paintComponent(Graphics g) {
		try {
			pcv.paint((Graphics2D) g, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Method returning the preferred size of the view (= of the photocomponent) */
	@Override
	public Dimension getPreferredSize() {
		return this.pcv.getPreferredSize();
	}
}
