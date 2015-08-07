package net.arcanesanctuary.Configuration;

import java.io.File;

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

public class XMLController {
	
	File filename;
	Document doc;

	public XMLController(File f) {
		filename = f;
	}
	
	public void save(ConfNode cn) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		
		try {
			
			docBuilder = docFactory.newDocumentBuilder();
			transformer = tFactory.newTransformer();			
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
		
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(cn.conf().getVar());
			doc.appendChild(rootElement);
			
			this.buildDocument(rootElement, cn);
			
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(this.filename);
				
			transformer.transform(source, result);
			
		} catch (TransformerException e) {
			System.out.format("Transformer error%n %s%n%s", e.getMessage());
			e.printStackTrace();
		} catch(ParserConfigurationException e) {
			System.out.format("Parser error%n %s%n%s", e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void buildDocument(Element element, ConfNode node) {
		for(int i = 0; i < node.getChildCount(); i++) {
			ConfNode cn = ((ConfNode) node.getChildAt(i));
			ConfData cd = ((ConfData) cn.conf());
			
			Element e = doc.createElement(cd.getVar());
			e.appendChild(doc.createTextNode(cd.getVal()));			
			element.appendChild(e);
			
			if(! cd.isDescNull()) {
				Attr desc = doc.createAttribute("desc");
				desc.setValue(cd.getDesc());
				e.setAttributeNode(desc);
			}
			
			if(cn.getChildCount() > 0) {
				buildDocument(e, cn);
			}
		}
	}
	
	public void load(ConfData cd) {
		
	}
}
