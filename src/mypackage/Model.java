package mypackage;

public class Model {
	private Event[] es;
	private Category[] cs;
	private int eNum;
	
	public Model()
	{
		
	}
	
	public void setEvents(String ps,String time)
	{
		//update es by connecting to webservice
		es[0]=new Event("Title: Orchestra","Location: Van Mildert","Info: great music and fantastic atmosphere.",new String[] {"social"});
		es[1]=new Event("Title: Talk on Black Holes","Location: Physics building","Info: chance to find out about these wonderful things.",new String[] {"academic"});
		es[2]=new Event("Title: Hatfield Book Club","Location: Hatfield JCR","Info: This weeks book is Twilight: Breaking Dawn.",new String[] {"social"});
		es[3]=new Event("Title: Employee Fair","Location: CL 205","Info: Come and get infomation about loads of graduate and internship schemes from top employers.",new String[] {"academic"});
		es[4]=new Event("Title: Pool Tournament","Location: Rileys, Stockton","Info: Come and test yourself against the best!",new String[] {"social"});
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
		cs = new Category[]{new Category("social"),new Category("academic")};
	}
	
}
