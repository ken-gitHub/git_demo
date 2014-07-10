package com.wk.service;

import javax.jws.WebService;


@WebService(endpointInterface="com.wk.service.IMyService")
public class IMyServiceImp implements IMyService {

	public void say(String s) {
		System.out.println(s);

	}

	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		return a+b;
	}

	@Override
	public int minus(int a, int b) {
		// TODO Auto-generated method stub
		return a-b;
	}

}
