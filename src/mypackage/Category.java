package mypackage;
/*
 * simple storage class for Strings representing categories
 */
public class Category {
	private String name;
	
	public Category(String n)
	{
		name=n;
	}
	
	public String getCatInfo()
	{
		return name;
	}
}