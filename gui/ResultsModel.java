package dblp.gui;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
abstract public class ResultsModel extends AbstractTableModel {
	protected int index = 0;
	
	abstract public void viewMore();
	abstract public boolean areMoreEntriesPresent();
}
