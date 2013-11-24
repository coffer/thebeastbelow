package com.grenadelawnchair.com.games.tbb.utils;

public class Constants {
	
	public static final String TITLE = "The Beast Below",
			VERSION = "0.1.4";
	
	public enum Items {
		HealthPotion;
	}
	
	public enum Weapons {
		IronSword, SteelSword, Spear, Mace;
	}
	
	public enum Characters {
		Player, Creep;
	}
	
	public enum Abilities {
		Sprint;
	}
	
	public enum Effects {
		HealthPotion, Slowed;
	}

	public static final int IDLESPRITE_RIGHT = 0, IDLESPRITE_LEFT = 4, ATTACKSPRITE_RIGHT = 1,
			ATTACKSPRITE_LEFT = 5, RUNSPRITE1_RIGHT = 2, RUNSPRITE1_LEFT = 6, RUNSPRITE2_RIGHT = 3,
			RUNSPRITE2_LEFT = 7;
}
