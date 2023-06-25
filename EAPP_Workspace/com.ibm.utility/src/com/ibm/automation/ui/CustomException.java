package com.ibm.automation.ui;



public class CustomException extends Exception

{
	private final int codes;
public CustomException(String errorMsg,Throwable cause,int codes)
{
	super(errorMsg, cause);
	this.codes = codes;
}
}
