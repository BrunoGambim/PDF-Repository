package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;

public class PDFInfo {
	private Long id;
	private String name;
	private String description;
	private String ownersName;
	private int size;
	private double evaluationMean;
	private int numberOfEvaluations;
	private int price;
	private PDFStatus status;
	private List<String> canBeAccessedBy;
	private byte[] data;
	
	public PDFInfo(Long id, String name, String description, int size, double evaluationMean,
			int numberOfEvaluations, int price, String ownersName, PDFStatus status, List<Client> canBeAccessedBy) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.size = size;
		this.evaluationMean = evaluationMean;
		this.price = price;
		this.numberOfEvaluations = numberOfEvaluations;
		this.ownersName = ownersName;
		this.status = status;
		this.canBeAccessedBy = canBeAccessedBy.stream().map(client -> client.getEmail()).toList();
	}

	public PDFInfo(Long id, String name, String description, int size, double evaluationMean,
			int numberOfEvaluations, int price, byte[] data, String ownersName, PDFStatus status, List<Client> canBeAccessedBy) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.size = size;
		this.evaluationMean = evaluationMean;
		this.price = price;
		this.data = data;
		this.numberOfEvaluations = numberOfEvaluations;
		this.ownersName = ownersName;
		this.status = status;
		this.canBeAccessedBy = canBeAccessedBy.stream().map(client -> client.getEmail()).toList();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return description;
	}

	public int getSize() {
		return size;
	}

	public double getEvaluationMean() {
		return evaluationMean;
	}

	public int getPrice() {
		return price;
	}

	public byte[] getData() {
		return data;
	}

	public int getNumberOfEvaluations() {
		return numberOfEvaluations;
	}

	public String getOwnersName() {
		return ownersName;
	}
	
	public String getStatus() {
		return status.getDescription();
	}

	public List<String> getCanBeAccessedBy() {
		return canBeAccessedBy;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() == this.getClass()) {
			PDFInfo pdf = (PDFInfo) obj;
			if(this.getId().equals(pdf.getId()) || this.getName().equals(pdf.getName())
					|| this.getDescription().equals(pdf.getDescription()) || this.getSize() == pdf.getSize()
					|| this.getEvaluationMean() == pdf.getEvaluationMean() || this.getPrice() == pdf.getPrice()) {
				return true;
			}
		}
		return false;
	}
}
