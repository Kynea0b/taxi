package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import sample.DistanceMeter;
import sample.Record;

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
class DistanceMeterTest {

	/**
	 * 総走行距離が正しく記録されていくことを確認するテスト
	 */
	@Test
	void test_records() {
		String strRecord1 = "00:00:01.000 1.0";
		String strRecord2 = "00:00:02.000 2.0";
		String strRecord3 = "00:00:03.000 3.0";
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
		dMeter.records(record1);
		assertEquals(dMeter.getTotalDistanceM(), 1.0, 0.0);
		dMeter.records(record2);
		assertEquals(dMeter.getTotalDistanceM(), 3.0, 0.0);
		dMeter.records(record3);
		assertEquals(dMeter.getTotalDistanceM(), 6.0, 0.0);
	}

}
