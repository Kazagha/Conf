package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.List; 

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlType(propOrder={ "name", "desc", "value", "childNodes" })
public class ConfXML {

	private String name;
	private String value;
	private String description;
	private ArrayList<ConfXML> childNodes;
	
	public ConfXML() {
		this.childNodes = new ArrayList<ConfXML>();
	}
	
	public ConfXML(String name, String val, String desc) {
		this.name = name;
		this.value = val;
		this.description = desc;
		this.childNodes = new ArrayList<ConfXML>();
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
	
	public void setChildNodes(ArrayList<ConfXML> nodes) {
		this.childNodes = nodes;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.value;
	}	
	
	public String getDesc() {
		return this.description;
	}
		
	public ArrayList<ConfXML> getChildNodes() {
		return this.childNodes;
	}
}
