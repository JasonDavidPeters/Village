package com.jasondavidpeters.village.world;

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

	public void beginMining() {
		// TODO Auto-generated method stub
		
	}

}
