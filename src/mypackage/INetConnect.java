package mypackage;
import javax.microedition.io.*;
import java.io.*;

import net.rim.device.api.ui.UiApplication;
import java.io.IOException;
import javax.microedition.io.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.io.messaging.*;
import org.json.me.*;
import java.util.Vector;


public class INetConnect {
	public INetConnect()
	{
		
	}
	public Category[] fetchCategories()
	{
		HttpConnection conn = null;
		int respc;
		Category[] ret;
    	try
    	{
    		conn = (HttpConnection) Connector.open("http://www.ignignokt.co.uk/service/category/;interface=wifi");
    		if(conn==null)
    			throw new IOException();
    		else//have connection
    		{
    			String inStr;
    			String[] names;
    			//Object in;
    			if((respc=conn.getResponseCode())!=HttpConnection.HTTP_OK) return new Category[]{new Category("ErrorResp")};;
    			DataInputStream is = conn.openDataInputStream();
    			inStr = new String(net.rim.device.api.io.IOUtilities.streamToBytes(is));
    			names=getnamesJSON(inStr);
    			ret=new Category[names.length];
    			for(int i=0;i<ret.length;i++)
    				ret[i]=new Category(names[i]);
    			return ret;
    		}
    	}
    	catch(IOException e)
    	{
    		System.out.println(e.getMessage());
    		//System.exit(1);
    	}
		return new Category[]{new Category("failed")};
	}
	
	private String[] getnamesJSON(String in)
	{
		int ptr=0,ptrend;
		String[] ret;
		Vector namesvec=new Vector();
		while(true)
		{
			while ((in.charAt(ptr++)!='{')&&(ptr<in.length()));
			while (in.charAt(ptr++)!='n');
			ptr+=6;
			ptrend=ptr;
			while (in.charAt(ptrend++)!='"');
			namesvec.addElement(in.substring(ptr,ptrend-1));
			if (in.charAt(ptrend+1)==']') break;
		}
		
		ret = new String[namesvec.size()];
		namesvec.copyInto(ret);
		
		return ret;
	}
	
	private Event[] getevsJSON(String in)
	{
		int start=0,fin,ts=0;
		Vector evs=new Vector();
		while(true)
		{
			while(in.charAt(start++)!=',');
			while(in.charAt(start++)!=':');
			start++;
			fin=start;
			while(in.charAt(fin++)!='"');
			evs.addElement(in.substring(start,fin-1));
			if(++ts==3)break;
		}
		return new Event[]{new Event((String)evs.elementAt(0),(String)evs.elementAt(1),(String)evs.elementAt(2))};
	}
	
	public Event[] fetchEvents(String[] cats, String[] ts)
	{
		HttpConnection conn = null;
		int respc;
		Event[] ret=new Event[]{new Event("n","n","n",new String[]{"n"})};
    	try
    	{
    		conn = (HttpConnection) Connector.open("http://www.ignignokt.co.uk/service/event/?"/*+"start="+ts[0]+"&end="+ts[1]*/+"&filter="+cats[0]+"/;interface=wifi");
    		if(conn==null)
    			throw new IOException();
    		else//have connection
    		{
    			String inStr;
    			String[] names;
    			//Object in;
    			if((respc=conn.getResponseCode())!=HttpConnection.HTTP_OK) return new Event[]{new Event("n","n","n",new String[]{"n"})};
    			DataInputStream is = conn.openDataInputStream();
    			inStr = new String(net.rim.device.api.io.IOUtilities.streamToBytes(is));
    			return getevsJSON(inStr);
    		}
    	}
    	catch(IOException e)
    	{
    		System.out.println(e.getMessage());
    		//System.exit(1);
    	}
		/*int num=0;
		Event[] ret=new Event[2];
		Event[] estmp=new Event[5];
		ret[0]=new Event("Title: Orchestra","Location: Van Mildert","Info: great music and fantastic atmosphere.",new String[] {"social"});
		ret[1]=new Event("Title: Talk on Black Holes","Location: Physics building","Info: chance to find out about these wonderful things.",new String[] {"academic"});
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
				}*/
		return ret;
	}
}
