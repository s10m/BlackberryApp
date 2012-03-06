package mypackage;

import java.util.Date;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.*;


public final class OutputScreen extends MainScreen{
	private View view;
	private EventScreen escr;
	private CategoryScreen cscr;

	public OutputScreen()
	{
		super();
	}

	public void setView(View v)
	{
		view=v;//for callbacks
	}

	public void displayEvent(Event e)
	{
		//make new eventscreen
		//pop if current is eventscreen
		//push new eventscreen with new event

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
		//pop if currently displaying catscreen
		//push new screen with new category set cs
		//stack only ever contains a category screen and maybe a single event screen
		if(cscr!=null)
		{
			UiApplication.getUiApplication().popScreen(cscr);
		}
		if (cs==null) cscr=new CategoryScreen();//call error constructor
		else cscr=new CategoryScreen(cs);
		UiApplication.getUiApplication().pushScreen(cscr);
		escr=null;
	}

	public void displayError(String err)//used for runtime errors (no events returned)
	{
		Dialog.alert(err);
	}

	/*
	 * called by View to get user input
	 */
	
	public String[] getSelectedCats()
	{
		return cscr.getSelectedCs();
	}

	public String[] getSelectedT()
	{
		return cscr.getSelectedTime();
	}

	public String getsortedby()
	{
		return cscr.getSortBy();
	}

	/*
	 * provides a view of a number of categories and the ability to pick from them
	 */
	private class CategoryScreen extends MainScreen
	{
		private ObjectChoiceField[] displayedPickCats;
		DateField fromDF,toDF;
		SimpleDateFormat dFormat;
		ObjectChoiceField sortBy;
		boolean isValid;

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
			if(isValid)
			{
				menu.add(_pickItem);
			}
			menu.add(_refresh);
			menu.add(_closeItem);
		}

		public CategoryScreen ()//"error" constructor
		{
			super();
			add(new RichTextField("Could not connect to WebService over WiFi. No other connection methods available. Please exit the App by selecting \"Close\" from the menu or attempt to refresh with \"Refresh Categories\"."));
			isValid=false;//set error flag
		}

		//normal constructor
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
			}//fill array containing strings of categories
			
			while(it<displayedPickCats.length)displayedPickCats[it++]=new ObjectChoiceField("Select a category",catsStrs);//create combo box objects
			it=0;
			
			while(it<displayedPickCats.length)add(displayedPickCats[it++]);//add to screen
			
			fromDF = new DateField ("from", date.getTime(), dFormat);
			toDF = new DateField ("until", date.getTime(),dFormat);
			
			sortBy=new ObjectChoiceField("Sort Events by date",new String[] {"Ascending","Descending"});//add sort by field
			
			isValid=true;//set non error flag
			
			add(fromDF);
			add(toDF);
			add(sortBy);//add ending elements
		}

		public String[] getSelectedCs()
		{
			String[] rettmp = new String[displayedPickCats.length],ret;
			int num=0,it;
			for (it=0;it<rettmp.length;it++)
				if (displayedPickCats[it].getSelectedIndex()!=0)
				{
					rettmp[num] = (String)displayedPickCats[it].getChoice(displayedPickCats[it].getSelectedIndex());//return those which aren't "<none>" and have been chosen by a user
					num++;
				}
			ret = new String[num];
			it=0;
			
			while (it<num)
				ret[it]=rettmp[it++];
			
			return ret;
		}

		public String[] getSelectedTime()
		{
			return new String[]{dFormat.formatLocal(fromDF.getDate()),dFormat.formatLocal(toDF.getDate())};
		}

		public String getSortBy()
		{
			if (sortBy.getSelectedIndex()==0) return "dateasc";//0 displays "Ascending", 1 displays "Descending"
			else return "datedesc";
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

		private MenuItem _ffwdItem = new MenuItem("Next 5", 110, 10) 
		{
			public void run()
			{
				view.FFwd();
			}
		};

		private MenuItem _prevItem = new MenuItem("Previous", 110, 10) 
		{
			public void run()
			{
				view.getPrevEvent();
			}
		};

		private MenuItem _rwndItem = new MenuItem("Previous 5", 110, 10) 
		{
			public void run()
			{
				view.rwind();
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
			menu.add(_ffwdItem);
			menu.add(_rwndItem);
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
			//setTitle(event.getTitle());
			RichTextField rtftit=new RichTextField("Title: "+event.getTitle());
			RichTextField rtfev=new RichTextField("Event "+event.getofN());
			RichTextField rtfstrt=new RichTextField("Start: "+event.getStart());
			RichTextField rtfend=new RichTextField("End: "+event.getEnd());
			RichTextField rtfloc=new RichTextField("Location: "+event.getLoc());
			RichTextField rtfdesc=new RichTextField("Description: "+event.getInfo());
			rtftit.setFont(Font.getDefault().derive(Font.PLAIN, 15, Ui.UNITS_pt));
			rtfev.setFont(Font.getDefault().derive(Font.PLAIN, 12, Ui.UNITS_pt));
			rtfloc.setFont(Font.getDefault().derive(Font.PLAIN, 14, Ui.UNITS_pt));
			rtfdesc.setFont(Font.getDefault().derive(Font.PLAIN, 13, Ui.UNITS_pt));
			rtfstrt.setFont(Font.getDefault().derive(Font.PLAIN, 12, Ui.UNITS_pt));
			rtfend.setFont(Font.getDefault().derive(Font.PLAIN, 12, Ui.UNITS_pt));
			add(rtftit);
			add(rtfev);
			add(rtfstrt);
			add(rtfend);
			add(rtfloc);
			add(rtfdesc);
		}
	}

}