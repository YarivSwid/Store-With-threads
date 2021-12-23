
abstract public class ElectricVehicle implements Comparable<ElectricVehicle>{
	protected String model;// the model of the vehicle
	protected int maxSpeed;// the maximum speed of the vehicle
	protected int weight;// the weight of the vehicle
	protected int price;//the price of the vehicle
	
	public ElectricVehicle(String model,int maxSpeed,int weight) {//constructor of vehicle that belongs to the customer
		this.model = model;
		this.maxSpeed = maxSpeed;
		this.weight = weight;
	}
	public ElectricVehicle(String model,int maxSpeed,int weight,int price) {//constructor of vehicle that belongs to the store
		this.model = model;
		this.maxSpeed = maxSpeed;
		this.weight = weight;
		this.price = price;
	}
	
	public int getPrice() {//returns the price of the vehicle
		return this.price;
	}
	public int compareTo(ElectricVehicle other) {//natural compare by the vehicles prices
		if(this.price>other.price) {
			return 1;
		}
		else if(this.price<other.price) {
			return -1;
		}
		return 0;
	}
}
