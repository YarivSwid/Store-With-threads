import java.util.Random;
import java.util.Vector;

abstract public class Employee  {
	protected int ID;//the ID of the employee
	private Vector<Integer> IDs = new Vector<Integer>();

	public Employee() {//constructor of the employee
		Random rnd = new Random();
		int id = rnd.nextInt(900000000)+100000000;//ID that build from 9 numbers
		for(int i = 0; i < IDs.size(); i++) {
			if(id == IDs.elementAt(i)){
				id = rnd.nextInt(900000000)+100000000;
				i = 0;
			}
		}
		this.ID = id;
		IDs.add(id);
	}
	public int getKey() {//returns the ID of the employee
		return this.ID;
	}
}

