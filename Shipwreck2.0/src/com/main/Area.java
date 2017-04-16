package com.main;

import javax.swing.JButton;

public class Area extends JButton {
	
	// Private attributes
	private static final long serialVersionUID = 1L;
	private AreaType type;
	
	// Constructor
	public  Area (String text){
		super(text);
		setType(AreaType.BLANK);}
		
	// Class methods
	public void setType(AreaType type){
		this.type = type;}
	public AreaType getType(){
		return this.type;}

}
