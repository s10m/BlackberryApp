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
		int num=0;
		Event[] estmp=new Event[5];
		estmp[0]=new Event("Title: Orchestra","Location: Van Mildert","Info: great music and fantastic atmosphere.",new String[] {"social"});
		estmp[1]=new Event("Title: Talk on Black Holes","Location: Physics building","Info: chance to find out about these wonderful things.",new String[] {"academic"});
		estmp[2]=new Event("Title: Hatfield Book Club","Location: Hatfield JCR","Info: This weeks book is Twilight: Breaking Dawn.",new String[] {"social"});
		estmp[3]=new Event("Title: Employee Fair","Location: CL 205","Info: Come and get infomation about loads of graduate and internship schemes from top employers.",new String[] {"academic"});
		estmp[4]=new Event("Title: Pool Tournament","Location: Rileys, Stockton","Info: Come and test yourself against the best!",new String[] {"social"});
		for(int i=0;i<estmp.length;i++)
			for(int j=0;j<ps.length;j++)
				if (estmp[i].getCats()[0].equals(ps[j]))
				{
					num++;
					break;
				}
		
		es=new Event[num];
		num=0;
		
		for(int i=0;i<estmp.length;i++)
			for(int j=0;j<ps.length;j++)
				if(estmp[i].getCats()[0].equals(ps[j]))
				{
					es[num++]=estmp[i];
					break;
				}
		
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
