package com.grenadelawnchair.games.tbb.model;

import java.util.List;

public abstract class Character {

	private Weapon weapon;
	private List<AbilityInterface> activeAbilites;
	private List<AbilityInterface> passiveAbilites;
}
