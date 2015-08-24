package net.arcanesanctuary.Configuration;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

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
					fileName.toPath().toString());
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void save(Conf conf) {
		checkFileName(fileName);
		
		try {
			marshaller.marshal(conf, fileName);
		} catch (JAXBException e) {
			System.out.format("Error saving to file: %s", fileName.toString());
			e.printStackTrace();
		}
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
