package game;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Font fontHeading = new Font("Arial", Font.BOLD, 40);
	JLabel lblHeading = new JLabel("AQADo - Main Menu");
	JLabel lblP1 = new JLabel("Enter Player 1's name:");
	JLabel lblP2 = new JLabel("Enter Player 2's name:");
	JTextField txtP1 = new JTextField(10);
	JTextField txtP2 = new JTextField(10);
	JButton btnPlay = new JButton("Play game");
	JButton btnQuit = new JButton("Quit");

	Window win;
	
	GridBagConstraints gc = new GridBagConstraints();

	public Menu(Window win) {
				
		this.win = win;
		setLayout(new GridBagLayout());
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 5;
		gc.weighty = 5;
		gc.gridwidth = 2;
		
		lblHeading.setFont(fontHeading);
		add(lblHeading, gc);
		
		gc.gridy++;
		gc.gridwidth = 1;
		add(lblP1, gc);
		
		gc.gridx++;
		add(txtP1, gc);
		
		gc.gridy++;
		gc.gridx--;
		add(lblP2, gc);
		
		gc.gridx++;
		add(txtP2, gc);
		
		gc.gridy++;
		gc.gridx--;
		gc.gridwidth = 2;
		
		btnPlay.addActionListener(this);
		add(btnPlay, gc);
		
		gc.gridy++;
		
		btnQuit.addActionListener(this);
		add(btnQuit, gc);
		
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnPlay) {
			win.toGame(txtP1.getText(), txtP2.getText());
		}
		
		if(e.getSource() == btnQuit) {
			win.dispose();
		}
	}

}
