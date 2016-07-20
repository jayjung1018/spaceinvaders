/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("Space Invaders");
		frame.setLocation(100, 100);
				
		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel scoreLabel = new JLabel("Score:");
		status_panel.add(scoreLabel);
		final JLabel score = new JLabel("0");
		status_panel.add(score);
		final JLabel livesLabel = new JLabel("Lives:");
		status_panel.add(livesLabel);
		final JLabel lives = new JLabel("3");
		status_panel.add(lives);

		// Main playing area
		final GameCourt court = new GameCourt(score, lives);
		court.setBackground(Color.BLACK);
		frame.add(court, BorderLayout.CENTER);

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
				court.start();
			}
		});
		
		control_panel.add(start);
		
		final JFrame insFrame = new JFrame("INSTRUCTIONS");
		insFrame.setLocation(620,100);
		final JPanel insPanel = new JPanel();
		insPanel.setLayout(new BoxLayout(insPanel, BoxLayout.PAGE_AXIS));
		insPanel.setBackground(Color.WHITE);
		insFrame.add(insPanel);
		
		JLabel title = new JLabel(new ImageIcon("images/title.png"));
		JLabel instruction = 
				new JLabel(new ImageIcon("images/instructions.png"));

		insPanel.add(title);
		insPanel.add(instruction);

		// Put the frame on the screen
		insFrame.pack();
		insFrame.setVisible(true);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


		// Set the game
		court.reset();
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
