package com.xeous.rpg;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameChar {
	private int x;
	private int y;
	private int attack;
	private int effectiveAttack;
	private int health;
	private int currHealth;
	private int level;
	private int dexterity;
	private int effectiveDexterity;
	private int spellpower;
	private int effectiveSpellpower;
	private int armor;
	private int effectiveArmor;
	private ArrayList<Item> equippedItems;
	private ArrayList<Item> items;
	
	public GameChar(){
		x = 0;
		y = 0;
		items = new ArrayList<Item>();
		equippedItems = new ArrayList<Item>();
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public void equipItem(Item item){
		equippedItems.add(item);
	}
	
	public void setHealth(int n){
		currHealth = currHealth -n;
	}
}
