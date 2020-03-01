package com.jasondavidpeters.village.game;

import java.util.Scanner;

import com.jasondavidpeters.village.entity.Player;
import com.jasondavidpeters.village.world.Mine;
import com.jasondavidpeters.village.world.Shop;

public class Game implements Runnable {

	Thread thread;

	private boolean running;

	private GameState defaultState = GameState.LOGIN;

	private Mine mine;
	private Shop shop;

	Player p = null;

	public Game() {
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.run();
	}

	public void run() {
		running = true;
		mine = new Mine();
		shop = new Shop();
		long beforeTime = System.nanoTime();
		long nowTime = 0;
		double pastTime = 0;
		double ticks = 60.0;
		double ns = 1000000000.0 / ticks;
		long timer = System.currentTimeMillis();
		while (running) {
			nowTime = System.nanoTime();
			pastTime += (nowTime - beforeTime) / ns;
			beforeTime = nowTime;

			while (pastTime >= 1) {
				pastTime -= 1;
				tick();
			}

			while ((System.currentTimeMillis() - timer) >= 1000) {
				timer = System.currentTimeMillis();
			}
		}
	}

	private void tick() {
		Scanner sc = new Scanner(System.in);
		String playerName = null;
		/* get gamestate, set gamestate to LOGIN */
		if (getGameState() == GameState.LOGIN) {
			System.out.println("What is your name?");
			if (sc.hasNext()) {
				playerName = sc.next();
				p = new Player(playerName, 0);

				setGameState(GameState.VILLAGE);
			}
		}
		if (getGameState() == GameState.VILLAGE) {
			System.out.println((p.playerExists(p)) ? "Hello and welcome back, " + p.getName()
					: "Hello, and welcome to the Village, " + p.getName());
			System.out.println("Where would you like to go today?");
			System.out.println("SHOP\tMINE\tINVENTORY\tEXIT");

			while (sc.hasNext()) {
				String response = sc.next();
				if (response.equalsIgnoreCase("shop")) {
					setGameState(GameState.SHOP);
					break;
				}
				if (response.equalsIgnoreCase("mine")) {
					setGameState(GameState.MINE);
					break;
				}
				if (response.equalsIgnoreCase("inventory")) {
					setGameState(GameState.INVENTORY);
					break;
				}
				if (response.equalsIgnoreCase("exit")) {
					p.save(p);
					stop();
					System.exit(1);
					break;
				} else {
					System.out.println("Location: " + response + " does not exist.");
					return;
				}
			}
		}
		if (getGameState() == GameState.MINE) {
			System.out.println("Entering the mine...");
			System.out.println("Please choose from the options below\n MINE_ORE\tINVENTORY\t EXIT");
			while (sc.hasNext()) {
				String response = sc.next();
				if (response.equalsIgnoreCase("mine_ore")) {
					// begin mining ore
					mine.beginMining(p);
					break;
				} else if (response.equalsIgnoreCase("exit")) {
					setGameState(GameState.VILLAGE);
					p.save(p);
					break;
				} else if (response.equalsIgnoreCase("inventory")) {
					setGameState(GameState.INVENTORY);
					p.save(p);
					break;
				} else {
					System.out.println("Option: " + response + " does not exist.");
					return;
				}
			}
		}
		if (getGameState() == GameState.INVENTORY) {
			int tin = -1, copper = -1, iron = -1, mithril = -1, adamant = -1, rune = -1;
			int[] inv = p.getInventory();
			for (int i : inv) {
				switch (i) {
				case 0:
					tin++;
					break;
				case 1:
					copper++;
					break;
				case 2:
					iron++;
					break;
				case 3:
					mithril++;
					break;
				case 4:
					adamant++;
					break;
				case 5:
					rune++;
					break;
				}
			}
			System.out.println("You currently have:\n" + (tin != -1 ? tin + "x tin ore\n" : "")
					+ (copper != -1 ? copper + "x copper ore\n" : "") + (iron != -1 ? iron + "x iron ore\n" : "")
					+ (mithril != -1 ? mithril + "x mithril ore\n" : "")
					+ (adamant != -1 ? adamant + "x adamant ore\n" : "") + (rune != -1 ? rune + "x runite ore\n" : "")
					+ "\nMoney: " + p.getMoney());
			/*
			 * check inventory 1x tin_ore 5x mithril_ore
			 */
			System.out.println("Where would you like to go now?");
			System.out.println("SHOP\tMINE\tEXIT");
			while (sc.hasNext()) {
				String response = sc.next();
				if (response.equalsIgnoreCase("shop")) {
					setGameState(GameState.SHOP);
					break;
				}
				if (response.equalsIgnoreCase("mine")) {
					setGameState(GameState.MINE);
					break;
				}
				if (response.equalsIgnoreCase("exit")) {
					p.save(p);
					stop();
					System.exit(1);
					break;
				} else {
					System.out.println("Location: " + response + " does not exist.");
					return;
				}
			}
		}
		if (getGameState() == GameState.SHOP) {
			System.out.println("Entering the shop");
			System.out.println("What would you like to do here?");
			System.out.println("SELL\tMINE\tINVENTORY\tEXIT");
			while (sc.hasNext()) {
				String response = sc.next();
				if (response.equalsIgnoreCase("mine")) {
					setGameState(GameState.MINE);
					break;
				}
				if (response.equalsIgnoreCase("inventory")) {
					setGameState(GameState.INVENTORY);
					break;
				}
				if (response.equalsIgnoreCase("sell")) {
					shop.sell(p,p.getInventory());
					break;
				}
				if (response.equalsIgnoreCase("exit")) {
					p.save(p);
					stop();
					break;
				} else {
					System.out.println("Location: " + response + " does not exist.");
					return;
				}
			}
		}
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setGameState(GameState gs) {
		defaultState = gs;
	}

	public GameState getGameState() {
		return defaultState;
	}
}
