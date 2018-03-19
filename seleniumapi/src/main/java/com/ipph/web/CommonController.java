package com.ipph.web;

import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CommonController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody String greetingExceptionHandler(Exception ex) {
		
		System.out.println("@getClass:" + this.getClass().getSimpleName() + "---Exception:" + ex.getMessage());
		
		return ex.getMessage();
	}
	@ExceptionHandler(UndeclaredThrowableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody String greetingExceptionHandler(UndeclaredThrowableException ex) {
		
		System.out.println("@getClass:" + this.getClass().getSimpleName() + "---UndeclaredThrowableException:" + ex.getUndeclaredThrowable().getMessage());
		
		return ex.getUndeclaredThrowable().getMessage();
	}


}