package mypackage;
/*
 * storage class for all attributes of an Event, represented as Strings
 */
public class Event {
	private String title,location,info,ofn,tstart,tend;
	private String[] categories;
	
	public String getTitle(){return title;}
	public String getLoc(){return location;}
	public String getInfo(){return info;}
	public String getStart(){return tstart;}
	public String getEnd() {return tend;}
	public String[] getCats(){return categories;}
	
	public String getofN(){return ofn;}
	
	public Event (String name,String desc,String loc,String start,String end)
	{
		title=name;
		info=desc;
		location =loc;
		tstart=start;
		tend=end;
	}
	
	public void setofN(int i,int of)
	{
		ofn=i+" of "+of;
	}
	
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
