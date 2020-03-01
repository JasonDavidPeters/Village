package com.jasondavidpeters.village.world;

import java.util.Random;

import com.jasondavidpeters.village.entity.Player;

public class Mine {

	enum Ores {
		TIN, COPPER, IRON, COAL, MITHRIL, ADAMANT, RUNITE;
	}

	public Mine() {

	}

	public double getChance(Ores ore) {
		/* Get chance of Ores from a file? */
		return 0.0;
	}

	public int getLevel(Ores ore) {
		switch (ore) {
		case TIN:
			return 1;
		case COPPER:
			return 1;
		case IRON:
			return 15;
		case COAL:
			return 30;
		case MITHRIL:
			return 55;
		case ADAMANT:
			return 70;
		case RUNITE:
			return 85;
		default:
			return 1;
		}
	}

	public void beginMining(Player p) {
		// get player free inventory space
		// if player has a free inventory space then begin mining
		// start timer
		int[] inventory = p.getInventory();

		int freeSlots = 0;

		for (int i : inventory)
			if (i == -1)
				freeSlots++;
		long timer = System.currentTimeMillis();
		Random chance;
		Random randomTime;
		chance = new Random(2);
		randomTime = new Random(1); // 0 - 10 seconds
		int lowerBound = 500; // lowest is 1 second
		int upperBound = 1000; // highest we want to wait is 10 seconds
		int randTime = randomTime.nextInt(upperBound - lowerBound) + lowerBound;
		Random r = new Random(Ores.values().length);
		System.out.println("You have " + freeSlots + "/" + p.getInventory().length + " free inventory slots");
		Ores oreMined = null;
		while (freeSlots > 0) { // while player has inventory slot

			if ((System.currentTimeMillis() - timer) >= randTime) {
				switch (chance.nextInt(2)) {
				case 0:
					switch (r.nextInt(7)) {
					case 0:
						oreMined = Ores.TIN;
						break;
					case 1:
						oreMined = Ores.COPPER;
						break;
					case 2:
						oreMined = Ores.IRON;
						break;
					case 3:
						oreMined = Ores.MITHRIL;
						break;
					case 4:
						oreMined = Ores.ADAMANT;
						break;
					case 5:
						oreMined = Ores.RUNITE;
						break;
					default:
						oreMined = Ores.TIN;
						break;

					}
					System.out.println("You have successfully mined some " + oreMined.toString() + " ore.");
					for (int i = 0; i < inventory.length; i++) {
						if (inventory[i] == -1) {
							inventory[i] = r.nextInt(7);
							break;
						}
					}
					freeSlots--;
					p.save(p);
					break;
				case 1:
					System.out.println("You swing your pickaxe at the rock.");
					break;
				}
				randTime = randomTime.nextInt(upperBound - lowerBound) + lowerBound;
				timer = System.currentTimeMillis();
			}
		}
		p.setInventory(inventory);
		p.save(p);
		System.out.println("Your inventory is full, " + p.getName() + "!");
	}

}
