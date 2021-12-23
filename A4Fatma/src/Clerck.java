
public class Clerck extends Employee implements Runnable {

	private EntryQueue<Customer> customers;//queue of customers that arrive to the store
	private BoundedQueue<Customer> juniorTechService;//queue of customers that want to repair electric vehicle
	private Queue<Customer> salesService;// queue of customers that wants to buy electric vehicle

	public Clerck(EntryQueue<Customer> customers, BoundedQueue<Customer> juniorTechService, 
			Queue<Customer> salesService ) {//constructor of clerck
		super();
		this.customers = customers;
		this.salesService = salesService;
		this.juniorTechService = juniorTechService;
	}
	public void run() {//
		while(!customers.dayIsOver()) {
			Customer c = customers.extract();//remove a customer from the queue to give him service
			if(c.getCustomerType()==null)
				break;
			try {
				Thread.sleep(((long)Math.random()*3 + 3)*1000);//time of service by the clerck
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(c.getCustomerType().equals("purchesing"))//if the customer wants to buy vehicle the clerck will send him to sales department
				salesService.insert(c);
			if(c.getCustomerType().equals("repairment"))//if the customer wants to repair vehicle the clerck will send him to technical department
				juniorTechService.insert(c);
		}
	}
}
