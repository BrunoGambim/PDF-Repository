package br.com.brunogambim.pdf_repository.database.mysql.models;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "pdf_management_parameters")
public class PDFManagementParametersModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int maxSize;
	private int minBigFileSize;
	private int bigFilePrice;
	private int smallFilePrice;
	private int highEvaluationMeanBonus;
	private int minEvaluationNumberToBonus;
	private double minHighEvaluationMean;

	public PDFManagementParametersModel() {
	}
	
	public PDFManagementParametersModel(int maxSize, int minBigFileSize, int bigFilePrice, int smallFilePrice,
			int highEvaluationMeanBonus, int minEvaluationNumberToBonus, double minHighEvaluationMean) {
		this.maxSize = maxSize;
		this.minBigFileSize = minBigFileSize;
		this.bigFilePrice = bigFilePrice;
		this.smallFilePrice = smallFilePrice;
		this.highEvaluationMeanBonus = highEvaluationMeanBonus;
		this.minEvaluationNumberToBonus = minEvaluationNumberToBonus;
		this.minHighEvaluationMean = minHighEvaluationMean;
	}
	
	public PDFManagementParameters toPDFManagementParameters() {
		return new PDFManagementParameters(maxSize, minBigFileSize, bigFilePrice, smallFilePrice,
				highEvaluationMeanBonus, minEvaluationNumberToBonus, minHighEvaluationMean);
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setMinBigFileSize(int minBigFileSize) {
		this.minBigFileSize = minBigFileSize;
	}

	public void setBigFilePrice(int bigFilePrice) {
		this.bigFilePrice = bigFilePrice;
	}

	public void setSmallFilePrice(int smallFilePrice) {
		this.smallFilePrice = smallFilePrice;
	}

	public void setHighEvaluationMeanBonus(int highEvaluationMeanBonus) {
		this.highEvaluationMeanBonus = highEvaluationMeanBonus;
	}

	public void setMinEvaluationNumberToBonus(int minEvaluationNumberToBonus) {
		this.minEvaluationNumberToBonus = minEvaluationNumberToBonus;
	}

	public void setMinHighEvaluationMean(double minHighEvaluationMean) {
		this.minHighEvaluationMean = minHighEvaluationMean;
	}
}
