package englard_shapiro_brickBreaker.englard_shapiro_brickBreaker;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Frame extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
	private ScheduledExecutorService executor;
	private boolean isPaused;
	private ScheduledExecutorService musicExecutor;
	private MusicThread music;

	public Frame() {
		setSize(600, 600);
		setTitle("Brick Braker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		createComponents();
		setProperties();
		addComponents();
		RunGame();

	}

	private void RunGame() {
		Runnable play = new Runnable() {

			public void run() {
				try{
				if (isPaused) {
					Thread.sleep(100);
				} else {
					board.moveBall();
					board.repaint();
				}
				}catch(InterruptedException  e){
					System.out.println("Interrupted thread exception");
				}
			}
		};
		this.executor.scheduleWithFixedDelay(play, 0, 5, TimeUnit.MILLISECONDS);
		Runnable playSound = new Runnable() {

			public void run() {
				music = new MusicThread();
				music.start();
			}
		};
		this.musicExecutor.scheduleAtFixedRate(playSound, 0, 22,
				TimeUnit.SECONDS);
	}

	private void addComponents() {
		add(board, BorderLayout.CENTER);

	}

	private void setProperties() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.setFocusable(true);
		container.addKeyListener(this);

	}

	private void createComponents() {
		board = new Board();
		isPaused = false;
		this.executor = Executors.newScheduledThreadPool(1);
		this.musicExecutor = Executors.newScheduledThreadPool(1);
		music = new MusicThread();

	}

	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_LEFT) {
			if (!isPaused) {
				board.movePaddleLeft();
			}
		} else if (c == KeyEvent.VK_RIGHT) {
			if (!isPaused) {
				board.movePaddleRight();
			}
		} else if (c == KeyEvent.VK_P) {
			isPaused = true;
		} else if (c == KeyEvent.VK_R) {
			isPaused = false;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
