package mypackage;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.*;


public final class OutputScreen extends MainScreen{
	private View view;
	private boolean eventornot;
	
	public OutputScreen()
	{
		super();
		add(new RichTextField("fetching categories"));
	}
	
	public void setView(View v)
	{
		view=v;//for callbacks
	}
	
	public void displayEvent(Event e)
	{
		//make new eventscreen
		//pop if current is eventscreen
		//push eventscreen
		eventornot=true;
	}
	
	public void displayCategories(Category[] cs)
	{
		//make new catscreen
		//pop
		//push new screen
		
		eventornot=false;
	}
	
	public String[] getSelectedCats()
	{
		return new String[]{"a","b"};
	}
	
	public String getSelectedT()
	{
		return "2012-02-20 19:40:44";
	}
	
	private class CategoryScreen extends MainScreen
	{
		public CategoryScreen(Category[] categories)
		{
			super();
			//display all categories
		}
	}
	
	private class EventScreen extends MainScreen
	{
		public EventScreen(Event event)
		{
			super();
			//display event
		}
	}

}