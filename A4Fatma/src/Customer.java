
public class Customer implements Runnable{
	private String name;// the name of the customer
	private String customerType;//the type of service that the customer needs
	private int arrivalTime; //the time that takes to the customer to arrive
	private String vehicleType;// the kind of vehicle that the customer have/wants to buy
	private ElectricVehicle vehicle;//the vehicle of the customer
	private EntryQueue<Customer> customers;//queue of customers that arrive to the store
	private boolean hasBeenAtTheManager = false;//true- the customer has been at the manager, false-the customer has't been
	private double payment = 0;//the payment that the customer needs to pay 
	private double discount = 0;//the discount that the customer get
	
	public Customer() {}//empty constructor to wake up all the threads in the end of the day
	
	public Customer(String name, String customerType, int arrivalTime,String vehicleType, 
			EntryQueue<Customer> customers) {//constructor of customer without a vehicle
		this.name = name;
		this.customerType = customerType;
		this.arrivalTime = arrivalTime;
		this.vehicleType = vehicleType;
		this.customers = customers;
	}
	public Customer(String name, String customerType, int arrivalTime,String vehicleType,
			EntryQueue<Customer> customers,ElectricVehicle vehicle) {//constructor of customer with a vehicle
		this(name, customerType, arrivalTime, vehicleType,customers);
		this.vehicle = vehicle;
	}
	public String getCustomerType() {//returns the service that the customer needs 
		return this.customerType;
	}
	public String getVehicleType() {//return the type of vehicle that the customer wants
		return this.vehicleType;
	}
	public void setElectricVehicle(ElectricVehicle vehicle) {//sets a vehicle to the customer
		this.vehicle = vehicle;
	}
	public ElectricVehicle getElectricVehicle() {//return the vehicle of the customer
		return this.vehicle;
	}
	public void sethasBeenAtTheManager(boolean b) {//update if the customer had been at the manager
		this.hasBeenAtTheManager = b;
	}
	public boolean gethasBeenAtTheManager() {//returns if the customer had been at the manager or not
		return this.hasBeenAtTheManager;
	}
	public void setPayment(double price) { //sets the payment
		this.payment = payment + price;
	}
	public double getPayment() {//returns the payment that the customer needs to pay
		return this.payment;
	}

	public void setDiscount(double discount) {//sets a discount to the customer
		this.discount = this.discount + discount;
	}
	public double getDiscount() {//returns the discount that the customer got
		return this.discount;
	}
	public String getName() {//returns the name of the customer
		return this.name;
	}
	public void run() {
		try {
			Thread.sleep(this.arrivalTime*1000);//the time that takes to the customer to arrive
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.customers.insert(this);
	}

}
