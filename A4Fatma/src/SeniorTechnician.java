
public class SeniorTechnician extends TechnicalEmployee implements Runnable {
	private boolean isMaster;//true- is master, false- is not master
	private Queue<Customer> seniorTechService;//queue for customers that have complicated problem in their vehicle
	private Queue<Customer> customerManager;//queue for customers that are not satisfied from the service
	private Queue<SummaryDetails> summaryDetails;//queue of summary details that will send to the cashier

	public SeniorTechnician(Queue<Customer> seniorTechService, Queue<Customer> customerManager, 
			Queue<SummaryDetails> summaryDetails) {//constructor of senior technician
		super();
		double prob = Math.random();
		if(prob < 0.5) {
			this.isMaster = true;	
		}
		else {
			this.isMaster = false;
		}
		this.seniorTechService = seniorTechService;
		this.customerManager = customerManager;
		this.summaryDetails = summaryDetails;
	}

	public void run() {
		while(!seniorTechService.dayIsOver()) {//while the day is not over
			Customer c = seniorTechService.extract();//remove a customer from the queue to give him service
			if(c.getCustomerType()==null)//if the day is over we will break the loop
				break;
			double price = 0;
			if(!c.gethasBeenAtTheManager()) {//if the customer has not been at the customer manager
				try {
					Thread.sleep(1000);//estimate the cost of repair
				} catch (InterruptedException e) {
					e.printStackTrace();
				}if(c.getVehicleType().equals("Bike")) {//if the kind of vehicle is bike, the service probably be more expensive
					price = (Math.random()*700 +100);
				}else {//if the kind of the vehicle is scooter
					price = (Math.random()*450 + 50);
				}
				c.setPayment(price);//update the payment that the customer needs to pay
				if(!this.isMaster) {
					double probabillity = Math.random();
					if(price > 450) {
						if(probabillity<=0.9) {
							this.customerManager.insert(c);//the customer wasn't satisfied from the service 
						}
					}
				}
				long serviceTime;//the repair time depends on the price of it
				if(price<=300) {
					serviceTime = 1000;
				}else if(price>300 && price<=450) {
					serviceTime = 2000;
				}else {
					serviceTime = 3000;
				}try {
					Thread.sleep(serviceTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {//if the customer had been at the customer manager
				try {
					Thread.sleep(1000);//time of repair
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				c.setPayment(-c.getDiscount());
			}
			this.numOfCustomersDone = this.numOfCustomersDone + 1;//update the number of customers that the employee serve		
			SummaryDetails s = new SummaryDetails("repairment",this.ID,c.getName(), c.getPayment());//create summary detail

			this.summaryDetails.insert(s);
		}
	}
}
