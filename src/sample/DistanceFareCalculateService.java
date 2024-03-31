package sample;

public class DistanceFareCalculateService {
	// 基本料金
	private static final int BASE_FARE = 40;
	// 割増率
	//   通常時間帯
	private static final double STANDARD_FARE_RATE = 1.0;
	//   ピーク時間帯
	private static final double PEEK_FARE_RATE = 1.3;
	//   深夜時間帯
	private static final double LATE_TIME_FARE_RATE = 1.5;
	// TODO ここに初乗区間の値を定義すると初乗区間影響範囲がわからなくなる
	// 初乗区間
	private static final int INITIAL_SECTOIN = 1000;
	// 課金区間単位
	//   短距離区間単位
	private static final int LONG_TRAVEL_SECTOIN_INTERVAL = 400;
	//   長距離区間単位
	private static final int SHORT_TRAVEL_SECTION_INTERVAL= 350;

	/**
	 * 距離運賃を取得する
	 * 走行距離に関するタクシーの運賃計算は、距離区間制に基づいて行われる。
	 * つまり、総走行距離を距離区間ごとに区切って、その距離区間をまたぐごとに運賃が加算される
	 * @param list
	 * @return
	 */
	public static double getFare(RecordList list) {
		double totalDistanceM = list.getTotalTraveledDistanceM();
		if(totalDistanceM <= INITIAL_SECTOIN) {
			return 0;
		}

		double traveledDistanceM = list.getTraveledDistanceM();
		if(isCrossingInterval(totalDistanceM, traveledDistanceM)) {
			TimeZoneType type = list.getCurrentTimeZoneType();
			return BASE_FARE * getFareRate(type);
		}
		return 0;
	}

	/**
	 * 各距離区間をまたいだかを判断する
	 * 短距離区間：距離メーターが1000+400i mを超え、1000+400(i+1) m以下の距離区間(ただし、iは0以上22以下の整数)
	 * 長距離区間：距離メーターが10200+350i mを超え、10200+350(i+1) m以下の距離区間(ただし、iは0以上の整数)
	 * @param list
	 * @return
	 */
	private static boolean isCrossingInterval(double totalDistanceM, double traveledDistanceM) {
		if((1000 + SHORT_TRAVEL_SECTION_INTERVAL) < totalDistanceM && totalDistanceM <= 10200) {
			return totalDistanceM % SHORT_TRAVEL_SECTION_INTERVAL <= traveledDistanceM;
		} else if(10200 + LONG_TRAVEL_SECTOIN_INTERVAL < totalDistanceM) {
			return totalDistanceM % LONG_TRAVEL_SECTOIN_INTERVAL <= traveledDistanceM;
		}
		return false;
	}

	/**
	 * 時間帯に応じて割増率を取得する
	 * @param record
	 * @return
	 */
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
