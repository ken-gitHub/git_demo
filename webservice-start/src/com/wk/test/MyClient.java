package com.wk.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.wk.service.IMyService;

public class MyClient {
	
	public static void main(String[] args) throws Exception {
		String ws="http://localhost:7777/ns?WSDL";
		URL url = new URL(ws);
		QName qn = new QName("http://imp.service.wk.com/","IMyServiceImpService");
		Service s = Service.create(url,qn);
		IMyService mys =  s.getPort(IMyService.class);
		mys.say("aa");
		
	}

}
