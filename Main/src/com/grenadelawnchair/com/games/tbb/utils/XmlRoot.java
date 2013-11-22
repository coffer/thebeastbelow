package com.grenadelawnchair.com.games.tbb.utils;

import com.badlogic.gdx.utils.XmlReader;

public class XmlRoot {
	
	private static XmlReader reader = null;
	
	public static XmlReader getInstance(){
		if(reader == null){
			reader = new XmlReader();
		}
		return reader;
	}
	
	


}
