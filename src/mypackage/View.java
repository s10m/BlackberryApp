package mypackage;

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
		screen.displayEvent(cur);//task screen with displaying cur event
	}
	
	public void setup()
	{
		getCategories();
		showCategories();
	}
	
	public void getNextEvent()
	{
		cur=model.getNextEvent();
		showEvent();
	}
	
	public void getPrevEvent()
	{
		cur=model.getPrevEvent();
		showEvent();
	}
	
	public void makeSelection()
	{
		String[] cats=screen.getSelectedCats();
		String t=screen.getSelectedT();
		controller.requestEvents(cats, t);
		cur=model.getNextEvent();
		showEvent();
	}
	
	public void getCategories()
	{
		controller.requestCategories();//task controller with fetching new categories
	}
	
	public void showCategories()
	{
		Category[] cats=model.getCategories();//get categories from model
		screen.displayCategories(cats);//task screen with displaying all categories
	}
}
