package com.jasondavidpeters.main;

public class Game implements Runnable {

	Thread thread;

	private boolean running;

	public static void main(String[] args) {
		Game game = new Game();
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
		int tickCounter = 0;

		while (running) {
			nowTime = System.nanoTime();
			pastTime += (nowTime - beforeTime) / ns;
			beforeTime = nowTime;
			
			while (pastTime >=1) {
				pastTime-=1;
				tick();
				//System.out.println("Tick");
				tickCounter++;
			}
			// System.out.println(pastTime);
			// System.out.println("Running");

			while ((System.currentTimeMillis() - timer) >= 1000) {
				System.out.println("Ticks per second: " + tickCounter);
				timer = System.currentTimeMillis();
				tickCounter=0;
			}
		}
	}

	private void tick() {

	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
