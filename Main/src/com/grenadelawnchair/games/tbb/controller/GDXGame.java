package com.grenadelawnchair.games.tbb.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class GDXGame extends Game {


	@Override
	public void create() {
		Gdx.app.log("The Beast Below", "creating game");
		

	}

	@Override
	public void dispose() {
		Gdx.app.log("The Beast Below", "Destroying game");
	}

}
