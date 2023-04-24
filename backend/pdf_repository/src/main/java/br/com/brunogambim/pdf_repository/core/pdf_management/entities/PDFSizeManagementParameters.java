package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

public class PDFSizeManagementParameters {
	private int maxSize;
	private int minBigFileSize;
	
	public PDFSizeManagementParameters(int maxSize, int minBigFileSize) {
		this.maxSize = maxSize;
		this.minBigFileSize = minBigFileSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public int getMinBigFileSize() {
		return minBigFileSize;
	}
}
