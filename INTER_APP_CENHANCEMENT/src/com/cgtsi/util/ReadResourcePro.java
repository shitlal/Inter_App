package com.cgtsi.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties; 
public class ReadResourcePro {
	
	
	public static Properties getProperties(String fileName) throws IOException {
        System.out.println("Reading from properties file");
        System.out.println("------- fileName ----------------------"+fileName);      
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis); 
        } catch (FileNotFoundException e) {
 
            e.printStackTrace();
        } catch (IOException e) {
 
            e.printStackTrace();
        } finally {
            fis.close();
        } 
        return prop; 
    }
 }
