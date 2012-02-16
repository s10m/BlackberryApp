package mypackage;

public class Controller {
	private Model model;
	
	public Controller(Model m)
	{
		model=m;
	}
	
	public void requestEvents(String[] prefs,String time)
	{
		//get new events into model
		model.setEvents(arrtoStr(prefs), "1000");
	}
	
	private String arrtoStr (String[] in)
	{
		StringBuffer retbuff=new StringBuffer();
		for(int i=0;i<in.length;i++)
			retbuff.append(in[i]);
		return retbuff.toString();
	}
	
	public void requestCategories()
	{
		model.setCategories();
	}
}
