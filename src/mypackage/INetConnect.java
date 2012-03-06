package mypackage;
import javax.microedition.io.*;
import java.io.*;

import java.io.IOException;
import java.util.Vector;

/*
 * manages connection to the network
 */
public class INetConnect {
	/*
	 * opens new connection, returns an array of categories
	 */
	public Category[] fetchCategories()
	{
		HttpConnection conn = null;
		Category[] ret;

		try
		{
			conn = (HttpConnection) Connector.open("http://www.ignignokt.co.uk/service/category;interface=wifi");//open connection to the webservice at the correct url 
			if (conn==null)
				throw new IOException();

			else//have connection
			{
				String inStr;
				String[] names;

				if (conn.getResponseCode() != HttpConnection.HTTP_OK)
					return null;//on error return null

				DataInputStream is = conn.openDataInputStream();
				inStr = new String(net.rim.device.api.io.IOUtilities.streamToBytes(is));
				names=getnamesJSON(inStr);//parse json to get names

				ret=new Category[names.length];

				for(int i=0;i<ret.length;i++)
					ret[i]=new Category(names[i]);
				conn.close();
				return ret;
			}
		}
		catch(IOException e)
		{
			return null;
		}
	}

	private String[] getnamesJSON(String in)
	{
		int ptr=0,ptrend;
		String[] ret;
		Vector namesvec=new Vector();

		while(true)
		{
			while ((in.charAt(ptr++)!='{')&&(ptr<in.length()));//increment to the next element
			while (in.charAt(ptr++)!=',');//increment until first char of "name" (second payload)
			while(in.charAt(ptr++)!=':');//advance to beginning of payload
			ptr++;
			ptrend=ptr;
			while (in.charAt(ptrend++)!='"');//advance to end of payload
			namesvec.addElement(in.substring(ptr,ptrend-1));//save payload
			if (in.charAt(ptrend+1)==']') break;//break at end of message
		}

		ret = new String[namesvec.size()];
		namesvec.copyInto(ret);

		return ret;
	}

	private Event[] getevsJSON(String in)
	{
		int start=0,fin,ts=0,tsout=0;
		boolean done=false;
		Event[] ret;
		Vector evsparams,evs=new Vector();

		while(!done)
		{
			evsparams = new Vector();//use for storing 5 parameters for event

			while(true)
			{
				while(in.charAt(start++)!=',');//increment to second thing
				while(in.charAt(start++)!=':');
				start++;//advance to payload start
				fin=start;
				while(in.charAt(fin++)!='"');
				evsparams.addElement(in.substring(start,fin-1));//copy payload
				if(++ts==5)break;//do five elements per event
			}
			evs.addElement(new Event((String)evsparams.elementAt(0),(String)evsparams.elementAt(1),(String)evsparams.elementAt(2),(String)evsparams.elementAt(3),(String)evsparams.elementAt(4)));//create new event with acquired params

			while ((!done)&&!((in.charAt(start)==',')&&(in.charAt(start+1)=='{')))//advance to next event while not at end
				if (++start>=in.length()) done=true;

			start++;//advance start to beginning of next event
			ts=0;
		}

		ret = new Event[evs.size()];
		evs.copyInto(ret);
		return ret;
	}

	public Event[] fetchEvents(String[] cats, String[] ts, String sort)
	{
		HttpConnection conn = null;

		Event[] ret=null;

		try
		{
			String urlStr;
			urlStr = "http://www.ignignokt.co.uk/service/event/?"+"start="+ts[0]+"&end="+ts[1];
			if(cats.length>0) urlStr += "&filter=";
			for (int c=0;c<cats.length;c++)
			{
				if(cats[c].length()>0)
				{
					urlStr += cats[c];
					if (c<cats.length-1)
						urlStr += ",";
				}
			}
			urlStr += "&sort="+sort+";interface=wifi";
			System.out.println("urlStr = "+urlStr);
			//create URL
			conn = (HttpConnection) Connector.open(urlStr);//make new connection

			if(conn==null)
				throw new IOException();


			if (conn!=null)//have connection
			{
				String inStr;
				//Object in;
				if(conn.getResponseCode()!=HttpConnection.HTTP_OK) return null;
				DataInputStream is = conn.openDataInputStream();
				inStr = new String(net.rim.device.api.io.IOUtilities.streamToBytes(is));

				conn.close();
				return getevsJSON(inStr);//parses json, if valid, into Event array
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			//System.exit(1);
		}

		return ret;//null on error
	}
}
