package mypackage;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.*;
//import net.rim.device.api.ui.container.*;
/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */

public final class CatScreen extends MainScreen
{

	/**
	 * Creates a new MyScreen object
	 */

	private Event[] eList;
	private int cur;
	private RichTextField num,title,loc,info;

	private Event[] populateEvents()
	{
		Event[] ret=new Event[5];
		for(int i=0;i<5;i++)
		{
			switch(i){
			case 0: ret[0]=new Event("Title: Orchestra","Location: Van Mildert","Info: great music and fantastic atmosphere.",new String[] {"social"});break;
			case 1: ret[1]=new Event("Title: Talk on Black Holes","Location: Physics building","Info: chance to find out about these wonderful things.",new String[] {"academic"});break;
			case 2: ret[2]=new Event("Title: Hatfield Book Club","Location: Hatfield JCR","Info: This weeks book is Twilight: Breaking Dawn.",new String[] {"social"});break;
			case 3: ret[3]=new Event("Title: Employee Fair","Location: CL 205","Info: Come and get infomation about loads of graduate and internship schemes from top employers.",new String[] {"academic"});break;
			case 4: ret[4]=new Event("Title: Pool Tournament","Location: Rileys, Stockton","Info: Come and test yourself against the best!",new String[] {"social"});break;
			}}
		return ret;
	}
	
	private Event[] filter(String [] cats)//assuming searching for one category for now
	{
		Event[] ret;
		int numEs=0;
		for(int i=0;i<cats.length;i++)
		{
			for(int e=0;e<eList.length;e++)
			{
				if(eList[e].isCat(cats[i])) numEs++;
			}
		}
		ret=new Event[numEs];
		numEs=0;
		for(int i=0;i<cats.length;i++)
		{
			for(int e=0;e<eList.length;e++)
			{
				if(eList[e].isCat(cats[0])){ ret[numEs]=eList[e]; numEs++;}
			}
		}
		return ret;
	}

	/*private MenuItem _go = new MenuItem("Go",110,10)
	{
		public void run()
		{
			cur=0;
			eList=populateEvents();
			//eList=filter(new String[]{"1"});
			UiApplication.getUiApplication().pushScreen(new EventScreen(eList[cur],cur));
		}
	};*/
	
	private MenuItem _closeItem = new MenuItem("Close", 200000, 10)
    {
         public void run()
         {
        	 Dialog.alert("Goodbye!");
             System.exit(0);
         }
    };
    
    private MenuItem _catIt1 = new MenuItem("Category: social",110,10)
    {
    	public void run()
    	{
    		cur=0;
			eList=populateEvents();
			eList=filter(new String[]{"social"});
			UiApplication.getUiApplication().pushScreen(new EventScreen(eList[cur],cur));
    	}
    };
    
    private MenuItem _catIt2 = new MenuItem("Category: academic",110,10)
    {
    	public void run()
    	{
    		cur=0;
			eList=populateEvents();
			eList=filter(new String[]{"academic"});
			UiApplication.getUiApplication().pushScreen(new EventScreen(eList[cur],cur));
    	}
    };
    
	public CatScreen()
	{
		super();
		add(new RichTextField("Select a category from the menu"));
		eList=populateEvents();
	}

	protected void makeMenu( Menu menu, int instance )
	{
		menu.add(_catIt1);
		menu.add(_catIt2);
		menu.add(_closeItem);
	}

	private class EventScreen extends MainScreen
	{
		private MenuItem _nextItemloc = new MenuItem("Next", 110, 10)
		{
			public void run()
			{
				cur=(cur+1)%eList.length;
				UiApplication.getUiApplication().pushScreen(new EventScreen(eList[cur],cur));
			}
		};
		
		private MenuItem _closeItem = new MenuItem("Close", 200000, 10) 
	     {
	          public void run()
	          {
	        	  Dialog.alert("Goodbye!");
	              System.exit(0);
	          }
	     };

		private MenuItem _prevItemloc = new MenuItem("Previous",110,10)
		{
			public void run()
			{
				cur--;
				cur = (cur < 0) ? eList.length-1 : cur;
				UiApplication.getUiApplication().pushScreen(new EventScreen(eList[cur],cur));
			}
		};

		private MenuItem _selectCats = new MenuItem("Select Categories",110,10)
		{
			public void run()
			{
				UiApplication.getUiApplication().pushScreen(new CatScreen());
			}
		};

		public EventScreen(Event e,int c)//takes event and extracts into screen. also adds next and prev menu items
		{
			super();
			setTitle(e.getTitle());
			add(new RichTextField((c+1)+" of "+eList.length));
			add(new RichTextField(e.getTitle()));
			add(new RichTextField(e.getLoc()));
			add(new RichTextField(e.getInfo()));
		}

		protected void makeMenu(Menu menu,int instance)
		{
			menu.add(_nextItemloc);
			menu.add(_prevItemloc);
			menu.add(_selectCats);
			menu.add(_closeItem);
		}
	}
}
