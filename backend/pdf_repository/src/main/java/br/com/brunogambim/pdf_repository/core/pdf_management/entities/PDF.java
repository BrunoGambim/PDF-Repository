package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEmptyOrNullFileFieldException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;

public class PDF {
	private static final String PDF_FORMAT = "pdf";
	private String name;
	private int size;
	private byte[] data;
	private PDFStatus status;
	
	public PDF(String name, String format, int size, byte[] data, PDFSizePolicy pdfSizePolicy) {
		status = PDFStatus.WAITING_FOR_ADMIN_VALIDATION;
		this.setName(name);
		this.setData(data, pdfSizePolicy);
		validateFormat(format);
		validateDataSize(size);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name.equals("")) {
			throw new InvalidEmptyOrNullFileFieldException("name");
		}
		
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public byte[] getData() {
		return data;
	}

	public PDFStatus getStatus() {
		return status;
	}

	public void setStatus(PDFStatus status) {
		this.status = status;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setData(byte[] data, PDFSizePolicy pdfSizePolicy) {
		if(data == null) {
			throw new InvalidEmptyOrNullFileFieldException("data");
		}
		this.data = data;
		this.size = data.length;
		pdfSizePolicy.execute(this);
	}
	
	private void validateDataSize(int size) {
		if(this.getSize() != size) {
			throw new InvalidFileDataSizeException();
		}
	}
	
	private void validateFormat(String format) {
		if(!format.equals(PDF_FORMAT)) {
			throw new InvalidFileFormatException(format);
		}
	}
}
