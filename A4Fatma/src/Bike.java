
public class Bike extends ElectricVehicle {
	private boolean inClosing;//true- if it can close, false- it can't close
	
	public Bike(String model, int maxSpeed, int weight , boolean isClosing) {//constructor for bike that belongs to customer
		super(model, maxSpeed, weight);
		this.inClosing = inClosing;
	}
	public Bike(String model, int maxSpeed, int weight , boolean isClosing,int price) {//constructor for bike that belongs to ths store
		super(model, maxSpeed, weight,price);
		this.inClosing = inClosing;
	}

}
