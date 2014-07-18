package net.arcanesanctuary.Configuration;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Conf
{
	ArrayList<confData> confArray = new ArrayList<confData>();
	Scanner scan;
	File configFileName;
	
	public Conf(File f)
	{
		configFileName = f;	
		
		try{
			//Check if the file exists
			if(f.exists())
			{
				//Load the specified File
				loadFile(f);
			} else {
				//Create the specified File (as there is nothing to load)
				f.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("Invalid File: " + f.getName());
			e.printStackTrace();
		}
	}
	
	/**
	 * Load File <br> 
	 * This method loads the specified file into <code> this</code><br>
	 * If the file doesn't exist, throw FileNotFoundException
	 * @param f - The specified File
	 * @throws FileNotFoundException
	 */
	private void loadFile(File f) throws FileNotFoundException
	{	
		FileInputStream fis = new FileInputStream(f);

		scan = new Scanner(fis);
		
		while(scan.hasNextLine())
		{
			String [] line = scan.nextLine().split("=");
			if(line.length == 1)
			{
				confArray.add(new confData(line[0]));
			} else if(line.length == 2){
				confArray.add(new confData(line[0], line[1]));
			}
		}		
		scan.close();
	}
	
	/**
	 * Returns the value that matches the first occurrence of the variable
	 * @param 	variable - String to search for
	 * @return				Value that matches the variable
	 */
	public String get(String variable)
	{
		for(confData cd : confArray)
		{
			if(cd.getVar().equals(variable))
			{
				return cd.getVal();
			} 
		}
		System.out.println("Unable to find variable: " + variable);
		return null;
	}
	
	/** 
	 * Set variable-value combination.  <br>
	 * This method will create a new variable if it doesn't exist.<br>
	 * Use <code>set("variable", "value")</code> to overwrite existing value.<br>
	 * Use <code>set("variable", null)</code> to create a blank value.<br>
	 * @param 	variable - name of variable
	 * @param 	value - new value
	 * @see 				Prompt
	 */
	public void set(String variable, String value)
	{
		int index = this.indexOf(variable);
		
		if(index != -1)
		{
			confArray.get(index).setVal(value);
		} else {
			confArray.add(new confData(variable, value));
		}		
	}
	
	/**
	 * Add an array of Variables<br>
	 * This method adds the specified variables that do not already <br> 
	 * exist in <code>this</code>. Variables are created with a <code>null</code> value
	 * @param variables - Specified array of variables
	 */
	public void add(String[] variables)
	{
		for(String var : variables)
		{
			if(! this.contains(var))
			{
				confArray.add(new confData(var));
			}
		}		
	}
	
	public void del(String variable)
	{
		int index = this.indexOf(variable);
		
		if(index != -1)
		{
			confArray.remove(index);	
		} else {
			System.out.println("Invalid Index");
		}
	}

	/**
	 * Prompt user for all NULL values 
	 */
	public void prompt()
	{
		scan = new Scanner(System.in);
	
		for(confData cd : confArray)
		{
			if(cd.isNull())
			{
				System.out.println(cd.getVar() + ": ");
				cd.setVal(scan.nextLine());
			}
		}
		scan.close();
	}
	
	/**
	 * Prompt user for the value of the specified variable<br>
	 * Create the variable if it doesn't exist
	 * @param 	variable - the specified variable
	 */
	public void prompt(String variable)
	{
		scan = new Scanner(System.in);
		int index = this.indexOf(variable);
		
		if(index != -1)
		{
			confArray.get(index).setVal(scan.nextLine());
		} else {
			confArray.add(new confData(variable, scan.nextLine()));
		}
		scan.close();
	}
	
	public void promptPassword()
	{
		Console cons = System.console();
		String username = cons.readLine("User name: ");
		char[] passwd = cons.readPassword("Password: ");
	}
	
	/**
	 * Returns <code>true</code> if this contains the specified variable
	 * @param 	variable - String to search for
	 * @return	Return <code>true</code> if this contains the specified element
	 */
	public boolean contains(String variable)
	{
		for(confData cd : confArray)
		{
			if(cd.getVar().equalsIgnoreCase(variable))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the index of the first occurrence of the specified variable.
	 * @param 	variable - String to search for
	 * @return				the index of the first occurrence of the variable
	 */
	public int indexOf(String variable)
	{
		int index = -1;
		for (int i = 0; i < confArray.size(); i++)
		{
			if(confArray.get(i).getVar().equalsIgnoreCase(variable))
			{
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * <code>null</code> the values of the specified variables
	 * @param variables in this will have their values set to <code>null</code>  
	 */
	public void nullValues(String [] variables)
	{
		for(confData cd : confArray)
		{
			for(String var : variables)
			{
				if(cd.getVar().equals(var))
				{
					cd.setVal(null);
				}
			}
		}		
	}
	
	/**
	 * Save all variables in this to file
	 */
	public void save()
	{

		String s = new String();
		
		for(confData cd : confArray)
		{
			s += String.format("%s=%s%n", cd.getVar(), cd.getVal());
		}
		
		writeToFile(s);
	}
	
	/**
	 * Save specified variables to file
	 * @param variables to save
	 */
	public void save(String[] variables)
	{
		String s = new String();
		for(confData cd : confArray)
		{
			for(String var : variables)
			{
				if(cd.getVar().equals(var))
				{
					s += String.format("%s=%s%n", cd.getVar(), cd.getVal());
				}
			}
		}
		writeToFile(s);
	}

	/**
	 * Overwrite the config file with contents of <code>confString</code>
	 * @param confString - Contents of the configuration file
	 */
	public void writeToFile(String confString)
	{
		try {
			
			FileOutputStream fos = new FileOutputStream(configFileName);
			byte[] outputBytes = confString.getBytes();
			
			fos.write(outputBytes);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			System.out.format("I/O Exception: %n %s", e.getMessage());
		}
	}
	
	public class confData
	{
		String variable;
		String value;
		
		public confData(String var, String val)
		{
			this.variable = var;
			this.value = val;
		}
		
		public confData(String var)
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
		 * Return <code>true</code> if this value is <code>null</code>
		 * @return boolean <code>True</code> if <code>null</code>
		 */
		public boolean isNull()
		{
			return this.value == null;
		}
		
		/**
		 * Set this value to the specified value
		 * @param val - Specified value
		 */
		public void setVal(String val)
		{
			this.value = val;
		}
	}

	public static void main (String[] args)
	{
		Conf config = new Conf(new File(args[0]));	
		config.prompt();
		System.out.println("Name: " + config.get("First Name") + " " + config.get("Last Name"));
		//config.promptPassword();
		config.nullValues(new String[] {"Database"});
		config.save();
	}
}