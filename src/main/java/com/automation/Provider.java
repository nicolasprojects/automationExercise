package com.automation;

import org.testng.annotations.DataProvider;


public class Provider {
	
	static String WORKING_PATH = "/home/nicolas/Downloads/";
	
    @DataProvider(name = "test02")
    public static Object[][] test02() {
    	return new Object[][] {{
    		  WORKING_PATH + "320x320.jpg"
  		    , "320x320.jpg"
    		, "test02"
    	}};
    }
    
    @DataProvider(name = "test03")
    public static Object[][] test03() {
    	return new Object[][] {{
    		  WORKING_PATH + "400x400.jpg"
    		, "test03"
    	}};
    }

    @DataProvider(name = "test04")
    public static Object[][] test04() {
    	return new Object[][] {{
    		  WORKING_PATH + "320x320.jpg"
    	}};
    }
    
    @DataProvider(name = "test05")
    public static Object[][] test05() {
    	return new Object[][] {{
    		  "test05"
    	}};
    }
    
    @DataProvider(name = "test06")
    public static Object[][] test06() {
    	return new Object[][] {{
    		  WORKING_PATH + "320x320.jpg"
    		, "this will be deleted"
    	}};
    }
    
    

    @DataProvider(name = "bug01")
    public static Object[][] bug01() {
    	return new Object[][] {{
    		  WORKING_PATH + "200x200.jpg"
  		    , "200x200.jpg"
    		, "lalala"
    		, WORKING_PATH + "320x320.jpg"
		    , "320x320.jpg"
		    , "lololo"
    	}};
    }
}
