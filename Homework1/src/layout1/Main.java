package layout1;

import layout2.UI2;
import layout3.UI3;

public class Main {
	public static void main(String args[]) {
		//UNCOMMENT THE LINE OF THE UI YOU WISH TO SEE
		
		//TO LAUNCH THE FIRST UI
		UI1 userInt1 = new UI1("Touch ID");
		//userInt1.setVisible(true);
		
		
		//TO LAUNCH THE SECOND UI
		UI2 userInt2 = new UI2("Biblio");
		//userInt2.setVisible(true);
		
		
		//TO LAUNCH THE THIRD UI
		UI3 userInt3 = new UI3("Spotify");
		userInt3.setVisible(true);
	}
	
	
}
