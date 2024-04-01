package sample;

public class DistanceMeter {
	private Record head;
	private Record tail;

	public DistanceMeter() {
		this.head = null;
		this.tail = null;
	}

	public double getFare() {
		if (this.tail == null) {
			// 初乗運賃計算
			Record current = this.head;
			return InitialFareCalculateService.calculateFare(current);
		} else {
			// 距離運賃計算
			Record current = this.tail;
			double totalDistanceM = this.getTotalDistanceM();
			return DistanceFareCalculateService.calculateFare(totalDistanceM, current);
		}
	}


	public void records(Record record) {
		if (this.head == null) {
			this.head = record;
		} else if (this.tail == null) {
			this.head.add(record);
			this.tail = record;
		} else {
			this.tail.add(record);
			this.tail = record;
		}
	}

	public double getTotalDistanceM() {
		double totalDistanceM  = 0;
		Record current = this.head;
		while(current != null) {
			totalDistanceM += current.getDistanceM();
			current = current.next();
		}
		return totalDistanceM;
	}

	public Record getCurrentRecord() {
		if (this.tail == null) {
			return this.head;
		}
		return this.tail;
	}

	public double getCurrentSpeedKmH() {
		return this.tail.getSpeedKmH();
	}

	public long getCurrentDrivingSeconds() {
		return this.tail.getDrivingSeconds();
	}

	public double getCurrentDistanceM() {
		return this.tail.getDistanceM();
	}

	public TimeZoneType getCurrentTimeZoneType() {
		return this.tail.getTimeZoneType();
	}


}
