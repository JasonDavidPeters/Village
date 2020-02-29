package com.jasondavidpeters.village.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Player extends Entity {
	String name;

	int[] inventory = new int[30];

	public Player(String name) {
		super(name);
		this.name = name;
	}

	private void createPlayer(Player p) {
		File saveDir = new File(System.getProperty("user.dir") + "/saves/");
		File saveFile = new File(saveDir.getPath() + "/" + p.getName() + ".txt");
		try {
			saveFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < inventory.length; i++) {
			inventory[i] = 0;
		}

		save(p);
	}

	public boolean playerExists(Player p) {
		File saveDir = new File(System.getProperty("user.dir") + "/saves/");
		File saveFile = new File(saveDir.getPath() + "/" + p.getName() + ".txt");

		if (!saveDir.exists()) { // Check if directory exists (folders)
			saveDir.mkdir(); // if folders do not exist then create
		}
		if (saveFile.exists()) { // if the player file exists then return true
			return true;
		} else {
			createPlayer(p);
			return false;

		}
	}

	public void save(Player p) {
		File saveDir = new File(System.getProperty("user.dir") + "/saves/");
		File saveFile = new File(saveDir.getPath() + "/" + p.getName() + ".txt");
		if (!saveDir.exists()) { // Check if directory exists (folders)
			saveDir.mkdir(); // if folders do not exist then create
		}
		if (saveFile.exists() == false) { // safely create player file in case for some reason it doesn't exist
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(saveFile);
			fw.write("Name:\t" + p.getName() + "\n");
			for (int i = 0; i < inventory.length; i++)
				fw.write("Inventory[" + i + "]\t" + inventory[i] + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void load() {

	}

	public String getName() {
		return name;
	}

	public int[] getInventory() {
		return this.inventory;
	}

}
