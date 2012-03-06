package mypackage;

/*
 * provides and manages access to arrays of categories and events, updated with an object which connects to the WebService
 */

public class Model {
	private Event[] es;
	private Category[] cs;
	private int eNum;
	private INetConnect netConn;

	public Model()
	{
		netConn=new INetConnect();//create new object
	}

	public void setEvents(String[] ps,String[] time, String sort)
	{
		//update es by connecting to webservice
		es = netConn.fetchEvents(ps, time, sort);//returns null on no events returned or on connection error 
		if (es!=null)//if not error
			for (int e=0;e<es.length;e++)
				es[e].setofN(e+1, es.length);//set "1 of 15" property of events

		eNum=-1;//initialise the counter
	}

	public Event advanceFive()//advance pointer to current event 5 forward modulo the number of events stored
	{
		eNum=(eNum+5)%es.length;
		return es[eNum];
	}

	public Event rewindFive()//move pointer back 5 modulo the number of events
	{
		eNum-=5;
		eNum = (eNum<0) ? (es.length-1) : eNum;
		return es[eNum];
	}

	public Event getNextEvent()//advance pointer one, returning null if error
	{
		if(es==null)return null;
		eNum=(eNum+1)%es.length;
		return es[eNum];
	}

	public Event getPrevEvent()//move back
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
