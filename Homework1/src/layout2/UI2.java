package layout2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class UI2 extends JFrame {

	public UI2(String title) {
		super(title);
		setupUI();
	}
	
	/* Sets up the UI */
	private void setupUI(){
		this.getContentPane().setLayout(new BorderLayout());
		setupToolBar();
		setupThumbnail();
		setupMainTable();
		
		this.setPreferredSize(new Dimension(1280, 720));
		this.pack();
	}
	
	/* Sets up the toolbar on the top */
	private void setupToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		
		//creating buttons with icons
		JButton act = new JButton("action");
		
		ImageIcon icon = new ImageIcon("new.png");
		Image newimg = icon.getImage().getScaledInstance( 30, 20,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon(newimg);
		JButton nvx = new JButton(icon);
		
		icon = new ImageIcon("edit.png");
		newimg = icon.getImage().getScaledInstance( 30, 20,  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon(newimg);
		JButton edit = new JButton(icon);
		
		icon = new ImageIcon("supp.png");
		newimg = icon.getImage().getScaledInstance( 30, 20,  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon(newimg);
		JButton supp = new JButton(icon);
		
		JButton aper = new JButton("aper");
		
		icon = new ImageIcon("cita.png");
		newimg = icon.getImage().getScaledInstance( 30, 20,  java.awt.Image.SCALE_SMOOTH ) ;
		icon = new ImageIcon(newimg);
		JButton citation = new JButton(icon);
		//end of button creation
		
		JTextField textField = new JTextField("Rechercher");
		
		//adding all components to the toolbar
		toolbar.add(act);
		toolbar.addSeparator();
		toolbar.add(nvx);
		toolbar.add(edit);
		toolbar.add(supp);
		toolbar.add(aper);
		toolbar.addSeparator(new Dimension(300, 0));//creates more "breathing room"
		toolbar.add(textField);
		toolbar.add(citation);
		
		//adding the toolbar to the top of the main panel
		this.getContentPane().add(toolbar, BorderLayout.NORTH);
		
	}
	
	/* sets up the thumbnails on the right side of the app */
	private void setupThumbnail() {
		JPanel thumbP = new JPanel();
		thumbP.setLayout(new BoxLayout(thumbP, BoxLayout.PAGE_AXIS));//from top to bottom
		thumbP.setBackground(Color.LIGHT_GRAY);
		
		//using same pdf icon for both "pdf files"
		ImageIcon icon1 = new ImageIcon("pdf.png");
		Image newimg = icon1.getImage().getScaledInstance( 50, 70,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon1 = new ImageIcon(newimg);
		
		//creating "thumbnails" -> theyre just buttons with a label + icon
		JButton pdf1 = new JButton("PDF1", icon1);
		pdf1.setVerticalTextPosition(SwingConstants.BOTTOM);
		pdf1.setHorizontalTextPosition(SwingConstants.CENTER);
		
		JButton pdf2 = new JButton("PDF2", icon1);
		pdf2.setVerticalTextPosition(SwingConstants.BOTTOM);
		pdf2.setHorizontalTextPosition(SwingConstants.CENTER);
		
		//adding buttons to panel
		thumbP.add(pdf1);
		thumbP.add(Box.createRigidArea(new Dimension(0,25)));
		thumbP.add(pdf2);
		
		//adding our panel to the left side of the main panel
		this.getContentPane().add(thumbP, BorderLayout.EAST);
	}
	
	
	/* Sets up the main table, containing all the info, at the center */
	private void setupMainTable() {
		//will contain the table above, and the text area below it
		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
		
		//creating the table
		String[] columns = {"Clé Cite", "Title", "Date", "Premier auteur", "Ajoute"};
		Object[][] data = {{"1", "2","3", "4", "5"}, {"1", "2","3", "4", "5"}, {"1", "2","3", "4", "5"}};//just to see what it looks like
		JTable table = new JTable(data, columns);
		
		
		JTextArea textArea = new JTextArea("THIS IS THE TEXT AREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");//to see the use of the scrollbars
		
		//in case the window is too small to see all the text, the user can scroll down to see everything
		JScrollPane infoTable = new JScrollPane(table);//creating a scrollPane of the table
		JScrollPane textScroll = new JScrollPane(textArea);//creating a scrollPane of the textfield
		textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//visible at all time
		textScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//will only appear if needed
		
		//adding them to the panel, infoTable situated above textScroll
		contents.add(infoTable);
		contents.add(textScroll);
		
		//adding the "main table" to the center of the main panel
		this.getContentPane().add(contents, BorderLayout.CENTER);
	}
}
