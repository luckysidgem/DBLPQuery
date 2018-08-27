package dblp.gui;
import java.util.List;

import dblp.model.*;

@SuppressWarnings("serial")
public class CountResultsModel extends ResultsModel {
	
	private static CountResultsModel instance = new CountResultsModel();
	private List<Author> results;
	private CountResultsModel()	{
		
	}
	private static final String colHeaders[] = {
			"#", "Authors", "Count"
	};
	
	private static final int ROW_COUNT = 20;
	
	public static CountResultsModel getInstance()	{
		return instance;
	}
	
	@Override
	public int getColumnCount() {
		return colHeaders.length;
	}
	
	public String getColumnName(int col) {
		return colHeaders[col];
	}

	@Override
	public int getRowCount() {
		return ROW_COUNT;
	}

	@Override
	public Object getValueAt(int row, int col) {
		if(results == null)
			return null;
		if(index + row < results.size())
			switch(col)	{
			case 0:
				return index + row + 1;
			case 1:
				return results.get(index + row).getName();
			case 2:
				return results.get(index + row).getPublicationCount();
		}
		
		return null;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col)	{
		
	}
	
	public void setData(List<Author> results)	{
		this.results = results;
		index = 0;
		GUIManager.setCount(results.size());
		fireTableDataChanged();
	}
	
	
	
	public boolean areMoreEntriesPresent()	{
		return index + 20 < results.size();
	}
	
	public void viewMore()	{			
		index += 20;
		fireTableDataChanged();
	}
}
