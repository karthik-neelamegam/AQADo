package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Window extends JFrame implements ActionListener {
	static final int WIDTH = 450, HEIGHT = 750;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Menu menu;
	Game game;

	JMenuBar mb = new JMenuBar();
	JMenu mHelp = new JMenu("Help");
	JMenuItem miInst = new JMenuItem("Instructions");
	JMenu mSettings = new JMenu("Settings");
	JMenu mQual = new JMenu("Quality");
	JMenuItem miLow = new JMenuItem("Low");
	JMenuItem miHigh = new JMenuItem("High");
	JMenuItem miColour = new JMenuItem("Change colours");

	public void toGame(String p1, String p2) {
		remove(menu);
		if (p1.trim().equals(""))
			p1 = "Player 1";
		if (p2.trim().equals(""))
			p2 = "Player 2";
		game = new Game(this, p1, p2);
		add(game);
		validate();
	}

	public void toMenu() {
		game.removeAll();
		game.validate();
		dispose();
		init();
		setVisible(true);
	}

	public Window() {
		super("AQADo");

		init();
	}

	public void init() {
		pack();
		setSize(Window.WIDTH, Window.HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		if (getJMenuBar() == null) {
			setJMenuBar(mb);

			mHelp.add(miInst);
			miInst.addActionListener(this);
			mb.add(mHelp);

			mQual.add(miLow);
			miLow.addActionListener(this);
			mQual.add(miHigh);
			miHigh.addActionListener(this);

			mSettings.add(mQual);
			mSettings.add(miColour);
			miColour.addActionListener(this);
			mb.add(mSettings);
		}

		menu = new Menu(this);
		add(menu);
	}

	public static void main(String[] args) {
		Window window = new Window();
		window.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miInst) {
			JOptionPane
					.showMessageDialog(
							this,
							"AQADo is a turn-based game where two players compete to move both of their pieces to the end of the board. The board consists of 11 spaces. "
									+ "\nEach player has two pieces. At the start of the game all the pieces are placed on the first space of the board (START). "
									+ "\nThe first, fifth and eleventh spaces (highlighted in green) on the board are safe spaces. "
									+ "\n"
									+ "\nThe players take it in turns to roll a 5-sided die. The result of the die roll determines the move that a player is allowed to make on the board. "
									+ "\n Die Number: 1 --- Move one of your pieces one space nearer to FINISH"
									+ "\n Die Number: 2 --- Move one of your pieces two spaces nearer to FINISH"
									+ "\n Die Number: 3 --- Move one of your pieces three spaces nearer to FINISH"
									+ "\n Die Number: 4 --- Move one of your pieces one space back towards START"
									+ "\n Die Number: 5 --- Move one of your pieces to the next unoccupied space after the space it is currently on"
									+ "\n"
									+ "\n\u2022 If, as result of a move, a player's piece lands on a space containing an opponent's piece then the opponent's piece is moved back to START unless"
									+ "\n   the space landed on is a safe space; a piece on a safe space cannot be sent back to START."
									+ "\n\u2022 A player cannot move one of their pieces onto a space already occupied by their other piece unless the space is a safe space."
									+ "\n\u2022 A piece cannot move backwards if it is on START or forwards if it is on FINISH."
									+ "\n\u2022 If a player can make a move then they must do so (eg if they have one piece on START and one piece on FINISH and they roll a four on the die they "
									+ "\n   must move the piece on FINISH back one space). "
									+ "\n\u2022 If a die roll allows a player to move either of their pieces they may choose which one of their pieces they want to move."
									+ "\n\u2022 If a move would take a piece past FINISH then the piece is moved to FINISH (eg a piece on space nine which is moved three spaces nearer to FINISH "
									+ "\n   would land on FINISH)."
									+ "\n\u2022 If a player cannot make a move then their turn is over and the other player has their turn. "
									+ "\n"
									+ "\nA player wins the game when both of their pieces are on FINISH.",
							"Instructions", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource() == miLow && game != null) {
			game.changeQuality("low");
		}
		if (e.getSource() == miHigh && game != null) {
			game.changeQuality("high");
		}
		if (e.getSource() == miColour && game != null) {
			Color[] cColours = { Color.RED, Color.BLUE, Color.GREEN,
					Color.BLACK, Color.PINK, Color.YELLOW };
			String[] sColours = new String[cColours.length];
			for (int i = 0; i < cColours.length; i++) {
				sColours[i] = colorToString(cColours[i]);
			}
			Color colour[] = new Color[2];

			for (int i = 0; i < colour.length; i++) {
				colour[i] = cColours[JOptionPane.showOptionDialog(this,
						"Choose Player " + (i + 1) + "'s colour",
						"Changing colour", JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, sColours, 0)];

				game.changeColour(i, colour[i]);
			}

		}
	}

	public String colorToString(Color c) {
		if (c == Color.black)
			return "black";
		if (c == Color.blue)
			return "blue";
		if (c == Color.cyan)
			return "cyan";
		if (c == Color.gray)
			return "grey";
		if (c == Color.green)
			return "green";
		if (c == Color.magenta)
			return "magenta";
		if (c == Color.orange)
			return "orange";
		if (c == Color.pink)
			return "pink";
		if (c == Color.white)
			return "white";
		if (c == Color.yellow)
			return "yellow";
		if (c == Color.red)
			return "red";

		return c.toString();
	}

}
