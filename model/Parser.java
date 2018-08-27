package dblp.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
import dblp.controller.*;
/**
*Class Parser
*SAX Parser for our xml file
**/
public class Parser extends DefaultHandler
{

	private static Parser parser; //!<Stores the instance for itself
	private static final String[] fields = {"author", "title", "pages", 
			"year", "volume", "journal", "member", "url",
			"bookTitle"};
	
	private Map<String, Boolean> tags = new HashMap<>(); //!<Maps true to the tag which is being processed at the moment
	StringBuilder total = new StringBuilder("");//!<Stores the String which is created by the characters()
	public Publication current;
	
	private List<Publication> results = new ArrayList<>();
	private Filter filter;
	private boolean makeAuthorMap = true;
	
	/** Initializes the tags in constructor
	*/
	Parser(){
		for(String field : fields)
			tags.put(field, false);
	}
	/**Using singleton Design pattern
	*/
	public static Parser getInstance(){
		if(parser == null)
			parser = new Parser();
		return parser;
	}
	
	/**Setter for filter
	*/
	public static void setFilter(Filter f)	{
		parser.filter = f;
	}
	/**Entered when a new start tag is found
	*param[qname] Contains the name of the tag  
	*/
	public void startElement(String uri, String localName, String qname, Attributes attributes) throws SAXException{
		
		if(isPublicationRecord(qname) || isAuthorRecord(qname))	{
			current = new Publication();
		}
		else	{
			if(tags.containsKey(qname))	{
				tags.put(qname, true);
				total = new StringBuilder("");
			}
			else	{
				total = total.append(String.format("<%s>", qname));
			}
		}
	}
	/** Entered for the String inbetween every tag
	*\param[start] Contains the start bit for the data 
	*\param[length] Contains the length of the data inside the tag
	*/
	public void characters(char ch[], int start, int length)throws SAXException	{	
				total = total.append(ch, start, length);
	}
	/**Entered whenever the end tag is reached
	*/
	public void endElement(String uri, 
      String localName, String qname) throws SAXException	{
		
		if(filter != null && isPublicationRecord(qname) && filter.isAllowed(current))
			results.add(current);
	
		//For synonymous names
		if(isAuthorMapIncomplete() && isAuthorRecord(qname))	{
			List<String> l = current.getAuthors();
			Author sig = null;
			if(l.size() != 0)
				sig = Author.map.get(l.get(0));
			for(String s: current.getAuthors())
				Author.map.put(s, sig);		
		}
		
		boolean required = false;
		for(Map.Entry<String, Boolean> entry : tags.entrySet()){
			if(entry.getValue()){
				if(qname.equals(entry.getKey()))	{
					entry.setValue(false);
					required = true;
				}
				else	{
					total = total.append(String.format("</%s>", qname));
				}
			}
		}
		if(required)
		{
			String value = total.toString();
			if(isAuthorMapIncomplete())
				if(qname.equalsIgnoreCase("author"))	{
					Author a;
					if(!Author.map.containsKey(value))	{
						a = new Author(value);
						Author.map.put(value, a);
					}
					else
						a = Author.map.get(value);
					a.incPublicationCount();
			}
			current.setField(qname, value);
			//System.out.println(total);
		}
	}
	/** Returns the resuls
	*/
	public List<Publication> getAndClearResults()	{
		List<Publication> r = results;
		results = new ArrayList<>();
		return r;
	}
	/**Checks whether it is a publication 
	*/
	private boolean isPublicationRecord(String qname)	{
		return qname.equalsIgnoreCase("article") || qname.equalsIgnoreCase("inproceedings")||
				qname.equalsIgnoreCase("book") || qname.equalsIgnoreCase("incollection") || 
				qname.equalsIgnoreCase("phdthesis") || qname.equalsIgnoreCase("masterthesis");
	}
	/**Checks whether it is a author 
	*/	
	private boolean isAuthorRecord(String qname)	{
		return qname.equalsIgnoreCase("www");
	}
	/**Enters when the parsing is done for the xml file
	*/
	@Override
	public void endDocument()	{
		makeAuthorMap = false;
	}

	public boolean isAuthorMapIncomplete() {
		return makeAuthorMap;
	}
}