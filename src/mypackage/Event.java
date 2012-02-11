package mypackage;
/**
 * class for holding and accessing data about events
 */

public class Event {
	private String title,location,info;
	private String[] categories;

	public String getTitle(){return title;}
	public String getLoc(){return location;}
	public String getInfo(){return info;}
	public String[] getCats(){return categories;}
	
	public Event ( String t,String l,String i,String[] cats)//constructor is only way of setting internal state
	{
		title=t;
		location=l;
		info=i;
		categories=cats;
	}
	//returns true if 
	public boolean isCat(String cat)
	{
		for(int i=0;i<categories.length;i++)
			if(categories[i].toUpperCase().equals(cat.toUpperCase()))
				return true;

		return false;
	}
}
