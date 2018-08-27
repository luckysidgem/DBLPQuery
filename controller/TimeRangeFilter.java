package dblp.controller;
import dblp.model.*;
/** TimeRangeFilter Class to use the Decorator Design pattern
*/
public class TimeRangeFilter extends FilterDecorator{
	
	private int fromYear, toYear;
	
	TimeRangeFilter(Filter f, int fromYear, int toYear) {
		super(f);
		this.fromYear = fromYear;
		this.toYear = toYear;
	}
	
	@Override
		/**Function to check if a publication is to stored in memory or not
	*\param[p] The publication which is checked
	*/
	public boolean isAllowed(Publication p)	{
		int yearOfPublication;
		try{
			yearOfPublication = Integer.valueOf(p.getYearAsString());
		}
		catch(Exception e)	{
			return false;
		}
		if(yearOfPublication >= fromYear && yearOfPublication <= toYear)
			return super.isAllowed(p);
		else
			return false;
	}

}
