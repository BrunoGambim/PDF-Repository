package br.com.brunogambim.pdf_repository.api.v1.dtos;

public class UpdatePDFFileDTO {
	String name;
	String description;
	String format;
	int size;
	byte[] data;
	
	public UpdatePDFFileDTO() {
	}

	public UpdatePDFFileDTO(String name, String description, String format, int size, byte[] data) {
		this.name = name;
		this.description = description;
		this.format = format;
		this.size = size;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
