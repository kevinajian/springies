package springies;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jgame.platform.JGEngine;

import org.w3c.dom.Document;

public abstract class Parser {
	protected JGEngine myEngine;
	protected Document myDocument;

	protected Parser(File file, JGEngine engine){
		myEngine = engine;
		myDocument = createDocument(file);
	}
	
	protected JGEngine getEngine(){
		return myEngine;
	}
	
	public abstract void parse();
	
	private Document createDocument(File file){
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
