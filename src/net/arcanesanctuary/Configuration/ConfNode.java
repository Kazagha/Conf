package net.arcanesanctuary.Configuration;

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
		if(! scanInstantiated) {
			scan = new Scanner(System.in);
			scanInstantiated = true;
		}
		
		for(int i = 0; i < this.getChildCount(); i++) {
			ConfNode cn = ((ConfNode) this.getChildAt(i));
			ConfData cd = ((ConfData) cn.getUserObject());
			
			if(cd.isNullVal()) {
				System.out.format("%s: ", cd.getVar());
				cd.setVal(scan.nextLine());				
			}
			
			if(cn.getChildCount() > 0) {
				cn.prompt();
			}
		}		
		
		//scan.close();
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
