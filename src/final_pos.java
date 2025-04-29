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
	private JButton empEditInvBackBtn;
	private JButton empEditInvAddBtnSubmit;
	private JButton empEditInvDelSubmit;
	private JButton empChangePriceBtn;
	private JButton empChangePriceQuantityBackBtn;
	private JButton empChangeInvPrice;
	
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
	private JPanel empEditInvBack;
	private JPanel empEditInvAdd;
	private JPanel empEditInvCategory;
	private JPanel empEditInvNamePanel;
	private JPanel empEditInvPricePanel;
	private JPanel empEditInvQuantityPanel;
	private JPanel empEditInvSubmitPanel;
	private JPanel empEditInvDelCategory;
	private JPanel empEditInvDelName;
	private JPanel empEditInvDelSubmitPanel;
	private JPanel empEditInvDelResultsPanel;
	private JPanel empEditInvResultPanel;
	private JPanel applabelPanel;
	private JPanel furnlabelPanel;
	private JPanel eleclabelPanel;
	private JPanel empChangePriceQuantityPanel;
	private JPanel empChangePriceQuantitySelection;
	private JPanel empChangePriceQuantityBackBtnPanel;
	private JPanel empChangePricePanel;
	private JPanel empChangePriceFromPanel;
	private JPanel empChangePriceNamePanel;
	private JPanel empChangePriceNewPricePanel;

	
	private JTextField userInput;
	private JTextField passInput;
	private JTextField empDeleteInvName;
	private JTextField empEditInvPrice;
	private JTextField empEditInvQuantity;
	private JTextField empEditInvNameField;
	private JTextField newPriceText;
	
	private JTextArea empViewInvResult;
	
	private JComboBox<String> empViewInvChoice;
	private JComboBox<String> empEditInvBox;
	private JComboBox<String> empEditInvBox1;
	private JComboBox<String> empChangePriceQuantity;
	private JComboBox<String> empChangePriceBox;
	private JComboBox<String> empChangePriceCategory;
	
	private JLabel empEditInvDelResults;
	private JLabel empEditInvResult;
	private JLabel empSelectPriceQuantity;
	
	Font font2 = new Font("Seriff", Font.BOLD, 30);
	
	private Map<String, JTextField> quantityFields = new HashMap<>();
	private Map<String,String> itemTableMap = new HashMap<>();
	
	private String[] empInvChoice = {" ", "Appliances", "Furniture", "Electronics"};
	private String[] empChangePriceQuantityChoice = {" ", "Price", "Quantity"};
	
	int cartNum;
	private int parseQty(JTextField f) {
	    try {
	      return Integer.parseInt(f.getText().trim());
	    } catch (NumberFormatException e) {
	      return 0;  // turns all the textfields into a integer
	    }
	}
	private void loadAppliances() {
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
		        itemTableMap.put(appName, "Appliances");
		       
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
	}
	
	private void loadElectronics() {
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
		        itemTableMap.put(elecName, "Electronics");
		       
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
	}
	
	private void loadFurniture() {
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
		        itemTableMap.put(furnName, "Furniture");
		       
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
	}
	private void calculateCart() {
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
		empChangeInvPrice = new JButton("Change Price/Quantity");
		empChangeInvPrice.setFont(font1);
		changePriceQuantity c2 = new changePriceQuantity();
		empChangeInvPrice.addActionListener(c2);
		
		empBtmPanel = new JPanel();
		empManipulation.add(empManipulateInv, BorderLayout.CENTER);
		empManipulation.add(empViewInv, BorderLayout.CENTER);
		empManipulation.add(empChangeInvPrice, BorderLayout.CENTER);
		empPage.add(new JLabel("Employee Home"), BorderLayout.NORTH);
		empBtmPanel.setLayout(new BoxLayout(empBtmPanel, BoxLayout.X_AXIS));
		empPage.add(empManipulation, BorderLayout.CENTER);
		empBackBtn = new JButton("Back");
		empBackBtn.setFont(font1);
		empBtmPanel.add(empBackBtn);
		empPage.add(empBtmPanel, BorderLayout.SOUTH);
		BackToIntro Back=new BackToIntro();
		empBackBtn.addActionListener(Back);
		
		//employee change price/quantity.
		empChangePriceQuantityPanel = new JPanel();//main change price / quantity panel
		empChangePriceQuantityPanel.setLayout(new BorderLayout());
		empChangePriceBtn = new JButton();
		empChangePriceQuantityBackBtnPanel = new JPanel();
		empChangePriceQuantityBackBtnPanel.setLayout(new BoxLayout(empChangePriceQuantityBackBtnPanel, BoxLayout.X_AXIS));
		backToEmpPage1 b1 = new backToEmpPage1();
		empChangePriceQuantityBackBtn = new JButton("Back");
		empChangePriceQuantityBackBtn.addActionListener(b1);
		empChangePriceQuantityBackBtn.setFont(font1);
		empChangePriceQuantityBackBtnPanel.add(empChangePriceQuantityBackBtn, BorderLayout.SOUTH);
		empChangePriceQuantityPanel.add(empChangePriceQuantityBackBtnPanel, BorderLayout.SOUTH);
		
		empChangePriceQuantitySelection = new JPanel();
		empSelectPriceQuantity = new JLabel("Change price or quantity:");
		empChangePriceQuantity = new JComboBox<>(empChangePriceQuantityChoice);
		selectPriceQuantity sq1 = new selectPriceQuantity();
		empChangePriceQuantity.addActionListener(sq1);
		empChangePriceQuantitySelection.add(empSelectPriceQuantity);
		empChangePriceQuantitySelection.add(empChangePriceQuantity);
		empChangePriceQuantityPanel.add(empChangePriceQuantitySelection, BorderLayout.NORTH);
		
		empChangePricePanel = new JPanel();
		JLabel changePrice = new JLabel("Change price from table:");
		JLabel changePriceFrom = new JLabel("with the name of:");
		empChangePriceBox = new JComboBox<>(); // box that will take the string array of named returned.
		empChangePriceCategory = new JComboBox<>(empInvChoice);
		empChangePriceFromPanel = new JPanel();
		empChangePriceFromPanel.add(changePrice);
		empChangePriceFromPanel.add(empChangePriceCategory);
		empChangePriceNamePanel = new JPanel();
		empChangePriceNamePanel.add(changePriceFrom);
		empChangePriceNamePanel.add(empChangePriceBox);
		empChangePricePanel.add(empChangePriceFromPanel);
		empChangePricePanel.add(empChangePriceNamePanel);
		
		empChangePriceNewPricePanel = new JPanel();
		JLabel newPriceLabel = new JLabel("New price:");
		newPriceText = new JTextField(10);
		empChangePriceNewPricePanel.add(newPriceLabel);
		empChangePriceNewPricePanel.add(newPriceText);
		
		//employee add/subtract inventory
		addSubtractInv addSubInv = new addSubtractInv();
		empManipulateInv.addActionListener(addSubInv);
		
		empEditInv = new JPanel();
		empEditInv.setLayout(new BorderLayout());
		empEditInvAddBtn = new JButton("Add");
		empEditInvAddBtn.setFont(font2);
		addInv addInv1 = new addInv();
		empEditInvAddBtn.addActionListener(addInv1);
		empEditInvDelBtn = new JButton("Delete");
		empEditInvDelBtn.setFont(font2);
		deleteInv deleteInv1 = new deleteInv();
		empEditInvDelBtn.addActionListener(deleteInv1);
		JLabel empEditInvChoice = new JLabel("Add or Delete Inventory?");
		empEditInvChoice.setFont(font2);
		
		empEditInvSelectPanel = new JPanel();
		empEditInvSelectPanel.setLayout(new FlowLayout());
		empEditInvSelectPanel.add(empEditInvChoice);
		empEditInvSelectPanel.add(empEditInvAddBtn);
		empEditInvSelectPanel.add(empEditInvDelBtn);
		empEditInv.add(empEditInvSelectPanel, BorderLayout.NORTH);

		empEditInvDel = new JPanel();
		empEditInvDel.setLayout(new BoxLayout(empEditInvDel, BoxLayout.Y_AXIS));
		empEditInvBox = new JComboBox<>(empInvChoice); //where category delete is held
		empEditInvBox.setFont(font2);
		JLabel empEditInvCategory1 = new JLabel("Category:");
		empEditInvCategory1.setFont(font2);
		empEditInvDelCategory = new JPanel();
		empEditInvDelCategory.add(empEditInvCategory1);
		empEditInvDelCategory.add(empEditInvBox);
		empEditInvDel.add(empEditInvDelCategory);
		JLabel empDeleteInvChoice = new JLabel("Item name:");
		empEditInvDelName = new JPanel();
		empDeleteInvChoice.setFont(font2);
		empEditInvDelName.add(empDeleteInvChoice);
		empDeleteInvName = new JTextField(10); //where name delete is 
		empDeleteInvName.setFont(font2);
		empEditInvDelName.add(empDeleteInvName);
		empEditInvDel.add(empEditInvDelName);
		
		empEditInvDelSubmitPanel = new JPanel();
		empEditInvDelSubmit = new JButton("Submit");
		deleteInventoryListener d1 = new deleteInventoryListener();
		empEditInvDelSubmit.addActionListener(d1);
		empEditInvDelSubmit.setFont(font2);
		empEditInvDelSubmitPanel.add(empEditInvDelSubmit);
		empEditInvDel.add(empEditInvDelSubmitPanel);
		
		empEditInvDelResultsPanel = new JPanel();
		empEditInvDelResults = new JLabel();
		empEditInvDel.add(empEditInvDelResults);
		empEditInvDel.add(empEditInvDelResultsPanel);

		empEditInvAdd = new JPanel();
		empEditInvAdd.setLayout(new BoxLayout(empEditInvAdd, BoxLayout.Y_AXIS));
		empEditInvBox1 = new JComboBox<>(empInvChoice);
		empEditInvBox1.setFont(font2);
		JLabel empEditInvCat = new JLabel("Category:");
		empEditInvCat.setFont(font2);
		empEditInvCategory = new JPanel();
		empEditInvCategory.add(empEditInvCat);
		empEditInvCategory.add(empEditInvBox1);
		empEditInvAdd.add(empEditInvCategory);
		empEditInvBox1.setFont(font2);
		
		empEditInvNamePanel = new JPanel();
		JLabel empEditInvName = new JLabel("Name of item:");
		empEditInvNamePanel.add(empEditInvName);
		empEditInvName.setFont(font2);
		empEditInvNameField = new JTextField(10);
		empEditInvNameField.setFont(font2);
		empEditInvNamePanel.add(empEditInvNameField);
		empEditInvAdd.add(empEditInvNamePanel);
		
		empEditInvPricePanel = new JPanel();
		JLabel empEditInvAddLabel = new JLabel("Price of item:");
		empEditInvPricePanel.add(empEditInvAddLabel);
		empEditInvAddLabel.setFont(font2);
		empEditInvPrice = new JTextField(10);
		empEditInvPrice.setFont(font2);
		empEditInvPricePanel.add(empEditInvPrice);
		empEditInvAdd.add(empEditInvPricePanel);
		
		JLabel empEditInvAddQuantityLabel = new JLabel("Quantity of item:");
		empEditInvQuantityPanel = new JPanel();
		empEditInvQuantityPanel.add(empEditInvAddQuantityLabel);
		empEditInvAddQuantityLabel.setFont(font2);
		empEditInvQuantity = new JTextField(10);
		empEditInvQuantity.setFont(font2);
		empEditInvQuantityPanel.add(empEditInvQuantity);
		empEditInvAdd.add(empEditInvQuantityPanel);
		
		empEditInvSubmitPanel = new JPanel();
		empEditInvAddBtnSubmit = new JButton("Submit");
		addInvListener a1 = new addInvListener();
		empEditInvAddBtnSubmit.addActionListener(a1);
		empEditInvAddBtnSubmit.setFont(font2);
		empEditInvSubmitPanel.add(empEditInvAddBtnSubmit);
		empEditInvAdd.add(empEditInvSubmitPanel);
		
		empEditInvResultPanel = new JPanel();
		empEditInvResult = new JLabel();
		empEditInvResultPanel.add(empEditInvResult);
		empEditInvAdd.add(empEditInvResultPanel);
		
		empEditInvBack = new JPanel();
		empEditInvBack.setLayout(new BoxLayout(empEditInvBack, BoxLayout.X_AXIS));
		backToEmpPage1 backToEmpPage3 = new backToEmpPage1();
		empEditInvBackBtn = new JButton("Back");
		empEditInvBackBtn.setFont(font1);
		empEditInvBackBtn.addActionListener(backToEmpPage3);
		empEditInvBack.add(empEditInvBackBtn);
		empEditInv.add(empEditInvBack, BorderLayout.SOUTH);

		empViewInvPanel = new JPanel();  //main view inventory page
		empViewInvPanelSelection = new JPanel(); //selection part of the page
		empViewInvPanelBack = new JPanel(); //where back button will be
		empViewInvPanelBack.setLayout(new BoxLayout(empViewInvPanelBack, BoxLayout.X_AXIS));
		empViewInvBackBtn = new JButton("Back");
		empViewInvBackBtn.setFont(font1);
		empViewInvPanelBack.add(empViewInvBackBtn);
		
		backToEmpPage1 backToEmpPage2 = new backToEmpPage1();
		empViewInvBackBtn.addActionListener(backToEmpPage2); //what i changed
		
		empViewInvResult = new JTextArea();
		empViewInvResult.setEditable(false);//where our inventory will be output.

		empViewInvPanelSelection.setLayout(new FlowLayout());
		
		empViewInvPanel.setLayout(new BorderLayout());
		JLabel empViewInvResultChoice = new JLabel("View inventory from");
		empViewInvResultChoice.setFont(font2);
		empViewInvPanelSelection.add(empViewInvResultChoice);
		empViewInvPanel.add(new JScrollPane(empViewInvResult));
		empViewInvChoice = new JComboBox<>(empInvChoice);
		empViewInvChoice.setFont(font2);
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

		
		applabelPanel = new JPanel();
		applabelPanel.setLayout(new BoxLayout(applabelPanel, BoxLayout.Y_AXIS)); // Stack labels vertically

		// Wrapper to vertically center applabelPanel
		JPanel centerWrapper = new JPanel();
		centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
		centerWrapper.add(Box.createVerticalGlue());        // pushes content down
		centerWrapper.add(applabelPanel);                   // your label panel
		centerWrapper.add(Box.createVerticalGlue());        // pushes content up
		
		loadAppliances();//load appliances
		
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
		
		eleclabelPanel = new JPanel();
		eleclabelPanel.setLayout(new BoxLayout(eleclabelPanel, BoxLayout.Y_AXIS)); // Stack labels vertically
		
		// Wrapper to vertically center applabelPanel
		JPanel centerWrapper2 = new JPanel();
		centerWrapper2.setLayout(new BoxLayout(centerWrapper2, BoxLayout.Y_AXIS));
		centerWrapper2.add(Box.createVerticalGlue());        // pushes content down
		centerWrapper2.add(eleclabelPanel);                   // your label panel
		centerWrapper2.add(Box.createVerticalGlue());        // pushes content up
		
	loadElectronics();
		
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

		
		furnlabelPanel = new JPanel();
		furnlabelPanel.setLayout(new BoxLayout(furnlabelPanel, BoxLayout.Y_AXIS)); // Stack labels vertically

		// Wrapper to vertically center applabelPanel
		JPanel centerWrapper3 = new JPanel();
		centerWrapper3.setLayout(new BoxLayout(centerWrapper3, BoxLayout.Y_AXIS));
		centerWrapper3.add(Box.createVerticalGlue());        // pushes content down
		centerWrapper3.add(furnlabelPanel);                   // your label panel
		centerWrapper3.add(Box.createVerticalGlue());        // pushes content up
		
		loadFurniture();
		
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
			empViewInvResult.setText("");
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
	public void empDeleteInv() throws Error{
		String deleteName = "";
		String deleteTable = "";
		String deleteStatement = "";
		try{
			Connection connection = DriverManager.getConnection(dbUrl);
			deleteName = (String) empDeleteInvName.getText();
			deleteTable = (String) empEditInvBox.getSelectedItem();
			deleteStatement = "DELETE FROM " + deleteTable + " WHERE Name = " + "'" +deleteName + "'";
			PreparedStatement stmt = connection.prepareStatement(deleteStatement);
			int amountDeleted = stmt.executeUpdate();
			if(amountDeleted == 0) {
				empEditInvDelResults.setText("Item does not exist.");
			} else {
				empEditInvDelResults.setText("Item has been deleted.");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage() + deleteStatement);
		}
	}
	public void empChangePriceCall() throws Error{
		String category = empChangePriceCategory.getSelectedItem().toString();
		String Query = "SELECT Name FROM " + "'" + category + "'";
		String[] nameList;
		int nameCount = 0;
		try {
			Connection connection = DriverManager.getConnection(dbUrl);
			PreparedStatement stmt = connection.prepareStatement(Query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				nameCount++;
			}
			nameList = new String[nameCount];
			
			stmt = connection.prepareStatement(Query);
	        rs = stmt.executeQuery();
	        
			for(int i = 0; i<nameCount; i++) {
				nameList[i] = rs.getString("Name");
			}
			
			empChangePriceBox.removeAllItems();
			for(int i=0; i<nameCount; i++) {
				empChangePriceBox.addItem(nameList[i]);
			}
			
		} catch(SQLException e) {
			System.out.println(e.getMessage() + "879");
		}
	}
	public void empAddInv() throws Error{
		String addCategory = "";
		String addName = "";
		String addQuantity = "";
		String addPrice = "";
		int addQuantity1;
		double addPrice1;
		String Query = "";
		String checkName = "";
		try {
			Connection connection = DriverManager.getConnection(dbUrl);
			addCategory = (String) empEditInvBox1.getSelectedItem();
			addName = (String) empEditInvNameField.getText();
			addPrice = (String) empEditInvPrice.getText();
			addQuantity = (String) empEditInvQuantity.getText();
			addPrice1 = Double.parseDouble(addPrice);
			addQuantity1 = Integer.parseInt(addQuantity);
			
			Query = "INSERT INTO " + addCategory + "(Name, Price, Quantity) VALUES (?, ?, ?)";
			checkName = "SELECT COUNT(*) FROM " + addCategory + " WHERE Name = ?";
			
			//checks if an item of the same name exists already
			PreparedStatement check = connection.prepareStatement(checkName);
			check.setString(1, addName);
			ResultSet rs = check.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            empEditInvResult.setText("Item already exists");
	            return; //exit current method if item already exists
	        }
	        rs.close();
	        check.close();
			
			//adds new item based of user answers
			PreparedStatement stmt = connection.prepareStatement(Query);
			stmt.setString(1, addName);
			stmt.setDouble(2, addPrice1);
			stmt.setInt(3, addQuantity1);
			
			int amountAdded = stmt.executeUpdate();
			if(amountAdded == 0) {
				empEditInvResult.setText("Did not work");
			} else {
				empEditInvResult.setText("Item has been added");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
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
				empPage.remove(empManipulation);
				empPage.remove(empBtmPanel);
				empPage.add(empViewInvPanel, BorderLayout.CENTER);
				empViewInvResult.setText("");
				empViewInvChoice.setSelectedItem(" ");
				setContentPane(empPage);
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
		class editInventory1 implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empManipulateInv) {
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
		            //check stock availability for each item before updating the cart
		            for (Map.Entry<String, JTextField> e : quantityFields.entrySet()) {
		                String item    = e.getKey();                  // the product name
		                int desired    = parseQty(e.getValue());      // how many the user entered
		                String table   = itemTableMap.get(item);      // which DB table it belongs to
		                String sql     = "SELECT Quantity FROM " 
		                                 + table + " WHERE Name = ?";
		                try (PreparedStatement ps = connection.prepareStatement(sql)) {
		                    ps.setString(1, item);                   // bind the product name
		                    try (ResultSet rs = ps.executeQuery()) {
		                        int available = rs.next() 
		                            ? rs.getInt("Quantity") 
		                            : 0;                            // get stock or 0 if no row
		                        if (desired > available) {           // if user wants more than stock
		                            JOptionPane.showMessageDialog(
		                                final_pos.this,
		                                "Sorry, only " + available 
		                                + " of \"" + item + "\" available.",
		                                "Out of Stock",
		                                JOptionPane.ERROR_MESSAGE
		                            );
		                            e.getValue().setText("0");		//sets the quantity wanted to 0 if they want more than stock
		                            calculateCart();
		                            return;                           // abort the listener
		                        }
		                    }
		                } catch (SQLException ex) {
		                    ex.printStackTrace();
		                    JOptionPane.showMessageDialog(
		                        final_pos.this,
		                        "DB error checking stock for " + item 
		                        + ": " + ex.getMessage(),
		                        "DB Error",
		                        JOptionPane.ERROR_MESSAGE
		                    );
		                    return;                                   // abort on DB error
		                }
		            }
		            	calculateCart();

					    
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
					revalidate();
					repaint();
				} else if(evt.getSource() == empEditInvBackBtn) {
					empPage.remove(empEditInv);
					empPage.add(empManipulation);
					empPage.add(empBtmPanel, BorderLayout.SOUTH);
					empPage.add(new JLabel("Employee Home"), BorderLayout.NORTH);
					revalidate();
					repaint();
				} else if(evt.getSource() == empChangePriceQuantityBackBtn) {
					empPage.remove(empChangePriceQuantityPanel);
					empPage.add(empManipulation);
					empPage.add(empBtmPanel, BorderLayout.SOUTH);
					empPage.add(new JLabel("Employee Home"), BorderLayout.NORTH);
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
					empEditInv.remove(empEditInvAdd);
					empEditInv.remove(empEditInvDel);
					setContentPane(empPage);
					empPage.revalidate();
					empPage.repaint();

					
				}
			}
		}
		class deleteInv implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empEditInvDelBtn) {
					empEditInv.remove(empEditInvAdd);
					empEditInv.add(empEditInvDel, BorderLayout.CENTER);
					empEditInvBox.setSelectedItem(" ");
					empDeleteInvName.setText("");
					empPage.revalidate();
					empPage.repaint();
				}
			}
		}
		class addInv implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empEditInvAddBtn) {
					empEditInv.remove(empEditInvDel);
					empEditInv.add(empEditInvAdd);
					empEditInvBox1.setSelectedItem(" ");
					empEditInvNameField.setText("");
					empEditInvPrice.setText("");
					empEditInvQuantity.setText("");
					empPage.revalidate();
					empPage.repaint();
				}
			}
		}
		class deleteInventoryListener implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empEditInvDelSubmit) {
					empDeleteInv();
					//Resets and reloads the category pages, so added/deleted items will show
					applabelPanel.removeAll(); 
					eleclabelPanel.removeAll();
					furnlabelPanel.removeAll();
					quantityFields.clear();
					loadAppliances();
					loadFurniture();
					loadElectronics();
			        appCartBtn.setText("Cart: 0");
			        elecCartBtn.setText("Cart: 0" );
			        furnCartBtn.setText("Cart: 0");
			        shopCartBtn.setText("Cart: 0");
				}
			}
		}
		class addInvListener implements ActionListener{
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empEditInvAddBtnSubmit) {
					empAddInv();
					//Resets and reloads the category pages, so added/deleted items will show
					applabelPanel.removeAll(); 
					eleclabelPanel.removeAll();
					furnlabelPanel.removeAll();
					quantityFields.clear();
					loadAppliances();
					loadFurniture();
					loadElectronics();
			        appCartBtn.setText("Cart: 0");
			        elecCartBtn.setText("Cart: 0" );
			        furnCartBtn.setText("Cart: 0");
			        shopCartBtn.setText("Cart: 0");
				}
			}
		}
		class changePriceQuantity implements ActionListener {
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empChangeInvPrice) {
					empPage.remove(empManipulation);
					empPage.remove(empBtmPanel);
					empPage.add(empChangePriceQuantityPanel, BorderLayout.CENTER);
					empChangePriceQuantity.setSelectedIndex(0);
					revalidate();
					repaint();
					
				}
			}
		}
		class selectPriceQuantity implements ActionListener {
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == empChangePriceQuantity) {
					int selectedIndex = empChangePriceQuantity.getSelectedIndex();
					if(selectedIndex == 1) {
						//price panel add
					}
				}
			}
		}
	}
