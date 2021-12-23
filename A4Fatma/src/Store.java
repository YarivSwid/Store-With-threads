import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Store{
	private EntryQueue<Customer> customers = new EntryQueue<Customer>();//queue of customers that arrive to the store
	private BoundedQueue<Customer> juniorTechService = new BoundedQueue<Customer>(5);//queue of customers that want to repair electric vehicle
	private Queue<Customer> seniorTechService = new Queue<Customer>();//queue for customers that have complicated problem in their vehicle
	private Queue<Customer> salesService = new Queue<Customer>();// queue of customers that wants to buy electric vehicle
	private Queue<Customer> customerManagerService = new Queue<Customer>();//queue for customers that are not satisfied from the service
	private Queue<SummaryDetails> summaryDetails = new Queue<SummaryDetails>();//queue  of summary details
	private Vector<Customer> cu = new Vector<Customer>();//the customers that will arrive today to the store
	private Vector<Clerck> clerck = new Vector<Clerck>();//the clercks that works in the store
	private Vector<JuniorTechnician> juniorTechnician = new Vector<JuniorTechnician>();//junior technician that works in the store
	private Vector<SeniorTechnician> seniorTechnician = new Vector<SeniorTechnician>();//senior technician that works in the store
	private Vector<SalesRepresentative> salesRepresentative = new Vector<SalesRepresentative>();//sales representatives that works in the store
	private Vector<CustomerManager> customerManager = new Vector<CustomerManager>();//customer managers that works in the store
	private Vector<SummaryDetails> summaryDetailsStock = new Vector<SummaryDetails>();//stock of all the summary details
	private Vector<Employee> employeesForDistinguish = new Vector<Employee>();
	private int numOfCustomerManager;//the number of customer managers that the store have
	private long timeOfRepair;
	private Cashier cashier;
	private Vector<ElectricVehicle> electricVehicleStock = new Vector<ElectricVehicle>();//the stock of vehicles that are for sale

	public Store(String Customers, String Stock, int numOfCustomerManager, long time) {//constructor of the store
		readCustomers(Customers);
		readStock(Stock);
		this.numOfCustomerManager = numOfCustomerManager;
		this.timeOfRepair = time;
		createAndAddClearck();
		createAndAddJuniorTechnician();
		createAndAddSeniorTechnician();
		createAndAddSalesRepresentative();
		createAndAddCustomerManager();
		createAndAddCashier();
		for(int i = 0; i <cu.size(); i++) {
			Thread customer = new Thread(this.cu.elementAt(i));
			customer.start();
		}
	}
	private void readStock(String Stock) {//reads the field of vehicles of the store
		BufferedReader inFile=null;
		try{
			FileReader fr = new FileReader (Stock);
			inFile = new BufferedReader (fr);
			inFile.readLine();
			String line;
			while ((line=inFile.readLine())!=null) {
				String[] input = line.trim().split("\\s+");
				String vehicleType = input[0];
				String model = input[1];
				int maxSpeed = Integer.parseInt(input[2]);
				int weight = Integer.parseInt(input[3]);
				int isclosing = Integer.parseInt(input[4]);
				int price = Integer.parseInt(input[5]);
				boolean isClosing;
				if(isclosing == 0) {
					isClosing = false;	
				}
				else {
					isClosing = true;
				}
				int unitsInStock = Integer.parseInt(input[6]);
				while(unitsInStock > 0) {
					if(vehicleType.equals("Scooter")) {
						Scooter scooter = new Scooter(model, maxSpeed, weight,price);
						electricVehicleStock.addElement(scooter);
					}if(vehicleType.equals("Bike")) {
						Bike bike = new Bike(model, maxSpeed, weight , isClosing, price);
						electricVehicleStock.addElement(bike);
					}
					unitsInStock = unitsInStock - 1;
				}
			}
		}
		catch (FileNotFoundException exception){
			System.err.println ("The file " + Stock + " was not found.");
		}
		catch (IOException exception){
			System.err.println (exception);
		}
		finally{
			try {
				inFile.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void readCustomers(String Customers) {//reads the customer field
		BufferedReader inFile=null;
		try{
			FileReader fr = new FileReader (Customers);
			inFile = new BufferedReader (fr);
			inFile.readLine();
			String line;
			while ((line=inFile.readLine())!=null) {
				String[] input = line.trim().split("\\s+");
				String name = input[0];
				String serviceType = input[1];
				int arrivalTime = Integer.parseInt(input[2]);
				String vehicleType = input[3];
				if(serviceType.equals("purchesing")) {
					Customer c = new Customer(name, serviceType, arrivalTime, vehicleType , this.customers);
					cu.add(c);
				}
				else if(serviceType.equals("repairment")) {
					String model = input[4];
					int maxSpeed = Integer.parseInt(input[5]);
					int weight = Integer.parseInt(input[6]);
					if(vehicleType.equals("Scooter")) {
						Scooter scooter = new Scooter(model, maxSpeed, weight);
						Customer c = new Customer(name, serviceType, arrivalTime, vehicleType, this.customers, scooter);
						cu.add(c);
					}
					else if(vehicleType.equals("Bike")) {
						int isclosing = Integer.parseInt(input[7]);
						boolean isClosing;
						if(isclosing == 0) {
							isClosing = false;
						}
						else {
							isClosing = true;
						}
						Bike bike = new Bike(model, maxSpeed, weight, isClosing);
						Customer c = new Customer(name, serviceType, arrivalTime, vehicleType, this.customers, bike);
						cu.add(c);
					}
				}
			}
		}
		catch (FileNotFoundException exception){
			System.err.println ("The file " + Customers + " was not found.");
		}
		catch (IOException exception){
			System.err.println (exception);
		}
		finally{
			try {
				inFile.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void createAndAddClearck() {//create the clercks and start their run
		Clerck clerck1 = new Clerck(customers,juniorTechService, salesService);
		Clerck clerck2 = new Clerck(customers,juniorTechService, salesService);
		clerck.add(clerck1);
		clerck.add(clerck2);
		for(int i = 0; i < clerck.size(); i++) {
			Thread clerck12 = new Thread(this.clerck.elementAt(i));
			clerck12.start();
		}
	}
	private void createAndAddJuniorTechnician() {//create the junior technician and start their run
		JuniorTechnician juniorTech1 = new JuniorTechnician(timeOfRepair,juniorTechService,seniorTechService,customerManagerService,summaryDetails);
		JuniorTechnician juniorTech2 = new JuniorTechnician(timeOfRepair,juniorTechService,seniorTechService,customerManagerService,summaryDetails);
		JuniorTechnician juniorTech3 = new JuniorTechnician(timeOfRepair,juniorTechService,seniorTechService,customerManagerService,summaryDetails);
		juniorTechnician.add(juniorTech1);
		juniorTechnician.add(juniorTech2);
		juniorTechnician.add(juniorTech3);
		employeesForDistinguish.add(juniorTech1);
		employeesForDistinguish.add(juniorTech2);
		employeesForDistinguish.add(juniorTech3);
		for(int i = 0; i < juniorTechnician.size(); i++) {
			Thread juniorTech12 = new Thread(this.juniorTechnician.elementAt(i));
			juniorTech12.start();
		}
	}
	private void createAndAddSeniorTechnician() {//create the senior technician and start their run
		SeniorTechnician seniorTech1 = new SeniorTechnician(seniorTechService, customerManagerService, summaryDetails);
		SeniorTechnician seniorTech2 = new SeniorTechnician(seniorTechService, customerManagerService, summaryDetails);
		seniorTechnician.add(seniorTech1);
		seniorTechnician.add(seniorTech2);
		employeesForDistinguish.add(seniorTech1);
		employeesForDistinguish.add(seniorTech2);
		for(int i = 0; i < seniorTechnician.size(); i++) {
			Thread seniorTech12 = new Thread(this.seniorTechnician.elementAt(i));
			seniorTech12.start();
		}
	}
	private void createAndAddSalesRepresentative() {//create the sales representative and start their run
		SalesRepresentative salesrep1 = new SalesRepresentative(salesService,customerManagerService, electricVehicleStock, summaryDetails);
		SalesRepresentative salesrep2 = new SalesRepresentative(salesService,customerManagerService, electricVehicleStock, summaryDetails);
		salesRepresentative.add(salesrep1);
		salesRepresentative.add(salesrep2);
		employeesForDistinguish.add(salesrep1);
		employeesForDistinguish.add(salesrep2);
		for(int i = 0; i < salesRepresentative.size(); i++) {
			Thread salesrep12 = new Thread(this.salesRepresentative.elementAt(i));
			salesrep12.start();
		}
	}
	private void createAndAddCustomerManager() {//create the customer manager and start their run
		while(numOfCustomerManager > 0) {
			CustomerManager manager = new CustomerManager(cu.size(), customers,  juniorTechService, salesService,seniorTechService , customerManagerService, 
					summaryDetails, electricVehicleStock);
			customerManager.add(manager);
			numOfCustomerManager = numOfCustomerManager-1;
		}
		for(int i = 0; i < customerManager.size(); i++) {
			Thread manager1 = new Thread(this.customerManager.elementAt(i));
			manager1.start();
		}
	}
	private void createAndAddCashier() {//create the cashier and start their run
		Cashier cashir1 = new Cashier(summaryDetails, employeesForDistinguish,summaryDetailsStock,customerManager,customerManagerService);
		this.cashier = cashir1;
		Thread cashir12 = new Thread(this.cashier);
		cashir12.start();
	}
}
