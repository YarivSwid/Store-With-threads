import java.util.Vector;

public class CustomerManager extends Employee implements Runnable {
	private EntryQueue<Customer> customers;//queue of customers that arrive to the store
	private BoundedQueue<Customer> juniorTechService;//queue of customers that want to repair electric vehicle
	private Queue<Customer> salesService;// queue of customers that wants to buy electric vehicle
	private Queue<Customer> seniorTechService;//queue for customers that have complicated problem in their vehicle
	private Queue<Customer> managerService;//queue for customers that are not satisfied from the service
	private Queue<SummaryDetails> summaryDetails;//queue of summary details that will send to the cashier
	private Vector<ElectricVehicle> electricVehicle;//the stock of the vehicle that are for sale
	private int numberOfCustomers = 0;//the of customers that arrive to the store
	private int howManyShouldCome;//the number of customers that should arrive today

	public CustomerManager(int howManyShouldCome, EntryQueue<Customer> customers, BoundedQueue<Customer> juniorTechService,Queue<Customer> salesService,Queue<Customer> seniorTechService ,Queue<Customer> managerService, 
			Queue<SummaryDetails> summaryDetails, Vector<ElectricVehicle> electricVehicle) {//constructor of customer manager
		super();
		this.howManyShouldCome = howManyShouldCome;
		this.customers = customers;
		this.juniorTechService = juniorTechService;
		this.salesService = salesService;
		this.managerService = managerService;
		this.seniorTechService = seniorTechService;
		this.summaryDetails = summaryDetails;
		this.electricVehicle = electricVehicle;
	}
	public boolean setNumberOfcustomers() {//update the number of customers that left the store
		this.numberOfCustomers = numberOfCustomers+1;
		if(numberOfCustomers == howManyShouldCome) {
			return true;
		}
		return false;
	}
	public int getNumberOfCustomers() {//returns the number of customers that left the store
		return this.numberOfCustomers;
	}
	public boolean dayOver() {//method to check if the day is over, if it's over the manager will announce all the employees
		if(numberOfCustomers>=howManyShouldCome) {
			customers.endDay();
			juniorTechService.endDay();
			salesService.endDay();
			seniorTechService.endDay();
			return true;
		}
		return false;
	}  
	public void run() {
		while(!dayOver()) {
			Customer c = this.managerService.extract();//remove a customer from the queue to give him service
			if(c.getCustomerType()==null)//if the day is over we will break the loop
				break;
			c.sethasBeenAtTheManager(true);//update that the customer had been at the manager
			if(c.getCustomerType().equals("repairment")) {//customers that need to repair their vehicle
				double probabillity = Math.random();
				if(probabillity <= 0.1) {
					c.setDiscount(50);//update discount to 50
					this.seniorTechService.insert(c);//send the customer back to senior technician
				}
				else if(probabillity>0.1 && probabillity <= 0.4) {//customers that regret and leave the store
					SummaryDetails s = new SummaryDetails("repairment",this.ID,c.getName(), c.getPayment());

					this.summaryDetails.insert(s);			
				}
				else {//the customer convinced that this is the right repair
					this.seniorTechService.insert(c);
				}
			}
			else {//customers that interested in purchasing
				double probabillity = Math.random();
				if(probabillity <= 0.7) {
					c.setDiscount(100);//update discount to 100
					this.salesService.insert(c);//send the customer back to sale representative
				}else {//customers that regret and leave the store
					ElectricVehicle vehicle = c.getElectricVehicle();
					synchronized(KeyForStock.key) {
					this.electricVehicle.addElement(vehicle);//insert the vehicle back to the stock
					}
					c.setElectricVehicle(null);//it was updated in the customer because he was supposed to get the discount on the same vehicle
					c.setPayment(0);//update payment
					SummaryDetails s = new SummaryDetails("purchesing",this.ID,c.getName(), c.getPayment());//create summary details
					this.summaryDetails.insert(s);
				}
			}
		}
	}
}
