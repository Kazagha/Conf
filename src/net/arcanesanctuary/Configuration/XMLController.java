package net.arcanesanctuary.Configuration;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLController {
	
	File fileName;
	Document doc;

	public XMLController(File file) {
		this.setFileName(file);
	}
	
	public void setFileName(File file) {
		if(! fileName.exists()) {
			try {
				fileName.createNewFile();
			} catch (IOException e) {
				System.out.format("Cannot create file in the following location%n%s%n",
						fileName.toString());
				e.printStackTrace();
			}
		}
		
		this.fileName = file;
	}
	
	public void save(Conf conf) {
		
	}
	
	public Conf load() {
		return new Conf();
	}
	
}
