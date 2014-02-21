import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Conf
{
	ArrayList<confData> confArray = new ArrayList<confData>();
	
	public Conf(File f)
	{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid File:" + f.getName());
		}
		Scanner scan = new Scanner(fis);
		
		scan.hasNextLine();
		scan.hasNextLine();
		
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
	
	public String getValue(String variable)
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
	
	public void setValue(String variable, String value)
	{
		for(confData cd : confArray)
		{
			if(cd.getVar().equals(variable))
			{
				cd.setVal(value);
			}
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
			return this.value;
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
		config.setValue("password", "1234");
		System.out.println(config.getValue("username") + " - " + config.getValue("password"));
	}
}