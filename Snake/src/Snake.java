import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

public class Snake extends Applet implements Runnable {
	
	private boolean running;
	private Thread ticker;
	private boolean over = false;
	
	private final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;
	private final int GRID_WIDTH = 20, GRID_HEIGHT = 20;
	private final int FPS = 11;
	private final int CELL_SIZE = 20;
	
	private KeyHandler keyHandler;
	
	private Vector<int[]> snake = new Vector<int[]>();
	private int[] head = new int[2];
	private int[] tail = new int[2];
	private int[] apple = new int[2];
	private int score;
	private Random rand;
	
	private int velX, velY;
	
	public void init() {
		
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		
		rand = new Random();
		
		score = 0;
		
		apple[0] = rand.nextInt(GRID_WIDTH);
		apple[1] = rand.nextInt(GRID_HEIGHT);
		
		int snakeX = 7;
		int snakeY = 10;
		
		for(int i = 0; i < 5; i++) {
			int[] snakePart = {snakeX - i, snakeY};
			snake.add(snakePart);
		}
		
		this.head = snake.elementAt(0);
		this.tail = snake.elementAt(snake.size() - 1);
		
		velX = 1;
		velY = 0;
	}
	
	public void start() {
		// Check for either no Thread or a dead Thread
		if (ticker == null || !ticker.isAlive()) {
			running = true;
			// Reassign ticker in case it is only dead
			ticker = new Thread(this);
			ticker.setPriority(Thread.MIN_PRIORITY + 1);
			ticker.start();
		}
		
		resize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.requestFocus();
	}
	
	public void stop() {
		over = true;
		repaint();
		running = false;
		
	}
	
	public void run() {
		while(running) {
			this.requestFocus();
			
			if(keyHandler.up) {
				velX = 0;
				velY = -1;
			} else if(keyHandler.down) {
				velX = 0;
				velY = 1;
			} else if(keyHandler.left) {
				velX = -1;
				velY = 0;
			} else if(keyHandler.right) {
				velX = 1;
				velY = 0;
			}
			
			keyHandler.moveExecuted = false;
			
			int[] newHead = {head[0] + velX, head[1] + velY};
			
			for(int index = 1; index < snake.size(); index++) {
				if(newHead[0] == snake.elementAt(index)[0] && newHead[1] == snake.elementAt(index)[1]) {
					stop();
				}
			}
			
			// Check edge cases
			if(newHead[1] >= GRID_HEIGHT) {
				//newHead[1] = 0;
				stop();
			} else if(newHead[1] < 0) {
				//newHead[1] = GRID_HEIGHT;
				stop();
			} else if(newHead[0] < 0) {
				//newHead[0] = GRID_WIDTH;
				stop();
			} else if(newHead[0] >= GRID_WIDTH) {
				//newHead[0] = 0;
				stop();
			}
			
			if(apple[0] == newHead[0] && apple[1] == newHead[1]) {
				
				boolean inSnake = true;
				
				while(inSnake) {
					
					apple[0] = rand.nextInt(GRID_WIDTH);
					apple[1] = rand.nextInt(GRID_HEIGHT);
					
					int j = 0;
					
					while (j < snake.size()) {
						
						if (apple[0] == snake.elementAt(j)[0] && apple[1] == snake.elementAt(j)[1]) {
							// The apple coordinates are in the snake
							break;
						}
						
						j++;
					}
					
					inSnake = j != snake.size();
				}
				
				score += 10;
			} else {
				snake.remove(tail);
			}
			
			snake.insertElementAt(newHead, 0);
			
			head = newHead;
			tail = snake.elementAt(snake.size() - 1);
			
			repaint();
			
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void paint(Graphics g) {
		if (!over) {
			for (int x = 0; x < GRID_WIDTH; x++) {
				for (int y = 0; y < GRID_HEIGHT; y++) {

					boolean contained = false;
					for (int[] snakeBit : snake) {
						if (snakeBit[0] == x && snakeBit[1] == y) {
							contained = true;
						}
					}

					if (contained) {
						g.setColor(Color.GREEN);
					} else if (apple[0] == x && apple[1] == y) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.WHITE);
					}

					g.fillRect(CELL_SIZE * x + 30, CELL_SIZE * y, CELL_SIZE, CELL_SIZE);

					g.setColor(Color.BLACK);
					g.drawRect(CELL_SIZE * x + 30, CELL_SIZE * y, CELL_SIZE, CELL_SIZE);
				}
			}
			
			g.setColor(Color.WHITE);
			g.fillRect(5, WINDOW_HEIGHT - 20, 50, 10);
			g.setColor(Color.BLACK);
			g.drawString("Score: " + score, 10, WINDOW_HEIGHT - 15);
			
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			
			g.setColor(Color.RED);
			g.drawString("GAME OVER. Score: " + score, WINDOW_WIDTH / 2 - 15, WINDOW_HEIGHT / 2);
		}
	}

}
