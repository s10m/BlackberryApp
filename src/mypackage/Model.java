package mypackage;


public class Model {
	private Event[] es;
	private Category[] cs;
	private int eNum;
	private INetConnect netConn;
	
	public Model()
	{
		netConn=new INetConnect();
	}
	
	public void setEvents(String[] ps,String[] time)
	{
		//update es by connecting to webservice
		netConn.fetchEvents(ps, time);
		eNum=-1;
	}
	
	public Event getNextEvent()
	{
		eNum=(eNum+1)%es.length;
		return es[eNum];
	}
	
	public Event getPrevEvent()
	{
		eNum--;
		eNum = (eNum<0) ? (es.length-1) : eNum;
		return es[eNum];
	}
	
	public Category[] getCategories()
	{
		return cs;
	}
	
	public void setCategories()
	{
		//update cs by connecting to webservice
		cs = netConn.fetchCategories();
	}
	
}
