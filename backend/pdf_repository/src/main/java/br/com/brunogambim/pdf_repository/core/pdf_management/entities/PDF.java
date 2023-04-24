package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEmptyOrNullFileFieldException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;

public class PDF {
	private static final String PDF_FORMAT = "pdf";
	private String name;
	private int size;
	private byte[] data;
	
	public PDF(String name, String format, int size, byte[] data) {
		this.setName(name);
		this.setData(data);
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

	public void setData(byte[] data) {
		if(data == null) {
			throw new InvalidEmptyOrNullFileFieldException("data");
		}
		this.data = data;
		this.size = data.length;
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
