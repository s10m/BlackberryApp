package mypackage;

public class Model {
	private Event[] es;
	private Category[] cs;
	
	public Model()
	{
		
	}
	
	public void setEvents(String ps,String time)
	{
		//update es by connecting to webservice
	}
	
	public Event getEvent(int n)
	{
		return es[n];
	}
	
	public Category[] getCategories()
	{
		return cs;
	}
	
	public void setCategories()
	{
		//update cs by connecting to webservice
	}
	
}
