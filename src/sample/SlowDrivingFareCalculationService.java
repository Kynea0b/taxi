package sample;

public class SlowDrivingFareCalculationService {
	// 基本料金
	private static final int BASE_FARE = 40;
	// 割増率
	//   通常時間帯
	private static final double STANDARD_FARE_RATE = 1.0;
	//   ピーク時間帯
	private static final double PEEK_FARE_RATE = 1.3;
	//   深夜時間帯
	private static final double LATE_TIME_FARE_RATE = 1.5;
	// TODO ここに初乗区間の値を定義すると影響範囲がわからなくなる
	// 初乗区間
	private static final int INITIAL_SECTOIN = 1000;

	private static final int TIME_INTERVAL = 45;

	private SlowDrivingFareCalculationService() {}

	public static double calculateFare(long totalSeconds, double totalDistanceM, Record record) {
		if(totalDistanceM <= INITIAL_SECTOIN) {
			return 0;
		}

		long totalSlowSpeedTravelSeconds = totalSeconds;
		long passedTime = record.getDrivingSeconds();
		if(isCrossingTimeInterval(totalSlowSpeedTravelSeconds, passedTime)) {
			TimeZoneType type = record.getTimeZoneType();
			return BASE_FARE * getFareRate(type);
		}
		return 0;
	}

	private static boolean isCrossingTimeInterval(long totalSlowSpeedTravelSeconds, long passedTime) {
		return totalSlowSpeedTravelSeconds > TIME_INTERVAL &&  totalSlowSpeedTravelSeconds % 45 <= passedTime;
	}

	private static double getFareRate(TimeZoneType type) {
		switch (type) {
		case STANDARD:
			return STANDARD_FARE_RATE;
		case LATE_TIME:
			return LATE_TIME_FARE_RATE;
		case PEEK:
			return PEEK_FARE_RATE;
		default:
			throw new RuntimeException();
		}
	}


}
