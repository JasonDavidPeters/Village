package com.jasondavidpeters.village.entity;

import java.io.File;
import java.io.IOException;

public class Player extends Entity {
	String name;

	public Player(String name) {
		super(name);
		this.name = name;
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
			try {
				saveFile.createNewFile(); // if the player file does not exist then attempt to create
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;

		}
	}

	public void save(Player p) {
		File saveFile = new File(System.getProperty("user.dir") + "/saves/" + p.getName());
		System.out.println(saveFile.getPath());
		if (saveFile.exists() == false) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void load() {

	}

	public String getName() {
		return name;
	}

}
