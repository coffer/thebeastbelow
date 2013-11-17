package com.grenadelawnchair.games.tbb.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.grenadelawnchair.games.tbb.view.GameWorld;

public class GDXGame extends Game {


	@Override
	public void create() {
		Gdx.app.log("The Beast Below", "creating game");
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameWorld());
	}

	@Override
	public void dispose() {
		Gdx.app.log("The Beast Below", "Destroying game");
	}

}
