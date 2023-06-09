package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEmptyOrNullFileFieldException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyHasAccessToPDFException;

public class PDF {
	public static final String PDF_FORMAT = "pdf";
	
	private Long id;
	private String name;
	private String description;
	private int size;
	private byte[] data;
	private Map<Long, Evaluation> evaluations;
	private PDFStatus status;
	private LocalDateTime createdAt;
	private Client owner;
	private Map<Long, Client> canBeAccessedBy;
	
	public PDF(Long id, String name, String description, String format, int size, byte[] data, PDFSizePolicy pdfSizePolicy,
			Client owner, Map<Long, Evaluation> evaluations, Map<Long, Client> canBeAccessedBy) {
		this.setId(id);
		this.setStatus(PDFStatus.WAITING_FOR_ADMIN_VALIDATION);
		this.setName(name);
		this.setData(data, pdfSizePolicy);
		this.setDescription(description);
		this.setEvaluations(evaluations);
		validateFormat(format);
		validateDataSize(size);
		this.createdAt = LocalDateTime.now();
		this.owner = owner;
		this.canBeAccessedBy = canBeAccessedBy;
	}
	
	public PDF(Long id, String name, String description, String format, int size, byte[] data, PDFSizePolicy pdfSizePolicy,
			Client owner) {
		this(id, name, description, format, size, data, pdfSizePolicy, owner, new HashMap<Long, Evaluation>(),
				new HashMap<Long, Client>());
	}
	
	public PDF(String name, String description, String format, int size, byte[] data, PDFSizePolicy pdfSizePolicy,
			Client owner) {
		this(null, name, description, format, size, data, pdfSizePolicy, owner);
	}
	
	public PDF(Long id, String name, String description, String format, int size, byte[] data, PDFStatus status, PDFSizePolicy pdfSizePolicy,
			Client owner, Map<Long, Evaluation> evaluations, Map<Long, Client> canBeAccessedBy) {
		this(id, name, description, format, size, data, pdfSizePolicy, owner, evaluations, canBeAccessedBy);
		this.status = status;
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
		if(description.equals("") || description == null) {
			throw new InvalidEmptyOrNullFileFieldException("description");
		}
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name.equals("") || name == null) {
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
		if(status == null) {
			throw new InvalidEmptyOrNullFileFieldException("status");
		}
		
		this.status = status;
	}
	
	public PDFInfo getPDFInfo(Long userId, PDFPricingPolicy pricingPolicy) {
		if(userId != null && (canBeAccessedBy(userId) || userId == owner.getId())) {
			return getPDFInfoWithData(pricingPolicy);
		} else {
			return getPDFInfoWithoutData(pricingPolicy);
		}
	}
	
	public PDFInfo getPDFInfoWithData(PDFPricingPolicy pricingPolicy) {
		PDFInfo pdfInfo = this.getPDFInfoWithoutData(pricingPolicy);
		pdfInfo.setData(this.getData());
		return pdfInfo;
	}
	
	public PDFInfo getPDFInfoWithoutData(PDFPricingPolicy pricingPolicy) {
		return new PDFInfo(id, name, description, size, this.getEvaluationMean(),
				this.evaluations.size(), pricingPolicy.execute(this), owner.getUsername(), status, owner.getEmail());
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
	
	public Map<Long, Evaluation> getEvaluations() {
		return new HashMap<Long, Evaluation>(evaluations);
	}

	public void setEvaluations(Map<Long, Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	public void addEvaluation(Client client, double value) {
		if(this.evaluations.containsKey(client.getId())) {
			this.evaluations.get(client.getId()).setValue(value);
		} else {
			this.evaluations.put(client.getId(), new Evaluation(value, client));
		}
	}
	
	public double getEvaluationMean() {
		if(evaluations.size() == 0) {
			return 0;
		}
		double evaluationSum = 0;
		for(Evaluation evaluation: this.evaluations.values()) {
			evaluationSum += evaluation.getValue(); 
		}
		return evaluationSum / evaluations.size();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Client getOwner() {
		return owner;
	}

	public Map<Long, Client> getCanBeAccessedBy() {
		return new HashMap<Long, Client>(canBeAccessedBy);
	}
	
	public void addToCanBeAccessedByList(Client client) {
		if(canBeAccessedBy(client.getId())) {
			throw new UserAlreadyHasAccessToPDFException();
		}
		this.canBeAccessedBy.put(client.getId(), client);
	}
	
	public boolean canBeAccessedBy(Long clientId) {
		return this.canBeAccessedBy.containsKey(clientId);
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
