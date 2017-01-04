package com.link;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

/**
 * 
 * @author paulbairdsmith
 * 3 dimensional version of Applet. Creates and simulates fixed length crank systems
 * and can draw trails for all components of a system. All systems are projected into
 * 2 dimensions here.
 */
public class LinkageApp extends Applet implements Runnable {

	
	
	/************ Variables used for init/running applet ******************************/
	
	
	
	private boolean running;
	private Thread ticker;

	private final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 700;
	private final int FPS = 1000 / 30;

	
	
	/************* Variables to be used in the crank systems *************************/
	
	
	
	private Crank cr, cr2, cr3, cr4, cr5, cr6;
	private Slider s1, s2, s3;
	private Connector c1, c2;
	private Vector<LComponent> components;

	private Vector<ColoredRect> trail;

	private boolean functioning;
	
	
	
	/***********************************************************************************/
	
	

	public void start() {
		if (ticker == null || !ticker.isAlive()) {
			running = true;
			ticker = new Thread(this);
			ticker.setPriority(Thread.MIN_PRIORITY);
			ticker.start();
		}
	}

	public void init() {
		resize(WINDOW_WIDTH, WINDOW_HEIGHT);

		components = new Vector<LComponent>();

		functioning = true;

		trail = new Vector<ColoredRect>();

		
		/**************** Pick normal vectors for the cranks ***********************/
		
		
		double[] norm1 = {0, 0, 1};
		double[] norm2 = {0, 1, 0};
		//double[] norm3 = {2, 3, 4};

		
		/***************** Describe the cranks here ********************************/
		
		
		// Order is parent crank, length of crank, theta0, normal vector, speed, color 		
		
		
		cr = new Crank(160, 0, norm2, 16, Color.WHITE);
		cr2 = new Crank(cr, 60, 0, norm1, 3, Color.BLUE);
		//cr3 = new Crank(400, 400, 38, norm3, 13, Color.RED);
		

		c1 = new Connector(cr2, Color.BLACK);
		
		
		/************************** Pick which cranks to follow *********************/
		
		
		c1.setFollowed(true);
		cr2.setFollowed(true);
		//cr3.setFollowed(true);
		
		
		/******************* Add the cranks, connector to components ****************/
		
		
		components.add(c1);
		components.add(cr);
		components.add(cr2);
		//components.add(cr3);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {

			// Move all the components in the system appropriately. Leave a bread
			// crumb trail if the component is set to be followed by the applet.
			if (functioning) {
				for (int i = 0; i < components.size(); i++) {
					
					LComponent c = components.elementAt(i);
					
					if (c instanceof Crank) {
						((Crank) c).move();
					} else if (c instanceof Slider) {
						((Slider) c).move();
					}

					if (c.getFollowed()) {
						Rectangle crumb = new Rectangle(c.midX(), c.midY(), 1, 1);
						Color cColor = c.getColor();
						ColoredRect cColRect = new ColoredRect(crumb, cColor);
						trail.add(cColRect);
					}
				}
			}

			repaint();

			try {
				ticker.sleep(FPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Draws all the components of the system, including rods, cranks, and connector
	 * objects
	 */
	public void paint(Graphics g) {
		for (LComponent c : components) {
			c.draw(g);
		}

		for (ColoredRect colRect : trail) {
			Rectangle r = colRect.getRect();
			Color col = colRect.getColor();
			g.setColor(col);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
	}

	public void stop() {
		running = false;
	}
}
