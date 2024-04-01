package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import sample.DistanceMeter;
import sample.Record;
import sample.SlowDrivingSecondsMeter;

/**
 * 注意点<br>
 * 総低速走行時間は初乗区間を超えたところから計測されるために
 * テスト内の最初のレコードのデータを"00:00:01.000 1000.0"としている<br>
 * しかしレコードのルールとして走行距離の範囲は0.0以上99.9以下のデータとなるので
 * 走行距離を値オブジェクトにするとエラーが発生する
 *
 * @author reofujimoto
 *
 */
class SlowDrivingSecondsMeterTest {

	/**
	 * 低速走行時間が記録されることのテスト<br>
	 * 低速走行をしている時間は1秒(record1, record2の間)<br>
	 * 低速走行をしていない時間は2秒(record2, record3の間)<br>
	 * よって総低速走行時間が1秒であればクリア
	 */
	@Test
	void test_records1() {
		String strRecord1 = "00:00:01.000 1000.0";
		String strRecord2 = "00:00:02.000 2.7";
		String strRecord3 = "00:00:04.000 6.0";
		LocalTime timestamp1 = LocalTime.parse(strRecord1.split(" ")[0]);
		double distanceM1 = Double.parseDouble(strRecord1.split(" ")[1]);
		LocalTime timestamp2 = LocalTime.parse(strRecord2.split(" ")[0]);
		double distanceM2 = Double.parseDouble(strRecord2.split(" ")[1]);
		LocalTime timestamp3 = LocalTime.parse(strRecord3.split(" ")[0]);
		double distanceM3 = Double.parseDouble(strRecord3.split(" ")[1]);

		Record record1 = new Record(timestamp1, distanceM1);
		Record record2 = new Record(timestamp2, distanceM2);
		Record record3 = new Record(timestamp3, distanceM3);

		DistanceMeter dMeter = new DistanceMeter();
		SlowDrivingSecondsMeter sMeter = new SlowDrivingSecondsMeter(dMeter);
		dMeter.records(record1);
		sMeter.records();
		dMeter.records(record2);
		sMeter.records();
		dMeter.records(record3);
		sMeter.records();
		double totalSlowSpeedDrivingSeconds = sMeter.getTotalSeconds();
		assertEquals(totalSlowSpeedDrivingSeconds, 1.0, 0.0);
	}

	/**
	 * 初乗区間をまたぐ時のテスト(低速走行でまたぐ場合)
	 * 低速走行をしている時間は10秒(record1, record2の間)<br>
	 * 低速走行をしていない時間は1秒(record2, record3の間)<br>
	 * よって総低速走行時間が10秒であればクリア<br>
	 * ※record1, record2の間は20秒経過しているが、最初の10秒は初乗区間を走行中なので計測しない
	 */
	@Test
	void test_records2() {
		String strRecord1 = "00:00:01.000 980.0";
		String strRecord2 = "00:00:21.000 40.0";
		String strRecord3 = "00:00:22.000 10.0";
		LocalTime timestamp1 = LocalTime.parse(strRecord1.split(" ")[0]);
		double distanceM1 = Double.parseDouble(strRecord1.split(" ")[1]);
		LocalTime timestamp2 = LocalTime.parse(strRecord2.split(" ")[0]);
		double distanceM2 = Double.parseDouble(strRecord2.split(" ")[1]);
		LocalTime timestamp3 = LocalTime.parse(strRecord3.split(" ")[0]);
		double distanceM3 = Double.parseDouble(strRecord3.split(" ")[1]);

		Record record1 = new Record(timestamp1, distanceM1);
		Record record2 = new Record(timestamp2, distanceM2);
		Record record3 = new Record(timestamp3, distanceM3);

		DistanceMeter dMeter = new DistanceMeter();
		SlowDrivingSecondsMeter sMeter = new SlowDrivingSecondsMeter(dMeter);
		dMeter.records(record1);
		sMeter.records();
		dMeter.records(record2);
		sMeter.records();
		dMeter.records(record3);
		sMeter.records();
		double totalSlowSpeedDrivingSeconds = sMeter.getTotalSeconds();
		assertEquals(totalSlowSpeedDrivingSeconds, 10.0, 0.0);
	}

	/**
	 * 初乗区間をまたぐ時のテスト(低速走行ではない速度でまたぐ場合)
	 * 低速走行をしている時間は2秒(record2, record3の間)<br>
	 * 低速走行をしていない時間は1秒(record1, record2の間)<br>
	 * よって総低速走行時間が2秒であればクリア<br>
	 */
	@Test
	void test_records3() {
		String strRecord1 = "00:00:01.000 920.0";
		String strRecord2 = "00:00:02.000 90.0";
		String strRecord3 = "00:00:04.000 5.4";
		LocalTime timestamp1 = LocalTime.parse(strRecord1.split(" ")[0]);
		double distanceM1 = Double.parseDouble(strRecord1.split(" ")[1]);
		LocalTime timestamp2 = LocalTime.parse(strRecord2.split(" ")[0]);
		double distanceM2 = Double.parseDouble(strRecord2.split(" ")[1]);
		LocalTime timestamp3 = LocalTime.parse(strRecord3.split(" ")[0]);
		double distanceM3 = Double.parseDouble(strRecord3.split(" ")[1]);

		Record record1 = new Record(timestamp1, distanceM1);
		Record record2 = new Record(timestamp2, distanceM2);
		Record record3 = new Record(timestamp3, distanceM3);

		DistanceMeter dMeter = new DistanceMeter();
		SlowDrivingSecondsMeter sMeter = new SlowDrivingSecondsMeter(dMeter);
		dMeter.records(record1);
		sMeter.records();
		dMeter.records(record2);
		sMeter.records();
		dMeter.records(record3);
		sMeter.records();
		double totalSlowSpeedDrivingSeconds = sMeter.getTotalSeconds();
		assertEquals(totalSlowSpeedDrivingSeconds, 2.0, 0.0);
	}

}
