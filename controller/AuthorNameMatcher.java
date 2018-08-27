package dblp.controller;

import dblp.model.*;

public class AuthorNameMatcher extends FilterDecorator {
	private String authorName;
	
	AuthorNameMatcher(Filter f, String authorName) {
		super(f);
		this.authorName = authorName;
	}
	
	@Override
	public boolean isAllowed(Publication p)	{
		boolean match = false;
		for(String name : p.getAuthors())	{
			Author a = Author.map.get(name);
			if(StringMatcher.compare(authorName, name) <= StringMatcher.THRESHOLD)
				match = true;
			for(Author al : a.getAliases())
				if(StringMatcher.compare(authorName, al.getName()) <= StringMatcher.THRESHOLD)
					match = true;
		}
		if(match)
			return super.isAllowed(p);
		else
			return false;
	}

}
