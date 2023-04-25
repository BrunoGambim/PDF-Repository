package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

public class PDFManagementParameters {
	private int maxSize;
	private int minBigFileSize;
	private int bigFilePrice;
	private int smallFilePrice;
	private int highEvaluationMeanBonus;
	private int minEvaluationNumberToBonus;
	private double minHighEvaluationMean;

	public PDFManagementParameters(int maxSize, int minBigFileSize, int bigFilePrice, int smallFilePrice,
			int highEvaluationMeanBonus, int minEvaluationNumberToBonus, double minHighEvaluationMean) {
		this.maxSize = maxSize;
		this.minBigFileSize = minBigFileSize;
		this.bigFilePrice = bigFilePrice;
		this.smallFilePrice = smallFilePrice;
		this.highEvaluationMeanBonus = highEvaluationMeanBonus;
		this.minEvaluationNumberToBonus = minEvaluationNumberToBonus;
		this.minHighEvaluationMean = minHighEvaluationMean;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public int getMinBigFileSize() {
		return minBigFileSize;
	}

	public int getBigFilePrice() {
		return bigFilePrice;
	}

	public int getSmallFilePrice() {
		return smallFilePrice;
	}

	public int getHighEvaluationMeanBonus() {
		return highEvaluationMeanBonus;
	}

	public int getMinEvaluationNumberToBonus() {
		return minEvaluationNumberToBonus;
	}

	public double getMinHighEvaluationMean() {
		return minHighEvaluationMean;
	}
}
