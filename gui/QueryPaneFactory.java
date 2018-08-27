package dblp.gui;
//Factory Method Pattern
public class QueryPaneFactory {
	public static QueryPane makeQueryPane(String queryType)	{
		switch(queryType)	{
		case "Query 1": return new Query1Pane();
		case "Query 2": return new Query2Pane();
		}
		return new NoQueryPane();
	}
}
