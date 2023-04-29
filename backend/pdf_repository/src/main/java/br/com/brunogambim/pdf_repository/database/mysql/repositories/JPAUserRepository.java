package br.com.brunogambim.pdf_repository.database.mysql.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brunogambim.pdf_repository.database.mysql.models.UserModel;

@Repository
public interface JPAUserRepository extends JpaRepository<UserModel, Long> {
	public Optional<UserModel> findByEmail(String email);
}
