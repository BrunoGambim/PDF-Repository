package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEmptyOrNullFileFieldException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;

public class PDF {
	private static final String PDF_FORMAT = "pdf";
	
	private Long id;
	private String name;
	private String description;
	private int size;
	private byte[] data;
	private PDFStatus status;
	
	public PDF(Long id, String name, String description, String format, int size, byte[] data, PDFSizePolicy pdfSizePolicy) {
		this.setId(id);
		this.setStatus(PDFStatus.WAITING_FOR_ADMIN_VALIDATION);
		this.setName(name);
		this.setData(data, pdfSizePolicy);
		this.setDescription(description);
		validateFormat(format);
		validateDataSize(size);
	}
	
	public PDF(String name, String description, String format, int size, byte[] data, PDFSizePolicy pdfSizePolicy) {
		this(null, name, description, format, size, data, pdfSizePolicy);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() == this.getClass()) {
			PDF pdf = (PDF) obj;
			if(this.getId()  != null && (this.getId().equals(pdf.getId())) || this.getName().equals(pdf.getName())) {
				return true;
			}
		}
		return false;
	}
}
