package dblp.gui;
import java.awt.Dimension;

import javax.swing.Box;

@SuppressWarnings("serial")
public class NoQueryPane extends QueryPane {
	
	NoQueryPane()	{
		add(Box.createRigidArea(new Dimension(WIDTH,HEIGHT)));
	}
	
	@Override
	public boolean validateInput()	{
		return true;
	}
	
	@Override
	public void makeQuery()	{
		
	}	
}
