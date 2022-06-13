package tictactoegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class SinglePlayer implements Runnable{
	private Board b=new Board();
	private Scanner scanner = new Scanner(System.in);
	private JFrame frame;
	private final int WIDTH = 506;
	private final int HEIGHT = 527;
	private Thread thread;

	private Painter painter;

	private BufferedImage board;
	private BufferedImage redX;
	private BufferedImage blueX;
	private BufferedImage redCircle;
	private BufferedImage blueCircle;

	private String[] spaces = new String[9];

	private boolean yourTurn = true;
	private boolean circle = true;
	private boolean won = false;
	private boolean enemyWon = false;
	private boolean tie = false;

	private int lengthOfSpace = 160;
	private int firstSpot = -1;
	private int secondSpot = -1;

	private Font font = new Font("Verdana", Font.BOLD, 32);
	private Font smallerFont = new Font("Verdana", Font.BOLD, 20);
	private Font largerFont = new Font("Verdana", Font.BOLD, 50);

	private String wonString = "You won!";
	private String enemyWonString = "Opponent won!";
	private String tieString = "Game ended in a tie.";

	private int[][] wins = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

	/**
	 * winning combinations
	 * 0, 1, 2 
	 * 3, 4, 5 
	 * 6, 7, 8
	 *
	 */

	public SinglePlayer() {
		loadImages();
		painter = new Painter();
		painter.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame = new JFrame();
		frame.setContentPane(painter);
		frame.setTitle("Single Player Tic Tac Toe");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		thread = new Thread(this, "TicTacToe");
		thread.start();
	}

	public void run() {
		while (tie==false && won==false && enemyWon==false) {
			tick();
			painter.repaint();

		}
	}

	private void render(Graphics g) {
		g.drawImage(board, 0, 0, null);
		for (int i = 0; i < spaces.length; i++) {
			if (spaces[i] != null) {
				if (spaces[i].equals("X")) {
					if (circle) {
						g.drawImage(redX, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
					} else {
						g.drawImage(blueX, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
					}
				} else if (spaces[i].equals("O")) {
					if (circle) {
						g.drawImage(blueCircle, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
					} else {
						g.drawImage(redCircle, (i % 3) * lengthOfSpace + 10 * (i % 3), (int) (i / 3) * lengthOfSpace + 10 * (int) (i / 3), null);
					}
				}
			}
		}
		if (won || enemyWon) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(10));
			g.setColor(Color.BLACK);
			g.drawLine(firstSpot % 3 * lengthOfSpace + 10 * firstSpot % 3 + lengthOfSpace / 2, (int) (firstSpot / 3) * lengthOfSpace + 10 * (int) (firstSpot / 3) + lengthOfSpace / 2, secondSpot % 3 * lengthOfSpace + 10 * secondSpot % 3 + lengthOfSpace / 2, (int) (secondSpot / 3) * lengthOfSpace + 10 * (int) (secondSpot / 3) + lengthOfSpace / 2);

			g.setColor(Color.RED);
			g.setFont(font);
			if (won) {
				int stringWidth = g2.getFontMetrics().stringWidth(wonString);
				g.drawString(wonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			} else if (enemyWon) {
				int stringWidth = g2.getFontMetrics().stringWidth(enemyWonString);
				g.drawString(enemyWonString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
			}
		}
		if (tie) {
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.BLACK);
			g.setFont(font);
			int stringWidth = g2.getFontMetrics().stringWidth(tieString);
			g.drawString(tieString, WIDTH / 2 - stringWidth / 2, HEIGHT / 2);
		}

	}

	private void tick() {
		if (!yourTurn) {
			b.setBoard(spaces);
			b.callMinimax(0,1);
			b.placeAMove(b.returnBestMove(), 1);
			spaces=b.getBoard();
			checkForEnemyWin();
			checkForTie();
			yourTurn = true;
		}
	}

	private void checkForWin() {
		for (int i = 0; i < wins.length; i++) {
			if (circle) {
				if (spaces[wins[i][0]] == "O" && spaces[wins[i][1]] == "O" && spaces[wins[i][2]] == "O") {
					firstSpot = wins[i][0];
					secondSpot = wins[i][2];
					won = true;
				}
			} else {
				if (spaces[wins[i][0]] == "X" && spaces[wins[i][1]] == "X" && spaces[wins[i][2]] == "X") {
					firstSpot = wins[i][0];
					secondSpot = wins[i][2];
					won = true;
				}
			}
		}
	}

	private void checkForEnemyWin() {
		for (int i = 0; i < wins.length; i++) {
			if (circle) {
				if (spaces[wins[i][0]] == "X" && spaces[wins[i][1]] == "X" && spaces[wins[i][2]] == "X") {
					firstSpot = wins[i][0];
					secondSpot = wins[i][2];
					enemyWon = true;
				}
			} else {
				if (spaces[wins[i][0]] == "O" && spaces[wins[i][1]] == "O" && spaces[wins[i][2]] == "O") {
					firstSpot = wins[i][0];
					secondSpot = wins[i][2];
					enemyWon = true;
				}
			}
		}
	}

	private void checkForTie() {
		for (int i = 0; i < spaces.length; i++) {
			if (spaces[i] == null) {
				return;
			}
		}
		tie = true;
	}


	private void loadImages() {
		try {
			board = ImageIO.read(getClass().getResourceAsStream("/board.png"));
			redX = ImageIO.read(getClass().getResourceAsStream("/redX.png"));
			redCircle = ImageIO.read(getClass().getResourceAsStream("/redCircle.png"));
			blueX = ImageIO.read(getClass().getResourceAsStream("/blueX.png"));
			blueCircle = ImageIO.read(getClass().getResourceAsStream("/blueCircle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Painter extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;

		public Painter() {
			setFocusable(true);
			requestFocus();
			setBackground(Color.WHITE);
			addMouseListener(this);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			render(g);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
				if (yourTurn && !won && !enemyWon) {
					int x = e.getX() / lengthOfSpace;
					int y = e.getY() / lengthOfSpace;
					y *= 3;
					int position = x + y;

					if (spaces[position] == null) {
						if (!circle) spaces[position] = "X";
						else spaces[position] = "O";
						yourTurn = false;
						repaint();
						Toolkit.getDefaultToolkit().sync();

						
						System.out.println("DATA WAS SENT");
						checkForWin();
						checkForTie();

					}
				}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

    
}
