package net.arcanesanctuary.Configuration;

import javax.swing.tree.DefaultMutableTreeNode;

public class ConfNode extends DefaultMutableTreeNode {
	
	public ConfNode(ConfData cd) {
		this.userObject = cd;
	}
	
	public ConfNode(String var, String desc, String val) {
		this.userObject = new ConfData(var, desc, val);
	}
}
