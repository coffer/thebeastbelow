package com.grenadelawnchair.games.tbb;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.grenadelawnchair.games.tbb.controller.GDXGame;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		new LwjglApplication(new GDXGame(), "Game", 960, 640, true);

	}

}
