package mypackage;

import java.util.Date;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.*;


public final class OutputScreen extends MainScreen{
	private View view;
	private boolean previsevent;
	private EventScreen escr;
	private CategoryScreen cscr;
	
	public OutputScreen()
	{
		super();
		//add(new RichTextField("fetching categories..."));
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
		
		try{
			if (UiApplication.getUiApplication().getScreenCount()>1)
			{
				UiApplication.getUiApplication().popScreen(escr);
			}
		}
		catch(IllegalArgumentException ex){}
		escr=new EventScreen(e);
		UiApplication.getUiApplication().pushScreen(escr);
		
	}
	
	public void displayCategories(Category[] cs)
	{
		//make new catscreen
		//pop
		//push new screen
		if(cscr!=null)
		{
			UiApplication.getUiApplication().popScreen(cscr);
		}
		cscr=new CategoryScreen(cs);
		UiApplication.getUiApplication().pushScreen(cscr);
		
		escr=null;
	}
	
	public String[] getSelectedCats()
	{
		return cscr.getSelectedCs();
	}
	
	public String[] getSelectedT()
	{
		return cscr.getSelectedTime();
	}
	
	private class CategoryScreen extends MainScreen
	{
		private ObjectChoiceField[] displayedPickCats;
		DateField fromDF,toDF;
		SimpleDateFormat dFormat;
		
		private MenuItem _closeItem = new MenuItem("Close", 200000, 10) 
	     {
	          public void run()
	          {
	        	  Dialog.alert("Goodbye!");
	              System.exit(0);
	          }
	     };

	     private MenuItem _pickItem = new MenuItem("Pick", 110, 10) 
	     {
	    	 public void run()
	    	 {
	    		 view.makeSelection();
	    	 }
	     };
	     
	     private MenuItem _refresh = new MenuItem("Refresh Categories",110,10)
	     {
	    	 public void run()
	    	 {
	    		 view.setup();
	    	 }
	     };

	     protected void makeMenu(Menu menu,int instance)
	     {
	    	 menu.add(_pickItem);
	    	 menu.add(_refresh);
	    	 menu.add(_closeItem);
	     }

		public CategoryScreen(Category[] categories)
		{
			super();
			dFormat = new SimpleDateFormat("yyyy-MM-dd");
			this.setToNew(categories);
			//display all categories
		}
		
		private void setToNew(Category[] categories)
		{
			int it=0;
			Date date = new Date(System.currentTimeMillis());
			displayedPickCats=new ObjectChoiceField[6];
			//display cats
			String[] catsStrs=new String[categories.length+1];
			catsStrs[0]=new String("<none>");
			for(int i=1;i<categories.length+1;i++)
			{
				catsStrs[i]=categories[i-1].getCatInfo();
			}
			while(it<displayedPickCats.length)displayedPickCats[it++]=new ObjectChoiceField("Select a category",catsStrs);
			it=0;
			while(it<displayedPickCats.length)add(displayedPickCats[it++]);
			
			fromDF = new DateField ("from", date.getTime(), dFormat);
			toDF = new DateField ("until", date.getTime(),dFormat);
			add(fromDF);
			add(toDF);
		}
		
		public String[] getSelectedCs()
		{
			String[] rettmp = new String[displayedPickCats.length],ret;
			int num=0,it;
			for(it=0;it<rettmp.length;it++)
				if(displayedPickCats[it].getSelectedIndex()!=0)
				{
					rettmp[num]=(String)displayedPickCats[it].getChoice(displayedPickCats[it].getSelectedIndex());
					num++;
				}
			ret=new String[num];
			it=0;
			while(it<num)
				ret[it]=rettmp[it++];
			
			return ret;
		}
		
		public String[] getSelectedTime()
		{
			return new String[]{dFormat.formatLocal(fromDF.getDate()),dFormat.formatLocal(toDF.getDate())};
		}
	}
	
	private class EventScreen extends MainScreen
	{
		private MenuItem _closeItemloc = new MenuItem("Close", 200000, 10) 
	    {
	          public void run()
	          {
	        	  Dialog.alert("Goodbye!");
	              System.exit(0);
	          }
	    };
	    
	    private MenuItem _nextItem = new MenuItem("Next", 110, 10) 
	    {
	          public void run()
	          {
	        	  view.getNextEvent();
	          }
	    };
	    
	    private MenuItem _prevItem = new MenuItem("Previous", 110, 10) 
	    {
	          public void run()
	          {
	        	  view.getPrevEvent();
	          }
	    };
	    
	    private MenuItem _refreshloc = new MenuItem("Get new Categories",110,10)
	     {
	    	 public void run()
	    	 {
	    		 UiApplication.getUiApplication().popScreen(escr);
	    		 view.setup();
	    	 }
	     };
	    
	    protected void makeMenu(Menu menu,int instance)
	     {
	    	 menu.add(_nextItem);
	    	 menu.add(_prevItem);
	    	 menu.add(_refreshloc);
	    	 menu.add(_closeItemloc);
	     }
	    
		public EventScreen(Event event)
		{
			super();
			this.setToNew(event);
			//display event
		}
		
		private void setToNew(Event event)
		{
			//display event
			setTitle(event.getTitle());
			//add(new RichTextField((c+1)+" of "+eList.length));
			//add(new RichTextField(event.getTitle()));
			add(new RichTextField(event.getLoc()));
			add(new RichTextField(event.getInfo()));
		}
	}

}