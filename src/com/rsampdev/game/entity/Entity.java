package com.rsampdev.game.entity;

public class Entity {
	
	public double x;
	public double z;
	protected boolean removed;
	
	protected Entity() {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public void tick() {
		
	}

}
