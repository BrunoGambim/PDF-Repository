package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import jakarta.persistence.Entity;

@Entity(name = "clients")
public class ClientModel extends UserModel{
	int balance;
	
	public ClientModel() {
	}
	
	public ClientModel(Client client) {
		this(client.getId(), client.getUsername(), client.getPassword(), client.getEmail(),
				new UpdatePasswordCodeModel(client.getPasswordUpdateCode()),
				client.getBalance());
	}
	
	public ClientModel(Long id, String username, String password, String email, UpdatePasswordCodeModel code,
			int balance) {
		super(id, username, password, email, code);
		this.balance = balance;
	}
	
	public static Map<Long, Client> modelListToEntityMap(List<ClientModel> modelList){
		Map<Long, Client> map = new HashMap<Long, Client>();
		modelList.stream().forEach(model -> {
			map.put(model.getId(), model.toEntity());
		});
		return map;
	}
	
	public static List<ClientModel>  modelListFromEntityList(Map<Long, Client> entityList){
		return new ArrayList<ClientModel>(entityList.values().stream().map(entity -> new ClientModel(entity)).toList());
	}
	
	public Client toEntity() {
		Client client = new Client(getId(), getUsername(), getPassword(), getEmail(),
				balance);
		if(this.getCode() != null) {
			client.setPasswordUpdateCode(this.getCode().toEntity());
		}
		return client;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
