package Graphics;

/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {
	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 70;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1)
			* BRICK_SEP)
			/ NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	
	private static final int BALL_SPEED = 10;
	
	private static final int DELAY = 100;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 30;

	/** Number of turns */
	private static final int NTURNS = 1;

	private void drawBricks() {
		for (int i = 0; i < NBRICK_ROWS; i++) {
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				GRect current = new GRect(0 + j * (BRICK_WIDTH + BRICK_SEP),
						BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP),
						BRICK_WIDTH, BRICK_HEIGHT);
				current.setFilled(true);
				if (i % 10 == 0 || i % 10 == 1) current.setColor(Color.RED);
				if (i % 10 == 2 || i % 10 == 3)
					current.setColor(Color.ORANGE);
				if (i % 10 == 4 || i % 10 == 5)
					current.setColor(Color.YELLOW);
				if (i % 10 == 6 || i % 10 == 7)
					current.setColor(Color.GREEN);
				if (i % 10 == 8 || i % 10 == 9)
					current.setColor(Color.CYAN);
				
				add(current);
			}
		}
	}
	private void drawPaddle() {
		GRect pad = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		pad.setFilled(true);
		add(pad, WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET);
	}	
	public void init() {
		lives = NTURNS;
	}
	public void setup() {
		addMouseListeners();
		gameOver = false;
		drawBricks();
		drawPaddle();
		ball = new GOval(WIDTH / 2, HEIGHT / 2, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
		xDirection = BALL_SPEED;
		yDirection = BALL_SPEED;
		GLabel label1 = new GLabel("click here to start");
		add(label1, 100, HEIGHT - PADDLE_Y_OFFSET);
		label = new GLabel("");
		label.setFont("Times New Roman-36");
		add(label, 50, 50);
		waitForClick();	
		remove(label1);		
	}	
	private void moveBall() {
		ball.move(xDirection, yDirection);
	}
	private void checkCollision() {
		lastb = new GPoint(ball.getX(), ball.getY());
		if (ball.getX() < 0) xDirection = BALL_SPEED;
		if (ball.getX() > WIDTH - 2 * BALL_RADIUS) xDirection = - BALL_SPEED;
		if (ball.getY() < 0) yDirection = BALL_SPEED;
		
		if (getElementAt(lastb) != null) {
			if (getElementAt(lastb) == pad) {
				yDirection = - BALL_SPEED;
			}
		}
	}
	public void mouseMoved(MouseEvent e) {
		lastp = new GPoint(e.getPoint());
		label.setLabel("Mouse: (" + e.getX() + ", " + e.getY() + ")");
		pad.setLocation(lastp);
	}
	public void mousePressed(MouseEvent e) {
		lastp = new GPoint(e.getPoint());
		gobj = getElementAt(lastp);
	}
	public void mouseDragged(MouseEvent e) {
		if (gobj != null) {
			gobj.move(e.getX() - lastp.getX(), 0);
			lastp = new GPoint(e.getPoint());
		}
	}
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		setup();	
		
		while(!gameOver) {
			moveBall();
			if (ball.getY() > HEIGHT) {
				lives--;
				if (lives < 1) {
					gameOver = true;
					break;
				}
			}
			
			//if (allBricksCleared) gameOver = true;
			checkCollision();
			pause(DELAY);
		}
		GLabel labelOver = new GLabel("GAME OVER", WIDTH / 2, HEIGHT / 2);
		add(labelOver);
	}
	private boolean gameOver;
	private int x;
	private int y;
	private int oldX;
	private int xDirection;
	private int yDirection;
	private int lives;
	private GRect pad;
	private GOval ball;
	private GObject gobj;
	private GPoint lastp;
	private GPoint lastb;
	private GLabel label;
	private GLabel label1;
	private GLabel label2;
}