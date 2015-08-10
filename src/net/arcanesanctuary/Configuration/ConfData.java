package net.arcanesanctuary.Configuration;

public class ConfData
{
	String variable;
	String value;
	String description;
	boolean hiddenPrompt;
	
	public ConfData(String var, String desc, String val) 
	{
		// TODO: Regex replace invalid characters: _ & < > 
		// TOOD: Camel case variable name with multiple words 
		this.variable = var.replace(" ", "");
		this.description = desc;
		this.value = val;
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
		if(this.value != null)
		{
			return this.value;
		} else {
			return "";
		}
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
	public boolean isValNull() {
		return this.value == null;
	}
	
	/**
	 * Return <code>true</code> if this description is <code>null</code>
	 * @return boolean <code>True</code> if <code>null</code>
	 * @return
	 */
	public boolean isDescNull() {
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
