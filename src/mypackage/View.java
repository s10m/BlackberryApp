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
	
	public void makeSelection(String[] c,String t)
	{}
	
	public void getCategories()
	{}
	
	public void showCategories()
	{}
}
