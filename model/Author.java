package dblp.model;
import java.util.*;

/**
*Class Author
*Stores a single individual author
**/
public class Author
{
	public static HashMap<String, Author> map = new HashMap<>();
	private String name;
	private int numberOfPublications = 0;
	private List<Author> aliases = new ArrayList<>();
	
	public Author(String name)	{	
		this.name = name;
	}
	/**Adds to the number of publications
	*/
	public void addPublicationCount(int c)	{
		numberOfPublications += c;
	}
	
	public void incPublicationCount()	{
		numberOfPublications++;
	}
	/** Getter for the publication Count
	*/
	public int getPublicationCount()	{
		int result = numberOfPublications;
		for(Author a : aliases)
			if(a != this)
				result += a.getPublicationCount();
		return result;
	}
	/**Getter for the name
	*/
	public String getName() {
		return name;
	}
	/**Getter for the aliases
	*/
	public List<Author> getAliases() {
		return aliases;
	}
	public static void display()	{
		for(Author a: map.values())	{
			System.out.println(a.name + " " + a.numberOfPublications);
		}
	}
	
}
