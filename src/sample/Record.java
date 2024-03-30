package sample;
import java.time.Duration;
import java.time.LocalTime;

public class Record{
	private final LocalTime timestamp;
	private final double distanceM;
	private Record prev;
	private Record next;

	public Record(LocalTime timestamp, double distanceM) {
		this.timestamp = timestamp;
		this.distanceM = distanceM;
		this.prev = null;
		this.next = null;
	}

	public void setNext(Record record) {
		this.next = record;
	}

	public void setPrev(Record record) {
		this.prev = record;
	}

	public Record getPrev() {
		return this.prev;
	}

	public double getSpeedKmH() {
		if(this.prev == null) {
			return 0;
		}
		long duration = Duration.between(this.prev.timestamp, this.timestamp).toMillis();
		double speed = (distanceM / duration) * 3600;
		return speed;
	}

	public long getDurationMs() {
		return Duration.between(this.prev.timestamp, this.timestamp).toMillis();
	}

	public double getDistanceM() {
		return this.distanceM;
	}

	public String getTimestamp() {
		return this.timestamp.toString();
	}

	public boolean hasNext() {
		return next != null;
	}

	public Record next() {
		return this.next;
	}

	public TimeZoneType getTimeZoneType() {
		if (this.timestamp.isAfter(LocalTime.of(9, 59, 59, 999))
				&& this.timestamp.isBefore(LocalTime.of(20, 0, 0, 0))) {
			// 通常
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

		@Override
		public String toString() {
			return "Record [timestamp=" + timestamp + ", distanceM=" + distanceM + "]";
		}
}
