import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyGui extends JFrame implements ActionListener{

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyGui Fatma = new MyGui(); // creates the Gui program 
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MyGui() {
	
		JFrame frame = new JFrame(); // creates the gui's frame
		JPanel panel = new JPanel(); // creates the gui's panel
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the exit close default buttons
		frame.getContentPane().add(panel); // adding the panel to the frame
		panel.setLayout(null); 
		frame.setSize(350,200); // setting the frame size
		JLabel label = new JLabel("How Many Managers: "); // creates the label before the text input of how many managers
		label.setBounds(20,40,200,25); // setting the label bounds
		JLabel label1 = new JLabel("How Much Time: "); // creates the label before the text input of how much time takes  
		label1.setBounds(20,65,200,25); // setting the label bounds
		JLabel titleLabel = new JLabel("Fatma Store"); // we create a title with a label
		titleLabel.setBounds(120,10,200,25); // setting the label location 
		JLabel success = new JLabel(""); // here we create a empty label 
		success.setBounds(20,90,200,25); // setting the spot we want to add the success label
		panel.add(success); // adding the label to the panel
		panel.add(titleLabel); // adding the label to the panel
		panel.add(label1); // adding the label to the panel
		JButton	startButton = new JButton("START"); // adding start button
		startButton.setBounds(110,120,75,21); // setting the start button 
		panel.add(startButton);
		panel.add(label);
		frame.setTitle("Fatma Store"); // we change the window title to Fatma Store
		JTextField write = new JTextField(20); // creating text fields
		JTextField write1 = new JTextField(20); // second text field
		write1.setBounds(150,70,150,20); 
		write.setText("1"); // setting the default to 1 
		write1.setText("1"); // setting the default to 1
		write.setBounds(150,50,150,20);
		panel.add(write);	 // adding the text fields to the panel
		panel.add(write1);	

		startButton.addActionListener(new ActionListener() { // here we use the start button to start the program 
			public void actionPerformed(ActionEvent e) {
				String Managers = write.getText(); // getting the input
				String repairTime = write1.getText(); 

				int numOfManagers = 1; // the default before changing 
				long timeForRepair = 2; // the default before changing 
				try {
					numOfManagers = Integer.parseInt(Managers); // we try to get the number input from the string
				}
				catch(NumberFormatException s) {
					//if the input of number of managers is incorrect add only one manager
					numOfManagers = 1; 
				}
					timeForRepair = Long.parseLong(repairTime);
					if(timeForRepair>5 || timeForRepair<0 ) {
						// if the integer is bigger then 5 or lower then 0 then insert time repair 1 second
						timeForRepair = 1;
					}

				Store S = new Store("Customers.txt","Stock.txt",numOfManagers,timeForRepair); // creating the store 
				success.setText("Creation was successfull!!"); // the success label update to this message
			}
		});

		JButton exitButton = new JButton("EXIT"); // creating the exit button
		exitButton.setBounds(20,120,75,21); // setting the button bounder
		panel.add(exitButton); // adding the button to the panel
		exitButton.addActionListener(new ActionListener() { // creating the exit button program 
			public void actionPerformed(ActionEvent e) { 
				System.exit(0); // exit the system
			}
		});
		frame.setVisible(true); 

	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Welcome To My Gui");
	}
}
