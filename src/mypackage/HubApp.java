package mypackage;

import net.rim.device.api.ui.UiApplication;

/**
 * Central app, starting in the constructor
 */
public class HubApp extends UiApplication
{
	/**
	 * Entry point for application
	 */ 
	public static void main(String[] args)
	{
		// Create a new instance of the application and make the currently
		// running thread the application's event dispatch thread.
		MyApp theApp = new MyApp();
		theApp.enterEventDispatcher();
	}

	/**
	 * Creates a new MyApp object
	 */
	public HubApp()
	{        
		// Push a screen onto the UI stack for rendering.
		pushScreen(new CatScreen());
	}    
}
