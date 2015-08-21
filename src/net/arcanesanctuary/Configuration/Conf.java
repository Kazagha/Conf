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
	
	/**
	 * Set this node's variable to <code>str</code>
	 * @param str - The specified String
	 */
	public void setVar(String str) {
		this.var = str;
	}
	
	/**
	 * Set this node's value to <code>str</code>
	 * @param str
	 */
	public void setVal(String str) {
		this.val = str;
	}
	
	/**
	 * Set this node's description to <code>str</code>
	 * @param str
	 */
	public void setDesc(String str) {
		this.desc = str;
	}
	
	/**
	 * Set this node's parent to <code>conf</code> but does not change the parent's child array
	 * @param conf
	 */
	private void setParentNode(Conf conf) {
		this.parent = conf;
	}
	
	/**
	 * Set this node's child array to <code>array</code>, including setting the parent node on each of the children
	 * @param array
	 */
	public void setChildNodes(ArrayList<Conf> array) {
		this.childNodes = array;
		
		for(Conf conf : this.getChildNodes()) {
			conf.setParentNode(this);
		}
	}
		
	/**
	 * Return the node's name, the name of this variable
	 * @return
	 */
	@XmlElement(name="var")
	public String getVar() {
		return this.var;
	}
	
	/**
	 * Return this node's value
	 * @return
	 */
	@XmlElement(name = "val")
	public String getVal() {
		return this.val;
	}	
	
	/**
	 * Get this node's description
	 * @return
	 */
	@XmlElement(name = "desc")
	public String getDesc() {
		return this.desc;
	}
	
	/**
	 * Get this node's parent node
	 * @return
	 */
	@XmlTransient
	public Conf getParentNode() {
		return this.parent;
	}
	
	/**
	 * Get this node's child node ArrayList 
	 * @return ArrayList of <code>Conf</code> nodes
	 */
	@XmlElement(name = "node")
	public ArrayList<Conf> getChildNodes() {
		return this.childNodes;
	}
	
	/**
	 * Return the number of children in the child array
	 * @return
	 */
	public int getChildCount() {
		return this.childNodes.size();
	}
	
	/**
	 * Return the nodes at the specified <code>index</code> in this node's child array 
	 * @param index
	 * @return
	 */
	public Conf getChildAt(int index) {
		return this.childNodes.get(index);
	}
	
	/**
	 * Return <code>true</code> if this node has children
	 * @return
	 */
	public boolean hasChildNodes() {
		return (! this.childNodes.isEmpty());
	}
	
	/**
	 * Return the value of the specified <code>variable</code>'s node. <br>
	 * If the node's value is <code>null</code> or doesn't exist return ""
	 * @param variable
	 * @return
	 */
	public String get(String variable) {
		String value = this.getNode(variable).getVal();
		
		if(value != null) {
			return value;
		}
		
		return "";
	}
	
	/**
	 * Return the specified node, if the node doesn't exist return <code>null</code>
	 * @param variable
	 * @return
	 */
	public Conf getNode(String variable) {
		for(Conf conf : this.getChildNodes()) {
			
			if(conf.getVar().equalsIgnoreCase(variable)) {
				return conf;
			}
			
			if(conf.hasChildNodes()) {
				Conf childNode = conf.getNode(variable);
				if(childNode != null) {
					return childNode;
				}
			}
		}
		return null;
	}
	
	/**
	 * Prompt the user for all <code>null</code> values in this node's child array
	 * @param withDesc
	 */
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
		
		if(withDesc == true && conf.getDesc() != null && ! conf.getDesc().isEmpty()) {
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
			if(conf.getVal() == null || conf.getVal().isEmpty()) {
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
		Conf conf = this.getNode(variable);
		
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
		return (this.getNode(variable)) != null;
	}
	
	public void appendChild(Conf conf) {
		this.childNodes.add(conf);
		conf.setParentNode(this);
	}
	
	public void appendChildren(String[] variables) {
		for(String var : variables) {
			this.appendChild(new Conf(var, null, null));
		}
	}
	
	public Conf appendChild() {
		Conf conf = new Conf();		
		this.childNodes.add(conf);
		conf.setParentNode(this);
		return conf;
	}	
	
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.setParentNode((Conf) parent);
	}
}
