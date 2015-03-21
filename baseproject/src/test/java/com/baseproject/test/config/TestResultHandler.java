package com.baseproject.test.config;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.baseproject.util.utils.DatabaseUtils;
import com.baseproject.util.utils.TerminalColors;

public class TestResultHandler extends TestWatcher {
	
    @Override
    protected void starting(Description description) {
		printTestBegin(description.getMethodName());
		DatabaseUtils.cleanAllTables();
    }
	
    @Override
    protected void succeeded(Description description) {
    	printTestSuccess(description.getMethodName());
    }
	
    @Override
    protected void failed(Throwable e, Description description) {
    	printTestError(description.getMethodName());
    }
	
	private void printTestSuccess(String testName) {
		TerminalColors.green();
		printTestStatus(testName, "SUCCESS");
		TerminalColors.finish();
	}
	
	private void printTestError(String testName) {
		TerminalColors.red();
		printTestStatus(testName, "ERROR");
		TerminalColors.finish();
	}
	
	private void printTestBegin(String testName) {		
		TerminalColors.yellow();
		printTestStatus(testName, "STARTED");
		TerminalColors.finish();
	}
	
	private void printTestStatus(String testName, String status) {
		System.out.println("     ~>   " + testName + spaces(80 - testName.length()) + "~>  " + status);
	}
	
	private String spaces(int count) {
		String spaces = "";
		for (int i = 0; i < count; i++) {
			spaces += " ";
		}
		
		return spaces;
	}
}
