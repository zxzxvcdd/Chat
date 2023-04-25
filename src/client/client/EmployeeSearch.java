package client.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import client.clientDTO.EmpDTO;

public class EmployeeSearch extends JFrame implements Runnable, ActionListener {
	// UI Components
	private JLabel nameLabel;
	private JButton searchButton;

	ObjectInputStream ois;
	ObjectOutputStream oos;
	JTextField textField;
	HashMap<Object, Object> reqMap;
	public static HashMap<Object, Object> resMap = null;
	public static boolean call = false;
	EmpDTO myEmp;
	private JTextField textName;
	private JTextArea textArea;

	
	public EmployeeSearch(ObjectOutputStream oos, EmpDTO myEmp) {
		this.oos = oos;
		this.myEmp = myEmp;

		// Set the title of the JFrame
		setTitle("Employee Search");

		// Set the size of the JFrame
		setSize(513, 272);

		// Center the JFrame on the screen
		setLocationRelativeTo(null);

		// Set the default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the UI components
		nameLabel = new JLabel("�궗�썝紐�:");
		nameLabel.setBounds(0, 0, 499, 101);
		searchButton = new JButton("寃��깋�븯湲�");

		// Create a JPanel to hold the UI components
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(nameLabel);

		// Create a JPanel to hold the search button
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(searchButton);

		// Add the panels to the JFrame
		getContentPane().add(panel, BorderLayout.CENTER);

		textName = new JTextField();
		textName.setBounds(58, 40, 96, 21);
		panel.add(textName);
		textName.setColumns(10);

		textArea = new JTextArea();
		textArea.setBounds(0, 69, 487, 123);
		panel.add(textArea);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// Add an action listener to the search button
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Add your search logic here
				String name = textName.getText();
				try {

					reqMap = new HashMap<Object, Object>();

					// �슂泥� 蹂대궡湲�
					reqMap.put("command", "selectByName");
					reqMap.put("name", textName.getText());
					oos.writeObject(reqMap);

					oos.flush();

					// 寃곌낵 諛쏄린
//                    resMap = (HashMap<Object, Object>)ois.readObject();
//                    if (resMap == null) {
//                        return;
//                    }
//                    ArrayList<String> searchResult = (ArrayList<String>)resMap.get("searchResult");
					// 寃��깋 寃곌낵 異쒕젰
//                    for (String result : searchResult) {
//                        System.out.println(result);
//                    }
//                    
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

		setVisible(true);

		Thread t1 = new Thread(this);
		t1.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		System.out.println("testFrame Thread run");

		try {

			System.out.println("검색 쓰레드");

			while (true) {

				
				if (call) {
					
					System.out.println("검색 입력 쓰레드 처리 시작");
					String resCom = (String) resMap.get("command");

					
					switch (resCom) {

					case "afterSelectByName":

						String printEmpList = "";
						List<EmpDTO> empList = (List<EmpDTO>) resMap.get("empList");
						if (empList.size() != 0) {

							for (EmpDTO result : empList) {

								printEmpList += result + "\n";

							}
							textArea.setText(printEmpList);
						} else {
							textArea.setText("검색결과가 없습니다.");

						}
						
						call=false;

						break;

					}

				}else {System.out.println(call);};
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("search Tread error");
		}

	}
}