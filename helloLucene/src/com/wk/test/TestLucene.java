package com.wk.test;

import org.junit.Test;

public class TestLucene {

	@Test
	public void testIndex(){
		HelloLucene h = new HelloLucene();
		h.index();
	}
	@Test
	public void testSearch(){
		HelloLucene h = new HelloLucene();
		h.Search("QueryParser ");
	}
}
