package client.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JList;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.ScrollPane;

public class chatmainEmp extends JFrame {

	private JPanel contentPane;
	private JTextField depSearchText;
	private JButton chatButten;
	private JTextArea userListText;
	private JScrollPane scrollPane;
	private JButton depButten;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chatmainEmp frame = new chatmainEmp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public chatmainEmp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		depSearchText = new JTextField();
		depSearchText.setBounds(423, 10, 147, 40);
		contentPane.add(depSearchText);
		depSearchText.setColumns(10);
		
		JButton depSearchButton = new JButton("");
		depSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		depSearchButton.setIcon(new ImageIcon("C:\\Users\\ilove\\Downloads\\iconmonstr-search-thin-32.png"));
		depSearchButton.setBounds(569, 10, 40, 40);
		contentPane.add(depSearchButton);
		
		chatButten = new JButton("\uCC44\uD305\uBC29");
		chatButten.setBounds(225, 9, 195, 40);
		contentPane.add(chatButten);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 111, 584, 422);
		contentPane.add(scrollPane);
		userListText = new JTextArea();
		scrollPane.setViewportView(userListText);
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		scrollPane.setVerticalScrollBar(scrollBar);
		
		userListText = new JTextArea();
		userListText.setBounds(25, 111, 584, 422);
		contentPane.add(userListText);
		
		JLabel userLabel = new JLabel("\uBD80\uC11C \uC9C1\uCC45 \uC774\uB984 \uC804\uD654\uBC88\uD638");
		userLabel.setBounds(25, 75, 555, 30);
		contentPane.add(userLabel);
		
		depButten = new JButton("\uC870\uC9C1\uB3C4");
		depButten.setBounds(25, 9, 195, 40);
		contentPane.add(depButten);
	}
}
