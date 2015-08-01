package net.arcanesanctuary.Configuration;

import java.util.ArrayList;

public class ConfData
{
	String variable;
	Object value;
	String description;
	boolean hiddenPrompt;
	
	ConfData parent;
	ArrayList<ConfData> children;
	
	public ConfData(String var, String desc, String val) 
	{
		this.variable = var;
		this.description = desc;
		this.value = val;
		
		this.parent = null;
		this.children = new ArrayList<ConfData>();
	}
	
	@Deprecated
	public ConfData(String var, String val)
	{
		this.variable = var;
		this.value = val;
	}
	
	@Deprecated
	public ConfData(String var)
	{
		this.variable = var;
		this.value = null;
	}
	
	/**
	 * Return the variable name
	 * @return The variable name of this 
	 */
	public String getVar()
	{
		return this.variable;
	}
	
	/**
	 * Return the description of <code>this</code>
	 * @return Detailed description of <code>this</code>
	 */
	public String getDesc()
	{
		if(this.description != null) 
		{
			return this.description;
		} else  {
			return "";
		}
	}
	
	/**
	 * Return value or "" if value is <code>null</code>
	 * @return The value of this
	 */
	public String getVal()
	{
		if(this.value != null && this.value instanceof String)
		{
				return (String) this.value;
		} else {
			return "";
		}
	}
	
	public ConfData getParent() 
	{
		return this.parent;
	}
	
	public ArrayList<ConfData> toConfDataArray()
	{
		return this.children;
	}
	
	public ConfData getChildAt(int index) 
	{
		return children.get(index);
	}
	
	public int childCount()
	{
		return children.size();
	}
	
	/**
	 * Return <code>true</code> the prompt is 'hidden'
	 * @return boolean <code>true</code> if hidden.
	 */
	public boolean isHidden()
	{
		return this.hiddenPrompt;
	}
	
	/**
	 * Return <code>true</code> if this value is <code>null</code>
	 * @return boolean <code>True</code> if <code>null</code>
	 */
	@Deprecated
	public boolean isNull()
	{
		return this.value == null;
	}
	
	/**
	 * Return <code>true</code> if this value is <code>null</code>
	 * @return boolean <code>True</code> if <code>null</code>
	 * @return
	 */
	public boolean isNullVal() {
		return this.value == null;
	}
	
	/**
	 * Return <code>true</code> if this description is <code>null</code>
	 * @return boolean <code>True</code> if <code>null</code>
	 * @return
	 */
	public boolean isNullDesc() {
		return this.description == null;
	}
	
	/**
	 * Set this value to the specified String
	 * @param val - Specified value
	 */
	public void setVal(String val)
	{
		this.value = val;
	}
	
	/**
	 * Set this description to the specified String 
	 * @param desc - Specified value
	 */
	public void setDesc(String desc)
	{
		this.description = desc;
	}
	
	/**
	 * Hide user input when prompting for this value 
	 * @param b - True to hide input
	 */
	public void setHidden(boolean b)
	{
		this.hiddenPrompt = b;
	}
}
