package net.arcanesanctuary.Configuration;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
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

public class XMLController {
	
	File fileName;
	
	private JAXBContext jaxbContext; 
	private Unmarshaller unmarshaller = null;
	private Marshaller marshaller = null;

	public XMLController(File file) {
		this.fileName = file;
		this.setUp();
	}
	
	private void setUp() {
		try {
			jaxbContext = JAXBContext.newInstance(Conf.class);		
			unmarshaller = jaxbContext.createUnmarshaller();
			marshaller = jaxbContext.createMarshaller();
		} catch (JAXBException e) {
			System.out.format("Error configuring JAXB / Marshaller%n");
			e.printStackTrace();
		}
	}
	
	public boolean checkFileName(File file) {
		if(fileName.exists()) {
			return true;
		}
		
		try {
			fileName.createNewFile();
		} catch (IOException e) {
			System.out.format("Cannot create file in the following location%n%s%n",
					fileName.toString());
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void save(Conf conf) {
		
	}
	
	public Conf load() {
		if(checkFileName(fileName)) {
			try {
				return (Conf) unmarshaller.unmarshal(fileName);
			} catch (JAXBException e) {
				System.out.format("Error loading file: %s", fileName.toString());
				e.printStackTrace();
			}
		}
		
		return new Conf("root", null, null);
	}	
}
