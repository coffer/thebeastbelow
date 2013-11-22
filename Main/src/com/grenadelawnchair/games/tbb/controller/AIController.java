package com.grenadelawnchair.games.tbb.controller;

import com.grenadelawnchair.games.tbb.entity.NPCEntity;

public class AIController {

	private NPCEntity npc;
	private boolean patrol;
	private float patrolDistance;
	private float originX, originY;
	
	public void update(NPCEntity npc){
		this.npc = npc;
	}
	
	public void patrol(float distance){
		patrol = true;
		patrolDistance = distance;
		
	}
	
	public void stopPatrolling(){
		patrol = false;
	}
	
	
}
