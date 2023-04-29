package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.util.ArrayList;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity(name = "clients")
public class ClientModel extends UserModel{
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	private List<PDFModel> ownedPDFList = new ArrayList<PDFModel>();
	@ManyToMany(fetch=FetchType.EAGER)
	private List<PDFModel> hasAccessPDFList = new ArrayList<PDFModel>();
	int balance;
	
	public ClientModel() {
	}
	
	public ClientModel(Client client) {
		this(client.getId(), client.getUsername(), client.getPassword(), client.getEmail(),
				new UpdatePasswordCodeModel(client.getUpdatePasswordCode()),
				PDFModel.pdfListToPDFModelList(client.getOwnedPDFList()),
				PDFModel.pdfListToPDFModelList(client.getHasAccessPDFList()),
				client.getBalance());
	}
	
	public ClientModel(Long id, String username, String password, String email, UpdatePasswordCodeModel code,
			List<PDFModel> ownedPDFList, List<PDFModel> hasAccessPDFList, int balance) {
		super(id, username, password, email, code);
		this.hasAccessPDFList = hasAccessPDFList;
		this.ownedPDFList = ownedPDFList;
		this.balance = balance;
	}
	
	public Client toClient(PDFSizePolicy sizePolicy) {
		List<PDF> ownedPDFList = new ArrayList<PDF>();
		List<PDF> hasAccessPDFList = new ArrayList<PDF>();
		if(!this.ownedPDFList.isEmpty()) {
			ownedPDFList = new ArrayList<PDF>(PDFModel.pdfModelListToPDFList(this.ownedPDFList,sizePolicy));
		}
		if(!this.hasAccessPDFList.isEmpty()) {
			hasAccessPDFList = new ArrayList<PDF>(PDFModel.pdfModelListToPDFList(this.hasAccessPDFList,sizePolicy));
		}
		return new Client(getId(), getUsername(), getPassword(), getEmail(),
				ownedPDFList, hasAccessPDFList, balance);
		
	}
	
	public List<PDFModel> getOwnedPDFList() {
		return ownedPDFList;
	}
	
	public void setOwnedPDFList(List<PDFModel> ownedPDFList) {
		this.ownedPDFList = ownedPDFList;
	}
	
	public List<PDFModel> getHasAccessPDFList() {
		return hasAccessPDFList;
	}
	
	public void setHasAccessPDFList(List<PDFModel> hasAccessPDFList) {
		this.hasAccessPDFList = hasAccessPDFList;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
