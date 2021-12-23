import java.util.Vector;
import java.util.Collections;

public class SalesRepresentative extends Employee implements Runnable, Comparable<SalesRepresentative>{
	private Queue<Customer> salesService;// queue of customers that wants to buy electric vehicle
	private Queue<Customer> customerManagerService;//queue of customers that not satisfied from the service
	private Vector<ElectricVehicle> electricVehicle;//the stock of the vehicle that are for sale
	private Queue<SummaryDetails> summaryDetails;//queue of summary details that will arrive to the cashier
	private double totalSales = 0;//the sum of vehicles prices that this employee sold

	public SalesRepresentative(Queue<Customer> salesService, Queue<Customer> customerManagerService, 
			Vector<ElectricVehicle> electricVehicle, Queue<SummaryDetails> summaryDetails) {//constructor of sales representative
		super();
		this.salesService = salesService;
		this.customerManagerService = customerManagerService;
		this.electricVehicle = electricVehicle;
		this.summaryDetails = summaryDetails;
	}
	public double getTotalSales() {//returns the total sales
		return this.totalSales;
	}
	private int helpfindMinPriceVehicle(String kindOfVehicle) {//find the first vehicle from the kind that the customer wants
		int j = 0;
		for(int i = 0; i < electricVehicle.size(); i++) {
			if((electricVehicle.elementAt(i) instanceof Bike) && (kindOfVehicle.equals("Bike"))){//if the customer wants bike
				return i;
			}
			if((electricVehicle.elementAt(i) instanceof Scooter) && (kindOfVehicle.equals("Scooter"))){//if the customer wants scooter
				return i;
			}
		}
		return j;
	}

	private ElectricVehicle findMinPriceVehicle(String kindOfVehicle) {//find the vehicle with the minimum price from the kind that the customer wants
		int j = helpfindMinPriceVehicle(kindOfVehicle);
		ElectricVehicle minVehiclePrice = electricVehicle.elementAt(j);

		for(int i = 0; i < electricVehicle.size(); i++) {
			if((electricVehicle.elementAt(i) instanceof Bike && kindOfVehicle.equals("Bike"))){//if the customer wants bike
				if(minVehiclePrice.compareTo(electricVehicle.elementAt(i)) == 1) {//compare between the bike prices
					minVehiclePrice = electricVehicle.elementAt(i);//update the vehicle with the minimum price
				}
			}
			if((electricVehicle.elementAt(i) instanceof Scooter && kindOfVehicle.equals("Scooter"))){//compare between the scooter prices
				if(minVehiclePrice.compareTo(electricVehicle.elementAt(i)) == 1) {
					minVehiclePrice = electricVehicle.elementAt(i);//update the vehicle with the minimum price
				}
			}
		}
		return minVehiclePrice;
	}
	public void run() {
		while(!salesService.dayIsOver()) {//while the day is not over
			Customer c = salesService.extract();//remove a customer from the queue to give him service
			if(c.getCustomerType()==null)//if the day is over we will break the loop
				break;
			synchronized(KeyForStock.key) {
				if(!c.gethasBeenAtTheManager()) {//if the customer has not been at the manager
				ElectricVehicle vehicle = findMinPriceVehicle(c.getVehicleType());
					try {
						Thread.sleep(3000);//time of conversation with the customer
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					double probabillity = Math.random();
					c.setPayment(vehicle.getPrice());//update the payment on the vehicle
					this.electricVehicle.remove(vehicle);//remove the vehicle from the stock
					c.setElectricVehicle(vehicle);
					if(probabillity <= 0.13) {//customers that wasn't satisfied from the service
						this.customerManagerService.insert(c);
					}else {
						this.totalSales = this.totalSales + c.getPayment();//update the sales of the employee
						SummaryDetails s = new SummaryDetails("purchesing",this.ID,c.getName(), c.getPayment());//create summary details
						this.summaryDetails.insert(s);
					}
				}
				if(c.gethasBeenAtTheManager()) {//customers that had been at the manager
					try {
						Thread.sleep(1500);//time of conversation with the customer
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					c.setPayment(-c.getDiscount());//update the payment on the vehicle	
					this.totalSales = this.totalSales + c.getPayment();//update the sales of the employee
					SummaryDetails s = new SummaryDetails("purchesing",this.ID,c.getName(), c.getPayment());//create summary details
					this.summaryDetails.insert(s);
				}
			}
		}
	}
	public synchronized int compareTo(SalesRepresentative other) {//natural compare between employees by the total sales
		if(this.totalSales > other.totalSales )
			return 1;
		else if(this.totalSales < other.totalSales)
			return -1;
		else
			return 0;
	}
}
