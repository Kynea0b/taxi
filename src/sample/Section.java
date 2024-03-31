package sample;

/**
 * 総低速走行時間を計算するために
 * RecordListから初乗区間を過ぎた後のRecordを取得する時の基準となる
 * 定数を設けようとこの列挙型を作成<br>
 * ※初乗区間は定額なため、初乗区間を過ぎてから総低速走行時間を計測することになる<br>
 * ただしこれを利用した総低速走行時間の計算に失敗しているため設計の見直しをすることにした
 * @author reofujimoto
 */
public enum Section {
	INITIAL(1000);
	private final double distanceM;

	private Section(double distanceM) {
		this.distanceM = distanceM;
	}

	public double getDistanceM() {
		return this.distanceM;
	}
}
