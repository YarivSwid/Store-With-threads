import java.util.Vector;

public class Queue<T> {
	protected Vector<T> queue = new Vector<T>();
	protected boolean dayOver = false;// true- the day is over, false- the day isn't over

	public synchronized boolean dayIsOver() {//returns if the day is over or if it is not over
		if(dayOver) {
			return true;
		}
		else return false;
	}
	public synchronized void endDay() {//method to end the day and wake up all the objects in the queue
		this.dayOver = true;
		this.notifyAll();
	}
	public synchronized void insert(T item){//method for insert objects to the queue
			queue.addElement(item);

		this.notifyAll();
	}
	public synchronized T extract() {//method for remove objects from the queue
		while (queue.isEmpty())
			try {
				if(!dayOver) {
				this.wait();//if the queue is empty and the day is not over the object will wait
				}else {
					Customer c= new Customer();
					return (T)c;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		T t = queue.elementAt(0); 
		queue.remove(t);//remove the object from the queue
		return t;
	}

}
