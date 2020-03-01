package edu.njau.chatserver.model;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseXml {
	public String pasrse(){
		StringBuffer stringBuffer = new StringBuffer();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(this.getClass().getResourceAsStream("/msg.xml"));
			Element root = document.getDocumentElement();
			NodeList messageNodeList = root.getElementsByTagName("message");
			for(int i=0;i<messageNodeList.getLength();i++){
				Element messageElement = (Element)messageNodeList.item(i);
				stringBuffer.append(messageElement.getTextContent()+"  ");
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
}
