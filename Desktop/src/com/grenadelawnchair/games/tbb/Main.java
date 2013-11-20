package com.grenadelawnchair.games.tbb;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.grenadelawnchair.com.games.tbb.utils.Constants;
import com.grenadelawnchair.games.tbb.controller.GDXGame;

public class Main {
	
	private static int width = 1280;
	private static int height = 720;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Constants.TITLE + " version: " + Constants.VERSION;
		config.vSyncEnabled = true; //TODO this does not work. (See Ep12-18min)
		config.useGL20 = true;
		config.width = width;
		config.height = height;
		
		new LwjglApplication(new GDXGame(), config);

	}

}
