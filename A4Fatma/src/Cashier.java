import java.util.Collections;
import java.util.Vector;

public class Cashier extends Employee implements Runnable{
	private Queue<SummaryDetails> summaryDetails;//queue  of summary details
	private Vector<Employee> employee;//employees that can be distinguished in the end of the day
	private Vector<SummaryDetails> summaryDetailsStock;//stock of all the summary details
	private Vector<CustomerManager> customerManager;//the customer managers of the store
	private Queue<Customer> managerService;//the queue that leads to customer manager service
	private double profitFromThisDay;// the profit from the current day

	public Cashier(Queue<SummaryDetails> summaryDetails, Vector<Employee> employee, Vector<SummaryDetails> summaryDetailsStock
			, Vector<CustomerManager> customerManager, Queue<Customer> managerService) {//constructor of cashier
		super();
		this.summaryDetails = summaryDetails;
		this.employee = employee;
		this.summaryDetailsStock = summaryDetailsStock;
		this.customerManager = customerManager;
		this.managerService = managerService;
	}
	private void notifyDistinguishedEmployees() {//method to find the distinguished employees in the end of the day
		Vector<SalesRepresentative> sales = new Vector<SalesRepresentative>();
		Vector<TechnicalEmployee> technical = new Vector<TechnicalEmployee>();
		for(int i=0;i < employee.size();i++) {//split the vector of employees to two vectors: sales and technical
			if(employee.elementAt(i) instanceof SalesRepresentative) {
				sales.addElement((SalesRepresentative)employee.elementAt(i));
			}
			if(employee.elementAt(i) instanceof TechnicalEmployee) {
				technical.addElement((TechnicalEmployee)employee.elementAt(i));
			}
		}
		SalesRepresentative DistinguishedSalesEmployee = Collections.max(sales);
		System.out.println("The most efficent employee from the sales department is: " + DistinguishedSalesEmployee.getKey());
		TechnicalEmployee DistinguishedTechEmployee = Collections.max(technical);
		System.out.println("The most efficent employee from the Technical department is: " + DistinguishedTechEmployee.getKey());

	}
	public void run() {
		while(!summaryDetails.dayIsOver()) {//while the day is not over
			SummaryDetails s = summaryDetails.extract();//remove a customer from the queue to give him service
			try {
				Thread.sleep(1000);//time of take the summary details and put it in the stock of the store
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.summaryDetailsStock.addElement(s);//ass the summary detail to the stock
			profitFromThisDay = profitFromThisDay + s.getPayment();//update the profit
			boolean reachNumberOfCustomer = false;
			for(int i=0;i<customerManager.size();i++) {//update the managers about a customer (summary details) that left the store
				reachNumberOfCustomer = customerManager.elementAt(i).setNumberOfcustomers();
				if(reachNumberOfCustomer) {//if all the customers left the cashier will wake up the managers
						this.managerService.endDay();
						this.summaryDetails.endDay();
				}
			}
		}
		System.out.println("The number of customers in this day is: " + this.summaryDetailsStock.size());
		System.out.println("The profit from this day is: " + this.profitFromThisDay);
		System.out.println("summary of the day: ");
		notifyDistinguishedEmployees();//find the distinguished employees
		for(int i = 0; i < summaryDetailsStock.size(); i ++) {
			SummaryDetails s = summaryDetailsStock.elementAt(i);
			System.out.println("customer " + s.getCustomerName() + " paid today " + s.getPayment());
		}
		for(int i = 0; i < customerManager.size(); i++) {
			customerManager.elementAt(i).dayOver();//one of the managers will wake up all the employees
		}
	}
}
