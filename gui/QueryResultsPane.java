package dblp.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

 @SuppressWarnings("serial")
abstract public class QueryResultsPane extends JPanel implements ActionListener{
	private ResultsModel model;
	private JTable table;
	private JLabel numberOfResults = new JLabel(" ");
	private JButton moreButton = new JButton("More");
	
	public QueryResultsPane(ResultsModel model)	{
		this.model = model;
		table = new JTable(model);
		setLayout(new BorderLayout());
		table.setPreferredScrollableViewportSize(new Dimension(800,200));
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(numberOfResults, BorderLayout.PAGE_START);
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel status = new JPanel();
		status.add(moreButton);
		moreButton.addActionListener(this);
		GUIManager.addMoreButton(moreButton);
		add(status, BorderLayout.PAGE_END);
		
		GUIManager.addCountBar(numberOfResults);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.viewMore();
		if(!model.areMoreEntriesPresent())
			moreButton.setEnabled(false);
	}
}
