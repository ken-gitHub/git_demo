package com.wk.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IMyService {

	public void say(@WebParam(name="Msg")String s);
	@WebResult(name="addReult")
	public int add(@WebParam(name="a")int a,@WebParam(name="b")int b);
	public int minus(int a,int b);
}
