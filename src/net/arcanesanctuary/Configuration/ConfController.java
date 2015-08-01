package net.arcanesanctuary.Configuration;

import java.io.File;
import java.util.Scanner;

public class ConfController {
	ConfNode rootNode;
	Scanner scan;
	File configFile;
	
	public ConfController(File f) {
		//TODO: Handle loading the specified file
		
		configFile = f;
		loadFile(f);
	}
	
	public void loadFile(File f) {
		Util u = new Util();
		rootNode = u.getConfNodes();
	}
}
