package com.jasondavidpeters.village.world;

import com.jasondavidpeters.village.entity.Player;

public class Shop {

	public void Shop() {

	}

	public void sell(Player p, int[] items) {
		int amountEarned = 0;
		for (int i : items) {
			amountEarned += getPrice(Ore.getID(i));
		}
		for (int j = 0; j < items.length; j++) items[j]=-1; // clear inventory
		p.setInventory(items);
		p.setMoney(p.getMoney() + amountEarned);
		p.save(p);
		System.out.println("You have made, " + amountEarned);
	}

	public void sell(int item) {

	}

	public int getPrice(Ore ore) {
		switch (ore) {
		case TIN:
			return 5;
		case COPPER:
			return 10;
		case IRON:
			return 15;
		case COAL:
			return 20;
		case MITHRIL:
			return 25;
		case ADAMANT:
			return 30;
		case RUNITE:
			return 35;
		default:
			return 0;
		}
	}

}
