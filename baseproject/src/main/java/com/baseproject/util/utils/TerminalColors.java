package com.baseproject.util.utils;

import org.apache.log4j.Logger;

public class TerminalColors {
	
	final static Logger logger = Logger.getLogger(TerminalColors.class);

	public static final String FINISH = "\u001b[m";

	public static final String RED = "\u001b[31m";

	public static final String GREEN = "\u001b[32m";

	public static final String YELLOW = "\u001b[33m";

	public static final String BLUE = "\u001b[34m";
	
	public static void finish() {
		changeColor(FINISH);
	}
	
	public static void red() {
		changeColor(RED);
	}
	
	public static void green() {
		changeColor(GREEN);
	}
	
	public static void yellow() {
		changeColor(YELLOW);
	}
	
	public static void blue() {
		changeColor(BLUE);
	}
	
	private static void changeColor(String color) {
		System.out.println(color);
	}
}
