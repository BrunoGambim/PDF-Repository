package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

public enum PDFStatus {
	WAITING_FOR_ADMIN_VALIDATION(1, "WAITING_FOR_ADMIN_VALIDATION"),
	VALIDATED(2, "VALIDATED"),
	REPORTED(3, "REPORTED");

	private int code;
	private String description;
	
	private PDFStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static PDFStatus toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		return PDFStatus.toEnum((int) code);
	}
	
	public static PDFStatus toEnum(int code) {
		for(PDFStatus pdfStatus : PDFStatus.values()) {
			if(code == pdfStatus.getCode()) {
				return pdfStatus;
			}
		}
		throw new IllegalArgumentException("Invalid argument! Id:" + code);
	}
	
	public static PDFStatus toEnum(String desc) {
		for(PDFStatus pdfStatus : PDFStatus.values()) {
			if(desc.equals(pdfStatus.getDescription())) {
				return pdfStatus;
			}
		}
		throw new IllegalArgumentException("Invalid argument! Id:" + desc);
	}
}
