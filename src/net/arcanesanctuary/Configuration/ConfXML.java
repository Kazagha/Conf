package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.List; 

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "conf")
@XmlType(propOrder={ "name", "desc", "value", "variables" })
public class ConfXML {

	private String name;
	private String value;
	private String description;
	private ArrayList<ConfXML> variables;
	
	public ConfXML() {
		this.variables = new ArrayList<ConfXML>();
	}
	
	public ConfXML(String name, String val, String desc) {
		this.name = name;
		this.value = val;
		this.description = desc;
		this.variables = new ArrayList<ConfXML>();
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
	
	public void setVariables(ArrayList<ConfXML> vars) {
		this.variables = vars;
	}
	
	@XmlElement(name="name")
	public String getName() {
		return this.name;
	}
	
	@XmlElement(name = "test")
	public String getValue() {
		return this.value;
	}	
	
	public String getDesc() {
		return this.description;
	}
		
	public ArrayList<ConfXML> getVariables() {
		return this.variables;
	}
}
