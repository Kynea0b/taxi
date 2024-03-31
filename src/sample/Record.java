package sample;

import java.time.Duration;
import java.time.LocalTime;

public class Record {
	private LocalTime timestamp;
	private double distanceM;
	private Record prev;
	private Record next;

	public Record(LocalTime timestamp,double distanceM) {
		this.timestamp = timestamp;
		this.distanceM = distanceM;
	}

	public void add(Record record) {
		if(this.next != null) {
			throw new RuntimeException();
		} else {
			this.next = record;
			record.prev = this;
		}
	}

	public boolean hasNext() {
		return this.next != null;
	}

	public Record next() {
		return this.next;
	}

	public TimeZoneType getTimeZoneType() {
		// 通常
		if (this.timestamp.isAfter(LocalTime.of(9, 59, 59, 999))
				&& this.timestamp.isBefore(LocalTime.of(20, 0, 0, 0))) {
			return TimeZoneType.STANDARD;
		} else if (this.timestamp.isAfter(LocalTime.of(5, 59, 59, 999))
				&& this.timestamp.isBefore(LocalTime.of(10, 0, 0, 0))
				|| this.timestamp.isAfter(LocalTime.of(19, 59, 59, 999))
						&& this.timestamp.isBefore(LocalTime.of(24, 0, 0, 0))) {
			// ピーク
			return TimeZoneType.PEEK;
		} else {
			// 深夜
			return TimeZoneType.LATE_TIME;
		}
	}

	public double getDistanceM() {
		return this.distanceM;
	}

	public double getSpeedKmH() {
		long durationMs = Duration.between(this.prev.timestamp, this.timestamp).toMillis();
		double speed = (distanceM / durationMs) * 3600;
		return speed;
	}

	public LocalTime getTimestamp() {
		return this.timestamp;
	}
}
