package dblp.gui;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

//Facade Pattern
public class GUIManager {
	public static final int BORDER_SIZE = 2;
	private static QuerySelectPane pane;
	static private ArrayList<JLabel> countBars = new ArrayList<>();
	static private ArrayList<JButton> moreButtons = new ArrayList<>();
	
	public static void setCount(int n) {
		for(JLabel l: countBars)
			l.setText(String.format("Returned %d results", n));
	}
	
	public static void addCountBar(JLabel countBar) {
		countBars.add(countBar);
	}
	
	public static void addMoreButton(JButton button)	{
		moreButtons.add(button);
	}
	public static void setPane(QuerySelectPane p) {
		pane = p;
	}
	
	public static void enableGUI(boolean enable)	{
		for(JButton b: moreButtons)
			b.setEnabled(enable);
		pane.setEnabledGUI(enable);
	}
	
	

}
