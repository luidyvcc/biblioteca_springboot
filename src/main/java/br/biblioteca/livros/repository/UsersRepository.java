package br.biblioteca.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.biblioteca.livros.entidades.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

}
