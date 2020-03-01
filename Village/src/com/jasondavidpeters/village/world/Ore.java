package com.jasondavidpeters.village.world;

public enum Ore {
	TIN, COPPER, IRON, COAL, MITHRIL, ADAMANT, RUNITE, NONE;
	
	public static Ore getID(int id) {
		switch(id) {
		case 0:
			return TIN;
		case 1:
			return COPPER;
		case 2:
			return IRON;
		case 3:
			return COAL;
		case 4:
			return MITHRIL;
		case 5:
			return ADAMANT;
		case 6:
			return RUNITE;
			default :
				return NONE;
		}
	}
}
