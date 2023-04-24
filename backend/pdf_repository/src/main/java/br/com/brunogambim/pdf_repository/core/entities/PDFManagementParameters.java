package br.com.brunogambim.pdf_repository.core.entities;

public class PDFManagementParameters {
	private int maxSize;
	private int minBigFileSize;
	private int bigFilePrice;
	private int smallFilePrice;

	public PDFManagementParameters(int maxSize, int minBigFileSize, int bigFilePrice, int smallFilePrice) {
		super();
		this.maxSize = maxSize;
		this.minBigFileSize = minBigFileSize;
		this.bigFilePrice = bigFilePrice;
		this.smallFilePrice = smallFilePrice;
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
}
