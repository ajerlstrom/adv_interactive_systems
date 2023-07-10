package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;

import controller.ActionL;
import controller.ChangeL;

public class PhotoBrowserApp extends JFrame {
	
	public static JLabel statusBarMsg = new JLabel("READY");
	ActionL al;
	ChangeL cl;
	
	//toolbar components
	public JToggleButton peopleB;
	public JToggleButton placesB;
	public JToggleButton schoolB;
	public JToggleButton holidayB;
	public JButton prev;
	public JButton next;
	
	//menu items
	public JMenuItem fImport, fDelete, fQuit;
	public JRadioButtonMenuItem vPhotoViewer, vBrowser;
	
	//photocomponent toolbar
	public JToggleButton circle;
	public JToggleButton rectangle;
	public JSpinner pencilSize;
	public JButton colorPick;
	public JButton colorPickText;
	public JButton colorPickGeo;
	
	public PhotoBrowserApp(String title) {
		super(title);
		this.al = new ActionL(this);
		this.cl = new ChangeL(this);
		setupUI();
		
	}

	
	/* Sets up the basic layout of the app */
	private void setupUI() {
		this.getContentPane().setLayout(new BorderLayout());

	
		
		setupStatusBar();
		setupMenuBar();
		setupToolBar();
		
		this.setPreferredSize(new Dimension(1080, 720));
		this.pack();
	}
	
	
	/* Sets up the status bar */
	private void setupStatusBar() {
		JPanel statusBarContainer = new JPanel();
		JLabel statusLabel = new JLabel("Status Bar: ");
		statusBarContainer.add(statusLabel);
		
		statusBarContainer.add(statusBarMsg);
		
		this.getContentPane().add(statusBarContainer, BorderLayout.SOUTH);
	}

	
	/* Sets up the menu bar */
	private void setupMenuBar() {
		JMenu fileMB, viewMB;//The contents of the menu bar
		JMenuBar menuBar = new JMenuBar();

		//Setting up our menus
		fileMB = setupMenuFile();
		viewMB = setupMenuView();
		
		//Adding the menus to the menu bar
		menuBar.add(fileMB);
		menuBar.add(viewMB);
		
		//Setting the JFrames menu bar to be the one created
		this.setJMenuBar(menuBar);
	}
	
	/* Sets up the tool bar */
	private void setupToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true);//creates "hovering" animation
		toolbar.setFloatable(false);
		
		setupToolBarButtons();
		//adding buttons to the toolbar, with a seperator between each button
		toolbar.add(prev);
		toolbar.add(next);
		toolbar.addSeparator(new Dimension(25, 0));
		toolbar.add(peopleB);
	    toolbar.addSeparator(); 
		toolbar.add(placesB);
	    toolbar.addSeparator(); 
		toolbar.add(schoolB);
	    toolbar.addSeparator(); 
		toolbar.add(holidayB);
		
		/* ATTEMPT AT MAKING A COMBOBOX OF MULTIPLE TOGGLEBUTTONS (didnt work)
		JComboBox comb = new JComboBox();
		comb.add(peopleB);
		comb.add(placesB);
		comb.add(schoolB);
		toolbar.add(comb);
		*/
		
		this.getContentPane().add(toolbar, BorderLayout.NORTH);
		
	}
	

	private void setupToolBarButtons() {
		//creating our JToggleButtons
		peopleB = new JToggleButton("People");
		placesB = new JToggleButton("Places");
		schoolB = new JToggleButton("School");
		holidayB = new JToggleButton("Holiday");
		//and so on, will most likely change/add more buttons with more specific categories, this is just to show that i know how to create buttons
		
		prev = new JButton("PREV");
		next = new JButton("NEXT");
		
		//adding action listeners to the buttons
		peopleB.addActionListener(al);
		placesB.addActionListener(al);
		schoolB.addActionListener(al);
		holidayB.addActionListener(al);
		
		prev.addActionListener(al);
		next.addActionListener(al);
	}
	
	/* Sets up the File menu of our menu bar */
	private JMenu setupMenuFile() {
		JMenu file = new JMenu("File");
		
	
		
		//Creating the menu items
		fImport = new JMenuItem("Import");
		fDelete = new JMenuItem("Delete");
		fQuit = new JMenuItem("Quit");

		//Adding Action Listeners
		fImport.addActionListener(al);
		fDelete.addActionListener(al);
		fQuit.addActionListener(al);
		
		//Adding items to the File menu
		file.add(fImport);
		file.add(fDelete);
		file.add(fQuit);

		return file;

	}
	
	
	/* Sets up the View menu of the menu bar */
	private JMenu setupMenuView() {
		// Menu items for view
		
		
		JMenu view = new JMenu("View");
		ButtonGroup RadioButtons = new ButtonGroup();//prevents multiple radio button selections
		
		//Creating Radio Buttons & adding listeners
		vPhotoViewer = new JRadioButtonMenuItem("Photo Viewer");
		vPhotoViewer.setSelected(true);//Photo Viewer becomes the default selection
		vPhotoViewer.addActionListener(al);
		RadioButtons.add(vPhotoViewer);
		
		vBrowser = new JRadioButtonMenuItem("Browser");
		vBrowser.addActionListener(al);
		RadioButtons.add(vBrowser);
		
		//Adding buttons to the View menu
		view.add(vPhotoViewer);
		view.add(vBrowser);
		return view;
	}
	
	/* Sets up a toolbar to help with manipulating PhotoComponents */
	
	public void setupPhotoComponentToolBar() {
		JToolBar tb = new JToolBar();
		tb.setRollover(true);
		tb.setFloatable(false);
		tb.setOrientation(JToolBar.VERTICAL);
		
		setupPCToolBarButtons();
		
		JLabel descrPen = new JLabel("Pencil Size");
		JLabel descrColor = new JLabel("Pencil Color");
		JLabel descrColor2 = new JLabel("Text Color");
		JLabel descrColor3 = new JLabel("Geometry Color");
		//To modify button size
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		
		
		tb.addSeparator();
		tb.add(circle);
		tb.addSeparator();
		tb.add(rectangle);
		tb.addSeparator();
		tb.add(descrPen);
		tb.add(pencilSize);
		tb.addSeparator();
		tb.add(descrColor);
		tb.add(colorPick);
		tb.addSeparator();
		tb.add(descrColor2);
		tb.add(colorPickText);
		tb.addSeparator();
		tb.add(descrColor3);
		tb.add(colorPickGeo);
		
		
		this.getContentPane().add(tb, BorderLayout.EAST);
	}
	
	public void setupPCToolBarButtons() {
		int defaultThickness = 7;
		Dimension sd = new Dimension(150, 30);
		SpinnerModel model = new SpinnerNumberModel(defaultThickness,defaultThickness - 6,defaultThickness + 8, 1);
		
		pencilSize = new JSpinner(model);
		pencilSize.setMaximumSize(sd);
		pencilSize.addChangeListener(cl);
		Color bg = Color.black;
		Color fg = Color.white;
		Dimension colorButtonDim = new Dimension(50, 50);
		colorPick = new JButton("Pick Pen Color");
		colorPick.setForeground(fg);
		colorPick.setBackground(bg);
		colorPick.setPreferredSize(colorButtonDim);
		colorPick.addActionListener(al);
		
		colorPickText = new JButton("Pick Text Color");
		colorPickText.setForeground(fg);
		colorPickText.setBackground(bg);
		colorPickText.setMinimumSize(colorButtonDim);
		colorPickText.addActionListener(al);
		
		colorPickGeo = new JButton("Pick Geo Color");
		colorPickGeo.setForeground(fg);
		colorPickGeo.setBackground(bg);
		colorPickGeo.setMinimumSize(colorButtonDim);
		colorPickGeo.addActionListener(al);
		
		circle = new JToggleButton("circle");
		circle.addActionListener(al);
		rectangle = new JToggleButton("rectangle");
		rectangle.addActionListener(al);
	}

	/*destroys the toolbar when the photocomponent is deleted */
	public void destroyPhotoComponentToolBar() {
		//cannot find a way to access the components of the BorderLayout.EAST yet
	}
	
	/*Method returning true if any togglebutton is active on the photocomponent toolbar */
	public boolean checkPCToggleButtons() {
		return (circle.isSelected() || rectangle.isSelected());
	}

}

