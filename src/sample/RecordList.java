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
			this.head.add(record);
			this.tail = record;
		} else {
			this.tail.add(record);
			this.tail = record;
		}
	}

	public double getTraveledDistanceM() {
		return this.tail.getDistanceM();
	}

	public double getTotalTraveledDistanceM() {
		double total = 0;
		Record current = this.head;
		while(current != null) {
			total += current.getDistanceM();
			current = current.next();
		}
		return total;
	}

	public Record getCurrent() {
		if(this.tail == null) {
			return this.head;
		}
		return this.tail;
	}

	public double getCurrentSpeed() {
		if(this.tail == null) {
			throw new RuntimeException();
		}
		return this.tail.getSpeedKmH();
	}

	public Record getRecordAtSection(Section initial) {
		Record current = this.head;
		double totalDistanceM = 0;
		while(current != null) {
			totalDistanceM += current.getDistanceM();
			if(totalDistanceM >= initial.getDistanceM()) {
				return current;
			}
			current = current.next();
		}
		return null;
	}

	public TimeZoneType getCurrentTimeZoneType() {
		return this.tail.getTimeZoneType();
	}
}
