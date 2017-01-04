import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

/**
 * 
 * @author paulbairdsmith
 * 2 dimensional version of Applet. Creates and simulates fixed length crank systems
 * and can draw trails for all components of a system.
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

		
		/************** Describe cranks that make up system **************/
		
		
		// Order is parent crank, length of crank, theta0, speed, color 
		
		cr = new Crank(75, 0, 19, Color.WHITE);
		cr2 = new Crank(cr, 50, Math.PI / 4, -5, Color.BLUE);
		cr3 = new Crank(cr2, 100, -Math.PI / 8, 5, Color.RED);
		//cr4 = new Crank(420, 300, 17, Color.GREEN);
		//cr5 = new Crank(460, 300, 21);
		//cr6 = new Crank(500, 300, 25);

		c1 = new Connector(cr3, Color.BLACK);
		//c2 = new Connector(450, 350);
		//c2.setFollowed(true);
		
		
		
		/************ Set which cranks to follow *********************/
		
		
		c1.setFollowed(true);
		cr.setFollowed(true);
		cr2.setFollowed(true);
		cr3.setFollowed(true);
		//cr4.setFollowed(true);
		
		
		/************* Add the components of the system ***************/
		
		
		components.add(c1);
		//components.add(c2);
		components.add(cr);
		components.add(cr2);
		components.add(cr3);
		//components.add(cr4);
		//components.add(cr5);
		//components.add(cr6);
		// components.add(s1);
		// components.add(s2);
		// components.add(s3);
		
		
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
			
			// Check all components to see that their movement follows a valid 
			// instruction set. Halt the system if any component does not follow
			// a valid instruction set
			for (int i = 0; i < components.size(); i++) {
				LComponent c = components.elementAt(i);
				for (LCompAssoc compAssoc : c.getConnected()) {

					LComponent currComp = compAssoc.getComp();

					int deltaX = c.getX() - currComp.getX();
					int deltaY = c.getY() - currComp.getY();

					double currDistance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

					double distDif = compAssoc.getDistance() - currDistance;

					if (distDif > 1.5 || distDif < -1.5) {
						functioning = false;
						currComp.setFunctioning(false);
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
