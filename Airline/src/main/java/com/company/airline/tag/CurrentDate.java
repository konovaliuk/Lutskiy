package com.company.airline.tag;

import java.sql.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class CurrentDate extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private String var;
	
	public void setVar(String var){
		this.var = var;
	}
	@Override
	public int doStartTag() throws JspException {
		pageContext.setAttribute(var, new Date(System.currentTimeMillis()));
		return SKIP_BODY;
	}
}
