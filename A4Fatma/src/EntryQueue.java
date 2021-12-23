
public class EntryQueue<T> extends Queue<T> {

	public synchronized T extract() {//method for remove objects from the queue
		while (this.queue.isEmpty())
			try {
				if(!dayOver)
				this.wait();//if the day is not over and the queue is empty the object will wait
				else {
					Customer c = new Customer();//return a fictitious customer to wake the objects up 
					return (T)c;

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		for(int i = 0;i<queue.size();i++) {
			if(((Customer)queue.elementAt(i)).getVehicleType().equals("Bike")) {//find the first customers that interested in bikes
				//(repair/purchase)
				T t = queue.elementAt(i);
				queue.remove(t);
				return t;
			}
		}
		T t = queue.elementAt(0);
		queue.remove(t);
		return t;
	}
}
