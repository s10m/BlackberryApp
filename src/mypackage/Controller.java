package mypackage;
/*
 * links View to Model, called whenever a change of state in the model is required
 */
public class Controller {
	private Model model;
	
	public Controller(Model m)
	{
		model=m;
	}
	
	public void requestEvents(String[] prefs,String[] time,String sortby)
	{
		//get new events into model
		model.setEvents(prefs, time, sortby);
	}
	
	public void requestCategories()
	{//get new categories into model
		model.setCategories();
	}
}
