package sample;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;

public class Main {
	public static void main(String[] args) {
		RecordList list = null;
		double fare = 0;
		double initialFare = 0;
		double distanceFare = 0;
		double lowSpeedTravelTimeFare = 0;


		File file = new File("testData/深夜帯.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			// 初乗料金計算
			String firstContent = br.readLine();
			LocalTime timestamp = LocalTime.parse(firstContent.split(" ")[0]);
			double distanceM = Double.parseDouble(firstContent.split(" ")[1]);
			Record firstRecord = new Record(timestamp, distanceM);
			list = new RecordList(firstRecord);

			initialFare = InitialFareCalculateService.getFare(list);
			fare += initialFare;


			String content;
			while((content=br.readLine()) != null) {
				timestamp = LocalTime.parse(content.split(" ")[0]);
				distanceM = Double.parseDouble(content.split(" ")[1]);
				Record currentRecord = new Record(timestamp, distanceM);
				list.add(currentRecord);
				//距離運賃計算
				distanceFare = DistanceFareCalculateService.getFare(list);
				fare += distanceFare;

				//低速走行時間運賃計算
				lowSpeedTravelTimeFare = SlowSpeedDrivingFareCalculationService.getFare(list);
				fare += lowSpeedTravelTimeFare;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("運賃：¥" + fare + "-");
	}
}
