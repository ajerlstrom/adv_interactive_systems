package controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.PhotoBrowserApp;

public class ChangeL implements ChangeListener{
	PhotoBrowserApp pba;
	
	public ChangeL(PhotoBrowserApp pba) {
		this.pba = pba;
	}
	
	public void pencilSizeC() {
		PhotoBrowserApp.statusBarMsg.setText("Pencil Size selected: " + pba.pencilSize.getValue());
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == pba.pencilSize) pencilSizeC();
		
	}

}
