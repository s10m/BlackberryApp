package mypackage;
/*
 * links everything together
 */
public class View {
	private Event cur;
	private Model model;
	private Controller controller;
	private OutputScreen screen;
	
	public View(Model m,Controller c,OutputScreen scr)
	{
		model=m;
		controller=c;
		screen=scr;
	}
	
	private void showEvent()
	{
		screen.displayEvent(cur);//task screen with displaying current event, cur
	}
	
	public void setup()//initialise and zero everything
	{
		getCategories();
		showCategories();
	}
	/*
	 * for cycling through events
	 */
	public void getNextEvent()//fetch next event, is first "cycle" call
	{
		cur=model.getNextEvent();
		if(cur!=null)//null is error
			showEvent();
		else
		{
			screen.displayError("There are no events between the specified dates in the specified categories.");//display error
		}
	}
	
	public void FFwd()
	{
		cur=model.advanceFive();
		showEvent();
	}
	
	public void rwind()
	{
		cur=model.rewindFive();
		showEvent();
	}
	
	public void getPrevEvent()
	{
		cur=model.getPrevEvent();
		showEvent();
	}
	
	public void makeSelection()//get user input and start displaying events
	{
		String[] cats=screen.getSelectedCats();
		String[] t = screen.getSelectedT();
		String sorted = screen.getsortedby();
		controller.requestEvents(cats, t, sorted);
		getNextEvent();
	}
	
	public void getCategories()
	{
		controller.requestCategories();//task controller with fetching new categories
	}
	
	public void showCategories()
	{
		Category[] cats=model.getCategories();//get categories from model
		screen.displayCategories(cats);//task screen with displaying all categories. will handle errors on it's own
	}
}