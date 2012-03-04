package mypackage;

import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class App extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */
	private Model m;
	private View v;
	private Controller c;
	private OutputScreen out;
	
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        App app = new App();
        app.enterEventDispatcher();
    }
    
    /**
     * Creates a new MyApp object
     */
    public App()
    {
    	m=new Model();
    	c=new Controller(m);
    	out=new OutputScreen();
    	v=new View(m,c,out);
    	out.setView(v);
    	v.setup();
        // Push a screen onto the UI stack for rendering.
        //pushScreen(out);//new CatScreen());
    }    
}
