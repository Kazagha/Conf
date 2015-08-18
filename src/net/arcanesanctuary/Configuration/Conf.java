package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "conf")
@XmlType(propOrder={ "var", "desc", "val", "childNodes" })
public class Conf {

	private String var;
	private String val;
	private String desc;
	private ArrayList<Conf> childNodes;
	private Conf parent;
	
	public Conf() {
		this.var = "";
		this.val = "";
		this.desc = "";
		this.childNodes = new ArrayList<Conf>();
	}
	
	public Conf(String name, String desc, String val) {
		this.var = name;
		this.val = val;
		this.desc = desc;
		this.childNodes = new ArrayList<Conf>();
	}
	
	public void set(String variable, String description, String value) {
		if(variable != null) {
			this.setVar(variable);
		}
		
		if(value != null) {
			this.setVal(value);
		}
		
		if(description != null) {
			this.setDesc(description);
		}		
	}
	
	public void setVar(String str) {
		this.var = str;
	}
	
	public void setVal(String str) {
		this.val = str;
	}
	
	public void setDesc(String str) {
		this.desc = str;
	}
	
	public void setParentNode(Conf c) {
		this.parent = c;
	}
	
	public void setChildNodes(ArrayList<Conf> array) {
		this.childNodes = array;
		
		for(Conf conf : this.getChildNodes()) {
			conf.setParentNode(this);
		}
	}
	
	@XmlElement(name="var")
	public String getVar() {
		return this.var;
	}
	
	@XmlElement(name = "val")
	public String getVal() {
		return this.val;
	}	
	
	@XmlElement(name = "desc")
	public String getDesc() {
		return this.desc;
	}
	
	@XmlTransient
	public Conf getParentNode() {
		return this.parent;
	}
		
	@XmlElement(name = "node")
	public ArrayList<Conf> getChildNodes() {
		return this.childNodes;
	}
	
	public int getChildCound() {
		return this.childNodes.size();
	}
	
	public Conf getChildAt(int i) {
		return this.childNodes.get(i);
	}
	
	public boolean hasChildNodes() {
		return (! this.childNodes.isEmpty());
	}
	
	public Conf get(String variable) {
		for(Conf conf : this.getChildNodes()) {
			
			if(conf.getVar().equalsIgnoreCase(variable)) {
				return conf;
			}
			
			if(conf.hasChildNodes()) {
				Conf childNode = conf.get(variable);
				if(childNode != null) {
					return childNode;
				}
			}
		}
		return null;
	}
	
	public void prompt(boolean withDesc, String[] variables) {
		Scanner scan = new Scanner(System.in);
		
		for(String var : variables) {
			this.prompt(scan, withDesc, var);
		}		
	}

	private void prompt(Scanner scan, boolean withDesc, String variable) {
		Conf conf = this.get(variable);	

		if(conf == null) 
			return;
		
		if(withDesc == true && !conf.getDesc().isEmpty()) {
			System.out.format("%s: ", conf.getDesc());
		} else {
			System.out.format("%s:", conf.getVar());
		}
		
		conf.setVal(scan.nextLine());	
	}
	
	public void getAll(ArrayList<Conf> array, String[] variables) {
		for(String var : variables) {
			this.getAll(array, var);
		}
	}
	
	private void getAll(ArrayList<Conf> array, String variable) {
		for(Conf conf : this.getChildNodes()) {
			if(conf.getVar().equalsIgnoreCase(variable)) {
				array.add(conf);
			}
			
			if(conf.hasChildNodes()) {
				conf.getAll(array, variable);
			}
		}
	}
	
	private void getAllNulls(ArrayList<Conf> array) {
		for(Conf conf : this.getChildNodes()) {
			if(conf.getVar() == null) {
				array.add(conf);
			}
			
			if(conf.hasChildNodes()) {
				conf.getAllNulls(array);
			}
		}
	}
	
	public void del(String variable) {
		Conf conf = this.get(variable);
		
		if(conf != null) {
			conf.getParentNode().getChildNodes().remove(conf);
		}
	}
	
	public boolean contains(String variable) {
		return (this.get(variable)) != null;
	}
	
	public void appendChild(Conf conf) {
		this.childNodes.add(conf);
		conf.setParentNode(this);
	}
	
	public Conf newChild() {
		Conf conf = new Conf();		
		this.childNodes.add(conf);
		conf.setParentNode(this);
		return conf;
	}	
}
