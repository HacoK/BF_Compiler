package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import rmi.RemoteHelper;


public class MainFrame extends JFrame {
	private JTextArea textArea;
	private JFrame frame;
	private JTextArea textAreaIn;
	private JTextArea textAreaOut;
	private ArrayList<String> userList=new ArrayList<>();
	private ArrayList<String> passwords=new ArrayList<>();
	private  String userName=null;
	private boolean duplicated=false; 
	
	public MainFrame() {
		// 创建窗体
		frame = new JFrame("BF Client");
		frame.setLayout(new BorderLayout());
		Font bigFont=new Font("sanserif",Font.BOLD,18);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem checkMenuItem = new JMenuItem("Check");
		fileMenu.add(checkMenuItem);
		
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		checkMenuItem.addActionListener(new MenuItemActionListener());
		
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		JMenuItem executeMenuItem = new JMenuItem("Excute");
		runMenu.add(executeMenuItem);

		executeMenuItem.addActionListener(new MenuItemActionListener());

		JMenu versionMenu = new JMenu("Version");
		menuBar.add(versionMenu);
		JMenuItem A = new JMenuItem("A");
		JMenuItem B = new JMenuItem("B");
		JMenuItem C = new JMenuItem("C");
		versionMenu.add(A);
		versionMenu.add(B);
		versionMenu.add(C);
		
		A.addActionListener(new VersionActionListener());
		B.addActionListener(new VersionActionListener());
		C.addActionListener(new VersionActionListener());
		
		JMenu userMenu = new JMenu("User");
		menuBar.add(userMenu);
		JMenuItem signInMenuItem = new JMenuItem("SignIn");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		JMenuItem registerMenuItem = new JMenuItem("Register");
		userMenu.add(signInMenuItem);
		userMenu.add(exitMenuItem);
		userMenu.add(registerMenuItem);
		
		signInMenuItem.addActionListener(new MenuItemActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		registerMenuItem.addActionListener(new MenuItemActionListener());
		
		frame.setJMenuBar(menuBar);
		
		textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.WHITE);
		textArea.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(bigFont);
		JScrollPane tScroller=new JScrollPane(textArea);
		tScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//frame.add(textArea, BorderLayout.CENTER);

		textAreaIn = new JTextArea();
		textAreaIn.setMargin(new Insets(10, 10, 10, 10));
		textAreaIn.setBackground(Color.WHITE);
		textAreaIn.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		textAreaIn.setLineWrap(true);
		textAreaIn.setWrapStyleWord(true);
		textAreaIn.setFont(bigFont);
		JScrollPane inScroller=new JScrollPane(textAreaIn);
		inScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		inScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaOut = new JTextArea();
		textAreaOut.setMargin(new Insets(10, 10, 10, 10));
		textAreaOut.setBackground(Color.WHITE);
		textAreaOut.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		textAreaOut.setLineWrap(true);
		textAreaOut.setWrapStyleWord(true);
		textAreaOut.setFont(bigFont);
		JScrollPane outScroller=new JScrollPane(textAreaOut);
		outScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		outScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JSplitPane splitPaneSouth = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inScroller, outScroller);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tScroller, splitPaneSouth);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
	   
		// 显示结果
		/*resultLabel = new JLabel();
		resultLabel.setText("result");
		frame.add(resultLabel, BorderLayout.SOUTH);*/

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocation(400, 200);
		frame.setVisible(true);
		splitPaneSouth.setDividerLocation(0.5);
		splitPane.setDividerLocation(0.7);
		splitPaneSouth.setEnabled(false);
		splitPane.setEnabled(false);
		
			try {
				String user_list =RemoteHelper.getInstance().getIOService().getUserList();
				String[] users=user_list.split("\n");
				String[] separate=null;
				for(String str:users){
					if(str.length()!=0){
					separate=str.split("/");
					userList.add(separate[0]);
					passwords.add(separate[1]);
					}
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }

	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("New")) {
				textArea.setText("");
				textAreaIn.setText("");
				textAreaOut.setText("");
			} else if(cmd.equals("Check")){
				try {
					System.out.println(RemoteHelper.getInstance().getIOService().readFileList(userName));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if (cmd.equals("Register")) {
				String userID = JOptionPane.showInputDialog(frame, "Please input an ID","ID input",JOptionPane.PLAIN_MESSAGE);
				String password = JOptionPane.showInputDialog(frame, "Please input a password","password input",JOptionPane.PLAIN_MESSAGE);
				Iterator<String> it=userList.iterator();
				while(it.hasNext()){
					if(userID.equals((String)it.next()))
							duplicated=true;
				}
				if(duplicated){
					JOptionPane.showConfirmDialog(frame,"Fail!Existing userID.","Notice",JOptionPane.OK_OPTION,JOptionPane.WARNING_MESSAGE);
				    duplicated=false;
				}
				else{
					userList.add(userID);
					passwords.add(password);
					try {
						RemoteHelper.getInstance().getIOService().writeUserList(userID+"/"+password+"\n");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showConfirmDialog(frame,"Register Successfully!","Notice",JOptionPane.OK_OPTION,JOptionPane.INFORMATION_MESSAGE);
				}
			} else if(cmd.equals("SignIn")){
				String userID = JOptionPane.showInputDialog(frame, "Please input your ID","ID input",JOptionPane.PLAIN_MESSAGE);
				String password = JOptionPane.showInputDialog(frame, "Please input your password","password input",JOptionPane.PLAIN_MESSAGE);
				boolean login=false;
				try {
					login=RemoteHelper.getInstance().getUserService().login(userID,password);
					if(login){
						userName=userID;
						JOptionPane.showConfirmDialog(frame,"Login Successfully!","Notice",JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE);
					}else{
						JOptionPane.showConfirmDialog(frame,"Error:Login Failed!","Notice",JOptionPane.OK_OPTION,JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(cmd.equals("Exit")){
				boolean logout=false;
				try {
					String userID = JOptionPane.showInputDialog(frame, "Please input the ID to exit","ID input",JOptionPane.PLAIN_MESSAGE);
					logout=RemoteHelper.getInstance().getUserService().logout(userID);
					if(logout){
						userName=null;
						JOptionPane.showConfirmDialog(frame,"Logout Successfully!","Notice",JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE);
					}else{
						JOptionPane.showConfirmDialog(frame,"UserNameError:Logout Failed!","Notice",JOptionPane.OK_OPTION,JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if (cmd.equals("Excute")) {
				try{
					textAreaOut.setText(RemoteHelper.getInstance().getExecuteService().execute(textArea.getText(),textAreaIn.getText()));
					}catch (RemoteException ex) {
						ex.printStackTrace();
					}
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textArea.getText();
			String file_name = JOptionPane.showInputDialog(frame, "Please input the file name","file_name",JOptionPane.PLAIN_MESSAGE);
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, userName, file_name);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}
	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code =null;
			String file_name = JOptionPane.showInputDialog(frame, "Please input the name of the file to read","file_name",JOptionPane.PLAIN_MESSAGE);
			try {
				code=RemoteHelper.getInstance().getIOService().readFile(userName,file_name);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			textArea.setText(code);
		}
	}
	class VersionActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			 String result = (String)JOptionPane.showInputDialog(frame,"Choose what you want to do","Operation Choice",JOptionPane.QUESTION_MESSAGE,null,new String[]{"Save","Load"},"Save");
			 if(result.equals("Save")){
				 File f = new File(e.getActionCommand());
					try {
						FileWriter fw = new FileWriter(f, false);
						fw.write(textArea.getText());
						fw.flush();
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			 }else{StringBuilder code=new StringBuilder();
				 try{
						FileReader fr=new FileReader(e.getActionCommand());
						BufferedReader br=new BufferedReader(fr);
						String str=null;
						while((str=br.readLine())!=null){
							code.append(str);
						}
						br.close();
						textArea.setText(code.toString());
					}catch(IOException e1){
						e1.printStackTrace();
					}
			 }
		}
	}
}


