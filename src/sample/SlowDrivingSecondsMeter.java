package sample;

public class SlowDrivingSecondsMeter {

	private long totalSeconds;
	private DistanceMeter dMeter;

	private static final int MAX_SLOW_DRIVING_SPEED_KMH = 10;

	public SlowDrivingSecondsMeter(DistanceMeter dMeter) {
		this.totalSeconds = 0;
		this.dMeter = dMeter;
	}

	public double getFare() {
		long totalSeconds = this.totalSeconds;
		double totalDistanceM = dMeter.getTotalDistanceM();
		Record current = dMeter.getCurrentRecord();
		return SlowDrivingFareCalculationService.calculateFare(totalSeconds, totalDistanceM, current);
	}

	public void records() {
		double totalDitanceM = dMeter.getTotalDistanceM();
		if (totalDitanceM <= 1000) {
			return;
		}

		if (dMeter.getCurrentSpeedKmH() <= MAX_SLOW_DRIVING_SPEED_KMH) {
			this.totalSeconds += getSlowDrivingSeconds();
		}
	}

	private double getSlowDrivingSeconds() {
		if (dMeter.getCurrentSpeedKmH() > MAX_SLOW_DRIVING_SPEED_KMH) {
			return 0;
		}

		double totalDistanceM = dMeter.getTotalDistanceM();
		long currentDrivingSeconds = dMeter.getCurrentDrivingSeconds();
		double passedDistanceM = dMeter.getCurrentDistanceM();
		if (totalDistanceM - 1000 < passedDistanceM) {
			double currentSpeedKmH = dMeter.getCurrentSpeedKmH();
			double currentSpeedMPS = currentSpeedKmH / 3.6;
			return (totalDistanceM - 1000) / currentSpeedMPS;
		} else {
			return currentDrivingSeconds;
		}
	}

	public double getTotalSeconds() {
		return this.totalSeconds;
	}
}
