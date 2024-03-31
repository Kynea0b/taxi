package sample;

public class InitialFareCalculateService {
	private static final int STANDARD_FARE = 400;
	private static final int LATE_TIME_FARE = 600;
	private static final int PEEK_FARE = 520;

	private InitialFareCalculateService() {}

	public static int getFare(RecordList list) {
		// TODO 例外処理：レコードが複数あればそれは初乗り運賃計算すべきではないもの

		Record record = list.getCurrent();
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