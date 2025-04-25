import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;


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
	private JButton elecBtn;
	private JButton furnBtn;
	private JButton shopBackBtn;
	private JButton shopCartBtn;
	private JButton appCartBtn;
	private JButton elecCartBtn;
	private JButton furnCartBtn;
	private JButton empBackBtn;
	private JButton appBackBtn;
	private JButton furnBackBtn;
	private JButton elecBackBtn;
	private JButton empViewInv;
	private JButton empViewInvBackBtn;
	private JButton empEditInvAddBtn;
	private JButton empEditInvDelBtn;
	private JButton appSubmitBtn;
	private JButton elecSubmitBtn;
	private JButton furnSubmitBtn;
	private JButton empManipulateInv;
	
	private JPanel centerPanel;
	private JPanel shopPage;//all new JPanels needs to be defined as a variable bc the shop page is built inside the constructor where it cant be accessed by the action listener
	private JPanel empLog;
	private JPanel empPage;
	private JPanel categPanel;
	private JPanel shopBtmPanel;
	private JPanel empBtmPanel;
	private JPanel appBtmPanel;
	private JPanel furnBtmPanel;
	private JPanel appPage;
	private JPanel empManipulation;
	private JPanel empViewInvPanel;
	private JPanel empViewInvPanelSelection;
	private JPanel empViewInvPanelBack;
	private JPanel empEditInv;
	private JPanel empEditInvSelectPanel;
	private JPanel empEditInvDel;
	private JPanel elecPage;
	private JPanel furnPage;
	
	private JTextField userInput;
	private JTextField passInput;
	
	private JTextArea empViewInvResult;
	private JTextArea empEditInvView;
	
	private JComboBox<String> empViewInvChoice;
	private JComboBox<String> empEditInvBox;
	
	private Map<String, JTextField> quantityFields = new HashMap<>();
	
	private String[] empInvChoice = {" ", "Appliances", "Furniture", "Electronics"};
	
	int cartNum;
	private int parseQty(JTextField f) {
	    try {
	      return Integer.parseInt(f.getText().trim());
	    } catch (NumberFormatException e) {
	      return 0;  // turns all the textfields into a integer
	    }
	}
	
	public final_pos() {//constructor
		setPreferredSize(new Dimension(750,750));;//set size to 800x800
		setMaximumSize(new Dimension(750,750));
		setMinimumSize(new Dimension(750,750));
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
		
		elecBtn = new JButton("Electronics");
		elecBtn.setFont(new Font("Serif", Font.BOLD, 24));
		elecBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
		categPanel.add(elecBtn);
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
		empManipulateInv = new JButton("Add/Delete Inventory");
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
		
		//employee add/subtract inventory
		editInventory editInventory1 = new editInventory();
		empManipulateInv.addActionListener(editInventory1);
		
		empEditInv = new JPanel();
		empEditInv.setLayout(new BorderLayout());
		empEditInvAddBtn = new JButton("Add");
		empEditInvDelBtn = new JButton("Delete");
		JLabel empEditInvChoice = new JLabel("Add or Delete Inventory?");
		
		empEditInvSelectPanel = new JPanel();
		empEditInvSelectPanel.setLayout(new FlowLayout());
		empEditInvSelectPanel.add(empEditInvChoice);
		empEditInvSelectPanel.add(empEditInvAddBtn);
		empEditInvSelectPanel.add(empEditInvDelBtn);
		empEditInv.add(empEditInvSelectPanel, BorderLayout.NORTH);
		
		empEditInvDel = new JPanel();
		empEditInvBox = new JComboBox<>(empInvChoice);
		empEditInvDel.add(new JLabel("What category to delete from?"));
		empEditInvDel.add(empEditInvBox);
		empEditInvView = new JTextArea();
		empEditInvView.setEditable(false);
		empEditInvDel.add(empEditInvView);
		//empEditInv.add(empEditInvDel, BorderLayout.CENTER);
		
		//EmployeeViewInventoryPage

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
		JLabel jlabel_app=new JLabel("Appliances", SwingConstants.CENTER);//adds the text and makes sure its centered
		jlabel_app.setFont(new Font("Serif",Font.PLAIN,40));

		
		JPanel applabelPanel = new JPanel();
		applabelPanel.setLayout(new BoxLayout(applabelPanel, BoxLayout.Y_AXIS)); // Stack labels vertically

		// Wrapper to vertically center applabelPanel
		JPanel centerWrapper = new JPanel();
		centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
		centerWrapper.add(Box.createVerticalGlue());        // pushes content down
		centerWrapper.add(applabelPanel);                   // your label panel
		centerWrapper.add(Box.createVerticalGlue());        // pushes content up
		
		try {
		    String query = "SELECT Name, Price FROM Appliances";
		    Connection c1 = getConnection();
		    PreparedStatement ps = c1.prepareStatement(query);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        String appName = rs.getString("Name");
		        double appPrice = rs.getDouble("Price");

		        JPanel rowPanel = new JPanel();
		        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
		        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		       
		        JLabel appNamelabel = new JLabel(appName);
		        JLabel appPricelabel = new JLabel(String.format("%.2f",appPrice));
		        JTextField appQfield = new JTextField();
		        appQfield.setText("0");
		        appQfield.setEditable(true);
		    	appQfield.setPreferredSize(new Dimension(150, 25));
		        appQfield.setMaximumSize(new Dimension(150, 25));
		        appNamelabel.setFont(new Font("Arial", Font.PLAIN, 30));
		        appPricelabel.setFont(new Font("Arial", Font.PLAIN, 30));
		        
		        quantityFields.put(appName, appQfield); //gets the info from each text field
		       
		        rowPanel.add(appNamelabel);
		        rowPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		        rowPanel.add(appPricelabel);
		        rowPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		        rowPanel.add(appQfield);
		        applabelPanel.add(rowPanel);
		        applabelPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		    }
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
		
		//Submit button to confirm quantity
		JPanel appSubmitRow = new JPanel();
		appSubmitBtn = new JButton("Submit");
		appSubmitBtn.setFont(new Font("Serif",Font.PLAIN,20));
		appSubmitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		appSubmitRow.add(appSubmitBtn);
		
		
		// Add the label panel to the center of your main page
		appBtmPanel = new JPanel();
		appBtmPanel.setLayout(new BoxLayout(appBtmPanel, BoxLayout.X_AXIS));
		
		appBackBtn = new JButton("Back");
		appBackBtn.setFont(new Font("Serif", Font.BOLD, 24));
		appBackBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		appCartBtn = new JButton("Cart: 0");
		appCartBtn.setFont(new Font("Serif", Font.BOLD, 24));
		appCartBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		appBtmPanel.add(appBackBtn);
		appBtmPanel.add(Box.createHorizontalGlue());
		appBtmPanel.add(appCartBtn);
		

		centerWrapper.add(appSubmitRow);
		appPage.add(jlabel_app, BorderLayout.NORTH);
		appPage.add(centerWrapper, BorderLayout.CENTER);
		appPage.add(appBtmPanel, BorderLayout.SOUTH);
		pack();
		
		
		BackToShopPage backToShop = new BackToShopPage();
		appBackBtn.addActionListener(backToShop);
		ToCategPage toCateg = new ToCategPage();
		appBtn.addActionListener(toCateg);

		/*--------------------------------------------------------------------------*/
		//Electronics PAGE
		elecPage= new JPanel();
		elecPage.setLayout(new BorderLayout()); 	
		JLabel jlabel_elec=new JLabel("Electronics", SwingConstants.CENTER);//adds the text and makes sure its centered
		elecPage.add(jlabel_elec, BorderLayout.NORTH);//label is added to the app and placed at the top
		jlabel_elec.setFont(new Font("Serif",Font.PLAIN,50));
		
		JPanel eleclabelPanel = new JPanel();
		eleclabelPanel.setLayout(new BoxLayout(eleclabelPanel, BoxLayout.Y_AXIS)); // Stack labels vertically
		
		// Wrapper to vertically center applabelPanel
		JPanel centerWrapper2 = new JPanel();
		centerWrapper2.setLayout(new BoxLayout(centerWrapper2, BoxLayout.Y_AXIS));
		centerWrapper2.add(Box.createVerticalGlue());        // pushes content down
		centerWrapper2.add(eleclabelPanel);                   // your label panel
		centerWrapper2.add(Box.createVerticalGlue());        // pushes content up
		
		try {
		    String query = "SELECT Name, Price FROM Electronics";
		    Connection c1 = getConnection();
		    PreparedStatement ps = c1.prepareStatement(query);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        String elecName = rs.getString("Name");
		        double elecPrice = rs.getDouble("Price");

		        JPanel rowPanel = new JPanel();
		        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
		        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		       
		        JLabel elecNamelabel = new JLabel(elecName);
		        JLabel elecPricelabel = new JLabel(String.format("%.2f",elecPrice));
		        JTextField elecQfield = new JTextField();
		        elecQfield.setText("0");
		        elecQfield.setEditable(true);
		    	elecQfield.setPreferredSize(new Dimension(150, 25));
		        elecQfield.setMaximumSize(new Dimension(150, 25));
		        elecNamelabel.setFont(new Font("Arial", Font.PLAIN, 30));
		        elecPricelabel.setFont(new Font("Arial", Font.PLAIN, 30));
		        
		        quantityFields.put(elecName, elecQfield); //gets the info from each text field
		       
		        rowPanel.add(elecNamelabel);
		        rowPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		        rowPanel.add(elecPricelabel);
		        rowPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		        rowPanel.add(elecQfield);
		        eleclabelPanel.add(rowPanel);
		        eleclabelPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		    }
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
		
		//Submit button to confirm quantity
		JPanel elecSubmitRow = new JPanel();
		elecSubmitBtn = new JButton("Submit");
		elecSubmitBtn.setFont(new Font("Serif",Font.PLAIN,20));
		elecSubmitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		elecSubmitRow.add(elecSubmitBtn);
		
		
		// Add the label panel to the center of your main page
		JPanel elecBtmPanel = new JPanel();
		elecBtmPanel.setLayout(new BoxLayout(elecBtmPanel, BoxLayout.X_AXIS));
		
		elecBackBtn = new JButton("Back");
		elecBackBtn.setFont(new Font("Serif", Font.BOLD, 24));
		elecBackBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		elecCartBtn = new JButton("Cart: "+cartNum);
		elecCartBtn.setFont(new Font("Serif", Font.BOLD, 24));
		elecCartBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		elecBtmPanel.add(elecBackBtn);
		elecBtmPanel.add(Box.createHorizontalGlue());
		elecBtmPanel.add(elecCartBtn);
		
		elecPage.add(jlabel_elec, BorderLayout.NORTH);
		centerWrapper2.add(elecSubmitRow);
		elecPage.add(centerWrapper2, BorderLayout.CENTER);
		elecPage.add(elecBtmPanel, BorderLayout.SOUTH);
		
		
		elecBackBtn.addActionListener(backToShop);
		elecBtn.addActionListener(toCateg);
		
		/*--------------------------------------------------------------------------*/
		//FURNITURE PAGE
		furnPage= new JPanel();
		furnPage.setLayout(new BorderLayout()); 	
		JLabel jlabel_furn=new JLabel("Furniture", SwingConstants.CENTER);//adds the text and makes sure its centered
		jlabel_furn.setFont(new Font("Serif",Font.PLAIN,40));

		
		JPanel furnlabelPanel = new JPanel();
		furnlabelPanel.setLayout(new BoxLayout(furnlabelPanel, BoxLayout.Y_AXIS)); // Stack labels vertically

		// Wrapper to vertically center applabelPanel
		JPanel centerWrapper3 = new JPanel();
		centerWrapper3.setLayout(new BoxLayout(centerWrapper3, BoxLayout.Y_AXIS));
		centerWrapper3.add(Box.createVerticalGlue());        // pushes content down
		centerWrapper3.add(furnlabelPanel);                   // your label panel
		centerWrapper3.add(Box.createVerticalGlue());        // pushes content up
		
		try {
		    String query = "SELECT Name, Price FROM Furniture";
		    Connection c1 = getConnection();
		    PreparedStatement ps = c1.prepareStatement(query);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        String furnName = rs.getString("Name");
		        double furnPrice = rs.getDouble("Price");

		        JPanel rowPanel = new JPanel();
		        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
		        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		       
		        JLabel furnNamelabel = new JLabel(furnName);
		        JLabel furnPricelabel = new JLabel(String.format("%.2f",furnPrice));
		        JTextField furnQfield = new JTextField();
		        furnQfield.setText("0");
		        furnQfield.setEditable(true);
		    	furnQfield.setPreferredSize(new Dimension(150, 25));
		        furnQfield.setMaximumSize(new Dimension(150, 25));
		        furnNamelabel.setFont(new Font("Arial", Font.PLAIN, 30));
		        furnPricelabel.setFont(new Font("Arial", Font.PLAIN, 30));
		        
		        quantityFields.put(furnName, furnQfield); //gets the info from each text field
		       
		        rowPanel.add(furnNamelabel);
		        rowPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		        rowPanel.add(furnPricelabel);
		        rowPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		        rowPanel.add(furnQfield);
		        furnlabelPanel.add(rowPanel);
		        furnlabelPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		    }
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
		
		//Submit button to confirm quantity
		JPanel furnSubmitRow = new JPanel();
		furnSubmitBtn = new JButton("Submit");
		furnSubmitBtn.setFont(new Font("Serif",Font.PLAIN,20));
		furnSubmitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		furnSubmitRow.add(furnSubmitBtn);
		
		
		// Add the label panel to the center of your main page
		furnBtmPanel = new JPanel();
		furnBtmPanel.setLayout(new BoxLayout(furnBtmPanel, BoxLayout.X_AXIS));
		
		furnBackBtn = new JButton("Back");
		furnBackBtn.setFont(new Font("Serif", Font.BOLD, 24));
		furnBackBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		furnCartBtn = new JButton("Cart: " + cartNum);
		furnCartBtn.setFont(new Font("Serif", Font.BOLD, 24));
		furnCartBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		furnBtmPanel.add(furnBackBtn);
		furnBtmPanel.add(Box.createHorizontalGlue());
		furnBtmPanel.add(furnCartBtn);
		

		centerWrapper3.add(furnSubmitRow);
		furnPage.add(jlabel_furn, BorderLayout.NORTH);
		furnPage.add(centerWrapper3, BorderLayout.CENTER);
		furnPage.add(furnBtmPanel, BorderLayout.SOUTH);
		pack();
		
		

		furnBackBtn.addActionListener(backToShop);
		furnBtn.addActionListener(toCateg);
		
		
		/*--------------------------------------------------------------------------*/
		//Cart Number
		cartNum=0;
		cartUpdate cartUpdate = new cartUpdate();
		appSubmitBtn.addActionListener(cartUpdate);
		elecSubmitBtn.addActionListener(cartUpdate);
		furnSubmitBtn.addActionListener(cartUpdate);
		
		/*--------------------------------------------------------------------------*/		
		
		
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
	class BackToShopPage implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == appBackBtn || evt.getSource() == elecBackBtn || evt.getSource() == furnBackBtn) {
				setContentPane(shopPage);
				revalidate();
				repaint();
			}
		}
	}
	
	class ToCategPage implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == appBtn) {
				setContentPane(appPage);
				revalidate();
				repaint();
				pack();
			}
			else if(evt.getSource()==elecBtn) {
				setContentPane(elecPage);
				revalidate();
				repaint();
			}
			else if(evt.getSource()==furnBtn) {
				setContentPane(furnPage);
				revalidate();
				repaint();
			}
		}
	}
	class EmpDisplayInv implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == empViewInv) {
				EmpInv();
				empPage.remove(empManipulation);
				empPage.add(empViewInvPanel);
				empPage.add(empBtmPanel, BorderLayout.SOUTH);
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
					empPage.add(new JLabel("Employee Home"), BorderLayout.NORTH);
					setContentPane(empPage);
					revalidate();
					repaint();
				}
			}
		}
		class editInventory implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empEditInv) {
					empPage.remove(empManipulation);
					empPage.add(empEditInv);
					setContentPane(empPage);
					revalidate();
					repaint();
				}
			}
		}
		class cartUpdate implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == appSubmitBtn||evt.getSource() == elecSubmitBtn||evt.getSource() == furnSubmitBtn) {
					  int total = 0;
					  for (JTextField field : quantityFields.values()) {
					    total += parseQty(field);
					  }

					  cartNum = total;   
					        appCartBtn.setText("Cart: " + cartNum);
					        elecCartBtn.setText("Cart: " + cartNum);
					        furnCartBtn.setText("Cart: " + cartNum);
					        shopCartBtn.setText("Cart: " + cartNum);

					    
				}
			}
		}
		class backToEmpPage1 implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empViewInvBackBtn) {
					empPage.remove(empViewInvPanel);
					empPage.add(empManipulation);
					empPage.add(empBtmPanel, BorderLayout.SOUTH);
					empPage.add(new JLabel("Employee Home"), BorderLayout.NORTH);
					setContentPane(empPage);
					revalidate();
					repaint();
				}
			}
		}
		class addSubtractInv implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empManipulateInv) {
					empPage.remove(empManipulation);
					empPage.remove(empBtmPanel);
					empPage.add(empEditInv, BorderLayout.CENTER);
					empPage.add(empViewInvPanelBack, BorderLayout.SOUTH);
					revalidate();
					repaint();
				}
			}
		}

	}
