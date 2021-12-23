
public class Scooter extends ElectricVehicle {
	
	public Scooter(String model, int maxSpeed, int weight) {//constructor of scooter that belongs to customer
		super(model, maxSpeed, weight);
	}
	public Scooter(String model, int maxSpeed, int weight,int price) {//constructor of scooter that belongs to the store
		super(model, maxSpeed, weight,price);
	}

}
