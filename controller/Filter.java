package dblp.controller;
import dblp.model.*;

/**Class to use the Decorator Design pattern
*/
public class Filter {
	/**Function to check if a publication is to stored in memory or not
	*\param[p] The publication which is checked
	*/
	public boolean isAllowed(Publication p)	{
		return true;
	}
}
