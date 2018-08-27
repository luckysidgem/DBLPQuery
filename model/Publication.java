package dblp.model;
import java.util.ArrayList;
import java.util.List;
/**
*Class Publication
*Stores a single individual record
**/
public class Publication {
	/**
	*Private variables defining the record.
	*
	**/
	private List<String> authors;
	private String title;
	private String pages;
	private String volume;
	private String year;
	private String journal;
	private String url;
	
	/**
	* The constructor to the class
	* Initializes the ArrayList
	**/
	public Publication() {
		authors = new ArrayList<>();
	}
	/**
	* getter for the list of authors 
	* 
	**/
	public List<String> getAuthors()	{
		return authors;
	}
	/**
	* Setter. Sets the value of the required field 
	* \param[key] Key constains the field to be changed
	* \param[values] Stores the value that is to be assigned to the field
	*/
	public void setField(String key, String value)	{
		switch(key)	{
		case "author": authors.add(value); break;
		case "title": title = value; break;
		case "pages": pages = value; break;
		case "volume": volume = value; break;
		case "journal": case "bookTitle": journal = value; break;
		case "url": url = value; break;
		case "year": year = value; break;
		default: System.out.println(key); throw new IllegalArgumentException();
		}
	}
	/** getter for the title
	*/
	public String getTitle() {
		return title;
	}
	/** getter for the year
	*/
	public String getYearAsString() {
		if(year == null)
			return "????";
		return year;
	}
	/** Getter for the author
	*/
	public String getAuthorsAsString()	{
		return String.join(", ", authors);
	}

	/** Prints the various details(fields) of the record
	*/
	public void print()	{
		System.out.println("Authors: ");
		for(String i: authors)
			System.out.print(i + ", ");
		System.out.println("Title: " + title);
		System.out.println("Pages: " + pages);
		System.out.println("Volume: " + volume);
		System.out.println("Journal: " + journal);
		System.out.println("Url: " + url);
	}
	/**Getter for the URL
	*/
	public String getURL() {
		return url;
	}
	/**Getter for the source
	*/
	public String getSource()	{
		return journal;
	}
	/**Getter for the pages
	*/
	public String getPages()	{
		return pages;
	}
	/** getter for the volume
	*/
	public String getVolume()	{
		return volume;
	}
	

}
