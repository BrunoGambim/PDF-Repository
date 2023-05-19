package br.com.brunogambim.pdf_repository.database;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Admin;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.entities.User;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.database.exceptions.ObjectNotFoundException;
import br.com.brunogambim.pdf_repository.database.mysql.models.AdminModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.ClientModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.UserModel;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAUserRepository;

@Service
public class UserRepositoryImpl implements UserRepository{
	private JPAUserRepository jpaUserRepository;
	
	@Autowired
	public UserRepositoryImpl(JPAUserRepository jpaUserRepository) {
		this.jpaUserRepository = jpaUserRepository;
	}
	

	@Override
	public Long save(User user) {
		UserModel userModel = null;
		if(userIsClient(user)) {
			userModel = new ClientModel((Client) user);
		}else if(userIsAdmin(user)) {
			userModel = new AdminModel((Admin) user);
		}else {
			throw new IllegalArgumentException("Unexpected user type.");
		}
		return this.jpaUserRepository.save(userModel).getId();
	}
	
	private static boolean userIsAdmin(User user) {
		return user.getClass() == Admin.class;
	}
	
	private static boolean userIsClient(User user) {
		return user.getClass() == Client.class;
	}

	@Override
	public Client findClient(Long id) {
		UserModel userModel = this.jpaUserRepository.findById(id)
				.orElseThrow(() -> {
			throw new ObjectNotFoundException();
		});
		if(userIsClient(userModel)) {
			ClientModel clientModel = (ClientModel)userModel;
			return clientModel.toEntity();
		} else {
			throw new ObjectNotFoundException();
		}
	}

	@Override
	public Client findClientByEmail(String email) {
		UserModel userModel = this.jpaUserRepository.findByEmail(email)
				.orElseThrow(() -> {
			throw new ObjectNotFoundException();
		});
		if(userIsClient(userModel)) {
			ClientModel clientModel = (ClientModel)userModel;
			return clientModel.toEntity();
		} else {
			throw new ObjectNotFoundException();
		}
	}
	
	@Override
	public User findUserByEmail(String email) {
		UserModel userModel = this.jpaUserRepository.findByEmail(email)
				.orElseThrow(() -> {
			throw new ObjectNotFoundException();
		});
		if(userIsClient(userModel)) {
			ClientModel clientModel = (ClientModel) userModel;
			return clientModel.toEntity();
		} else {
			AdminModel adminModel = (AdminModel) userModel;
			return adminModel.toEntity();
		}
	}

	@Override
	public boolean isAdmin(Long id) {
		Optional<UserModel> userModel = this.jpaUserRepository.findById(id);
		if(userModel.isEmpty()) {
			return false;
		} else {
			return userIsAdmin(userModel.get());
		}
	}
	
	@Override
	public boolean isClient(Long id) {
		Optional<UserModel> userModel = this.jpaUserRepository.findById(id);
		if(userModel.isEmpty()) {
			return false;
		} else {
			return userIsClient(userModel.get());
		}
	}
	
	private static boolean userIsAdmin(UserModel user) {
		return user.getClass() == AdminModel.class;
	}
	
	private static boolean userIsClient(UserModel user) {
		return user.getClass() == ClientModel.class;
	}


	@Override
	public boolean emailIsBeingUsed(String email) {
		return !this.jpaUserRepository.findByEmail(email).isEmpty();
	}
}
