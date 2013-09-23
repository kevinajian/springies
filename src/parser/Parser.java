package parser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jgame.platform.JGEngine;

import org.w3c.dom.Document;

/**
 * Abstract Parser class
 * Contains the JGEngine that the simulation is run in and the file that is being parsed
 * @author Kevin
 *
 */
public abstract class Parser {
	protected JGEngine myEngine;
	protected Document myDocument;

	/**
	 * Sets up parser
	 * @param file - File to be parsed
	 * @param engine - JGEngine being used
	 */
	protected Parser(File file, JGEngine engine){
		myEngine = engine;
		myDocument = createDocument(file);
	}
	
	/**
	 * 
	 * @return JGEngine that the simulation is in
	 */
	protected JGEngine getEngine(){
		return myEngine;
	}
	
	/**
	 * parses the document
	 */
	public abstract void parse();
	
	/**
	 * Uses DocumentBuilder to create a document
	 * @param file - File to be parsed
	 * @return returns the document that was parsed from the file
	 */
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
