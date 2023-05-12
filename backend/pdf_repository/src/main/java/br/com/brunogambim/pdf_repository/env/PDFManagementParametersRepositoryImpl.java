package br.com.brunogambim.pdf_repository.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;

@Service
public class PDFManagementParametersRepositoryImpl implements PDFManagementParametersRepository{
	@Value("${pdf.parameters.maxSize}")
	private int maxSize;
	@Value("${pdf.parameters.minBigFileSize}")
	private int minBigFileSize;
	@Value("${pdf.parameters.bigFilePrice}")
	private int bigFilePrice;
	@Value("${pdf.parameters.smallFilePrice}")
	private int smallFilePrice;
	@Value("${pdf.parameters.highEvaluationMeanBonus}")
	private int highEvaluationMeanBonus;
	@Value("${pdf.parameters.minEvaluationNumberToBonus}")
	private int minEvaluationNumberToBonus;
	@Value("${pdf.parameters.minHighEvaluationMean}")
	private double minHighEvaluationMean;
	
	public PDFManagementParametersRepositoryImpl() {
		
	}

	@Override
	public PDFManagementParameters findParameters() {
		return new PDFManagementParameters(maxSize, minBigFileSize, bigFilePrice, smallFilePrice,
				highEvaluationMeanBonus, minEvaluationNumberToBonus, minHighEvaluationMean);
	}

}
