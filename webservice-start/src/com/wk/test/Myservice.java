package com.wk.test;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Endpoint;

import org.junit.Test;

import com.wk.service.IMyServiceImp;

public class Myservice {

	public static void main(String[] args) {
		
		String address="http://localhost:7878/ns";
		Endpoint.publish(address, new IMyServiceImp());
	}
	
	@Test
	public void testPublishSoap() throws Exception{
		
		MessageFactory mf = MessageFactory.newInstance();
		SOAPMessage message = mf.createMessage();
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();
		
		QName qname = new QName("http://localhost:7878/ns","add","ns");
		
		body.addBodyElement(qname).setValue("<a>bbb</a>");
		 
		SOAPBodyElement element = body.addBodyElement(qname);
		element.addChildElement("b").setValue("adfd");
		
		
		message.writeTo(System.out);
		
		
	}
	private String wsdlUrl = "http://localhost:7878/ws?wsdl";
	private String ns = "http://localhost:7878/ws?wsdl";
	@Test
	public void testPublishSoap2() throws Exception{
		
		 String wsdlString = "";
		
		
	}
}
