import java.util.Random;

public class JuniorTechnician extends TechnicalEmployee implements Runnable{
	private int yearsInJob;//the number of years that the employee work in the store
	private BoundedQueue<Customer> juniorTechService;//queue of customers that wants to repair their vehicle
	private Queue<Customer> seniorTechService;//queue of customers that have a complicated problem in their vehicle
	private Queue<Customer> managerService;//queue of customers that not satisfied from the service
	private Queue<SummaryDetails> summaryDetails;//queue of summary details that will arrive to the cashier
	private long timeOfRepair;

	public JuniorTechnician(long timeOfRepair, BoundedQueue<Customer> juniorTechService,Queue<Customer> seniorTechService,Queue<Customer> managerService,
			Queue<SummaryDetails> summaryDetails) {//constructor of junior technician
		super();
		Random rnd = new Random();
		this.yearsInJob = rnd.nextInt(48);//we assumed that employee can start to work when he is 20 and can work until age 67
		this.timeOfRepair = timeOfRepair;
		this.juniorTechService = juniorTechService;
		this.seniorTechService = seniorTechService;
		this.managerService = managerService;
		this.summaryDetails = summaryDetails;
	}
	public void run() {
		while(!juniorTechService.dayIsOver()) {//while the day is not over
			Customer c = juniorTechService.extract();//remove a customer from the queue to give him service
			if(c.getCustomerType()==null)
				break; //if the day is over we will break the while loop
			try {
				Thread.sleep(timeOfRepair*1000);//time of general vehicle check 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			double probabillity = Math.random();
			if(probabillity<=0.2) {//the problem was complicated so the technician will sent the customer to senior technician
				this.seniorTechService.insert(c);
			}
			else if(probabillity > 0.2 && probabillity <=0.3) {//customers that are not satisfied from the check
				this.managerService.insert(c);
			}else {
				try {
					Thread.sleep(3000);//time of service
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				double price = (Math.random()*700 +100);
				c.setPayment(price);//set payment for the service
				this.numOfCustomersDone = this.numOfCustomersDone + 1;	//update the number of customers that this employee serve	
				SummaryDetails s = new SummaryDetails("repairment",this.ID,c.getName(), c.getPayment());//create summary details
				this.summaryDetails.insert(s);
			}
		}
	}
}
