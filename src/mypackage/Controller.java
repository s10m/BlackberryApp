package mypackage;

public class Controller {
	private Model model;
	
	public Controller(Model m)
	{
		model=m;
	}
	
	public void requestEvents(String[] prefs,String[] time)
	{
		//get new events into model
		model.setEvents(prefs, time);
	}
	
	public void requestCategories()
	{
		model.setCategories();
	}
}
