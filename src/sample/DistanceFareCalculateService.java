package sample;

public class DistanceFareCalculateService {
	private static final int BASE_FARE = 40;
	private static final double STANDARD_FARE_RATE = 1.0;
	private static final double LATE_TIME_FARE_RATE = 1.5;
	private static final double PEEK_FARE_RATE = 1.3;

	/**
	 * 距離運賃を取得する
	 * 走行距離に関するタクシーの運賃計算は、距離区間制に基づいて行われる。
	 * つまり、総走行距離を距離区間ごとに区切って、その距離区間をまたぐごとに運賃が加算される
	 * @param list
	 * @return
	 */
	public static double getDistanceFare(RecordList list) {
		double totalDistance = list.getTotalDistance();
		if(totalDistance < 1000) return 0;
		if(totalDistance <= 10200) {
			if(isExceededOfShortDistanceSection(list)) {
				return BASE_FARE * getWarimashiRate(list.getLatestRecord());
			}
		} else if (totalDistance > 10200) {
			if(isExceededOfLongDistanceSection(list)) {
				return BASE_FARE * getWarimashiRate(list.getLatestRecord());
			}
		}
		return 0;
	}

	/**
	 * 短距離区間をまたいだかを判断する
	 * 短距離区間：距離メーターが1000+400i mを超え、1000+400(i+1) m以下の距離区間(ただし、iは0以上22以下の整数)
	 * @param list
	 * @return
	 */
	private static boolean isExceededOfShortDistanceSection(RecordList list) {
		double totalDistance = list.getTotalDistance();
		// 最新の走行距離によって400mの区切りを超えていたらtrue
		// (1000 + 400i)mの区切りを超えると距離運賃が発生する
		return (totalDistance - 1000) > 400 && (totalDistance - 1000) % 400 <= list.getLatestDistance();
	}

	/**
	 * 長距離区間をまたいだかを判断する
	 * 長距離区間：距離メーターが10200+350i mを超え、10200+350(i+1) m以下の距離区間(ただし、iは0以上の整数)
	 * @param list
	 * @return
	 */
	private static boolean isExceededOfLongDistanceSection(RecordList list) {
		double totalDistance = list.getTotalDistance();
		return (totalDistance - 1000) > 350 && (totalDistance - 1000) % 350 <= list.getLatestDistance();
	}

	/**
	 * 時間帯に応じて割増率を取得する
	 * @param record
	 * @return
	 */
	private static double getWarimashiRate(Record record) {
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
}
