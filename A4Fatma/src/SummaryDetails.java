
public class SummaryDetails {
	private int employeeID;//the employee that made the service in this deal
	private String customerName;//the customer that was served in this deal
	private String serviceType;// the king of service
	private double payment;// the price of the service in this deal
	
	public SummaryDetails(String serviceType, int employeeID, String customerName, double payment) {//constructor of summary details
		this.serviceType = serviceType;
		this.employeeID = employeeID;
		this.customerName = customerName;
		this.payment = payment;
	}
	public String getCustomerName() {//returns the name of the customer
		return this.customerName;
	}
	public double getPayment() {//returns the price that the customer paid
		return this.payment;
	}
}
