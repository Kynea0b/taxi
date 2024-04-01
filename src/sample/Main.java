package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;

public class Main {
	public static void main(String[] args) {
		DistanceMeter dMeter = new DistanceMeter();
		SlowDrivingSecondsMeter sMeter = new SlowDrivingSecondsMeter(dMeter);
		double fare = 0;

		File file = new File("testData/深夜帯.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String content;
			while ((content = br.readLine()) != null) {

				LocalTime timestamp = LocalTime.parse(content.split(" ")[0]);
				double distanceM = Double.parseDouble(content.split(" ")[1]);
				Record currentRecord = new Record(timestamp, distanceM);

				dMeter.records(currentRecord);
				fare += dMeter.getFare();
				sMeter.records();
				fare += sMeter.getFare();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("総走行距離：" + dMeter.getTotalDistanceM() + " m");
		System.out.println("総低速走行時間：" + sMeter.getTotalSeconds() + " 秒");
		System.out.println("運賃:¥" + fare + "-");
	}
}
