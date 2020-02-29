package com.jasondavidpeters.village.game;

import java.util.Scanner;

import com.jasondavidpeters.village.entity.Player;
import com.jasondavidpeters.village.world.Mine;

public class Game implements Runnable {

	Thread thread;

	private boolean running;

	private GameState defaultState = GameState.LOGIN;
	
	private static Mine mine;

	Player p = null;

	public Game() {
	}

	public static void main(String[] args) {
		Game game = new Game();
		mine = new Mine();
		game.start();
	}

	public void start() {
		thread = new Thread(this);
		thread.run();
	}

	public void run() {
		running = true;

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
				// System.out.println("Ticks per second: " + tickCounter);
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
				p = new Player(playerName);

				setGameState(GameState.VILLAGE);
			}
		}
		if (getGameState() == GameState.VILLAGE) {
			System.out.println((p.playerExists(p)) ? "Hello and welcome back, " + p.getName()
					: "Hello, and welcome to the Village, " + p.getName());
			System.out.println("Where would you like to go today?");
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
			System.out.println("Please choose from the options below\n MINE_ORE\t EXIT");
			while (sc.hasNext()) {
				String response = sc.next();
				if (response.equalsIgnoreCase("mine_ore")) {
					// begin mining ore
					mine.beginMining();
					break;
				} else if (response.equalsIgnoreCase("exit")) {
					setGameState(GameState.VILLAGE);
					p.save(p);
					break;
				} else {
					System.out.println("Option: " + response + " does not exist.");
					return;
				}
			}
		}
		if (getGameState() == GameState.SHOP) {
			System.out.println("Entering the shop");
		}
	}

	public void stop() {
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
