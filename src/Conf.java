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
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid File:" + f.getName());
		}
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
	
	public String get(String variable)
	{
		for(confData cd : confArray)
		{
			if(cd.getVar().equals(variable))
			{
				return cd.getVal();
			}
		}
		return null;
	}
	
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
	
	public void save()
	{

		String s = new String();
		
		for(confData cd : confArray)
		{
			s += String.format("%s=%s%n", cd.getVar(), cd.getVal());
		}
		
		saveFile(s);
	}
	
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
		saveFile(s);
	}
	
	public void saveFile(String confString)
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
		
		//Getter Methods
		public String getVar()
		{
			return this.variable;
		}
		
		public String getVal()
		{
			if(this.value != null)
			{
				return this.value;
			} else {
				return "";
			}
		}
		
		public boolean isNull()
		{
			return this.value == null;
		}
		
		//Setter Methods
		public void setVal(String val)
		{
			this.value = val;
		}
	}

	public static void main (String[] args)
	{
		Conf config = new Conf(new File("src\\example.conf"));
		config.prompt();
		System.out.println("Name: " + config.get("First Name") + " " + config.get("Last Name"));
		config.nullValues(new String[] {"Database"});
		config.save();
	}
}