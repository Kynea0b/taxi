package sample;

public class SlowSpeedDrivingFareCalculationService {
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

	/**
	 * 低速走行時間運賃を取得する
	 * 低速走行時間に関するタクシーの運賃計算は、低速走行時間区間制に基づいて行われる。
	 * つまり、総低速走行時間を低速走行時間区間ごとに区切って、その低速走行時間区間をまたぐごとに運賃が加算される
	 * @param list
	 * @return
	 */
	public static double getFare(RecordList list) {
		double totalDistanceM = list.getTotalTraveledDistanceM();
		if(totalDistanceM <= INITIAL_SECTOIN) {
			return 0;
		}

		int totalSlowSpeedTravelSeconds = getTotalSlowSpeedTravelSeconds(list);
		int passedTime = 0;
		if(isCrossingTimeInterval(totalSlowSpeedTravelSeconds, passedTime)) {
			TimeZoneType type = list.getCurrentTimeZoneType();
			return BASE_FARE * getFareRate(type);
		}
		return 0;
	}

	/**
	 * 低速走行時間区間をまたいだかを判断する
	 * 低速走行時間区間は、低速走行時間メーターが45i秒以上45(i+1)秒未満の低速走行時間区間が一種類のみ存在する(iは0以上の整数)
	 * @param list
	 * @return
	 */
	private static boolean isCrossingTimeInterval(int totalSlowSpeedTravelSeconds, int passedTime) {
		// TODO おそらくロジックが間違っている、、、
		return totalSlowSpeedTravelSeconds % TIME_INTERVAL <= passedTime;
	}

	// TODO 実装途中で断念。設計の見直しをすることにした
    //     ここで毎回計算させるのは無駄な処理になる、、、
	//      └総低速走行時間を属性にもつ？
	//      └このクラスをServiceとして扱うのは間違っている？
	//      └初乗区間以降の総低速走行時間を求めるのが難しい、、、
	private static int getTotalSlowSpeedTravelSeconds(RecordList list) {
		int totalSlowSpeedTravelSeconds = 0;
		Record record = list.getRecordAtSection(Section.INITIAL);
		return 0;
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
