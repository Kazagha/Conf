package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.List; 

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "conf")
@XmlType(propOrder={ "name", "desc", "value", "childNodes" })
public class Conf {

	private String name;
	private String value;
	private String description;
	private ArrayList<Conf> childNodes;
	private Conf parent;
	
	public Conf() {
		this.name = "";
		this.value = "";
		this.description = "";
		this.childNodes = new ArrayList<Conf>();
	}
	
	public Conf(String name, String desc, String val) {
		this.name = name;
		this.value = val;
		this.description = desc;
		this.childNodes = new ArrayList<Conf>();
	}
	
	public void setName(String str) {
		this.name = str;
	}
	
	public void setValue(String str) {
		this.value = str;
	}
	
	public void setDesc(String str) {
		this.description = str;
	}
	
	public void setParent(Conf c) {
		this.parent = c;
	}
	
	public void setChildNodes(ArrayList<Conf> vars) {
		this.childNodes = vars;
	}
	
	@XmlElement(name="name")
	public String getName() {
		return this.name;
	}
	
	@XmlElement(name = "value")
	public String getValue() {
		return this.value;
	}	
	
	@XmlElement(name = "description")
	public String getDesc() {
		return this.description;
	}
	
	public Conf getParent() {
		return this.parent;
	}
		
	@XmlElement(name = "variable")
	public ArrayList<Conf> getChildNodes() {
		return this.childNodes;
	}
}
