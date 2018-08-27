package dblp.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
abstract public class QueryPane extends JPanel {
	static protected final int WIDTH = 350;
	static protected final int HEIGHT = 200;
	
	protected JButton submit = new JButton("Submit");
	protected JButton reset = new JButton("Reset");
	protected JLabel errorBar = new JLabel(" ");
	protected JPanel bp;
	
	QueryPane()	{
		
		submit.addActionListener(e->{
			if(validateInput())
				makeQuery();
		});
		
		errorBar.setForeground(Color.RED);
		submit.setBackground(Color.BLACK);
		bp = new JPanel(new BorderLayout());
		JPanel p = new JPanel(new GridLayout(0, 2, 10, 10));
		p.add(submit);
		p.add(reset);
		bp.add(p, BorderLayout.PAGE_START);
		bp.add(errorBar, BorderLayout.PAGE_END);
	}
	
	abstract public boolean validateInput();
	abstract public void makeQuery();
	
}
