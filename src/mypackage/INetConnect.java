package mypackage;
import javax.microedition.io.*;
import java.io.*;

import net.rim.device.api.ui.UiApplication;
import java.io.IOException;
import javax.microedition.io.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.io.messaging.*;
import org.json.me.*;


public class INetConnect {
	public INetConnect()
	{
		
	}
	public Category[] fetchCategories()
	{
		HttpConnection conn = null;
    	try
    	{
    		conn = (HttpConnection) Connector.open("http://www.ignignokt.co.uk/;interface=wifi");
    		if(conn==null)
    			throw new IOException();
    		else//have connection
    		{
    			String inStr;
    			//Object in;
    			DataOutputStream os = conn.openDataOutputStream();
    			conn.setRequestMethod("GET");
    			os.write("GET /service/category/ HTTP/1.1".getBytes());
    			os.write(new byte[]{'\r','\n'});
    			os.write("Host: www.ignignokt.co.uk".getBytes());
    			os.write(new byte[]{'\r','\n','\r','\n'});
    			DataInputStream is = conn.openDataInputStream();
    			inStr = new String(net.rim.device.api.io.IOUtilities.streamToBytes(is));
    			inStr=getFirstfromJSON(inStr);
    			return new Category[] {new Category(inStr)};
    		}
    	}
    	catch(IOException e)
    	{
    		System.out.println(e.getMessage());
    		//System.exit(1);
    	}
		return new Category[]{new Category("failed")};
	}
	
	private String getFirstfromJSON(String in)
	{
		int ptr=0,ptrend;
		while((in.charAt(ptr++)!='{')&&(ptr<in.length()));
		if(ptr>=in.length()) return ""+in.charAt(ptr-1);
		while(in.charAt(ptr++)!='n');
		ptr+=7;
		ptrend=ptr;
		while(in.charAt(ptrend++)!='"');
		return in.substring(ptr,ptrend);
	}
	
	public Event[] fetchEvents(String[] cats, String[] ts)
	{
		int num=0;
		Event[] ret;
		Event[] estmp=new Event[5];
		estmp[0]=new Event("Title: Orchestra","Location: Van Mildert","Info: great music and fantastic atmosphere.",new String[] {"social"});
		estmp[1]=new Event("Title: Talk on Black Holes","Location: Physics building","Info: chance to find out about these wonderful things.",new String[] {"academic"});
		estmp[2]=new Event("Title: Hatfield Book Club","Location: Hatfield JCR","Info: This weeks book is Twilight: Breaking Dawn.",new String[] {"social"});
		estmp[3]=new Event("Title: Employee Fair","Location: CL 205","Info: Come and get infomation about loads of graduate and internship schemes from top employers.",new String[] {"academic"});
		estmp[4]=new Event("Title: Pool Tournament","Location: Rileys, Stockton","Info: Come and test yourself against the best!",new String[] {"social"});
		for(int i=0;i<estmp.length;i++)
			for(int j=0;j<cats.length;j++)
				if (estmp[i].getCats()[0].equals(cats[j]))
				{
					num++;
					break;
				}
		
		ret=new Event[num];
		num=0;
		
		for(int i=0;i<estmp.length;i++)
			for(int j=0;j<cats.length;j++)
				if(estmp[i].getCats()[0].equals(cats[j]))
				{
					ret[num++]=estmp[i];
					break;
				}
		return ret;
	}
}
