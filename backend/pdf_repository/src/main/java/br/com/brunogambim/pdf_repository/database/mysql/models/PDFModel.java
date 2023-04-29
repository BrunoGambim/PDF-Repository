package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;

@Entity(name = "pdfs")
public class PDFModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private int size;
	private byte[] data;
	private int status;
	private LocalDateTime createdAt;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapsId("pdfId")
	@JoinColumn(name = "pdf_id")
	private List<EvaluationModel> evaluations  = new ArrayList<EvaluationModel>();
	
	public PDFModel() {
	}
	
	public PDFModel(PDF pdf) {
		this(pdf.getId(), pdf.getName(), pdf.getDescription(), pdf.getSize(), pdf.getData(),
				EvaluationModel.evaluationCollectionToEvaluationModelList(pdf.getEvaluations().values(), pdf.getId()), 
				pdf.getStatus().getCode(),
				pdf.getCreatedAt());
	}
	
	public PDFModel(Long id, String name, String description, int size, byte[] data,
			List<EvaluationModel> evaluations, int status, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.size = size;
		this.data = data;
		this.evaluations = evaluations;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	public PDF toPDF(PDFSizePolicy pdfSizePolicy) {
		return new PDF(id, name, description, PDF.PDF_FORMAT, size, data, pdfSizePolicy);
	}
	
	public static List<PDF> pdfModelListToPDFList(List<PDFModel> pdfModelList, PDFSizePolicy pdfSizePolicy) {
		return pdfModelList.stream().map(pdfModel -> pdfModel.toPDF(pdfSizePolicy)).toList();
	}
	
	public static List<PDFModel> pdfListToPDFModelList(List<PDF> pdfList){
		return pdfList.stream().map(pdf -> new PDFModel(pdf)).toList();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public List<EvaluationModel> getEvaluations() {
		return evaluations;
	}
	
	public void setEvaluations(List<EvaluationModel> evaluations) {
		this.evaluations = evaluations;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
