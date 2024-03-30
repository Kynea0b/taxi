package sample;
public class RecordList {

	private Record head;
	private Record tail;

	public RecordList(Record record) {
		this.head = record;
		this.tail = null;
	}

	public void add(Record record) {
		if(this.tail == null) {
			this.tail = record;
			this.head.setNext(record);
			this.tail.setPrev(this.head);
		} else {
			this.tail.setNext(record);
			record.setPrev(this.tail);
			this.tail = record;
		}
	}

	public int length() {
		int length = 0;
		Record current = this.head;
		while(current != null) {
			length++;
			current = current.next();
		}
		return length;
	}

	public Record get(int index) {
		int currentIndex = 0;
		Record current = this.head;
		while(current != null) {
			if(index == currentIndex) return current;
			currentIndex++;
			current = current.next();
		}
		return null;
	}

	public double getTotalDistance() {
		double total = 0;
		Record current = this.head;
		while(current != null) {
			total += current.getDistanceM();
			current = current.next();
		}
		return total;
	}

	public double getLatestDistance() {
		double distance = 0;
		if(this.tail == null) {
			distance = this.head.getDistanceM();
		} else {
			distance = this.tail.getDistanceM();
		}
		return distance;
	}

	public Record getLatestRecord() {
		Record record = null;
		if(this.tail == null) {
			record = this.head;
		} else {
			record = this.tail;
		}
		return record;
	}

	public long getTotalLowSpeedDrivingSeconds() {
		if(this.head.hasNext() == false) return 0;
		Record current = this.head.next();
		long seconds = 0;
		while(current != null) {
			double currentTotalDistance = 0;
			Record index = current;
			while(index != null) {
				currentTotalDistance += index.getDistanceM();
				index = index.getPrev();
			}
			if(current.getSpeedKmH() <= 10 && currentTotalDistance > 1000) {
				seconds += current.getDurationMs()/1000;
			}
			current = current.next();
		}
		return seconds;
	}

	public long getLatestLowSpeedDrivingSeconds() {
		if(this.tail == null) return 0;
		return this.tail.getDurationMs() / 1000;
	}

	public void getTest() {
		Record current = this.head.next();
		while(current != null) {
			current = current.next();
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Record current = this.head;
		while(current != null) {
			sb.append(current.toString() + System.getProperty("line.separator"));
			current = current.next();
		}
		return sb.toString();
	}

}
