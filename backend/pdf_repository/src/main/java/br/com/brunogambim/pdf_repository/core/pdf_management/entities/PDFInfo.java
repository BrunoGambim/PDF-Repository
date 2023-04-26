package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

public class PDFInfo {
	private Long id;
	private String Name;
	private String description;
	private int size;
	private double evaluationMean;
	private int price;
	
	public PDFInfo(Long id, String name, String description, int size, double evaluationMean, int price) {
		super();
		this.id = id;
		Name = name;
		this.description = description;
		this.size = size;
		this.evaluationMean = evaluationMean;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return Name;
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
