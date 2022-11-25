import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

/**
 * {@code Dashboard} class provides the dashboard page and all associated
 * utilities for the same. It displays statistics of a player only if he has
 * played atleast one game.
 * 
 * <p>
 * Copyright (c) 2021. All rights reserved to Emmanuel Jojy. Use is subject to
 * the above conditions.
 * </p>
 * 
 * @author Emmanuel Jojy
 * 
 */
public class Dashboard implements ActionListener {

	/**
	 * Pluggable panel which is plugged in to the frame generated by the {@code GUI}
	 * class.
	 */
	JPanel p;
	private JButton game, log, about;
	private JLabel detail, credit;
	private String name, time;
	private boolean newUser = true;
	private int tot, win, loss;

	/**
	 * Default constrctor provided for {@code Dashboard} class. Initializes the
	 * pluggable JPanel p.
	 * 
	 * To be invoked only from {@code GUI} class.
	 */
	public Dashboard() {
		p = new JPanel();
		p.setSize(1366, 720);
		p.setLayout(null);
		name = GUI.name;
		tot = 0;
		win = 0;
		loss = 0;
		JLabel head = new JLabel("Hello, @" + name.toLowerCase(), JLabel.CENTER);
		head.setBounds(283, 10, 800, 40);
		head.setFont(new Font("Consolas", Font.PLAIN, 28));
		p.add(head);

		analyse();

		if (newUser) {
			JLabel msg = new JLabel("IT'S EMPTY IN HERE. PLAY A GAME!", JLabel.CENTER);
			msg.setBounds(283, 250, 800, 40);
			msg.setFont(new Font("Calibri", Font.ITALIC, 24));
			p.add(msg);
		} else {
			JPanel min = new JPanel();
			min.setBounds(483, 90, 400, 290);
			min.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			min.setLayout(null);

			JLabel score = new JLabel("ACCURACY: " + (float) (win * 100.00 / tot) + "%", JLabel.CENTER);
			score.setBounds(20, 40, 350, 30);
			score.setFont(new Font("Calibri", Font.PLAIN, 20));
			min.add(score);

			JLabel last = new JLabel("LAST PLAYED: " + time.substring(0, 10), JLabel.CENTER);
			last.setBounds(20, 80, 350, 30);
			last.setFont(new Font("Calibri", Font.PLAIN, 20));
			min.add(last);

			JLabel total = new JLabel("GAMES PLAY: " + tot, JLabel.CENTER);
			total.setBounds(20, 120, 350, 30);
			total.setFont(new Font("Calibri", Font.PLAIN, 20));
			min.add(total);

			JLabel winner = new JLabel("GAMES WON: " + win, JLabel.CENTER);
			winner.setBounds(20, 160, 350, 30);
			winner.setFont(new Font("Calibri", Font.PLAIN, 20));
			min.add(winner);

			JLabel lost = new JLabel("GAMES LOST: " + loss, JLabel.CENTER);
			lost.setBounds(20, 200, 350, 30);
			lost.setFont(new Font("Calibri", Font.PLAIN, 20));
			min.add(lost);

			JLabel left = new JLabel("GAMES LEFT: " + (tot - win - loss), JLabel.CENTER);
			left.setBounds(20, 240, 350, 30);
			left.setFont(new Font("Calibri", Font.PLAIN, 20));
			min.add(left);

			p.add(min);
		}

		game = new JButton("NEW GAME");
		game.setBounds(483, 420, 100, 30);
		game.addActionListener(this);
		p.add(game);

		about = new JButton("ABOUT");
		about.setBounds(633, 420, 100, 30);
		about.addActionListener(this);
		p.add(about);

		log = new JButton("LOG OUT");
		log.setBounds(783, 420, 100, 30);
		log.addActionListener(this);
		p.add(log);

		detail = new JLabel("HangMan, a word guessing game. Created as part of Java Final Project.", JLabel.CENTER);
		detail.setFont(new Font("Calibri", Font.PLAIN, 20));
		detail.setBounds(383, 490, 600, 30);
		detail.setVisible(false);
		p.add(detail);

		credit = new JLabel("Adhil | Bivin | Emmanuel | Garry", JLabel.CENTER);
		credit.setFont(new Font("Calibri", Font.PLAIN, 20));
		credit.setBounds(383, 520, 600, 30);
		credit.setVisible(false);
		p.add(credit);
	}

	private void analyse() {
		String query = "SELECT * FROM game WHERE NAME = '" + name + "';";
		try {
			ResultSet res = Main.st.executeQuery(query);
			res.next();
			time = res.getString("TIMESTAMP");
			tot = res.getInt("TOTAL");
			win = res.getInt("WIN");
			loss = res.getInt("LOSS");
			newUser = false;
		} catch (SQLException e) {
		}
	}

	/**
	 * Overriden method of {@code ActionListener}. Refer original documentation of
	 * the same for more details.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == game)
			GUI.game();
		if (e.getSource() == log)
			GUI.login();
		if (e.getSource() == about) {
			if (detail.isVisible()) {
				detail.setVisible(false);
				credit.setVisible(false);
			} else {
				detail.setVisible(true);
				credit.setVisible(true);
			}
		}
	}
}