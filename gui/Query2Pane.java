package dblp.gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import dblp.model.*;
import dblp.controller.*;

@SuppressWarnings("serial")
public class Query2Pane extends QueryPane{
	
	private TextField numberOfPublicationsInput = new TextField(20);
	Query2Pane()	{
		this.setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JPanel ip = new JPanel(new GridLayout(1,2));
		ip.add(new JLabel("Number of Publications:"));
		ip.add(numberOfPublicationsInput);
		p.add(ip);
		
		add(p, BorderLayout.CENTER);
		add(bp, BorderLayout.PAGE_END);
		
	}
	
	@Override
	public void makeQuery()	{
		GUIManager.enableGUI(false);
		SwingWorker<List<Author>, Object> querier = new SwingWorker<List<Author>, Object>(){

			@Override
			protected List<Author> doInBackground() throws Exception {
				Controller controller = Controller.getInstance();
				return controller.query2(Integer.valueOf(numberOfPublicationsInput.getText()));
			}
			
			@Override
			protected void done()	{
				GUIManager.enableGUI(true);
				CountResultsModel model = CountResultsModel.getInstance();
				try {
					model.setData(get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		querier.execute();
	}
	
	@Override
	public boolean validateInput()	{
		try	{
			Integer.valueOf(numberOfPublicationsInput.getText());
		}
		catch(NumberFormatException e)	{
			errorBar.setText("Enter a number");
			return false;
		}
		errorBar.setText(" ");
		return true;
	}
}
