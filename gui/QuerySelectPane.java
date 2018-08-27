package dblp.gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuerySelectPane extends JPanel	
	implements ActionListener{
	
	private JComboBox<String> menu;
	private JPanel leftCards;
	private JPanel rightCards;
	private List<JButton> buttons = new ArrayList<>();
	
	public QuerySelectPane()	{
		super(new BorderLayout());
		createLeftPane();
		createRightPane();
		///add(new Query1ResultsPane(), BorderLayout.CENTER);
	}
	
	public void createLeftPane()	{
		JPanel left = new JPanel(new BorderLayout());
		String[] options = {"Queries", "Query 1", "Query 2"};
		menu = new JComboBox<>(options);
		menu.addActionListener(this);
		menu.setPreferredSize(new Dimension(100,30));
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		p1.add(Box.createRigidArea(new Dimension(30, 0)));
		p1.add(menu);
		left.add(p1, BorderLayout.PAGE_START);
		
		leftCards = new JPanel(new CardLayout());
		for(String o:options)	{
			QueryPane qp = QueryPaneFactory.makeQueryPane(o);
			leftCards.add(qp, o);
			buttons.add(qp.submit);
			buttons.add(qp.reset);
		}
		
		left.add(leftCards, BorderLayout.CENTER);
		
		left.setBorder(BorderFactory.createLineBorder(Color.black, GUIManager.BORDER_SIZE));
		
		add(left, BorderLayout.LINE_START);
	}
	
	public void createRightPane()	{
		rightCards = new JPanel(new CardLayout());
		rightCards.add(new Query1ResultsPane(), "Query 1");
		rightCards.add(new Query2ResultsPane(), "Query 2");
		rightCards.add(createLoadingPane(), "Loading");
		rightCards.setBorder(BorderFactory.createLineBorder(Color.black, GUIManager.BORDER_SIZE));
		add(rightCards, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String option = (String) menu.getSelectedItem();
		CardLayout cl = (CardLayout) (leftCards.getLayout());
		CardLayout cr = (CardLayout) (rightCards.getLayout());
		
		cl.show(leftCards, option);
		if(option.equals("Query 2"))
			cr.show(rightCards, option);
		else
			cr.show(rightCards, "Query 1");
			
	}
	
	private JPanel createLoadingPane()	{
		JPanel load = new JPanel(new BorderLayout());
		ImageIcon loading = new ImageIcon("ajax-loader.gif");
		load.add(new JLabel(loading, JLabel.CENTER), BorderLayout.CENTER);
		return load;
	}
	
	public void setEnabledGUI(boolean enable)	{
		menu.setEnabled(enable);
		for(JButton b: buttons)
			b.setEnabled(enable);

		CardLayout cr = (CardLayout) (rightCards.getLayout());
		if(enable == false)	{
			cr.show(rightCards, "Loading");
		}
		else	{
			String option = (String) menu.getSelectedItem();
			if(option.equals("Query 2"))
				cr.show(rightCards, option);
			else
				cr.show(rightCards, "Query 1");
		}
			
	}
}
