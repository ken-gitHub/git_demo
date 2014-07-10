package com.wk.test;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.xpath.XPathExpression;

import com.wk.model.User;

public class TestJaxb {

	public static void main(String[] args) throws JAXBException {
		
		
		JAXBContext jax = JAXBContext.newInstance(User.class);
		Marshaller mar = jax.createMarshaller();
		User us = new User();
		us.setId(332);
		us.setUname("ddd");
		
		mar.marshal(us, System.out);
		
		
	}
	
	@Test
	public void test01() throws JAXBException{
		
		String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><id>332</id><uname>ddd</uname></user>";
		JAXBContext jax = JAXBContext.newInstance(User.class);
		Unmarshaller mar = jax.createUnmarshaller();
		User us = new User();
		
		us  = (User)mar.unmarshal(new StringReader(xml));
		
		System.out.println(us);
		
	}
	
	
	@Test
	public void testStax() throws Exception{
		
		String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><id>332</id><uname>ddd</uname></user>";
	 
		XMLInputFactory xmlft = XMLInputFactory.newFactory();
//		String url =TestJaxb.class.getClassLoader().getResource("user.xml").getPath();
//		System.out.println(url);
//		InputStreamReader stream = new InputStreamReader(new FileInputStream(new File(url)));
		
		InputStream r = TestJaxb.class.getClassLoader().getResourceAsStream("book.xml");
		// 基于迭代的模式查找
//		XMLEventReader eventR = xmlft.createXMLEventReader(r);
		
		XMLEventReader eventR = xmlft.createFilteredReader(xmlft.createXMLEventReader(r), new EventFilter() {
			
			@Override
			public boolean accept(XMLEvent event) {
				if(event.isStartDocument()) return true;
				return false;
			}
		});
		
		
		while(eventR.hasNext()){
			XMLEvent ev = eventR.nextEvent();
			if(ev.isStartElement()){
				System.out.println(ev.asStartElement().getName());
			}else if(ev.isEndElement()){
				System.out.println("/"+ev.asEndElement().getName());
			}else if(ev.isCharacters()){
				System.out.println(ev.asCharacters());
			}
			
		}
		System.out.println("=====================================");
		
		//基于光标的
		XMLStreamReader read= xmlft.createXMLStreamReader(TestJaxb.class.getClassLoader().getResourceAsStream("book.xml"));
		while(read.hasNext()){
			int type = read.next();
			if(type==XMLStreamConstants.START_ELEMENT){
				System.out.println(read.getName());
				if(read.getAttributeCount()>0){
					System.out.println("att:"+read.getAttributeName(0)+" = "+read.getAttributeValue(0));
				}
			}else if(type == XMLStreamConstants.CHARACTERS){
				System.out.println(read.getText());
			}else if(type == XMLStreamConstants.END_ELEMENT){
				System.out.println("/"+read.getName());
			}
		}
		
	}
	
	@Test
	public void testDocumentBuilder() throws Exception{
		
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		Document doc = db.parse(TestJaxb.class.getClassLoader().getResourceAsStream("book.xml"));
		
		XPath xpath =  XPathFactory.newInstance().newXPath();
		String s = xpath.evaluate("/", doc);
		System.out.println(s);
//		XPathExpression ex = xpath.compile("//");
		
	}
	
	
}
