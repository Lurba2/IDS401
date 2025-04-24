import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;


public class final_pos extends JFrame{

	private Connection connection;
	private String dbUrl="jdbc:sqlite:C:/Users/Leonard/Documents/IDS401FinalPos/Inventory.db";
	public Connection getConnection() throws SQLException{
		connection=DriverManager.getConnection(dbUrl);
		return connection;
	}

	
	private Container intro;
	
	private JButton shopBtn;
	private JButton empBtn;
	private JButton logBtn;
	private JButton appBtn;
	private JButton clothBtn;
	private JButton furnBtn;
	private JButton shopBackBtn;
	private JButton shopCartBtn;
	private JButton empBackBtn;
	private JButton checkInv;
	private JButton updateInv;
	private JButton changePrice;
	private JButton empViewInv;
	private JButton empViewInvBackBtn;
	
	private JPanel centerPanel;
	private JPanel shopPage;//all new JPanels needs to be defined as a variable bc the shop page is built inside the constructor where it cant be accessed by the action listener
	private JPanel empLog;
	private JPanel empPage;
	private JPanel categPanel;
	private JPanel shopBtmPanel;
	private JPanel empBtmPanel;
	private JPanel appPage;
	private JPanel appTable;
	private JPanel empManipulation;
	private JPanel empViewInvPanel;
	private JPanel empViewInvPanelSelection;
	private JPanel empViewInvPanelBack;
	
	private JTextField userInput;
	private JTextField passInput;
	
	private JTextArea empViewInvResult;
	
	private JComboBox<String> empViewInvChoice;
	
	
	public final_pos() {//constructor
		setPreferredSize(new Dimension(800,800));;//set size to 800x800
		setMaximumSize(new Dimension(800,800));
		setMinimumSize(new Dimension(800,800));
		setResizable(false); //doesnt allow user to resize the application
		setLocationByPlatform(true);//depends on the system you use, so it appears in the correct location
		


		//predefined data member on JFrame so the underlying process is also terminated along with the application
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		intro=getContentPane();
		intro.setLayout(new BorderLayout());
		
		//ADD WELCOME SIGN
		JLabel jlabel_welcome=new JLabel("Welcome to Store!", SwingConstants.CENTER);//adds the text and makes sure its centered
		intro.add(jlabel_welcome, BorderLayout.NORTH);//label is added to the app and placed at the top
		jlabel_welcome.setFont(new Font("Serif",Font.PLAIN,50));
		
		
		//ADD EMPLOYEE AND CUSTOMER BUTTONS (on a Jpanel)
		centerPanel= new JPanel();//create a Jpanel to add more labels so we can use a different layout other than BorderLayout
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));//makes the next things we add stack vertically
		
		JLabel jlabel_newCust=new JLabel("New Customer? Lets get started!", SwingConstants.CENTER);//adds the text and makes sure its centered
		jlabel_newCust.setFont(new Font("Serif",Font.PLAIN,30));
		jlabel_newCust.setAlignmentX(Component.CENTER_ALIGNMENT);//label for customers
		
		JLabel jlabel_emp=new JLabel("Current Employee?", SwingConstants.CENTER);//adds the text and makes sure its centered
		jlabel_emp.setFont(new Font("Serif",Font.PLAIN,30));
		jlabel_emp.setAlignmentX(Component.CENTER_ALIGNMENT);//label for employees
		
		shopBtn = new JButton("Shop Now");
		shopBtn.setFont(new Font("Serif", Font.BOLD, 24));
		shopBtn.setAlignmentX(Component.CENTER_ALIGNMENT);//button to take customer to shop page
		
		empBtn = new JButton("Log In");
		empBtn.setFont(new Font("Serif", Font.BOLD, 24));
		empBtn.setAlignmentX(Component.CENTER_ALIGNMENT);//button to take employee to log in
		
		//add everything to the center Jpanel in order
		centerPanel.add(Box.createRigidArea(new Dimension(0, 150)));//add space between the title and buttons(we could also add a photo)
		centerPanel.add(jlabel_newCust);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		centerPanel.add(shopBtn);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		centerPanel.add(jlabel_emp);
		centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		centerPanel.add(empBtn);
        intro.add(centerPanel, BorderLayout.CENTER);
        pack();
        
        //ADD ACTION LISTENER TO TAKE EMPLOYEES/SHOPPERS TO NEW PAGE
		IntroToPage switchIntro=new IntroToPage();
		shopBtn.addActionListener(switchIntro);
		empBtn.addActionListener(switchIntro);
        
		//EMPLOYEE LOG IN
		empLog = new JPanel();
		empLog.setLayout(new BoxLayout(empLog, BoxLayout.Y_AXIS));
		empLog.setBorder(BorderFactory.createCompoundBorder(  //only one setBorder takes effect so have to use compound border
			    BorderFactory.createLineBorder(Color.BLACK),
			    BorderFactory.createEmptyBorder(8, 20, 10, 20)
			));
		empLog.setPreferredSize(new Dimension (275,206));
		empLog.setMaximumSize(new Dimension (275,206));//required when changing the dimension of a panel in a box layout
		
		JPanel userRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel user= new JLabel("Username: ");
		user.setFont(new Font("Serif",Font.PLAIN,20));
		userInput = new JTextField();
		userInput.setPreferredSize(new Dimension(150, 25));
		userInput.setEditable(true);
		userRow.add(user);
		userRow.add(userInput);
		
		JPanel passRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel pass= new JLabel("Password: ");	
		pass.setFont(new Font("Serif",Font.PLAIN,20));
		passInput = new JTextField();
		passInput.setPreferredSize(new Dimension(150, 25));
		passInput.setEditable(true);
		passRow.add(pass);
		passRow.add(passInput);
		
		logBtn = new JButton("Log In");
		logBtn.setFont(new Font("Serif",Font.PLAIN,18));
		
			//let employees enter their username and password
		empLog.add(userRow);
		empLog.add(passRow);
		empLog.add(logBtn);
		
		empLogIn LogIn=new empLogIn();
		logBtn.addActionListener(LogIn);


/*--------------------------------------------------------------------------*/
		
		//SHOP PAGE
		//JPanel shopPage= new JPanel();-- cannot do this bc its defined as a variable already and this would override it
		shopPage= new JPanel();
		JLabel categLabel = new JLabel("Shop by Category",SwingConstants.CENTER);
		shopPage.setLayout(new BorderLayout());
		categLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		shopPage.add(categLabel, BorderLayout.NORTH);
		
		categPanel = new JPanel();
		categPanel.setLayout(new BoxLayout(categPanel, BoxLayout.Y_AXIS));


		//Buttons for different categories
		appBtn = new JButton("Appliances");
		appBtn.setFont(new Font("Serif", Font.BOLD, 24));
		appBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		clothBtn = new JButton("Clothing");
		clothBtn.setFont(new Font("Serif", Font.BOLD, 24));
		clothBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		furnBtn = new JButton("Furniture");
		furnBtn.setFont(new Font("Serif", Font.BOLD, 24));
		furnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
				/*---------------------*/
		
		//Bottom buttons for back and the cart
		shopBtmPanel = new JPanel();
		shopBtmPanel.setLayout(new BoxLayout(shopBtmPanel, BoxLayout.X_AXIS));
		
		shopBackBtn = new JButton("Back");
		shopBackBtn.setFont(new Font("Serif", Font.BOLD, 24));
		shopBackBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		shopCartBtn = new JButton("Cart: 0");
		shopCartBtn.setFont(new Font("Serif", Font.BOLD, 24));
		shopCartBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		shopBtmPanel.add(shopBackBtn);
		shopBtmPanel.add(Box.createHorizontalGlue());
		shopBtmPanel.add(shopCartBtn);
		
		categPanel.add(Box.createRigidArea(new Dimension(0, 150)));
		categPanel.add(appBtn);
		categPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		categPanel.add(clothBtn);
		categPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		categPanel.add(furnBtn);
		shopPage.add(categPanel, BorderLayout.CENTER);
		shopPage.add(shopBtmPanel, BorderLayout.SOUTH);
		pack();
		
		BackToIntro shopBack=new BackToIntro();
		shopBackBtn.addActionListener(shopBack);
/*--------------------------------------------------------------------------*/	
		
		//EMPLOYEE PAGE
		empPage= new JPanel();
		empPage.setLayout(new BorderLayout()); 
		empManipulation = new JPanel();
		empManipulation.setLayout(new GridLayout(3,1, 10, 10));
		
		Font font1= new Font("Serif", Font.BOLD, 24);
		JButton empManipulateInv = new JButton("Add/Delete Inventory");
		empManipulateInv.setFont(font1);
		empViewInv = new JButton("View Inventory");
		empViewInv.setFont(font1);
		
		empViewInv.addActionListener(new EmpDisplayInv());
		JButton empChangeInvPrice = new JButton("Change Prices");
		empChangeInvPrice.setFont(font1);
		
		empBtmPanel = new JPanel();
		empManipulation.add(empManipulateInv, BorderLayout.CENTER);
		empManipulation.add(empViewInv, BorderLayout.CENTER);
		empManipulation.add(empChangeInvPrice, BorderLayout.CENTER);
		empPage.add(new JLabel("Employee Home"), BorderLayout.NORTH);
		empBtmPanel.setLayout(new BoxLayout(empBtmPanel, BoxLayout.X_AXIS));
		empPage.add(empManipulation, BorderLayout.CENTER);
		empBackBtn = new JButton("Back");
		empBackBtn.setFont(font1);
		empBackBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		empBtmPanel.add(empBackBtn);
		empPage.add(empBtmPanel, BorderLayout.SOUTH);
		BackToIntro Back=new BackToIntro();
		empBackBtn.addActionListener(Back);
		
		//EmployeeViewInventoryPage

		String[] empInvChoice = {" ", "Appliances", "Furniture", "Electronics"};
		empViewInvPanel = new JPanel();  //main view inventory page
		empViewInvPanelSelection = new JPanel(); //selection part of the page
		empViewInvPanelBack = new JPanel(); //where back button will be
		empViewInvPanelBack.setLayout(new BoxLayout(empViewInvPanelBack, BoxLayout.X_AXIS));
		empViewInvBackBtn = new JButton("Back");
		empViewInvBackBtn.setFont(font1);
		empViewInvBackBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		empViewInvPanelBack.add(empViewInvBackBtn);
		
		backToEmpPage backToEmpPage1 = new backToEmpPage();
		empViewInvBackBtn.addActionListener(backToEmpPage1);
		
		empViewInvResult = new JTextArea();
		empViewInvResult.setEditable(false);//where our inventory will be output.

		empViewInvPanelSelection.setLayout(new FlowLayout());
		
		empViewInvPanel.setLayout(new BorderLayout());
		JLabel empViewInvResultChoice = new JLabel("View inventory from");
		Font empViewInvChoiceFont = new Font("Seriff", Font.BOLD, 36);
		empViewInvResultChoice.setFont(empViewInvChoiceFont);
		empViewInvPanelSelection.add(empViewInvResultChoice);
		empViewInvPanel.add(new JScrollPane(empViewInvResult));
		empViewInvChoice = new JComboBox<>(empInvChoice);
		empViewInvChoice.setFont(empViewInvChoiceFont);
		empViewInvPanelSelection.add(empViewInvChoice);
		empViewInvPanel.add(empViewInvPanelSelection, BorderLayout.NORTH);
		empViewInvChoiceListener empViewInvChoiceListener1 = new empViewInvChoiceListener();
		empViewInvChoice.addActionListener(empViewInvChoiceListener1);
		
		empViewInvPanel.add(empViewInvPanelBack, BorderLayout.SOUTH); //back button
		empViewInvPanelBack.setFont(new Font("Serif", Font.BOLD, 24));

/*--------------------------------------------------------------------------*/
		//APPLIANCE PAGE
		appPage= new JPanel();
		appPage.setLayout(new BorderLayout()); 		
		
		
		setVisible(true);//makes sure everything done previously is seen
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final_pos connect1 = new final_pos();
		try {
			connect1.getConnection();//should be defined in the JAR file
			System.out.println("Connection to SQLite has been established.");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	class IntroToPage implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource()==shopBtn){
				setContentPane(shopPage);
				revalidate();
				repaint();
			}
			else if (evt.getSource()==empBtn){
				centerPanel.remove(empBtn);
				centerPanel.add(empLog);
				empLog.setAlignmentX(Component.CENTER_ALIGNMENT);
				revalidate();
				repaint();
			}

			}


		}
	//----Calls employee view of items-----
	public void EmpInv() throws Error{
		String empViewInvCall = "";
		String selectedTable = "";
		try {
			Connection connection = DriverManager.getConnection(dbUrl);
			selectedTable = (String) empViewInvChoice.getSelectedItem(); 
			empViewInvCall = "SELECT * FROM " + selectedTable + " WHERE Quantity != 0";
			Statement stmtEmpViewInv = connection.createStatement();
			ResultSet rsEmpViewInv = stmtEmpViewInv.executeQuery(empViewInvCall);
			empViewInvResult.setText(" ");
			while (rsEmpViewInv.next()){
				String name= rsEmpViewInv.getString("Name");
				int quantity = rsEmpViewInv.getInt("Quantity");
				double price = rsEmpViewInv.getDouble("Price");
				String output = "Item: " + name + ", Quantity: " + quantity + ", Price: " + price + "\n";
				empViewInvResult.append(output);
				empViewInvResult.setFont(new Font("Seriff",Font.PLAIN, 30));
			}		
		} catch(SQLException e) {
			System.out.println(e.getMessage() +empViewInvCall );
		}
	}
	//Employee Login after pressing log in
	class empLogIn implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
            String username = userInput.getText();
            String password = passInput.getText();
			if (evt.getSource()==logBtn){
				if (username.equals("employee32") && password.equals("Passw0rd1")) {
					setContentPane(empPage);
					revalidate();
					repaint();
				}
				else if (!username.equals("employee32") || !password.equals("Passw0rd1")) {
	               passInput.setText("");
	               userInput.setText("");
	                JOptionPane.showMessageDialog(null, "Username or Password is incorrect. Please try again.");
				}
		}
		}
	}
	class BackToIntro implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource()==shopBackBtn ||evt.getSource()== empBackBtn){
	            passInput.setText("");
	            userInput.setText("");
				centerPanel.remove(empLog);
				centerPanel.add(empBtn);
				setContentPane(intro);
				revalidate();
				repaint();
			}
			}
		}
	
	class EmpDisplayInv implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == empViewInv) {
				empPage.remove(empManipulation);
				empPage.add(empViewInvPanel);
				setContentPane(empViewInvPanel);
				revalidate();
				repaint();
			}
		}
	}
	class empViewInvChoiceListener implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == empViewInvChoice) {
				EmpInv();
			}
		}
	}
	class backToEmpPage implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == empViewInvBackBtn) {
				empPage.remove(empViewInvPanel);
				empPage.add(empManipulation);
				empPage.add(empBtmPanel, BorderLayout.SOUTH);
				setContentPane(empPage);
				revalidate();
				repaint();
			}
		}
	}
	}