import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class gamePanel extends JPanel implements Runnable {
	
	static final int game_width = 1000;
	static final int game_height = (int)(game_width * (0.5555));
	static final Dimension screenSize = new Dimension(game_width, game_height);
	static final int ball_diameter = 20;
	static final int paddle_width = 25;
	static final int paddle_height = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	paddle paddle1;
	paddle paddle2;
	ball Ball;
	score Score;
	
	gamePanel() {
		newPaddle();
		newBall();
		Score = new score(game_width, game_height);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(screenSize);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newBall() {
		random = new Random();
		Ball = new ball((game_width / 2) - (ball_diameter / 2), random.nextInt(game_height - ball_diameter), ball_diameter, ball_diameter);
	}
	
	public void newPaddle() {
		paddle1 = new paddle(0, (game_height / 2) - (paddle_height / 2), paddle_width, paddle_height, 1);
		paddle2 = new paddle(game_width - paddle_width, (game_height / 2) - (paddle_height / 2), paddle_width, paddle_height, 2);

	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
		
	}
	
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		Ball.draw(g);
		Score.draw(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void move() {
		paddle1.move();
		paddle2.move();
		Ball.move();
	}
	
	public void checkCollision() {
		//bounce balls off screen edges
		if (Ball.y <= 0) {
			Ball.setYDirection(-Ball.yVelocity);
		}
		
		if (Ball.y >= game_height - ball_diameter) {
			Ball.setYDirection(-Ball.yVelocity);
		}
		//bounces ball off paddles
		if (Ball.intersects(paddle1)) {
			Ball.xVelocity = Math.abs(Ball.xVelocity);
			Ball.xVelocity++; //increases game difficulty
			if (Ball.yVelocity > 0) {
				Ball.yVelocity++; //increase game difficulty
			} else 
				Ball.yVelocity--;
			Ball.setXDirection(Ball.xVelocity);
			Ball.setYDirection(Ball.yVelocity);
		}
		
		if (Ball.intersects(paddle2)) {
			Ball.xVelocity = Math.abs(Ball.xVelocity);
			Ball.xVelocity++; //increases game difficulty
			if (Ball.yVelocity > 0) {
				Ball.yVelocity++; //increase game difficulty
			} else 
				Ball.yVelocity--;
			Ball.setXDirection(-Ball.xVelocity);
			Ball.setYDirection(Ball.yVelocity);
		}
		
		//stops paddle from moving off the screen edges
		if (paddle1.y <= 0) {
			paddle1.y = 0;
		}
		
		if (paddle1.y >= (game_height - paddle_height)) {
			paddle1.y = game_height - paddle_height;
		}
		
		if (paddle2.y <= 0) {
			paddle2.y = 0;
		}
		
		if (paddle2.y >= (game_height - paddle_height)) {
			paddle2.y = game_height - paddle_height;
		}
		
		//give a player 1 point and creates a new round to play
		if (Ball.x <= 0) {
			Score.player2++;
			newPaddle();
			newBall();
			System.out.println("Player 2: " + Score.player2);
		}
		
		if (Ball.x >= game_width - ball_diameter) {
			Score.player1++;
			newPaddle();
			newBall();
			System.out.println("Player 1: " + Score.player1);
		}
	}
	
	public void run() {
		//basic game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
		
	}
	
	public class AL extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}

