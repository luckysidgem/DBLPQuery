package dblp;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dblp.gui.GUIManager;
import dblp.gui.QuerySelectPane;

public class ApplicationFrame {
	JFrame window;
	
	public ApplicationFrame()	{
		window = new JFrame("DBLP Query");
		
		JLabel header = new JLabel("DBLP Query Engine");
		header.setFont(new Font("Berlin Sans FB", Font.TRUETYPE_FONT, 48));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel main = new JPanel(new BorderLayout());
		main.add(header, BorderLayout.PAGE_START);
		QuerySelectPane qp = new QuerySelectPane();
		GUIManager.setPane(qp);
		main.add(qp, BorderLayout.CENTER);
		//main.add(new Query1ResultsPane(), BorderLayout.CENTER);
		window.setContentPane(main);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startApp()	{
		window.pack();
		window.setVisible(true);
	}
}
