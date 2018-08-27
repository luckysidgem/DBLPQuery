package dblp.gui;
import java.util.List;

import dblp.model.*;
@SuppressWarnings("serial")
public class SearchResultsModel extends ResultsModel {
	
	private static SearchResultsModel instance = new SearchResultsModel();
	private List<Publication> results;
	private int index = 0;
	private SearchResultsModel()	{
		
	}
	private static final String colHeaders[] = {
			"#", "Authors", "Title", "Year", "Volume", "Source",
			"URL"
	};
	
	private static final int ROW_COUNT = 20;
	
	public static SearchResultsModel getInstance()	{
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
		String baseURL = "http://dblp.uni-trier.de/db/";
		if(results == null)
			return null;
		if(index + row < results.size())
			switch(col)	{
			case 0:
				return index + row + 1;
			case 1:
				return results.get(index + row).getAuthorsAsString();
			case 2:
				return "<html>"+results.get(index + row).getTitle()+"</html>";
			case 3:
				return results.get(index + row).getYearAsString();
			case 4:
				return results.get(index + row).getVolume();
			case 5:
				return results.get(index + row).getSource();
			case 6:
				return baseURL+results.get(index + row).getURL();
		}
		
		return null;
	}

	public void setData(List<Publication> list) {
		results = list;
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
