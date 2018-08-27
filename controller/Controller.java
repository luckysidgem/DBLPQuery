package dblp.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.*;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import dblp.model.*;

/**Controller Class to use the MVC Design pattern
*/
public class Controller
{
	public static Controller instance; //!< Instance of the dblp.controller class to implement Singleton design pattern
	private XMLReader reader;
	private Parser parser = Parser.getInstance();
	private String filename;
	/**Private constructor to read the file
	*/
	private Controller(String filename)	{
		this.filename = filename;
		System.setProperty("jdk.xml.entityExpansionLimit", "0");
		try	{
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(parser);
		}
		catch(Exception e)	{
			
		}
	}
	/**Getinstance for the Singleton design pattern
	*/
	public static synchronized Controller getInstance()	{
		if(instance == null)
			instance = new Controller("dblp.xml");
		return instance;
	}
	/**Query1 handled here
	*/
	public List<Publication> query1(String terms, int type, int fromYear, int toYear)	{
		Filter f = new Filter();
		if(type == 1)
			f = new AuthorNameMatcher(f, terms);
		else
			f = new TitleNameMatcher(f, terms);
		if(fromYear > 0 && toYear > 0)
			f = new TimeRangeFilter(f, fromYear, toYear);
		
		Parser.setFilter(f);
		try {
			reader.parse(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parser.getAndClearResults();
	}
	/**Query2 handled here
	*/
	public List<Author> query2(int numberOfPublications)	{
		
		if(parser.isAuthorMapIncomplete())
			try {
				reader.parse(filename);
			} catch (IOException | SAXException e) {
				e.printStackTrace();
			}
		
		List<Author> results = new ArrayList<>();
		Set<Author> uniqueAuthors = new HashSet<>(Author.map.values()); 
		for(Author a : uniqueAuthors)
			if(a.getPublicationCount() >= numberOfPublications)
				results.add(a);
		
		return results;
	}
	/**Main Function
	*Parsde is called here
	*/
	public static void main(String args[]) throws IOException, SAXException{
		XMLReader reader = XMLReaderFactory.createXMLReader();
		Parser parser = Parser.getInstance();
		Parser.setFilter(new Filter(){

			@Override
			public boolean isAllowed(Publication p) {
				return true;
			}
			
		});
		
		reader.setContentHandler(parser);
		System.setProperty("jdk.xml.entityExpansionLimit", "0");
		reader.parse("dblp1.xml");
		for(Publication p: parser.getAndClearResults())
			p.print();
		
		Author.display();
	}
}