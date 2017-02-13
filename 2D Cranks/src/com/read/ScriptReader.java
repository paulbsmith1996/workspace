package com.read;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.Vector;

import com.apprun.Connector;
import com.apprun.Crank;
import com.apprun.LComponent;

public class ScriptReader {
    
    private String fileName;
    private Vector<LComponent> components;
    
    public ScriptReader(String fileName) {
	    this.fileName = fileName;
	    components = new Vector<LComponent>();
	}
    
    public Vector<LComponent> getComponents() {
    	Connector c = new Connector((Crank)components.elementAt(components.size() - 1), Color.BLACK);
    	c.setFollowed(true);
    	components.add(c);
    	return this.components;
    }
    
    public void scan() throws URISyntaxException {
	    try {
	    	
	    	URL url = getClass().getResource(fileName);    	
		    File f = new File(url.toURI());   
			Scanner fileScan = new Scanner(f);
			
			String curLine = "";
			int i = -1;
			
			while(fileScan.hasNextLine()) {
			    curLine = fileScan.nextLine();
				
				int nameIndex = 0;
				int lengthIndex = curLine.indexOf("l:") + 3;
				int speedIndex = curLine.indexOf("s: ") + 3;
				int angleIndex = curLine.indexOf("t: ") + 3;
				int colorIndex = curLine.indexOf("c: ") + 3;
				int followFlag = curLine.indexOf("f");
				
				String name = curLine.substring(nameIndex, curLine.indexOf(" "));
				//System.out.println(name);
				
				int length = Integer.parseInt(curLine.substring(lengthIndex, curLine.indexOf(" ", lengthIndex)));
				//System.out.println("l: " + length);
				
				int speed = Integer.parseInt(curLine.substring(speedIndex, curLine.indexOf(" ", speedIndex)));
				//System.out.println("s: " + speed);
				
				String aStr = curLine.substring(angleIndex, curLine.indexOf(" ", angleIndex));
				
				double angle = Double.parseDouble(aStr) * Math.PI;
				//System.out.println("angle: " + angle);
				
				int colorEnd = curLine.indexOf(" ", colorIndex);
				
				if(colorEnd == -1) {
					colorEnd = curLine.length() - 1;
				}
				
				String colorStr = curLine.substring(colorIndex, colorEnd);
				Color color = getColor(colorStr);
				
				Crank crank = null;
				
				if(components.isEmpty()) {
					crank = new Crank(length, angle, speed, color); 
				} else {
					crank = new Crank((Crank)components.elementAt(i), length, angle, speed, color);
				}
				
				if(followFlag != -1) {
					crank.setFollowed(true);
				}
				
				components.add(crank);
				
				i++;
				
			}
			
			fileScan.close();
			
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    
	}
    
    public Color getColor(String colStr) {
    	
    	if(colStr.toLowerCase().equals("blue")) {
    		return Color.BLUE;
    	} else if(colStr.toLowerCase().equals("cyan")) {
    		return Color.CYAN;
    	} else if(colStr.toLowerCase().equals("dark gray")) {
    		return Color.DARK_GRAY;
    	} else if(colStr.toLowerCase().equals("gray")) {
    		return Color.GRAY;
    	} else if(colStr.toLowerCase().equals("green")) {
    		return Color.GREEN;
    	} else if(colStr.toLowerCase().equals("light gray")) {
    		return Color.LIGHT_GRAY;
    	} else if(colStr.toLowerCase().equals("magenta")) {
    		return Color.MAGENTA;
    	} else if(colStr.toLowerCase().equals("orange")) {
    		return Color.ORANGE;
    	} else if(colStr.toLowerCase().equals("pink")) {
    		return Color.PINK;
    	} else if(colStr.toLowerCase().equals("red")) {
    		return Color.RED;
    	} else if(colStr.toLowerCase().equals("white")) {
    		return Color.WHITE;
    	} else if(colStr.toLowerCase().equals("yellow")) {
    		return Color.YELLOW;
    	}
    	
    	return Color.BLACK;
    }
    
    public static void main(String[] args) throws URISyntaxException {
    	ScriptReader systemScan = new ScriptReader(args[0]);	
	
		systemScan.scan();
    }
}
