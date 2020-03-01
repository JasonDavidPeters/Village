package com.jasondavidpeters.village.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
			inventory[i] = -1;
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
			load(p);
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
			FileWriter fw = new FileWriter(saveFile, false);
			fw.write("Name\t" + p.getName() + "\n");
			for (int i = 0; i < inventory.length; i++) {
				// System.out.println(inventory[i]);
				fw.write("Inventory-" + i + "\t" + inventory[i] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void load(Player p) {
		File saveDir = new File(System.getProperty("user.dir") + "/saves/");
		File saveFile = new File(saveDir.getPath() + "/" + p.getName() + ".txt");

		try {
			FileReader fr = new FileReader(saveFile);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				String[] t = line.split("\t"); // splitting the key from value in file
				line.trim();
				//System.out.println(t[0] + " " + t[1]);
				String[] invSlots=t[0].split("-");
				if (invSlots[0].equalsIgnoreCase("inventory"))
				for (int i = 0; i < inventory.length; i++) {
					inventory[Integer.parseInt(invSlots[1])] = Integer.parseInt(t[1]);
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// int[] inv = new int[30]; // load player inventory here
		// setInventory(inv);
	}

	public String getName() {
		return name;
	}

	public void setInventory(int[] inventory) {
		this.inventory = inventory;
	}

	public int[] getInventory() {
		return this.inventory;
	}

}
