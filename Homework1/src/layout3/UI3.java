package layout3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;


public class UI3 extends JFrame{
	
	public UI3(String title) {
		super(title);
		setupUI();
	}
	
	/* Sets up the UI */
	private void setupUI(){
		this.getContentPane().setLayout(new BorderLayout());
		setupToolBar();
		setupBottomPlayer();
		setupMainPanel();
		
		this.setPreferredSize(new Dimension(1280, 720));
		this.pack();
		
	}
	
	/* Sets up toolbar on left side*/
	private void setupToolBar() {
		JPanel contents = new JPanel();//the contents of our left hand side toolbar
		contents.setLayout(new BorderLayout());
		
		JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
	
		//Toolbar buttons
		JButton home = new JButton("Home");
		JButton explore = new JButton("Explore");
		JButton radio = new JButton("Radio");
	
		//adding buttons to toolbar
		toolbar.add(home); 
		toolbar.add(explore); 
		toolbar.add(radio);
		
		contents.add(toolbar, BorderLayout.NORTH);
		
		//Panel for the navigation of the different playlists created
		JPanel libContents = new JPanel();
		libContents.setLayout(new BoxLayout(libContents, BoxLayout.Y_AXIS));
		JLabel lib = new JLabel("Your Library");
		JLabel play = new JLabel("Playlists");
		
		//Using basic buttons here since its quicker
		JButton b1 = new JButton("For you");
		JButton b2 = new JButton("Recently Played");
		JButton b3 = new JButton("Liked Songs");
		JButton b4 = new JButton("Album");
		JButton b5 = new JButton("80's");
		JButton b6 = new JButton("WORKOUT");
		JButton b7 = new JButton("Chill");
		JButton b8 = new JButton("Rock");
		
		//adding every button to the panel containing all the playlists
		libContents.add(Box.createRigidArea(new Dimension(0, 25)));
		libContents.add(lib);
		libContents.add(b1);
		libContents.add(b2);
		libContents.add(b3);
		libContents.add(b4);
		libContents.add(Box.createRigidArea(new Dimension(0, 25)));
		libContents.add(play);
		libContents.add(b5);
		libContents.add(b6);
		libContents.add(b7);
		libContents.add(b8);
		
		//creating a Scrollpane to be able to scroll through playlists
		JScrollPane music = new JScrollPane(libContents);
		music.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//always active scrollbar to show that it is there, otherwhise would only activate it when needed
		music.setBorder(BorderFactory.createEmptyBorder());//removes the border, looked really weird if kept
		
		//we add the scrollbar to the panel containing our entire "toolbar"
		contents.add(music, BorderLayout.CENTER);
		
		//to handle the create new playlist button, could be used to have a bigger label containing current music playing 
		JPanel create = new JPanel();
		create.setLayout(new BoxLayout(create, BoxLayout.Y_AXIS));
		
		JButton b9 = new JButton("New Playlist");
		
		create.add(b9);
		
		contents.add(create, BorderLayout.SOUTH);
		
		//addding the completed toolbar to our main panel
		this.getContentPane().add(contents, BorderLayout.WEST);
		
	}
	
	/* Sets up the bottom part of the app */
	private void setupBottomPlayer() {
		//using 4 panels to try to create a symetrical layout (which is kind of ruined by the addition of the slider volume since i cannot get it to resize)
		JPanel player = new JPanel();
		player.setLayout(new BoxLayout(player, BoxLayout.X_AXIS));
		
		JPanel player2 = new JPanel();
		player2.setLayout(new BoxLayout(player2, BoxLayout.X_AXIS));
		
		JPanel player3 = new JPanel();
		player3.setLayout(new BoxLayout(player3, BoxLayout.Y_AXIS));
		
		JPanel player4 = new JPanel();
		player4.setLayout(new BoxLayout(player4, BoxLayout.X_AXIS));
		
		//CREATING LABEL WITH ICON
		ImageIcon icon = new ImageIcon("queen.png");
		Image newimg = icon.getImage().getScaledInstance( 75, 75,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon(newimg);
		
		JLabel currSong = new JLabel("Somebody to love", icon, JLabel.RIGHT);
		JLabel vol = new JLabel("Vol: ");
		
		//creating buttons
		JButton b1 = new JButton("like");
		JButton b2 = new JButton("Back");
		JButton b3 = new JButton("Play");
		JButton b4 = new JButton("Frwd");
		JButton b5 = new JButton("Queue");
		JButton b6 = new JButton("Stream");
		
		JSlider volume = new JSlider();
		volume.setMaximumSize(new Dimension(1, 25));//having trouble reducing the size of the slider
		
		JSlider songSlider = new JSlider();
		
		//adding items to pannel 4 (this is to create the part where you see "play".. buttons above the slider to choose the song
		player4.add(b2);
		player4.add(b3);
		player4.add(b4);
		
		player3.add(player4);
		player3.add(songSlider);//adding the slider below all those buttons
		
		player2.add(player3);
		player2.add(Box.createHorizontalGlue());//used to create a symetry (which is ruined by the slider, if it only contained buttons it would be symetrical
		player2.add(b5);
		player2.add(b6);
		player2.add(vol);
		player2.add(volume);
		//adding items to panel 1
		player.add(currSong);
		player.add(Box.createRigidArea(new Dimension(25, 0)));
		player.add(b1);
		player.add(Box.createHorizontalGlue());//used to create a symetry (which is ruined by the slider, if it only contained buttons it would be symetrical
		player.add(player2);//adding panel 2 to panel 1,
		
		this.getContentPane().add(player, BorderLayout.SOUTH);
		
	}
	
	/* Sets up the main central panel of the app */
	private void setupMainPanel() {
		JPanel center = new JPanel(new BorderLayout());
		setupPlaylist(center);
		setupMenuBar(center);
		
		this.getContentPane().add(center, BorderLayout.CENTER);
	}
	
	/* Sets up the "menu bar" at the top of the app */
	private void setupMenuBar(JPanel center) {
		JToolBar tb = new JToolBar();//we use a toolbar instead of a real menubar
		tb.setRollover(true);
		tb.setFloatable(false);
		
		//creating buttons
		JButton bckwd = new JButton("back");
		JButton fwd = new JButton("frwrd");
		JButton user = new JButton("User");
		
		JTextField textField = new JTextField("Search");
		
		//adding everything to the toolbar
		tb.add(bckwd);
		tb.add(fwd);
		tb.add(textField);
		tb.addSeparator();
		tb.add(user);
		
		center.add(tb, BorderLayout.NORTH);
	}
	
	/* Sets up the panel containing the playlist interface */
	private void setupPlaylist(JPanel center) {
		
		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
		
		//header of the playlist
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
		
		//creating icon for label
		ImageIcon icon = new ImageIcon("queen.png");
		Image newimg = icon.getImage().getScaledInstance( 150, 150,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon(newimg);
		
		JLabel playlistPic = new JLabel("Rock Playlist", icon, JLabel.RIGHT);
		
		JButton play = new JButton("Play");
		JToggleButton dl = new JToggleButton("Download");
		
		//adding all items to the header
		header.add(playlistPic);
		header.add(Box.createRigidArea(new Dimension(10, 0)));
		header.add(play);
		header.add(Box.createHorizontalGlue());
		header.add(dl);
		
		//creating the table containing the info about all the songs in the playlist
		String[] columns = {"Title", "Artist", "Album", "Date", "Duration"};
		Object[][] data = {{"1", "2","3", "4", "5"}, {"1", "2","3", "4", "5"}, {"1", "2","3", "4", "5"}};//just to see what it looks like
		JTable table = new JTable(data, columns);
		
		//adding it to a scrollpane
		JScrollPane infoTable = new JScrollPane(table);
		infoTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//just to show that there is one there at this moment 
		//(would probably put JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED otherwhise)
		
		//adding the header and the infotable to the contents panel
		contents.add(header);
		contents.add(infoTable);
		
		center.add(contents, BorderLayout.CENTER);
	}
	
}
