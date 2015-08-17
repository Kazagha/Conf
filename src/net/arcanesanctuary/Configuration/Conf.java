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
	
	public void setChildNodes(ArrayList<Conf> vars) {
		this.childNodes = vars;
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
				return conf.get(variable);
			}
		}
		return null;
	}	
	
	public void appendChild(Conf conf) {
		this.childNodes.add(conf);
	}
	
	public void set(String variable, String desc, String val) {
		
	}
}
