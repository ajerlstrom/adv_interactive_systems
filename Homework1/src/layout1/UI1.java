package layout1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class UI1 extends JFrame {

	public UI1(String title) {
		super(title);
		setupUI();
	}

	/* Sets up the basic layout of the app */
	private void setupUI() {
		this.getContentPane().setLayout(new BorderLayout());
		setupToolBar();
		setupFingerPrint();
		setupMainScreen();
		this.setPreferredSize(new Dimension(1280, 720));
		this.pack();
	}

	/* Sets up the tool bar at the top of the app */
	private void setupToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		
		//the icon images are not exactly the same "in reverse", but didnt want to spend time looking for the pefect couple of frwd and bckwrd arrows
		ImageIcon icon1 = new ImageIcon("backward.png");
		Image newimg = icon1.getImage().getScaledInstance( 50, 20,  java.awt.Image.SCALE_SMOOTH ) ;//using a downscaling method found on stackoverflow, forgot to save link 
		icon1 = new ImageIcon(newimg);
		
		ImageIcon icon2 = new ImageIcon("forward.png");
		newimg = icon2.getImage().getScaledInstance( 50, 20,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon2 = new ImageIcon(newimg);
		
		ImageIcon icon3 = new ImageIcon("squares.png");
		newimg = icon3.getImage().getScaledInstance( 30, 20,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon3 = new ImageIcon(newimg);
		
		JButton backward = new JButton(icon1);
		JButton forward = new JButton(icon2);
		JButton squares = new JButton(icon3);
		//end of button creation
		
		JTextField textField = new JTextField("Rechercher");
		JLabel appName = new JLabel("Touch ID");//to recreate the "title of the app", it was only to copy the image
		
		//adding all components to the toolbar
		toolbar.addSeparator();
		toolbar.add(backward);
		toolbar.add(forward);
		toolbar.addSeparator(new Dimension(50, 10));
		toolbar.add(squares);
		toolbar.addSeparator(new Dimension(250, 10));
		toolbar.add(appName);
		toolbar.addSeparator(new Dimension(200, 10));
		toolbar.add(textField);

		//adding the toolbar to the top of the main pane
		this.getContentPane().add(toolbar, BorderLayout.NORTH);

	}

	/* Sets up the left part of the app, containing only one big button */
	private void setupFingerPrint() {
		
		JButton finger = new JButton();
		
		JPanel jp = new JPanel();
		//jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));//kind of "cheating", but it allowed to center the button
		
		//rescaling icon to not make it too big
		ImageIcon icon = new ImageIcon("fp1.png");
		Image newimg = icon.getImage().getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon(newimg);
		
		finger.setIcon(icon);
		
		jp.add(finger);
		
		//adding the panel containing the big fingerprint button the the left side of the main panel
		this.getContentPane().add(jp, BorderLayout.WEST);
		
	}
	
	// taken from https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
	private void setupMainScreen() {
	GridBagConstraints c = new GridBagConstraints();
	
	JPanel p1 = new JPanel();
	p1.setBackground(Color.LIGHT_GRAY);
	p1.setLayout(new GridBagLayout());
	
	//description of the app, using html to make break lines
	JLabel appDescription = new JLabel("<html>Touch ID vous permet d'utiliser votre empreinte digitale pour "
			+ "déverouiller votre Mac et <br> effectuer des achats avec Apple Pay, "
			+ "l'iTunes Store, l'App Store et l'Apple Books.<html>");
	c.weightx = 0;//still not entirely sure what the weight is used for (i looked it up in the doc, but when changing the weight, for one or more grid cases, nothing changed)
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	p1.add(appDescription, c);//adding to the case[0,0]
	
	//icon for the already existing fingerprint
	ImageIcon icon = new ImageIcon("fp2.png");
	Image newimg = icon.getImage().getScaledInstance( 70, 70,  java.awt.Image.SCALE_SMOOTH ) ;  
	icon = new ImageIcon(newimg);
	
	//creating label containing the icon
	JLabel l1 = new JLabel("Doigt 1", icon, JLabel.CENTER);
	l1.setVerticalTextPosition(JLabel.BOTTOM);
	l1.setHorizontalTextPosition(JLabel.CENTER);
	c.insets = new Insets(30,0,0,30);
	c.gridx = 0;
	c.gridy = 1;
	p1.add(l1, c);
	
	//creating button to add more fingerprints
	icon = new ImageIcon("plus.png");
	newimg = icon.getImage().getScaledInstance( 70, 70,  java.awt.Image.SCALE_SMOOTH ) ;  
	icon = new ImageIcon(newimg);
	JButton b2 = new JButton(icon);
	c.gridx = 1;
	c.gridy = 1;
	p1.add(b2, c);
	
	setupCheckBoxes(p1, c);
	
	//adding the panel to the main panel of the app
	this.getContentPane().add(p1, BorderLayout.CENTER);
	}
	
	/* Sets up the list of checkboxes */
	private void setupCheckBoxes(JPanel p1, GridBagConstraints c ) {
		JLabel checkBoxTitle = new JLabel("Utiliser Touch ID pour: ");
		c.gridx = 0;
		c.gridy = 2;
		p1.add(checkBoxTitle, c);
		
		//creating checkboxes
		JCheckBox c1 = new JCheckBox("Deverouiller votre Mac");
		c1.setBackground(Color.LIGHT_GRAY);//needed to set the backgroundColor to make it fit with the background color of the panel
		JCheckBox c2 = new JCheckBox("Apple Play");
		c2.setBackground(Color.LIGHT_GRAY);
		JCheckBox c3 = new JCheckBox("Itunes Store");
		c3.setBackground(Color.LIGHT_GRAY);
		JCheckBox c4 = new JCheckBox("Remplissage mots de passe");
		c4.setBackground(Color.LIGHT_GRAY);
		
		//adding checkboxes to the panel
		c.insets = new Insets(0,15,0,0);//to make a little left side margin
		c.gridx = 0;
		c.gridy = 3;
		p1.add(c1, c);
		c.gridy = 4;
		p1.add(c2, c);
		c.gridy = 5;
		p1.add(c3, c);
		c.gridy = 6;
		p1.add(c4, c);
	}
}
