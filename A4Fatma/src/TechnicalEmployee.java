
abstract public class TechnicalEmployee extends Employee implements Comparable<TechnicalEmployee>{
	protected int numOfCustomersDone = 0;
	
	public TechnicalEmployee() {//constructor for technical employee
		super();
	}
	public int getNumOfCustomersDone() {//returns the number of customer that served by this employee
		return this.numOfCustomersDone;
	}
	public synchronized int compareTo(TechnicalEmployee other) {//natural compare by the number of customer that served by the employees
		if(this.numOfCustomersDone > other.numOfCustomersDone )
			return 1;
		else if(this.numOfCustomersDone < other.numOfCustomersDone)
			return -1;
		else
		return 0;
	}
}
