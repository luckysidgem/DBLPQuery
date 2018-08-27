package dblp.controller;
import dblp.model.*;
/** TitleNameMatcher Class to use the Decorator Design pattern
*/
public class TitleNameMatcher extends FilterDecorator {
	
	private String title;
	TitleNameMatcher(Filter f, String titleToBeMatched) {
		super(f);
		this.title = titleToBeMatched;
	}
	
	@Override
		/**Function to check if a publication is to stored in memory or not
	*\param[p] The publication which is checked
	*/
	public boolean isAllowed(Publication p)	{
		if(StringMatcher.compare(title, p.getTitle()) <= StringMatcher.THRESHOLD)
			return super.isAllowed(p);
		else
			return false;
	}

}
