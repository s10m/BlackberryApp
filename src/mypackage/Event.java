package mypackage;

public class Event {
	private String title,location,info;
	private String[] categories;
	public String getTitle(){return title;}
	public String getLoc(){return location;}
	public String getInfo(){return info;}
	public String[] getCats(){return categories;}
	
	public Event ( String t,String l,String i,String[] cats)
	{
		title=t;
		location=l;
		info=i;
		categories=cats;
	}
	
	public boolean isCat(String cat)
	{
		for(int i=0;i<categories.length;i++)
		{
			if(categories[i].equals(cat)) return true;
		}
		return false;
	}
}
