package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;

import javax.xml.bind.Unmarshaller;
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
		this.var = null;
		this.val = null;
		this.desc = null;
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
	
	private void setParentNode(Conf conf) {
		this.parent = conf;
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
	
	public int getChildCount() {
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
	
	public void prompt(boolean withDesc) {
		Scanner scan = new Scanner(System.in);
		
		ArrayList<Conf> array = new ArrayList<Conf>();		
		this.getNulls(array);
		
		for(Conf conf : array) {
			this.prompt(scan, withDesc, conf);
		}
	}
	
	public void prompt(boolean withDesc, String[] variables) {
		Scanner scan = new Scanner(System.in);
		
		ArrayList<Conf> array = new ArrayList<Conf>();		
		this.getChildNodes(array, variables);
		
		for(Conf conf : array) {
			this.prompt(scan, withDesc, conf);
		}	
		
		//TODO: Close the Scanner without also closing System.in as this will prevent further input 
	}

	private void prompt(Scanner scan, boolean withDesc, Conf conf) {
		
		if(withDesc == true && !conf.getDesc().isEmpty()) {
			System.out.format("%s: ", conf.getDesc());
		} else {
			System.out.format("%s: ", conf.getVar());
		}
		
		conf.setVal(scan.nextLine());	
	}
	
	public void getChildNodes(ArrayList<Conf> array, String[] variables) {
		for(String var : variables) {
			this.getChildNodes(array, var);
		}
	}
	
	private void getChildNodes(ArrayList<Conf> array, String variable) {
		for(Conf conf : this.getChildNodes()) {
			if(conf.getVar().equalsIgnoreCase(variable)) {
				array.add(conf);
			}
			
			if(conf.hasChildNodes()) {
				conf.getChildNodes(array, variable);
			}
		}
	}
	
	private void getNulls(ArrayList<Conf> array) {
		for(Conf conf : this.getChildNodes()) {
			if(conf.getVar() == null || conf.getVal().isEmpty()) {
				array.add(conf);
			}
			
			if(conf.hasChildNodes()) {
				conf.getNulls(array);
			}
		}
	}
	
	public void setNulls(String[] variables) {
		ArrayList<Conf> array = new ArrayList<Conf>();
		this.getChildNodes(array, variables);
		
		for(Conf conf : array) {
			conf.setVal(null);
		}
	}
	
	public void removeChild(String variable) {
		Conf conf = this.get(variable);
		
		if(conf != null) {
			conf.getParentNode().getChildNodes().remove(conf);
		}
	}
	
	public void removeAllChildren(String[] variables) {
		ArrayList<Conf> array = new ArrayList<Conf>();
		this.getChildNodes(array, variables);
		
		for(Conf conf : array) {
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
	
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.setParentNode((Conf) parent);
	}
}
