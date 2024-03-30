package sample;

public class SlowTravelFareCulculateService {
	private static final int BASE_FARE = 40;
	private static final double STANDARD_FARE_RATE = 1.0;
	private static final double LATE_TIME_FARE_RATE = 1.5;
	private static final double PEEK_FARE_RATE = 1.3;

	/**
	 * 低速走行時間運賃を取得する
	 * 低速走行時間に関するタクシーの運賃計算は、低速走行時間区間制に基づいて行われる。
	 * つまり、総低速走行時間を低速走行時間区間ごとに区切って、その低速走行時間区間をまたぐごとに運賃が加算される
	 * @param list
	 * @return
	 */
	public static double getSlowTravelFare(RecordList list) {
		if(list.getTotalDistance() <= 1000) return 0;

		if(isExceededOfLowSpeedDrivingSection(list)) {
			return BASE_FARE * getWarimashiRate(list.getLatestRecord());
		}
		return 0;
	}

	/**
	 * 時間帯に応じて割増率を取得する
	 * @param record
	 * @return
	 */
	public static double getWarimashiRate(Record record) {
		TimeZoneType type = record.getTimeZoneType();
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

	/**
	 * 低速走行時間区間をまたいだかを判断する
	 * 低速走行時間区間は、低速走行時間メーターが45i秒以上45(i+1)秒未満の低速走行時間区間が一種類のみ存在する(iは0以上の整数)
	 * @param list
	 * @return
	 */
	private static boolean isExceededOfLowSpeedDrivingSection(RecordList list) {
		long totalLowSpeedDrivingSeconds = list.getTotalLowSpeedDrivingSeconds();
		if(totalLowSpeedDrivingSeconds == 0) return false;
		return totalLowSpeedDrivingSeconds > 45 &&  totalLowSpeedDrivingSeconds % 45 <= list.getLatestLowSpeedDrivingSeconds();
	}

}
