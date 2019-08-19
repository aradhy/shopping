package shop.util;

public enum WeightGreaterRange {

	RANGE1(1, 2), RANGE2(2, 5), RANGE3(5, 10), RANGE4(10, 20);

	private final Integer max;
	private final Integer min;

	private WeightGreaterRange(Integer min, Integer max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @return the min
	 */
	public Integer getMin() {
		return min;
	}
}
