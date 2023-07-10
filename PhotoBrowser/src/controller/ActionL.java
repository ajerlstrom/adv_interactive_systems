package controller;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import view.PhotoBrowserApp;

public class ActionL implements ActionListener{
	PhotoBrowserApp pba;
	PhotoComponent pc;
/* METHODS FOR MENU BAR ITEMS */
	//Actions performed by "File" menu items
	
	public ActionL(PhotoBrowserApp pba) {
		this.pba = pba;
	}
	
	public void ImportA() {
	JFileChooser fileChooser = new JFileChooser();
		
		//taken from https://www.codejava.net/java-se/swing/add-file-filter-for-jfilechooser-dialog
		fileChooser.addChoosableFileFilter(new FileFilter() {
			 public String getDescription() {//adds a description to the filechooser (you can pick this option now)
			        return ".png files (*.png)";
			    }
			 
			    public boolean accept(File f) {//tests if the file is the correct format
			        if (f.isDirectory()) {
			            return true;
			        } else {
			            return f.getName().toLowerCase().endsWith(".png");
			        }
			    }
		});
		
		//adapted for jpg
		fileChooser.addChoosableFileFilter(new FileFilter() {
			 public String getDescription() {//adds a description to the filechooser (you can pick this option now)
			        return ".jpg files (*.jpg)";
			    }
			 
			    public boolean accept(File f) {//tests if the file is the correct format
			        if (f.isDirectory()) {
			            return true;
			        } else {
			            return f.getName().toLowerCase().endsWith(".jpg");
			        }
			    }
		});
		
		//adapted for jpeg
				fileChooser.addChoosableFileFilter(new FileFilter() {
					 public String getDescription() {//adds a description to the filechooser (you can pick this option now)
					        return ".jpeg files (*.jpeg)";
					    }
					 
					    public boolean accept(File f) {//tests if the file is the correct format
					        if (f.isDirectory()) {
					            return true;
					        } else {
					            return f.getName().toLowerCase().endsWith(".jpeg");
					        }
					    }
				});
		
		int r = fileChooser.showOpenDialog(null);
		//taken from the JFileChooser Doc: https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
		if(r == JFileChooser.APPROVE_OPTION) {
			System.out.println("Opening file: " + fileChooser.getSelectedFile().getName());
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			PhotoComponent pc = new PhotoComponent(path, pba);
			this.pc = pc;
			//putting photocomponent into a JScrollPane
			JScrollPane photoScroll = new JScrollPane(pc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			photoScroll.setSize(pba.getSize());
			pba.setupPhotoComponentToolBar();//sets up the toolbar for the photocomponent
			pba.getContentPane().add(photoScroll, BorderLayout.CENTER);
			
		}
		
		PhotoBrowserApp.statusBarMsg.setText("Import pressed");
	}
	
	/* Called when a photoComponent is deleted, all traces of it are erased*/
	public void DeleteA() {
		//  I STILL NEED AND  WANT TO FIND A WAY TO DELETE THE JTOOLBAR THAT COMES WITH THE PHOTOCOMPONENT BUT I HAVE NOT FOUND A WAY
		// TO  DELETE SOMETHING IN BORDERLAYOUT.EAST
		Component[] components = pba.getContentPane().getComponents();
		System.out.println(components.length);
		for(Component c : components) {
			
			if(c instanceof JScrollPane) {//this works as long as there is only 1 JScrollPane
				//if any more are added i will need to verify that that JScrollPane contains a PhotoComponent before deleting it
				System.out.println("DELETED");
				pba.getContentPane().remove(c);//removing the photoComponent
			}
			
		}
	
		//to redraw and tell the panel to update
		pba.getContentPane().revalidate();
		pba.getContentPane().repaint();
			
		PhotoBrowserApp.statusBarMsg.setText("Delete pressed");
	}
	
	public void QuitA() {
		System.exit(0);
	}
	
	//Actions performed by "View" menu items
	public void PhotoViewerA() {
		PhotoBrowserApp.statusBarMsg.setText("Photo Viewer Selected");
	}
	
	public void BrowserA() {
		PhotoBrowserApp.statusBarMsg.setText("Browser Selected");
	}
	
	/* METHODS FOR TOOL BAR ITEMS */
	
	//Actions performed by toggleButtons in tool bar
	public void PeopleA(boolean b) {
		PhotoBrowserApp.statusBarMsg.setText("People category toggled to " + b);
	}
	
	public void PlacesA(boolean b) {
		PhotoBrowserApp.statusBarMsg.setText("Places category toggled to " + b);
	}
	
	public void SchoolA(boolean b) {
		PhotoBrowserApp.statusBarMsg.setText("School category toggled to " + b);
	}
	
	public void HolidayA(boolean b) {
		PhotoBrowserApp.statusBarMsg.setText("Holiday category toggled to " + b);
	}
	
	public void prevA() {
		PhotoBrowserApp.statusBarMsg.setText("prev button pressed");
	}
	
	public void nextA() {
		PhotoBrowserApp.statusBarMsg.setText("next button pressed");
	}
	
	/* METHODS FOR PHOTOCOMPONENT TOOLBAR */
	public void createCircleA(boolean b) {
		if(pba.circle.isSelected()) {
			pba.rectangle.setEnabled(false);
		} else {
			pba.rectangle.setEnabled(true);
		}
		PhotoBrowserApp.statusBarMsg.setText("Circle toggled to " + b);
	}
	
	public void createRectangleA(boolean b) {
		if(pba.rectangle.isSelected()) {
			pba.circle.setEnabled(false);
		} else {
			pba.circle.setEnabled(true);
		}
		PhotoBrowserApp.statusBarMsg.setText("Rectangle toggled to " + b);
	}
	
	public void pickColorA() {
		Color cc =  new JColorChooser().showDialog(null, "pick the color for the pencil ", pba.colorPick.getBackground());
		if(cc == null) pba.colorPick.setBackground(Color.black);
		else pba.colorPick.setBackground(cc);
		//pc.getModel().setPenColor(pba.colorPick.getBackground());
		pc.repaint();
		PhotoBrowserApp.statusBarMsg.setText("Color Picker pressed");
	}
	
	public void pickColorTextA() {
		Color cc =  new JColorChooser().showDialog(null, "pick the color for the text ", pba.colorPickText.getBackground());
		if(cc == null) pba.colorPickText.setBackground(Color.black);
		else pba.colorPickText.setBackground(cc);
		//pc.getModel().setTextColor(pba.colorPickText.getBackground());
		pc.repaint();
		PhotoBrowserApp.statusBarMsg.setText("Color Picker pressed");
	}
	public void pickColorGeoA() {
		Color cc =  new JColorChooser().showDialog(null, "pick the color for the geometry ", pba.colorPickGeo.getBackground());
		if(cc == null) pba.colorPickGeo.setBackground(Color.black);
		else pba.colorPickGeo.setBackground(cc);
		//pc.getModel().setGeoColor(pba.colorPickGeo.getBackground());
		pc.repaint();
		PhotoBrowserApp.statusBarMsg.setText("Color Picker pressed");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pba.peopleB) PeopleA(pba.peopleB.isSelected());
		if(e.getSource() == pba.placesB) PlacesA(pba.placesB.isSelected());
		if(e.getSource() == pba.schoolB) SchoolA(pba.schoolB.isSelected());
		if(e.getSource() == pba.holidayB) HolidayA(pba.holidayB.isSelected());
		if(e.getSource() == pba.prev) prevA();
		if(e.getSource() == pba.next) nextA();
		
		if(e.getSource() == pba.fImport) ImportA();
		if(e.getSource() == pba.fDelete) DeleteA();
		if(e.getSource() == pba.fQuit) QuitA();
		
		
		if(e.getSource() == pba.vPhotoViewer) PhotoViewerA();
		if(e.getSource() == pba.vBrowser) BrowserA();
		
		if(e.getSource() == pba.circle) createCircleA(pba.circle.isSelected());
		if(e.getSource() == pba.rectangle) createRectangleA(pba.rectangle.isSelected());
		if(e.getSource() == pba.colorPick) pickColorA();
		if(e.getSource() == pba.colorPickText) pickColorTextA();
		if(e.getSource() == pba.colorPickGeo) pickColorGeoA();
	
	}
}
