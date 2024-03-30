package sample;
public class InitialFareCalculateService {
	private static final int STANDARD_FARE = 400;
	private static final int LATE_TIME_FARE = 600;
	private static final int PEEK_FARE = 520;

	private InitialFareCalculateService() {}


	/**
	 * 時間帯に応じて初乗料金を取得する
	 * @param record
	 * @return
	 */
	public static int getInitialFare(Record record) {
		TimeZoneType type = record.getTimeZoneType();
		switch (type) {
		case STANDARD:
			return STANDARD_FARE;
		case LATE_TIME:
			return LATE_TIME_FARE;
		case PEEK:
			return PEEK_FARE;
		default:
			throw new RuntimeException();
		}
	}

}