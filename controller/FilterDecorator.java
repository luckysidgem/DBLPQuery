package dblp.controller;
import dblp.model.*;
/**Abstract Class to use the Decorator Design pattern
*/
public abstract class FilterDecorator extends Filter {
	
	private Filter filter;
	
	FilterDecorator(Filter f)	{
		filter = f;
	}
	@Override
		/**Function to check if a publication is to stored in memory or not
	*\param[p] The publication which is checked
	*/
	public boolean isAllowed(Publication p) {
		return filter.isAllowed(p);
	}

}
