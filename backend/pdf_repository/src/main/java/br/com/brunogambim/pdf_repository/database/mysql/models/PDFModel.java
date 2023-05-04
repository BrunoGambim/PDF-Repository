package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<ClientModel> canBeAccessedBy = new ArrayList<ClientModel>();
	
	@ManyToOne
	private ClientModel owner;

	@Lob
	@Column(length=16777215)
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
				pdf.getStatus().getCode(), new ClientModel(pdf.getOwner()),
				pdf.getCreatedAt(), ClientModel.modelListFromEntityList(pdf.getCanBeAccessedBy()));
	}
	
	public PDFModel(Long id, String name, String description, int size, byte[] data,
			List<EvaluationModel> evaluations, int status, ClientModel owner, LocalDateTime createdAt, List<ClientModel> canBeAccessedBy) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.size = size;
		this.data = data;
		this.evaluations = evaluations;
		this.status = status;
		this.createdAt = createdAt;
		this.owner = owner;
		this.canBeAccessedBy = canBeAccessedBy;
	}
	
	public PDF toEntity(PDFSizePolicy pdfSizePolicy) {
		return new PDF(id, name, description, PDF.PDF_FORMAT, size, data, PDFStatus.toEnum(status),
				pdfSizePolicy, owner.toEntity(), EvaluationModel.evaluationModelListToEvaluationMap(evaluations, pdfSizePolicy), 
				ClientModel.modelListToEntityList(canBeAccessedBy));
	}
	
	public static List<PDF> pdfModelListToEntityList(List<PDFModel> pdfModelList, PDFSizePolicy pdfSizePolicy) {
		return pdfModelList.stream().map(pdfModel -> pdfModel.toEntity(pdfSizePolicy)).toList();
	}
	
	public static List<PDFModel> pdfModelListFromEntityList(List<PDF> pdfList){
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

	public List<ClientModel> getCanBeAccessedBy() {
		return canBeAccessedBy;
	}

	public void setCanBeAccessedBy(List<ClientModel> canBeAccessedBy) {
		this.canBeAccessedBy = canBeAccessedBy;
	}

	public ClientModel getOwner() {
		return owner;
	}

	public void setOwner(ClientModel owner) {
		this.owner = owner;
	}
}
