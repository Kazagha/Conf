package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "conf")
@XmlType(propOrder={ "var", "desc", "val", "mask", "childNodes" })
public class Conf {

	private String var;
	private String val;
	private String desc;
	private Boolean mask;
	private ArrayList<Conf> childNodes;
	private Conf parent;
	
	private static Scanner scan;
	private boolean scanInstantiated = false;
	
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
	
	public Conf(Conf conf) {
		this.var = conf.getVar();
		this.val = conf.getVal();
		this.desc = conf.getDesc();
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
	 * Mask this node's value during input
	 * @param isMasked
	 */
	public void setMask(Boolean isMasked) {
		this.mask = isMasked;
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
	 * Return <code>true</code> if this node's value is masked, otherwise return <code>null</code><br>
	 * A node without masking (<code>false</code>) has no significance therefore <code>null</code> is returned instead
	 * @return Boolean
	 * @see isMarked
	 */
	public Boolean getMask() {
		if(this.mask != null && this.mask == true){
			return true;
		} 
		
		return null;
	}
	
	/**
	 * Return <code>true</code> if this node's value is masked, otherwise return <code>false</code>
	 * @return
	 * @see getMask
	 */
	public boolean isMasked() {
		if(this.mask != null) {
			return this.mask;
		}
		
		return false;
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
	 * Get the root node by finding the parent of this node and repeating 
	 * @return Conf - The root node
	 */
	public Conf getRootNode() {
		Conf conf = this;
		Conf next = this.getParentNode();
		
		while(next != null) {
			conf = next;
			next = next.getParentNode();
		}		
		
		return conf;
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
	 * Prompt the user for all <code>null</code> values in this node's child array.
	 * If <code>withDesc</code> is true prompt with the node's description instead of the name
	 * @param withDesc
	 */
	public void prompt(boolean withDesc) {
		if(! scanInstantiated) {
			scan = new Scanner(System.in);
			scanInstantiated = true;
		}
		
		ArrayList<Conf> array = new ArrayList<Conf>();		
		this.getNulls(array);
		
		for(Conf conf : array) {
			this.prompt(scan, withDesc, conf);
		}
		
		//TODO: Close the Scanner without also closing System.in as this will prevent further input 
	}
	
	/**
	 * Prompt the user for the specified <code>variables</code>, if <code>withDesc</code> is true
	 * prompt with the node's description instead of the name
	 * 
	 * @param withDesc
	 * @param variables
	 */
	public void prompt(boolean withDesc, String[] variables) {
		if(! scanInstantiated) {
			scan = new Scanner(System.in);
			scanInstantiated = true;
		}
		
		ArrayList<Conf> array = new ArrayList<Conf>();		
		this.getChildNodes(array, variables);
		
		for(Conf conf : array) {
			this.prompt(scan, withDesc, conf);
		}	
		
		//TODO: Close the Scanner without also closing System.in as this will prevent further input 
	}

	/**
	 * Prompt the user to enter the vaule for <code>conf</code> using the <code>scan</code> object
	 * @param scan - The <code>Scanner</code> object
	 * @param withDesc - <code>true</code> to prompt with description
	 * @param conf - The node that will be set
	 */
	private void prompt(Scanner scan, boolean withDesc, Conf conf) {
		
		if(withDesc == true && conf.getDesc() != null && ! conf.getDesc().isEmpty()) {
			System.out.format("%s: ", conf.getDesc());
		} else {
			System.out.format("%s: ", conf.getVar());
		}
		
		conf.setVal(scan.nextLine());	
	}
	
	public void promptJOptionPane(String title, String[] variables) {
		
		// Fetch all the specified variables
		ArrayList<Conf> array = new ArrayList<Conf>();
		this.getChildNodes(array, variables);
				
		// Create a list of labels and JTextField/JPasswordField boxes
		ArrayList<Object> menu = new ArrayList<Object>();
		for(Conf conf : array) {
			menu.add(conf.getVar());
						
			if(conf.isMasked()) {
				menu.add(new JPasswordField(conf.getVal()));
			} else {
				menu.add(new JTextField(conf.getVal()));
			}
		}
		
		// Prompt the user for input
		JOptionPane.showConfirmDialog(null, menu.toArray(), title, JOptionPane.OK_CANCEL_OPTION);
		
		// Transfer the input back into the Conf objects
		int count = 0;
		for(Object obj : menu) {
			if(obj instanceof JTextField) {				
				array.get(0).setVal(((JTextField) obj).getText());
				count++;
			}
		}
	}
	
	/**
	 * Add nodes to <code>array</code> that match the names in <code>variables</code>
	 * @param array
	 * @param variables
	 */
	public void getChildNodes(ArrayList<Conf> array, String[] variables) {
		for(String var : variables) {
			this.getChildNodes(array, var);
		}
	}
	
	/**
	 * Add nodes to <code>array</code> where the node name equals <code>variable</code> 
	 * @param array
	 * @param variable
	 */
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
	
	/**
	 * Return all the nodes with <code>null</code> values
	 * @param array
	 */
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
	
	/**
	 * Set the value of the specified <code>variables</code> to <code>null</code> 
	 * @param variables
	 */
	public void setNullValues(String[] variables) {
		ArrayList<Conf> array = new ArrayList<Conf>();
		this.getChildNodes(array, variables);
		
		for(Conf conf : array) {
			conf.setVal(null);
		}
	}
	
	/**
	 * Remove the specified <code>variable</code> node
	 * @param variable
	 */
	public void removeChild(String variable) {
		Conf conf = this.getNode(variable);
		
		if(conf != null) {
			conf.getParentNode().getChildNodes().remove(conf);
		}
	}
	
	/**
	 * Remove all nodes listed in <code>variables</code> 
	 * @param variables
	 */
	public void removeChildren(String[] variables) {
		ArrayList<Conf> array = new ArrayList<Conf>();
		this.getChildNodes(array, variables);
		
		for(Conf conf : array) {
			conf.getParentNode().getChildNodes().remove(conf);
		}
	}
	
	/**
	 * Return <code>true</code> if this node's children contains a <code>variable</code> node
	 * @param variable
	 * @return
	 */
	public boolean contains(String variable) {
		return (this.getNode(variable)) != null;
	}
	
	/**
	 * Append child nodes for each <code>String</code> in <code>variables</code> to this node 
	 * @param variables
	 */
	public void appendChildren(String[] variables) {
		for(String var : variables) {
			this.appendChild(new Conf(var, null, null));
		}
	}
	
	/**
	 * Append <code>conf</code> to this node's children array
	 * @param conf
	 */
	public void appendChild(Conf conf) {
		this.childNodes.add(conf);
		conf.setParentNode(this);
	}
	
	/**
	 * Append a new child to this node's children array
	 * @return
	 */
	public Conf appendChild() {
		Conf conf = new Conf();		
		this.childNodes.add(conf);
		conf.setParentNode(this);
		return conf;
	}	
	
	/**
	 * Set the <code>parent</code> on this node, once it has been loaded from file
	 * @param unmarshaller
	 * @param parent
	 */
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		this.setParentNode((Conf) parent);
	}
}
