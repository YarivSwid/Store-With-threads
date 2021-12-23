import java.util.Vector;

public class BoundedQueue<T> {
	private Vector<T> boundedQueue = new Vector<T>();//the bounded queue
	private int maxSize;//the maximum objects that can be in the queue
	private boolean dayOver = false;// true- the day is over, false- the day isn't over
	
	public BoundedQueue(int maxSize) {//constructor for bounded queue
		boundedQueue = new Vector<T>();
		this.maxSize=maxSize;
	}
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
	public synchronized void insert(T item) {//method for insert objects to the queue
		while(boundedQueue.size()>=maxSize) {
			try {
				this.wait();// if there is no place in the queue the object will wait
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		boundedQueue.add(item);//add the item to the queue
		notifyAll();  
	}
	public synchronized T extract(){//method for remove objects from the queue
	     while (boundedQueue.isEmpty())
			try {
				if(!dayOver) {// if the day isn't over and the queue is empty the object will wait
				this.wait();
				}
				else {
					Customer c = new Customer();
					return (T)c;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	     T item = boundedQueue.elementAt(0);
	     boundedQueue.remove(item);//remove the item from the queue
	     notifyAll();
	     return item;    
	}




}
