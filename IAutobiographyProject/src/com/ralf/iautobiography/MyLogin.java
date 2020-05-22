package com.ralf.iautobiography;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class MyLogin {
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response) {
		return "hello";
	}
	
	public static void main(String[] args) {
	}
}
