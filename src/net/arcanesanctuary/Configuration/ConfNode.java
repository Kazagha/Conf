package net.arcanesanctuary.Configuration;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;

public class ConfNode extends DefaultMutableTreeNode {
	
	static Scanner scan;
	static boolean scanInstantiated = false;
	
	public ConfNode(ConfData cd) {
		this.userObject = cd;
	}
	
	public ConfNode(String var, String desc, String val) {
		this.userObject = new ConfData(var, desc, val);
	}
	
	public ConfData getConfAt(int i) {
		return (ConfData) (((ConfNode) this.getChildAt(i)).getUserObject());
	}
	
	public ConfData getConfData() {
		return (ConfData) this.userObject;
	}
	
	public String get(String variable) {
		
		for(int i = 0; i < this.getChildCount(); i++) {
			ConfNode cn = ((ConfNode) this.getChildAt(i));
			ConfData cd = ((ConfData)cn.getUserObject());
			
			if(cd.getVar().equals(variable)) {
				return cd.getVal();
			}
			
			if(cn.getChildCount() > 0) {
				String returnStr = cn.get(variable);
				if(returnStr != null) {
					return returnStr;
				}
			}
		}
		
		return null;
	}
	
	public void set(String variable, String value) {
		set(variable, null, value);
	}
	
	public void set(String variable, String desc, String value) {
		int index = this.getIndexOf(variable);
		
		if(index != -1) {	
			ConfData cd = this.getConfAt(index);
			
			if(desc != null) 
				this.getConfAt(index).setVal(desc);
						
			if(value != null) 
				this.getConfAt(index).setVal(value);
				
		} else {
			this.add(new ConfNode(new ConfData(variable, desc, value)));
		}		
	}
	
	public void del(String[] variables) {
		for(String var : variables) {		
			for(int i = 0; i < this.getChildCount(); i++) {
				ConfNode cn = ((ConfNode) this.getChildAt(i));
				ConfData cd = ((ConfData)cn.getUserObject());
				
				if(cd.getVar().equals(var)) {
					this.remove(cn);
					break;
				}
			}
		}
	}
	
	//TODO: Delete the current node; this.parent.remove(this)
	
	public void prompt() {
		this.prompt(false, new String[] { });
	}
	
	public void prompt(boolean withDesc) {
		this.prompt(withDesc, new String[]{ });
	}
	
	public void prompt(boolean withDesc, String[] variables) {
		if(! scanInstantiated) {
			scan = new Scanner(System.in);
			scanInstantiated = true;
		}
		
		
		ArrayList<ConfData> array = new ArrayList<ConfData>();
		if(variables.length > 0) {
			this.getVarArray(array, variables);
		} else {
			this.getNullArray(array);
		}
		
		promptUserFor(withDesc, array);
		
		// TODO: Close the scanner. 
		// As this also closes the System.in Stream in prevents further input
		//scan.close();		
		//scanInstantiated = false;
	}
	
	public void promptUserFor(boolean withDesc, ArrayList<ConfData> varArray) {
		for(ConfData cd : varArray) {
			if(withDesc == true && ! cd.isDescNull()) {
				System.out.format("%s: ", cd.getDesc());				
			} else {
				System.out.format("%s: ", cd.getVar());
			}
			cd.setVal(scan.nextLine());
		}
	}
	
	private void getNullArray(ArrayList<ConfData> array) {
		for(int i = 0; i < this.getChildCount(); i++) {
			ConfNode cn = ((ConfNode) this.getChildAt(i));
			ConfData cd = ((ConfData) cn.getUserObject());
						
			if(cd.isValNull()) {
				array.add(cd);			
			}
			
			if(cn.getChildCount() > 0) {
				cn.getNullArray(array);
			}
		}
	}
	
	private void getVarArray(ArrayList<ConfData> array, String[] variables) {
		for(String var : variables) {
			for(int i = 0; i < this.getChildCount(); i++) {
				ConfNode cn = ((ConfNode) this.getChildAt(i));
				ConfData cd = ((ConfData) cn.getUserObject());
							
				if(cd.getVar().equals(var)) {
					array.add(cd);	
				}
				
				if(cn.getChildCount() > 0) {
					cn.getVarArray(array, new String[]{ var });
				}
			}
		}
	}
	
	public int getIndexOf(String variable) {
		for(int i = 0; i < this.getChildCount(); i++) {
			ConfData cd = this.getConfAt(i);
			
			if(cd.getVar().equals(variable)) {
				return i;
			}
		}
		
		return -1;
	}
}
