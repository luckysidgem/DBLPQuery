package dblp.gui;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;

import dblp.controller.Controller;
import dblp.controller.StringMatcher;
import dblp.model.Publication;

@SuppressWarnings("serial")
public class Query1Pane extends QueryPane {
	
	private JComboBox<String> menu;
	private TextField searchTerms = new TextField(20);
	private TextField sinceYear = new TextField("YYYY");
	private TextField fromYear = new TextField("YYYY"), toYear = new TextField("YYYY");
	private JRadioButton sortYear= new JRadioButton("Sort by year (descending)");
	private JRadioButton sortRelevance = new JRadioButton("Sort by Relevance");
	private int since, from, to;
	
	public Query1Pane()	{
		JPanel outer = new JPanel();
		outer.setLayout(new BoxLayout(outer, BoxLayout.PAGE_AXIS));
		String[] options = {"Search by", "Author", "Title"};
		menu = new JComboBox<>(options);
		menu.setPreferredSize(new Dimension(100,30));
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		p1.add(Box.createRigidArea(new Dimension(15, 0)));
		p1.add(menu);
		
		outer.add(p1);
		outer.add(createSearchForm());
		outer.add(Box.createRigidArea(new Dimension(0,20)));
		
		ButtonGroup sortOptions = new ButtonGroup();
		sortOptions.add(sortYear);
		sortOptions.add(sortRelevance);
		
		outer.add(sortYear);
		outer.add(sortRelevance);
		outer.add(bp);
		
		reset.addActionListener(e->{
			sortOptions.clearSelection();
			menu.setSelectedIndex(0);
			searchTerms.setText(" ");
			sinceYear.setText("YYYY");
			fromYear.setText("YYYY");
			toYear.setText("YYYY");
		});
		
		add(outer);
		
	}
	
	private JPanel createSearchForm()	{
		JPanel sf = new JPanel(new GridLayout(0,2,0,0));
		sf.add(new JLabel("Search terms"));
		sf.add(searchTerms);
		
		sf.add(new JLabel("Since year"));
		JPanel year = new JPanel(new FlowLayout(FlowLayout.LEADING));
		year.add(sinceYear);
		sf.add(year);
		
		sf.add(new JLabel("Custom Range"));
		JPanel yearRange = new JPanel(new FlowLayout(FlowLayout.LEADING));
		yearRange.add(fromYear);
		yearRange.add(new JLabel(" - "));
		yearRange.add(toYear);
		sf.add(yearRange);
		
		return sf;
	}
	
	@Override
	public void makeQuery()	{
		GUIManager.enableGUI(false);
		SwingWorker<List<Publication>, Object> querier = new SwingWorker<List<Publication>, Object>(){

			@Override
			protected List<Publication> doInBackground() throws Exception {
				Controller controller = Controller.getInstance();
				List<Publication> pl = controller.query1(
						searchTerms.getText(),
						menu.getSelectedIndex(),
						from,
						to);
				if(sortRelevance.isSelected())	{
					Collections.sort(pl,
							(a,b)-> new Double(StringMatcher.compare(a.getTitle(), searchTerms.getText())).
							compareTo(StringMatcher.compare(b.getTitle(), searchTerms.getText())));
				} else if(sortYear.isSelected())	{
					Collections.sort(pl,
							(a,b)->b.getYearAsString().compareTo(a.getYearAsString()));
				} else	{
					Collections.sort(pl,
							(b,a)->b.getYearAsString().compareTo(a.getYearAsString()));
				}
				return pl;
			}
			
			@Override
			protected void done()	{
				GUIManager.enableGUI(true);
				SearchResultsModel model = SearchResultsModel.getInstance();
				try {
					List<Publication> pl = get();
					model.setData(pl);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		querier.execute();
	}
	
	@Override
	public boolean validateInput()	{
		if(menu.getSelectedIndex() == 0)	{
			errorBar.setText("Select between Author/Title");
			return false;
		}
		if(searchTerms.getText().equals(""))	{
			errorBar.setText("Search field cannot be empty");
			return false;
		}

		since = from = to = -1;
		int choice = 0;
		try	{
			if(!sinceYear.getText().equals("YYYY"))
				since = Integer.valueOf(sinceYear.getText());
			if(!fromYear.getText().equals("YYYY"))
				from = Integer.valueOf(fromYear.getText());
			if(!toYear.getText().equals("YYYY"))
				to = Integer.valueOf(toYear.getText());
		}
		catch(NumberFormatException e)	{
			errorBar.setText("Enter a number for year fields");
			return false;
		}
		if(since == -1 && from > 1900 && to > 1900)	{
			choice = 1;
		}
		else if(since > 1900 && from == -1 && to == -1)	{
			choice = 2;
			from = since;
			to = 3000;
		}
		else if(since == -1 && from == -1 && to == -1)	{
			choice = 3;
		}
		if(choice == 0)	{
			errorBar.setText("Enter value for only one set of year fields");
			return false;
		}
		errorBar.setText(" ");
		return true;
	}
}
