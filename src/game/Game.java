package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Paint paint;
	JButton btnDie = new JButton("Roll the die");
	JLabel lblDie = new JLabel();
	JLabel lblMove = new JLabel();
	Random rand = new Random();
	int die;
	Counter[] counters1 = new Counter[2], counters2 = new Counter[2];
	Counter[][] counters = { counters1, counters2 };
	String[] p = new String[2];
	JLabel lblTurn = new JLabel();
	int turn = 0;
	int posY[] = { -100, -100 };
	int posX[] = new int[2];
	Color colours[] = {Color.RED, Color.BLACK};
	boolean hov1 = false, hov2 = false;
	boolean press1 = false, press2 = false;
	boolean highQuality = true;
	
	Window win;

	public Game(Window win, String p1, String p2) {
		this.win = win;
		p[0] = p1;
		p[1] = p2;

		for (int i = 0; i < counters.length; i++) {
			for (int j = 0; j < counters[0].length; j++) {
				counters[i][j] = new Counter();
			}
		}

		paint = new Paint();
		add(paint);
		lblTurn.setText("This Turn: " + p[0]);
		add(lblTurn);
		add(btnDie);
		btnDie.addActionListener(this);
		add(lblDie);
		add(lblMove);

	}

	class Paint extends JPanel implements MouseListener, MouseMotionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int width = Window.WIDTH - 50, height = Window.HEIGHT - 150;
		int nw = 48;
		int rw = (width - nw) / 4;
		int rh = (height) / 12;
		int cw = 30;
		int ch = 30;

		Paint() {
			addMouseListener(this);
			addMouseMotionListener(this);
			setPreferredSize(new Dimension(width, height));
			setBorder(BorderFactory.createLineBorder(Color.black, 2));

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			if(highQuality) {
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
			
			// grid
			for (int i = 0; i < 12; i++) {
				int row = 11 - i;
				for (int j = 0; j < 5; j++) {
					if (row == 0) {
						if (j == 0)
							continue;
						if (j < 3) {
							g2.drawString(p[0], nw + (j - 1) * rw + (rw / 4), i
									* rh + (rh / 2));
						} else {
							g2.drawString(p[1], nw + (j - 1) * rw + (rw / 4), i
									* rh + (rh / 2));

						}
					} else if (j == 0) {
						g2.setColor(Color.black);
						g2.drawRect(j, i * rh, nw, rh);
						g2.drawString(Integer.toString(row), j + (nw / 2) - 3,
								(nw / 2) + i * rh);
						if (row == 11 || row == 1) {
							g2.drawString((row == 1) ? "START" : "FINISH",
									(nw / 7), nw - (nw / 5) + i * rh);
						}
					} else {
						if (row == 1 || row == 5 || row == 11) {
							g2.setColor(new Color(119, 145, 61));
							g2.fillRect(nw + (j - 1) * rw, i * rh, rw, rh);
						}
						if ((posY[0] == row && posX[0] == j)
								|| (posY[1] == row && posX[1] == j)) {

							if ((hov1 && j == posX[0])
									|| (hov2 && j == posX[1])) {
								g2.setColor(colours[turn]);
							} else
								g2.setColor(new Color(colours[turn].getRed(), colours[turn].getGreen(), colours[turn].getBlue(), 100));

							if ((press1 && j == posX[0])
									|| (press2 && j == posX[1])) {
								g2.fillOval(nw + (j - 1) * rw + (rw / 8), i
										* rh + (rh / 8), rw - (rw / 4), rh
										- (rh / 4));
							} else
								g2.fillOval(nw + (j - 1) * rw, i * rh, rw, rh);

						}

					}
				}

				g2.setColor(Color.black);
				g2.drawRect(nw, i * rh, rw * 4, rh);
			}

			// counters
			for (int i = 0, a = 0; i < counters.length; i++) {
				for (int j = 0; j < counters[i].length; j++, a++) {
					if (i == 0)
						g2.setColor(colours[0]);
					else if (i == 1)
						g2.setColor(colours[1]);
					g2.fillOval(nw + (rw / 2) - (cw / 2) + (a * rw), (rh / 2)
							- (ch / 2) + rh * (11 - (counters[i][j].getPos())),
							cw, ch);
					if(highQuality) {
						g2.setColor(Color.black);
	
						g2.drawOval(nw + (rw / 2) - (cw / 2) + (a * rw), (rh / 2)
								- (ch / 2) + rh * (11 - (counters[i][j].getPos())),
								cw, ch);
					}
				}
			}

		}

		public boolean checkMouse(int counter, MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			if (counter == 1) {
				int pos1XX = nw + ((posX[0] - 1) * rw);
				int pos1YY = (11 - posY[0]) * rh;
				if ((x > pos1XX && x < pos1XX + rw)
						&& (y > pos1YY && y < pos1YY + rh)) {
					return true;
				}
			} else if (counter == 2) {
				int pos2XX = nw + ((posX[1] - 1) * rw);
				int pos2YY = (11 - posY[1]) * rh;
				if ((x > pos2XX && x < pos2XX + rw)
						&& (y > pos2YY && y < pos2YY + rh)) {
					return true;
				}
			}
			return false;
		}

		public void mousePressed(MouseEvent e) {
			press1 = (checkMouse(1, e) ? true : false);
			press2 = (checkMouse(2, e) ? true : false);

			repaint();
		}

		public void mouseClicked(MouseEvent e) {
			for(int i = 0; i < counters[turn].length; i++) {
				if(checkMouse(i+1, e)) {
					counters[turn][i].setPos(posY[i]);
					moveDone();
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
			press1 = false;
			press2 = false;
			repaint();
		}

		public void mouseDragged(MouseEvent arg0) {
		}

		public void mouseMoved(MouseEvent e) {
			hov1 = (checkMouse(1, e) ? true : false);
			hov2 = (checkMouse(2, e) ? true : false);
			repaint();
		}

	}

	public void checkMove(int turn, int die) {
		int[] newpos = new int[2];
		for(int i = 0; i < newpos.length; i++) {
			newpos[i] = counters[turn][i].getPos() + ((die == 4) ? -1 : die);
		}


		switch (die) {
		case 1:
		case 2:
		case 3:
			for(int i = 0; i < counters[turn].length; i++) {
				if (counters[turn][i].getPos() < 11 && movable(newpos[i], counters[turn][(i==0)?1:0].getPos())) {
					posY[i] = newpos[i];
					if (posY[i] > 11)
						posY[i] = 11;
				}
			}

			lblMove.setText(p[turn] + ", move one of your pieces " + die
					+ " space" + ((die == 1) ? "" : "s") + " nearer to FINISH");

			break;

		case 4:
			if (counters[turn][0].getPos() == 1 && counters[turn][1].getPos() == 1) {
				JOptionPane
						.showMessageDialog(
								this,
								"Sorry, "
										+ p[turn]
										+ ", but you rolled a 4 and both your pieces are at START so there is no possible move for you!",
								"Oh noes!", JOptionPane.PLAIN_MESSAGE);
				moveDone();
			} else {
				for(int i = 0; i < counters[turn].length; i++) {
					if (counters[turn][i].getPos() > 1 && movable(newpos[i], counters[turn][(i==0)?1:0].getPos())) {
						posY[i] = newpos[i];
					}
				}
				lblMove.setText(p[turn]
						+ ", move one of your pieces 1 space back towards START");
				
			}
			break;
			
		case 5:
			for(int i = 0; i < posY.length; i++) {
				posY[i] = firstUnoccupiedSpace(counters[turn][i]);
			}

			if (posY[0] == -100 && posY[1] == -100) {
				JOptionPane
						.showMessageDialog(
								this,
								"Sorry, "
										+ p[turn]
										+ ", but you rolled a 5 and there are no unoccupied spaces ahead, so there is no possible move for you!",
								"Oh noes!", JOptionPane.PLAIN_MESSAGE);
				moveDone();
			} else {
				lblMove.setText(p[turn]
						+ ", move one of your pieces to the next unoccupied space after it");
			}
			break;
		}
		posX[0] = (turn == 0) ? 1 : 3;
		posX[1] = (turn == 0) ? 2 : 4;
		paint.repaint();
	}
	
	public boolean movable(int newPosition, int otherCounterPosition) {
		if(newPosition != otherCounterPosition || isSafe(newPosition)) {
			return true;
		}
		return false;
	}

	public int firstUnoccupiedSpace(Counter c) {
		for (int i = c.getPos() + 1; i <= 11; i++) {
			boolean occupied = false;
			for(Counter[] cc : counters) {
				for(Counter ccc : cc) {
					if(i == ccc.getPos())
						occupied = true;
				}
			}
			if (!occupied) {
				return i;
			}
		}
		return -100;
	}

	public void moveDone() {
		evalMove();
		turn = (turn == 0) ? 1 : 0;
		lblTurn.setText("This Turn: " + p[turn]);
		lblDie.setText("");
		lblMove.setText("");
		posY[0] = -100;
		posY[1] = -100;
		repaint();
		btnDie.setEnabled(true);

	}

	public void evalMove() {
		int enemy = (turn == 0) ? 1 : 0;
		Counter c1 = counters[turn][0];
		Counter c2 = counters[turn][1];
		for (int i = 0; i < 2; i++) {
			Counter e = counters[enemy][i];
			if ((c1.getPos() == e.getPos() || c2.getPos() == e.getPos())
					&& !e.isSafe()) {
				e.setPos(1);
			}
		}

		if (c1.isFinish() && c2.isFinish()) {
			JOptionPane.showMessageDialog(this, p[turn] + " won!",
					"Congratulations!", JOptionPane.PLAIN_MESSAGE);
			win.toMenu();
		}
	}

	public boolean isSafe(int row) {
		if (row == 1 || row == 5 || row == 11) {
			return true;
		}
		return false;
	}
	
	public void changeQuality(String s) {
		if(s == "low") {
			highQuality = false;
		}
		else if(s == "high") {
			highQuality = true;
		}
		repaint();
	}
	
	public void changeColour(int player, Color colour) {
		colours[player] = colour;
		repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnDie) {
			die = rand.nextInt(5) + 1;
			lblDie.setText(p[turn] + " rolled: " + die);
			btnDie.setEnabled(false);
			checkMove(turn, die);
		}
	}

}
