package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.List; 

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "conf")
@XmlType(propOrder={ "variable", "desc", "value", "childNodes" })
public class Conf {

	private String variable;
	private String value;
	private String description;
	private ArrayList<Conf> childNodes;
	private Conf parent;
	
	public Conf() {
		this.variable = "";
		this.value = "";
		this.description = "";
		this.childNodes = new ArrayList<Conf>();
	}
	
	public Conf(String name, String desc, String val) {
		this.variable = name;
		this.value = val;
		this.description = desc;
		this.childNodes = new ArrayList<Conf>();
	}
	
	public void setVariable(String str) {
		this.variable = str;
	}
	
	public void setValue(String str) {
		this.value = str;
	}
	
	public void setDesc(String str) {
		this.description = str;
	}
	
	public void setParentNode(Conf c) {
		this.parent = c;
	}
	
	public void setChildNodes(ArrayList<Conf> vars) {
		this.childNodes = vars;
	}
	
	@XmlElement(name="var")
	public String getVariable() {
		return this.variable;
	}
	
	@XmlElement(name = "val")
	public String getValue() {
		return this.value;
	}	
	
	@XmlElement(name = "desc")
	public String getDesc() {
		return this.description;
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
	
	public boolean hasChildNodes() {
		return (! this.childNodes.isEmpty());
	}
	
	public String get(String variable) {
		for(Conf conf : this.getChildNodes()) {
			
			if(conf.getVariable().equals(variable)) {
				return conf.getValue();
			}
			
			if(conf.hasChildNodes()) {
				return get(variable);
			}
		}
		
		return null;
	}
	
	public void appendChild(Conf conf) {
		this.childNodes.add(conf);
	}
}
