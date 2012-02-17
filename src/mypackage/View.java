package mypackage;

public class View {
	private Event cur;
	private int curnum;
	private Model model;
	private Controller controller;
	private OutputScreen screen;
	
	public View(Model m,Controller c,OutputScreen scr)
	{
		model=m;
		controller=c;
		curnum=0;
		screen=scr;
	}
	
	public void showEvent()
	{
		
	}
	
	public void makeSelection()
	{
		String[] cats=screen.getSelectedCats();
		String t=screen.getSelectedT();
		controller.requestEvents(cats, t);
		cur=model.getEvent(0);
		curnum=0;
	}
	
	public void getCategories()
	{}
	
	public void showCategories()
	{}
}
