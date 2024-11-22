package sample;

import java.time.Duration;
import java.time.LocalTime;

public class Record {
	private double distanceM;
	private LocalTime timestamp;
	private Record prev;
	private Record next;

	public Record(LocalTime timestamp, double distanceM) {
		this.timestamp = timestamp;
		this.distanceM = distanceM;
	}

	public double getDistanceM() {
		return this.distanceM;
	}

	public void add(Record record) {
		if (this.next != null) {
			throw new RuntimeException("this.next != null");
		} else {
			this.next = record;
			record.prev = this;
		}
	}

	public double getSpeedKmH() {
		// 前回レコードと今回レコードの秒速を求める
		long durationMs = Duration.between(this.prev.timestamp, this.timestamp).toMillis();
		// 時速に変換
		double speed = (distanceM / durationMs) * 3600;
		return speed;
	}

	public long getDrivingSeconds() {
		long durationS = Duration.between(this.prev.timestamp, this.timestamp).toSeconds();
		return durationS;
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

	public Record next() {
		return this.next;
	}

}
