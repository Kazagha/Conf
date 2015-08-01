package net.arcanesanctuary.Configuration;

import javax.swing.tree.DefaultMutableTreeNode;

public class ConfNode extends DefaultMutableTreeNode {
	
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
		for(int i = 0; i < this.getChildCount() - 1; i++) {
			ConfData cd = this.getConfAt(i);
			
			if(cd.getVar().equals(variable)) {
				return cd.getVal();
			}
		}
		
		return null;
	}
	
	public int getIndexOf(String variable) {
		for(int i = 0; i < this.getChildCount() - 1; i++) {
			ConfData cd = this.getConfAt(i);
			
			if(cd.getVar().equals(variable)) {
				return i;
			}
		}
		
		return -1;
	}
}
